package com.hotel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.common.Result;
import com.hotel.entity.DictType;
import com.hotel.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dict/types")
public class DictTypeController {

    @Autowired
    private DictTypeService dictTypeService;

    @GetMapping
    @PreAuthorize("hasAuthority('system:dict:list')")
    public Result<Page<DictType>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            DictType dictType) {
        Page<DictType> page = new Page<>(current, size);
        Page<DictType> result = dictTypeService.pageList(page, dictType);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:dict:query')")
    public Result<DictType> getById(@PathVariable Long id) {
        DictType dictType = dictTypeService.getById(id);
        return Result.success(dictType);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:dict:add')")
    public Result<DictType> save(@RequestBody DictType dictType) {
        dictTypeService.save(dictType);
        return Result.success("新增成功", dictType);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('system:dict:edit')")
    public Result<DictType> update(@RequestBody DictType dictType) {
        dictTypeService.updateById(dictType);
        return Result.success("修改成功", dictType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Void> delete(@PathVariable Long id) {
        dictTypeService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        dictTypeService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/listAll")
    public Result<List<DictType>> listAll() {
        List<DictType> dictTypes = dictTypeService.list();
        return Result.success(dictTypes);
    }
}
