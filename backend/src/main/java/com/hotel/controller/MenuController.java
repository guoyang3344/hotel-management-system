package com.hotel.controller;

import com.hotel.common.Result;
import com.hotel.entity.Menu;
import com.hotel.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    @PreAuthorize("hasAuthority('system:menu:list')")
    public Result<List<Menu>> list(Menu menu) {
        List<Menu> menus = menuService.list();
        List<Menu> treeMenus = menuService.buildTree(menus);
        return Result.success(treeMenus);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:menu:query')")
    public Result<Menu> getById(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        return Result.success(menu);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:menu:add')")
    public Result<Menu> save(@RequestBody Menu menu) {
        menuService.save(menu);
        return Result.success("新增成功", menu);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('system:menu:edit')")
    public Result<Menu> update(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return Result.success("修改成功", menu);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:menu:remove')")
    public Result<Void> delete(@PathVariable Long id) {
        menuService.removeById(id);
        return Result.success();
    }

    @GetMapping("/tree")
    public Result<List<Menu>> getTree() {
        List<Menu> menus = menuService.list();
        List<Menu> treeMenus = menuService.buildTree(menus);
        return Result.success(treeMenus);
    }
}
