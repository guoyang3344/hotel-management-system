package com.hotel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hotel.entity.Role;

public interface RoleService extends IService<Role> {
    Page<Role> pageList(Page<Role> page, Role role);
}
