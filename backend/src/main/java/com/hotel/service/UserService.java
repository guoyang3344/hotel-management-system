package com.hotel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hotel.entity.User;

public interface UserService extends IService<User> {
    User getByUsername(String username);
    Page<User> pageList(Page<User> page, User user);
    boolean checkUsernameUnique(User user);
}
