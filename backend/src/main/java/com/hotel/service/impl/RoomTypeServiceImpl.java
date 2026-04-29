package com.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.RoomType;
import com.hotel.mapper.RoomTypeMapper;
import com.hotel.service.RoomTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoomTypeServiceImpl extends ServiceImpl<RoomTypeMapper, RoomType> implements RoomTypeService {

    @Override
    public Page<RoomType> pageList(Page<RoomType> page, RoomType roomType) {
        LambdaQueryWrapper<RoomType> queryWrapper = new LambdaQueryWrapper<>();
        if (roomType != null) {
            if (StringUtils.hasText(roomType.getTypeName())) {
                queryWrapper.like(RoomType::getTypeName, roomType.getTypeName());
            }
            if (StringUtils.hasText(roomType.getStatus())) {
                queryWrapper.eq(RoomType::getStatus, roomType.getStatus());
            }
        }
        queryWrapper.orderByAsc(RoomType::getId);
        return this.page(page, queryWrapper);
    }
}
