package param.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.util.BeanUtils;
import com.key.win.basic.util.IndivdualSoldierAuthConstantUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import param.dao.SysDictTreeDao;
import param.model.SysDictTree;
import param.service.SysDictTreeService;
import param.utils.ParamUtils;

import java.util.*;

@Service
public class SysDictTreeServiceImpl extends ServiceImpl<SysDictTreeDao, SysDictTree> implements SysDictTreeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public List<SysDictTree> findSysDictTree(SysDictTree sysDictTree) {
        List<SysDictTree> list = this.list(this.buildSysDictTreeLambdaQueryWrapper(sysDictTree));
        return list;
    }

    @Override
    @Cacheable(cacheNames = ParamUtils.REDIS_SYS_DICT_TREE_KEY_PREFIX, key = "#type")
    public List<SysDictTree> getDictTree(Long type) {
        SysDictTree sdt = new SysDictTree();
        sdt.setType(type);
        List<SysDictTree> list = this.findSysDictTree(sdt);
        List<SysDictTree> topList = new ArrayList<>();
        Map<Long, SysDictTree> levelAll = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SysDictTree sysDictTree : list) {
                if (IndivdualSoldierAuthConstantUtils.TREE_PARENT_ID.equals(sysDictTree.getParentId())) {
                    topList.add(sysDictTree);
                }
                levelAll.put(sysDictTree.getId(), sysDictTree);
            }
            for (Map.Entry<Long, SysDictTree> entry : levelAll.entrySet()) {
                SysDictTree value = entry.getValue();
                if (!IndivdualSoldierAuthConstantUtils.TREE_PARENT_ID.equals(value.getParentId())) {
                    Long key = value.getParentId();
                    SysDictTree parentDictTree = levelAll.get(key);
                    parentDictTree.addSubSysDictTree(value);
                }
            }
        }
        this.sysDictTreeSort(topList);
        return topList;
    }

    public void sysDictTreeSort(List<SysDictTree> sysDictTrees) {
        sysDictTrees.sort((o1, o2) -> {
            if (o1.getSort() > o2.getSort()) {
                return 1;
            } else if (o1.getSort() < o2.getSort()) {
                return -1;
            } else {
                return 0;
            }
        });
        sysDictTrees.forEach(sysDictTree -> {
            if (!CollectionUtils.isEmpty(sysDictTree.getSubDictTree())) {
                this.sysDictTreeSort(sysDictTree.getSubDictTree());
            }
        });
    }

    @Override
    public List<SysDictTree> findSysDictTreeByParentId(Long parentId) {
        if (parentId != null) {
            log.error("parent Id is null !");
            throw new BizException("父节点为空！");
        }
        SysDictTree sysDictTree = new SysDictTree();
        sysDictTree.setParentId(parentId);
        return this.findSysDictTree(sysDictTree);
    }

    @Override
    public PageResult<SysDictTree> findSysDictTreeByPaged(PageRequest<SysDictTree> t) {

        MybatisPageServiceTemplate<SysDictTree, SysDictTree> mybatiesPageServiceTemplate = new MybatisPageServiceTemplate<SysDictTree, SysDictTree>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysDictTree sysDictTree) {
                LambdaQueryWrapper<SysDictTree> lambdaQueryWrapper = buildSysDictTreeLambdaQueryWrapper(sysDictTree);
                return lambdaQueryWrapper;
            }
        };

        return mybatiesPageServiceTemplate.doPagingQuery(t);
    }

    private LambdaQueryWrapper<SysDictTree> buildSysDictTreeLambdaQueryWrapper(SysDictTree sysDictTree) {
        LambdaQueryWrapper<SysDictTree> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (sysDictTree != null) {
            if (StringUtils.isNotBlank(sysDictTree.getLabel())) {
                if (StringUtils.isNotBlank(sysDictTree.getLabel())) {
                    lambdaQueryWrapper.like(SysDictTree::getLabel, sysDictTree.getLabel());
                }
                if (sysDictTree.getType() != null) {
                    lambdaQueryWrapper.eq(SysDictTree::getType, sysDictTree.getType());
                }
                if (StringUtils.isNotBlank(sysDictTree.getValue())) {
                    lambdaQueryWrapper.eq(SysDictTree::getValue, sysDictTree.getValue());
                }
                if (sysDictTree.getParentId() != null) {
                    lambdaQueryWrapper.eq(SysDictTree::getParentId, sysDictTree.getParentId());
                }
            }
        }

        return lambdaQueryWrapper;
    }

    @Override
    public List<SysDictTree> findLeafNode(Long type) {
        return this.baseMapper.findLeafNode(type);
    }

    @Override
    @CacheEvict(cacheNames = ParamUtils.REDIS_SYS_DICT_TREE_KEY_PREFIX, key = "#sysDictTree.type")
    public boolean saveOrUpdateDictTree(SysDictTree sysDictTree) {
        SysDictTree po = null;
        if (sysDictTree.getId() != null) {
            po = this.getById(sysDictTree.getId());
            if (po == null) {
                logger.error("基础树信息不存在！");
                throw new BizException("基础树信息不存在!");
            }
            if (!po.getValue().equals(sysDictTree.getValue())) {
                logger.error("基础树信息的value[{}]已锁定，不允许修改！", sysDictTree.getValue());
                throw new BizException("基础树信息的value已锁定，不允许修改！!");
            }
            BeanUtils.copyPropertiesToPartField(sysDictTree, po);
        } else {
            po = sysDictTree;
            List<SysDictTree> sysDictTrees = this.findSysDictTreeByCode(sysDictTree);
            if (!CollectionUtils.isEmpty(sysDictTrees)) {
                logger.error("基础树信息的value[{}]已存在！", sysDictTree.getValue());
                throw new BizException("基础树信息的value已存在！");
            }
        }
        boolean b = this.saveOrUpdate(po);
        return b;
    }

    private List<SysDictTree> findSysDictTreeByCode(SysDictTree sysDictTree) {
        SysDictTree dt = new SysDictTree();
        sysDictTree.setValue(dt.getValue());
        sysDictTree.setType(dt.getType());
        return this.findSysDictTree(sysDictTree);
    }


    public SysDictTree getDictTreeFullById(Long id) {
        SysDictTree sysDictTree = this.getById(id);
        return sysDictTree;
    }

    @Override
    @CacheEvict(cacheNames = ParamUtils.REDIS_SYS_DICT_TREE_KEY_PREFIX)
    public boolean deleteById(Long id) {
        List<SysDictTree> sysDictTreeByParentId = this.findSysDictTreeByParentId(id);
        if (!CollectionUtils.isEmpty(sysDictTreeByParentId)) {
            logger.error("删除SysDictTree[{}]时,发现已经有[{}]孩子节点", id, sysDictTreeByParentId.size());
            throw new BizException("请先删除孩子节点!");
        }

        return this.removeById(id);
    }

    public List<SysDictTree> getSysDictTreeByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            logger.error("ids Collection is null or ids Collection size is 0! ");
            throw new BizException("基础树信息id为空！");
        }
        LambdaQueryWrapper<SysDictTree> lambdaQueryWrapper = new LambdaQueryWrapper<SysDictTree>();
        lambdaQueryWrapper.in(SysDictTree::getId, ids);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public PageResult<SysDictTree> findChildrenNodeByParentId(PageRequest<SysDictTree> t) {
        MybatisPageServiceTemplate<SysDictTree, SysDictTree> mybatisPageServiceTemplate = new MybatisPageServiceTemplate<SysDictTree, SysDictTree>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysDictTree sysDictTree) {
                return buildSysDictTreeLambdaQueryWrapper(sysDictTree);
            }

            @Override
            protected String constructNativeSql() {
                return "SELECT * FROM ( SELECT o.* FROM ( SELECT @pId AS pId, ( SELECT @pId := GROUP_CONCAT(id) FROM sys_sysDictTree WHERE FIND_IN_SET(parent_Id, @pId)) AS p FROM sys_sysDictTree, (SELECT @pId := #{map.pId} ) b WHERE @pId IS NOT NULL ) temp, sys_sysDictTree o WHERE FIND_IN_SET(o.parent_Id, temp.pId) AND o.enable_flag = 1) sysDictTree";
            }

            @Override
            public Map constructNativeSqlArgsToMap(SysDictTree sysDictTree) {
                Map<String, Long> map = constructMapArgs(sysDictTree);
                return map;
            }
        };
        return mybatisPageServiceTemplate.doPagingQuery(t);
    }

    private Long getGenericId(SysDictTree t) {
        Long id = IndivdualSoldierAuthConstantUtils.TREE_PARENT_ID;
        if (t != null) {
            id = t.getId();
        }
        return id;
    }

    @Override
    public PageResult<SysDictTree> findParentNodeByParentId(PageRequest<SysDictTree> t) {
        MybatisPageServiceTemplate<SysDictTree, SysDictTree> mybatisPageServiceTemplate = new MybatisPageServiceTemplate<SysDictTree, SysDictTree>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysDictTree sysDictTree) {
                return buildSysDictTreeLambdaQueryWrapper(sysDictTree);
            }

            @Override
            protected String constructNativeSql() {
                return "SELECT * FROM ( SELECT o.* FROM ( SELECT @pId AS pId, ( SELECT @pId := parent_id FROM sys_sysDictTree WHERE FIND_IN_SET(id, @pId)) AS p FROM sys_sysDictTree, ( SELECT @pId := #{map.pId}  ) b WHERE @pId IS NOT NULL ) temp, sys_sysDictTree o WHERE FIND_IN_SET(o.id, temp.pId) AND o.enable_flag = 1) sysDictTree";
            }

            @Override
            public Map constructNativeSqlArgsToMap(SysDictTree sysDictTree) {
                Map<String, Long> map = constructMapArgs(sysDictTree);
                return map;
            }
        };
        return mybatisPageServiceTemplate.doPagingQuery(t);
    }

    private Map<String, Long> constructMapArgs(SysDictTree sysDictTree) {
        Map<String, Long> map = new HashMap<>();
        map.put("pId", getGenericId(sysDictTree));
        return map;
    }
}
