package com.hotel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.common.Result;
import com.hotel.entity.DictData;
import com.hotel.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dict/data")
public class DictDataController {

    @Autowired
    private DictDataService dictDataService;

    @GetMapping
    @PreAuthorize("hasAuthority('system:dict:list')")
    public Result<Page<DictData>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            DictData dictData) {
        Page<DictData> page = new Page<>(current, size);
        Page<DictData> result = dictDataService.pageList(page, dictData);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:dict:query')")
    public Result<DictData> getById(@PathVariable Long id) {
        DictData dictData = dictDataService.getById(id);
        return Result.success(dictData);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:dict:add')")
    public Result<DictData> save(@RequestBody DictData dictData) {
        dictDataService.save(dictData);
        return Result.success("新增成功", dictData);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('system:dict:edit')")
    public Result<DictData> update(@RequestBody DictData dictData) {
        dictDataService.updateById(dictData);
        return Result.success("修改成功", dictData);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Void> delete(@PathVariable Long id) {
        dictDataService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:dict:remove')")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        dictDataService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/type/{dictType}")
    public Result<List<DictData>> getByType(@PathVariable String dictType) {
        List<DictData> dictDataList = dictDataService.getDictDataByType(dictType);
        return Result.success(dictDataList);
    }
}
