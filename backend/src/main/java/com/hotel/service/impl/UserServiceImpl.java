package com.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.User;
import com.hotel.mapper.UserMapper;
import com.hotel.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectUserByUsername(username);
    }

    @Override
    public Page<User> pageList(Page<User> page, User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (user != null) {
            if (StringUtils.hasText(user.getUsername())) {
                queryWrapper.like(User::getUsername, user.getUsername());
            }
            if (StringUtils.hasText(user.getNickName())) {
                queryWrapper.like(User::getNickName, user.getNickName());
            }
            if (StringUtils.hasText(user.getPhone())) {
                queryWrapper.like(User::getPhone, user.getPhone());
            }
            if (user.getStatus() != null) {
                queryWrapper.eq(User::getStatus, user.getStatus());
            }
        }
        queryWrapper.orderByDesc(User::getCreateTime);
        return this.page(page, queryWrapper);
    }

    @Override
    public boolean checkUsernameUnique(User user) {
        Long userId = user.getId() == null ? -1L : user.getId();
        User info = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (info != null && !info.getId().equals(userId)) {
            return false;
        }
        return true;
    }
}
