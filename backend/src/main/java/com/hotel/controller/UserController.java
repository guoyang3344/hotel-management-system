package com.hotel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.common.Result;
import com.hotel.entity.User;
import com.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAuthority('system:user:list')")
    public Result<Page<User>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            User user) {
        Page<User> page = new Page<>(current, size);
        Page<User> result = userService.pageList(page, user);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:user:add')")
    public Result<User> save(@RequestBody User user) {
        if (!userService.checkUsernameUnique(user)) {
            return Result.error("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1);
        userService.save(user);
        return Result.success("新增成功", user);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result<User> update(@RequestBody User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        userService.updateById(user);
        return Result.success("修改成功", user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:user:remove')")
    public Result<Void> delete(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:user:remove')")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        userService.removeByIds(ids);
        return Result.success();
    }

    @PutMapping("/resetPwd")
    @PreAuthorize("hasAuthority('system:user:resetPwd')")
    public Result<Void> resetPwd(@RequestBody User user) {
        User existingUser = userService.getById(user.getId());
        if (existingUser != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.updateById(existingUser);
        }
        return Result.success();
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result<Void> changeStatus(@RequestBody User user) {
        User existingUser = userService.getById(user.getId());
        if (existingUser != null) {
            existingUser.setStatus(user.getStatus());
            userService.updateById(existingUser);
        }
        return Result.success();
    }
}
