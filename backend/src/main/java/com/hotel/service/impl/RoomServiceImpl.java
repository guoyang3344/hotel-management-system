package com.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.Room;
import com.hotel.mapper.RoomMapper;
import com.hotel.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Override
    public Page<Room> pageList(Page<Room> page, Room room) {
        LambdaQueryWrapper<Room> queryWrapper = new LambdaQueryWrapper<>();
        if (room != null) {
            if (StringUtils.hasText(room.getRoomNumber())) {
                queryWrapper.like(Room::getRoomNumber, room.getRoomNumber());
            }
            if (room.getRoomTypeId() != null) {
                queryWrapper.eq(Room::getRoomTypeId, room.getRoomTypeId());
            }
            if (room.getFloor() != null) {
                queryWrapper.eq(Room::getFloor, room.getFloor());
            }
            if (StringUtils.hasText(room.getStatus())) {
                queryWrapper.eq(Room::getStatus, room.getStatus());
            }
        }
        queryWrapper.orderByAsc(Room::getFloor).orderByAsc(Room::getRoomNumber);
        return this.page(page, queryWrapper);
    }
}
