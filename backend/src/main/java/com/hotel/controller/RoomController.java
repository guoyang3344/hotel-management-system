package com.hotel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.common.Result;
import com.hotel.entity.Room;
import com.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    @PreAuthorize("hasAuthority('room:room:list')")
    public Result<Page<Room>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            Room room) {
        Page<Room> page = new Page<>(current, size);
        Page<Room> result = roomService.pageList(page, room);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('room:room:query')")
    public Result<Room> getById(@PathVariable Long id) {
        Room room = roomService.getById(id);
        return Result.success(room);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('room:room:add')")
    public Result<Room> save(@RequestBody Room room) {
        roomService.save(room);
        return Result.success("新增成功", room);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('room:room:edit')")
    public Result<Room> update(@RequestBody Room room) {
        roomService.updateById(room);
        return Result.success("修改成功", room);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('room:room:remove')")
    public Result<Void> delete(@PathVariable Long id) {
        roomService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('room:room:remove')")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        roomService.removeByIds(ids);
        return Result.success();
    }
}
