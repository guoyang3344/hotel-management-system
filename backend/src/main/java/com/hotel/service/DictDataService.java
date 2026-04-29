package com.hotel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hotel.entity.DictData;

import java.util.List;

public interface DictDataService extends IService<DictData> {
    Page<DictData> pageList(Page<DictData> page, DictData dictData);
    List<DictData> getDictDataByType(String dictType);
}
