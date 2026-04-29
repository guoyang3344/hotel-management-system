package com.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.Menu;
import com.hotel.mapper.MenuMapper;
import com.hotel.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> getMenusByUserId(Long userId) {
        return baseMapper.selectMenusByUserId(userId);
    }

    @Override
    public List<Menu> getMenusByRoleId(Long roleId) {
        return baseMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public List<Menu> buildTree(List<Menu> menus) {
        Map<Long, List<Menu>> parentMap = menus.stream()
                .collect(Collectors.groupingBy(Menu::getParentId));
        
        menus.forEach(menu -> {
            menu.setChildren(parentMap.getOrDefault(menu.getId(), new ArrayList<>()));
        });
        
        return menus.stream()
                .filter(menu -> menu.getParentId() == 0)
                .collect(Collectors.toList());
    }
}
