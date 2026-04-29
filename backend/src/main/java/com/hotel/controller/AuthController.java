package com.hotel.controller;

import com.hotel.common.Result;
import com.hotel.entity.User;
import com.hotel.service.MenuService;
import com.hotel.service.UserService;
import com.hotel.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtil.generateToken(user.getUsername());
            User loginUser = userService.getByUsername(user.getUsername());
            
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", loginUser);
            result.put("menus", menuService.buildTree(menuService.getMenusByUserId(loginUser.getId())));

            return Result.success("登录成功", result);
        } catch (Exception e) {
            return Result.error("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        if (!userService.checkUsernameUnique(user)) {
            return Result.error("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1);
        userService.save(user);
        return Result.success("注册成功", user);
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUsername(username);
        
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("menus", menuService.buildTree(menuService.getMenusByUserId(user.getId())));
        
        return Result.success(result);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        SecurityContextHolder.clearContext();
        return Result.success();
    }
}
