package com.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.DictData;
import com.hotel.mapper.DictDataMapper;
import com.hotel.service.DictDataService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {

    @Override
    public Page<DictData> pageList(Page<DictData> page, DictData dictData) {
        LambdaQueryWrapper<DictData> queryWrapper = new LambdaQueryWrapper<>();
        if (dictData != null) {
            if (StringUtils.hasText(dictData.getDictType())) {
                queryWrapper.eq(DictData::getDictType, dictData.getDictType());
            }
            if (StringUtils.hasText(dictData.getDictLabel())) {
                queryWrapper.like(DictData::getDictLabel, dictData.getDictLabel());
            }
            if (StringUtils.hasText(dictData.getStatus())) {
                queryWrapper.eq(DictData::getStatus, dictData.getStatus());
            }
        }
        queryWrapper.orderByAsc(DictData::getDictSort);
        return this.page(page, queryWrapper);
    }

    @Override
    public List<DictData> getDictDataByType(String dictType) {
        return baseMapper.selectDictDataByType(dictType);
    }
}
