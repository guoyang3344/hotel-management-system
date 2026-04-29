package com.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hotel.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> getMenusByUserId(Long userId);
    List<Menu> getMenusByRoleId(Long roleId);
    List<Menu> buildTree(List<Menu> menus);
}
