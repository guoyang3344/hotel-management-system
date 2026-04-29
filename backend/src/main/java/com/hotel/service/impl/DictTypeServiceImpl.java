package com.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.DictType;
import com.hotel.mapper.DictTypeMapper;
import com.hotel.service.DictTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    @Override
    public Page<DictType> pageList(Page<DictType> page, DictType dictType) {
        LambdaQueryWrapper<DictType> queryWrapper = new LambdaQueryWrapper<>();
        if (dictType != null) {
            if (StringUtils.hasText(dictType.getDictName())) {
                queryWrapper.like(DictType::getDictName, dictType.getDictName());
            }
            if (StringUtils.hasText(dictType.getDictType())) {
                queryWrapper.like(DictType::getDictType, dictType.getDictType());
            }
            if (StringUtils.hasText(dictType.getStatus())) {
                queryWrapper.eq(DictType::getStatus, dictType.getStatus());
            }
        }
        queryWrapper.orderByDesc(DictType::getCreateTime);
        return this.page(page, queryWrapper);
    }
}
