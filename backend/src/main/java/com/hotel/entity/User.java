package com.hotel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String nickName;

    private String phone;

    private String email;

    private String avatar;

    private Long deptId;

    private Long postId;

    private Integer status;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;

    @TableField(exist = false)
    private Dept dept;

    @TableField(exist = false)
    private Post post;

    @TableField(exist = false)
    private Set<Role> roles;

    @TableField(exist = false)
    private Set<Menu> menus;
}
