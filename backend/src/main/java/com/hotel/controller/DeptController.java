package com.hotel.controller;

import com.hotel.common.Result;
import com.hotel.entity.Dept;
import com.hotel.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping
    @PreAuthorize("hasAuthority('system:dept:list')")
    public Result<List<Dept>> list(Dept dept) {
        List<Dept> depts = deptService.selectDeptList(dept);
        List<Dept> treeDepts = deptService.buildDeptTree(depts);
        return Result.success(treeDepts);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:dept:query')")
    public Result<Dept> getById(@PathVariable Long id) {
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:dept:add')")
    public Result<Dept> save(@RequestBody Dept dept) {
        deptService.save(dept);
        return Result.success("新增成功", dept);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('system:dept:edit')")
    public Result<Dept> update(@RequestBody Dept dept) {
        deptService.updateById(dept);
        return Result.success("修改成功", dept);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:dept:remove')")
    public Result<Void> delete(@PathVariable Long id) {
        deptService.removeById(id);
        return Result.success();
    }

    @GetMapping("/tree")
    public Result<List<Dept>> getTree() {
        List<Dept> depts = deptService.list();
        List<Dept> treeDepts = deptService.buildDeptTree(depts);
        return Result.success(treeDepts);
    }
}
