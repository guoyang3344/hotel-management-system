package com.hotel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.common.Result;
import com.hotel.entity.RoomType;
import com.hotel.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roomTypes")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping
    @PreAuthorize("hasAuthority('room:roomType:list')")
    public Result<Page<RoomType>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            RoomType roomType) {
        Page<RoomType> page = new Page<>(current, size);
        Page<RoomType> result = roomTypeService.pageList(page, roomType);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('room:roomType:query')")
    public Result<RoomType> getById(@PathVariable Long id) {
        RoomType roomType = roomTypeService.getById(id);
        return Result.success(roomType);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('room:roomType:add')")
    public Result<RoomType> save(@RequestBody RoomType roomType) {
        roomTypeService.save(roomType);
        return Result.success("新增成功", roomType);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('room:roomType:edit')")
    public Result<RoomType> update(@RequestBody RoomType roomType) {
        roomTypeService.updateById(roomType);
        return Result.success("修改成功", roomType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('room:roomType:remove')")
    public Result<Void> delete(@PathVariable Long id) {
        roomTypeService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('room:roomType:remove')")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        roomTypeService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/listAll")
    public Result<List<RoomType>> listAll() {
        List<RoomType> roomTypes = roomTypeService.list();
        return Result.success(roomTypes);
    }
}
