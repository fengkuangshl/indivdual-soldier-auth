package com.key.win.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.basic.web.*;
import com.key.win.common.model.system.*;

import java.util.List;

public interface SysGroupService extends IService<SysGroup> {

    PageResult<SysGroup> findSysGroupByPaged(PageRequest<SysGroup> t);

    List<SysGroup> findSysGroup(SysGroup sysGroup);

    List<SysGroup> findSysGroupByParentId(Long parentId);

    List<SysGroup> getGroupTree();

    List<SysGroup> findLeafNode();

    boolean saveOrUpdateGroup(SysGroup sysGroup);

    List<SysGroup> findSysGroupByUserId(Long userId);

    boolean deleteById(Long id);

    SysGroup getSysGroupFullById(Long id);
}
