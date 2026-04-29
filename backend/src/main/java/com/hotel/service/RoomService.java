package com.hotel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hotel.entity.Room;

public interface RoomService extends IService<Room> {
    Page<Room> pageList(Page<Room> page, Room room);
}
