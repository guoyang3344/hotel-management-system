package com.hotel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hotel.entity.DictType;

public interface DictTypeService extends IService<DictType> {
    Page<DictType> pageList(Page<DictType> page, DictType dictType);
}
