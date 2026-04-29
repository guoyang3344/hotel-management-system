-- 创建数据库
CREATE DATABASE IF NOT EXISTS hotel_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE hotel_management;

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nick_name VARCHAR(50) COMMENT '昵称',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像',
    dept_id BIGINT COMMENT '部门ID',
    post_id BIGINT COMMENT '岗位ID',
    status INT DEFAULT 1 COMMENT '状态（1正常 0停用）',
    deleted INT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_key VARCHAR(100) NOT NULL COMMENT '角色权限字符串',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    remark VARCHAR(500) COMMENT '备注',
    status INT DEFAULT 1 COMMENT '状态（1正常 0停用）',
    deleted INT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 菜单表
CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '菜单ID',
    menu_name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    path VARCHAR(200) COMMENT '路由地址',
    component VARCHAR(255) COMMENT '组件路径',
    query VARCHAR(255) COMMENT '路由参数',
    route_name VARCHAR(50) COMMENT '路由名称',
    is_frame INT DEFAULT 0 COMMENT '是否为外链（0是 1否）',
    is_cache INT DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
    menu_type VARCHAR(1) COMMENT '菜单类型（M目录 C菜单 F按钮）',
    visible VARCHAR(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    status VARCHAR(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    perms VARCHAR(100) COMMENT '权限标识',
    icon VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
    deleted INT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 部门表
CREATE TABLE IF NOT EXISTS sys_dept (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '部门ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
    ancestors VARCHAR(500) COMMENT '祖级列表',
    dept_name VARCHAR(50) NOT NULL COMMENT '部门名称',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    leader VARCHAR(50) COMMENT '负责人',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    status VARCHAR(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
    deleted INT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 岗位表
CREATE TABLE IF NOT EXISTS sys_post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '岗位ID',
    post_code VARCHAR(64) NOT NULL COMMENT '岗位编码',
    post_name VARCHAR(50) NOT NULL COMMENT '岗位名称',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status VARCHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    deleted INT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- 字典类型表
CREATE TABLE IF NOT EXISTS sys_dict_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '字典主键',
    dict_name VARCHAR(100) COMMENT '字典名称',
    dict_type VARCHAR(100) NOT NULL COMMENT '字典类型',
    status VARCHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    deleted INT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- 字典数据表
CREATE TABLE IF NOT EXISTS sys_dict_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '字典编码',
    dict_sort INT DEFAULT 0 COMMENT '字典排序',
    dict_label VARCHAR(100) NOT NULL COMMENT '字典标签',
    dict_value VARCHAR(100) NOT NULL COMMENT '字典键值',
    dict_type VARCHAR(100) NOT NULL COMMENT '字典类型',
    css_class VARCHAR(100) COMMENT '样式属性',
    list_class VARCHAR(100) COMMENT '表格回显样式',
    is_default VARCHAR(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    status VARCHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    deleted INT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- 房间类型表
CREATE TABLE IF NOT EXISTS room_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '房型ID',
    type_name VARCHAR(50) NOT NULL COMMENT '房型名称',
    price DECIMAL(10, 2) NOT NULL COMMENT '价格',
    bed_count INT DEFAULT 1 COMMENT '床位数量',
    bed_type VARCHAR(50) COMMENT '床型',
    area INT COMMENT '面积（平方米）',
    facilities VARCHAR(500) COMMENT '设施',
    image_url VARCHAR(255) COMMENT '图片URL',
    status VARCHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    deleted INT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间类型表';

-- 房间表
CREATE TABLE IF NOT EXISTS room (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '房间ID',
    room_number VARCHAR(20) NOT NULL COMMENT '房间号',
    room_type_id BIGINT NOT NULL COMMENT '房型ID',
    floor INT COMMENT '楼层',
    status VARCHAR(1) DEFAULT '0' COMMENT '房间状态（0空闲 1已入住 2维护中）',
    description VARCHAR(500) COMMENT '描述',
    deleted INT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    remark VARCHAR(500) COMMENT '备注',
    UNIQUE KEY uk_room_number (room_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间表';

-- 初始化部门数据
INSERT INTO sys_dept (id, parent_id, ancestors, dept_name, sort, status) VALUES
(1, 0, '0', '酒店管理公司', 0, '0'),
(2, 1, '0,1', '总经办', 1, '0'),
(3, 1, '0,1', '财务部', 2, '0'),
(4, 1, '0,1', '前台部', 3, '0'),
(5, 1, '0,1', '客房部', 4, '0'),
(6, 1, '0,1', '人事部', 5, '0');

-- 初始化岗位数据
INSERT INTO sys_post (id, post_code, post_name, sort, status) VALUES
(1, 'ceo', '总经理', 1, '0'),
(2, 'manager', '部门经理', 2, '0'),
(3, 'receptionist', '前台接待', 3, '0'),
(4, 'housekeeper', '客房服务员', 4, '0'),
(5, 'accountant', '会计', 5, '0');

-- 初始化角色数据
INSERT INTO sys_role (id, role_name, role_key, sort, status) VALUES
(1, '超级管理员', 'admin', 1, 1),
(2, '部门经理', 'manager', 2, 1),
(3, '前台接待', 'receptionist', 3, 1),
(4, '普通用户', 'common', 4, 1);

-- 初始化菜单数据
INSERT INTO sys_menu (id, menu_name, parent_id, sort, path, component, menu_type, visible, status, perms, icon) VALUES
(1, '首页', 0, 1, '/index', 'index/index', 'C', '0', '0', NULL, 'home'),
(2, '房间管理', 0, 2, '/room', NULL, 'M', '0', '0', NULL, 'office-building'),
(3, '房间列表', 2, 1, '/room/list', 'room/roomList', 'C', '0', '0', 'room:room:list', 'list'),
(4, '房型管理', 2, 2, '/room/type', 'room/roomTypeList', 'C', '0', '0', 'room:roomType:list', 'setting'),
(5, '系统管理', 0, 3, '/system', NULL, 'M', '0', '0', NULL, 'setting'),
(6, '用户管理', 5, 1, '/system/user', 'system/userList', 'C', '0', '0', 'system:user:list', 'user'),
(7, '角色管理', 5, 2, '/system/role', 'system/roleList', 'C', '0', '0', 'system:role:list', 'team'),
(8, '菜单管理', 5, 3, '/system/menu', 'system/menuList', 'C', '0', '0', 'system:menu:list', 'menu'),
(9, '部门管理', 5, 4, '/system/dept', 'system/deptList', 'C', '0', '0', 'system:dept:list', 'apartment'),
(10, '岗位管理', 5, 5, '/system/post', 'system/postList', 'C', '0', '0', 'system:post:list', 'user-add'),
(11, '字典管理', 5, 6, '/system/dict', 'system/dictList', 'C', '0', '0', 'system:dict:list', 'book');

-- 初始化菜单按钮权限
INSERT INTO sys_menu (id, menu_name, parent_id, sort, menu_type, visible, status, perms) VALUES
(101, '房间查询', 3, 1, 'F', '0', '0', 'room:room:query'),
(102, '房间新增', 3, 2, 'F', '0', '0', 'room:room:add'),
(103, '房间修改', 3, 3, 'F', '0', '0', 'room:room:edit'),
(104, '房间删除', 3, 4, 'F', '0', '0', 'room:room:remove'),
(105, '房型查询', 4, 1, 'F', '0', '0', 'room:roomType:query'),
(106, '房型新增', 4, 2, 'F', '0', '0', 'room:roomType:add'),
(107, '房型修改', 4, 3, 'F', '0', '0', 'room:roomType:edit'),
(108, '房型删除', 4, 4, 'F', '0', '0', 'room:roomType:remove'),
(201, '用户查询', 6, 1, 'F', '0', '0', 'system:user:query'),
(202, '用户新增', 6, 2, 'F', '0', '0', 'system:user:add'),
(203, '用户修改', 6, 3, 'F', '0', '0', 'system:user:edit'),
(204, '用户删除', 6, 4, 'F', '0', '0', 'system:user:remove'),
(205, '用户重置密码', 6, 5, 'F', '0', '0', 'system:user:resetPwd'),
(206, '角色查询', 7, 1, 'F', '0', '0', 'system:role:query'),
(207, '角色新增', 7, 2, 'F', '0', '0', 'system:role:add'),
(208, '角色修改', 7, 3, 'F', '0', '0', 'system:role:edit'),
(209, '角色删除', 7, 4, 'F', '0', '0', 'system:role:remove'),
(210, '菜单查询', 8, 1, 'F', '0', '0', 'system:menu:query'),
(211, '菜单新增', 8, 2, 'F', '0', '0', 'system:menu:add'),
(212, '菜单修改', 8, 3, 'F', '0', '0', 'system:menu:edit'),
(213, '菜单删除', 8, 4, 'F', '0', '0', 'system:menu:remove'),
(214, '部门查询', 9, 1, 'F', '0', '0', 'system:dept:query'),
(215, '部门新增', 9, 2, 'F', '0', '0', 'system:dept:add'),
(216, '部门修改', 9, 3, 'F', '0', '0', 'system:dept:edit'),
(217, '部门删除', 9, 4, 'F', '0', '0', 'system:dept:remove'),
(218, '岗位查询', 10, 1, 'F', '0', '0', 'system:post:query'),
(219, '岗位新增', 10, 2, 'F', '0', '0', 'system:post:add'),
(220, '岗位修改', 10, 3, 'F', '0', '0', 'system:post:edit'),
(221, '岗位删除', 10, 4, 'F', '0', '0', 'system:post:remove'),
(222, '字典查询', 11, 1, 'F', '0', '0', 'system:dict:query'),
(223, '字典新增', 11, 2, 'F', '0', '0', 'system:dict:add'),
(224, '字典修改', 11, 3, 'F', '0', '0', 'system:dict:edit'),
(225, '字典删除', 11, 4, 'F', '0', '0', 'system:dict:remove');

-- 初始化超级管理员角色菜单关联
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11),
(1, 101), (1, 102), (1, 103), (1, 104), (1, 105), (1, 106), (1, 107), (1, 108),
(1, 201), (1, 202), (1, 203), (1, 204), (1, 205), (1, 206), (1, 207), (1, 208), (1, 209),
(1, 210), (1, 211), (1, 212), (1, 213), (1, 214), (1, 215), (1, 216), (1, 217),
(1, 218), (1, 219), (1, 220), (1, 221), (1, 222), (1, 223), (1, 224), (1, 225);

-- 初始化前台接待角色菜单关联
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(3, 1), (3, 2), (3, 3), (3, 101), (3, 102), (3, 103), (3, 104),
(3, 4), (3, 105), (3, 106), (3, 107), (3, 108);

-- 初始化房间类型数据
INSERT INTO room_type (id, type_name, price, bed_count, bed_type, area, facilities, status) VALUES
(1, '标准间', 299.00, 2, '单人床', 25, '空调、电视、独立卫浴、WiFi', '0'),
(2, '大床房', 399.00, 1, '双人床', 30, '空调、电视、独立卫浴、WiFi、迷你吧', '0'),
(3, '商务套房', 699.00, 1, '双人床', 45, '空调、电视、独立卫浴、WiFi、迷你吧、商务书桌', '0'),
(4, '豪华套房', 999.00, 2, '双人床', 60, '空调、电视、独立卫浴、WiFi、迷你吧、客厅、浴缸', '0'),
(5, '总统套房', 2999.00, 2, '双人床', 120, '空调、电视、独立卫浴、WiFi、迷你吧、客厅、餐厅、厨房', '0');

-- 初始化房间数据
INSERT INTO room (id, room_number, room_type_id, floor, status) VALUES
(1, '101', 1, 1, '0'),
(2, '102', 1, 1, '0'),
(3, '103', 1, 1, '0'),
(4, '104', 1, 1, '0'),
(5, '105', 2, 1, '0'),
(6, '106', 2, 1, '0'),
(7, '201', 1, 2, '0'),
(8, '202', 1, 2, '0'),
(9, '203', 2, 2, '0'),
(10, '204', 2, 2, '0'),
(11, '301', 3, 3, '0'),
(12, '302', 3, 3, '0'),
(13, '401', 4, 4, '0'),
(14, '501', 5, 5, '0');

-- 初始化字典类型数据
INSERT INTO sys_dict_type (id, dict_name, dict_type, status) VALUES
(1, '用户状态', 'sys_user_status', '0'),
(2, '房间状态', 'room_status', '0'),
(3, '菜单状态', 'sys_menu_status', '0'),
(4, '菜单类型', 'sys_menu_type', '0');

-- 初始化字典数据
INSERT INTO sys_dict_data (id, dict_sort, dict_label, dict_value, dict_type, list_class, is_default, status) VALUES
(1, 1, '正常', '1', 'sys_user_status', 'success', 'Y', '0'),
(2, 2, '停用', '0', 'sys_user_status', 'danger', 'N', '0'),
(3, 1, '空闲', '0', 'room_status', 'success', 'Y', '0'),
(4, 2, '已入住', '1', 'room_status', 'danger', 'N', '0'),
(5, 3, '维护中', '2', 'room_status', 'warning', 'N', '0'),
(6, 1, '正常', '0', 'sys_menu_status', 'success', 'Y', '0'),
(7, 2, '停用', '1', 'sys_menu_status', 'danger', 'N', '0'),
(8, 1, '目录', 'M', 'sys_menu_type', 'primary', 'N', '0'),
(9, 2, '菜单', 'C', 'sys_menu_type', 'success', 'Y', '0'),
(10, 3, '按钮', 'F', 'sys_menu_type', 'warning', 'N', '0');

-- 初始化用户数据（密码：123456，使用BCrypt加密）
INSERT INTO sys_user (id, username, password, nick_name, phone, email, dept_id, post_id, status) VALUES
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoU2O0Zx6Z5Z4Z3Z2Z1Z0Z9Z8Z7Z6Z5Z4', '系统管理员', '13800138000', 'admin@hotel.com', 2, 1, 1),
(2, 'zhangsan', '$2a$10$N.zmdr9k7uOCQb376NoU2O0Zx6Z5Z4Z3Z2Z1Z0Z9Z8Z7Z6Z5Z4', '张三', '13800138001', 'zhangsan@hotel.com', 4, 3, 1),
(3, 'lisi', '$2a$10$N.zmdr9k7uOCQb376NoU2O0Zx6Z5Z4Z3Z2Z1Z0Z9Z8Z7Z6Z5Z4', '李四', '13800138002', 'lisi@hotel.com', 4, 3, 1);

-- 初始化用户角色关联
INSERT INTO sys_user_role (user_id, role_id) VALUES
(1, 1),
(2, 3),
(3, 3);
