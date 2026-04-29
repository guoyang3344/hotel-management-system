package com.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.Role;
import com.hotel.mapper.RoleMapper;
import com.hotel.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Page<Role> pageList(Page<Role> page, Role role) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (role != null) {
            if (StringUtils.hasText(role.getRoleName())) {
                queryWrapper.like(Role::getRoleName, role.getRoleName());
            }
            if (StringUtils.hasText(role.getRoleKey())) {
                queryWrapper.like(Role::getRoleKey, role.getRoleKey());
            }
            if (role.getStatus() != null) {
                queryWrapper.eq(Role::getStatus, role.getStatus());
            }
        }
        queryWrapper.orderByAsc(Role::getSort);
        return this.page(page, queryWrapper);
    }
}
