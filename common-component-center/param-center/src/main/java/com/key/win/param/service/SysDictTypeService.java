package com.key.win.param.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.param.model.SysDictType;

import java.util.List;

public interface SysDictTypeService extends IService<SysDictType> {

    PageResult<SysDictType> getSysDictTypeByPaged(PageRequest<SysDictType> t);

    List<SysDictType> findSysDictType(SysDictType sysDictType);

    boolean saveOrUpdateSysDictType(SysDictType sysDictType);

    boolean deleteSysDictType(Long id);

    SysDictType getSysDictTypeById(Long id);

    SysDictType findSysDictTypeByCode(String code);

    boolean updateEnabled(Long id, Boolean status);
}
