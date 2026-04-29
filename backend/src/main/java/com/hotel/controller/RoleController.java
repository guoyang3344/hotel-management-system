package com.hotel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.common.Result;
import com.hotel.entity.Role;
import com.hotel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('system:role:list')")
    public Result<Page<Role>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            Role role) {
        Page<Role> page = new Page<>(current, size);
        Page<Role> result = roleService.pageList(page, role);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<Role> getById(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.success(role);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:role:add')")
    public Result<Role> save(@RequestBody Role role) {
        role.setStatus(1);
        roleService.save(role);
        return Result.success("新增成功", role);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('system:role:edit')")
    public Result<Role> update(@RequestBody Role role) {
        roleService.updateById(role);
        return Result.success("修改成功", role);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:remove')")
    public Result<Void> delete(@PathVariable Long id) {
        roleService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:role:remove')")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        roleService.removeByIds(ids);
        return Result.success();
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("hasAuthority('system:role:edit')")
    public Result<Void> changeStatus(@RequestBody Role role) {
        Role existingRole = roleService.getById(role.getId());
        if (existingRole != null) {
            existingRole.setStatus(role.getStatus());
            roleService.updateById(existingRole);
        }
        return Result.success();
    }

    @GetMapping("/listAll")
    public Result<List<Role>> listAll() {
        List<Role> roles = roleService.list();
        return Result.success(roles);
    }
}
