package com.hotel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hotel.common.Result;
import com.hotel.entity.Room;
import com.hotel.entity.User;
import com.hotel.service.RoomService;
import com.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        long totalRooms = roomService.count();
        statistics.put("totalRooms", totalRooms);

        long availableRooms = roomService.count(
                new LambdaQueryWrapper<Room>().eq(Room::getStatus, "0")
        );
        statistics.put("availableRooms", availableRooms);

        long occupiedRooms = roomService.count(
                new LambdaQueryWrapper<Room>().eq(Room::getStatus, "1")
        );
        statistics.put("occupiedRooms", occupiedRooms);

        long maintenanceRooms = roomService.count(
                new LambdaQueryWrapper<Room>().eq(Room::getStatus, "2")
        );
        statistics.put("maintenanceRooms", maintenanceRooms);

        long totalUsers = userService.count();
        statistics.put("totalUsers", totalUsers);

        return Result.success(statistics);
    }
}
