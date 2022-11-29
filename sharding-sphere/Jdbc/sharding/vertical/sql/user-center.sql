SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE user_center;
USE user_center;

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_Id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_group
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `parent_id` int(11) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `css` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `is_menu` int(11) NULL DEFAULT NULL COMMENT '是否菜单 1 是 2 不是',
  `is_hidden` int(11) NULL DEFAULT NULL COMMENT '是否隐藏,0 false 1 true',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '2021-12-02 11:27:17', -1, 'admin', b'1', '2021-12-02 11:27:17', 24, '1111', 1, -1, '后台管理', '', 'javascript:;', '', 'el-icon-setting', -100, 1, 0);
INSERT INTO `sys_menu` VALUES (2, '2021-12-02 11:28:47', -1, 'admin', b'1', '2021-12-02 11:28:47', -1, 'admin', 0, 1, '菜单管理', '', '/sysmenu', 'system/menu/SysMenu', 'el-icon-user-solid', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (3, '2021-12-02 13:22:05', -1, 'admin', b'1', '2021-12-02 13:22:05', -1, 'admin', 1, 1, '我的信息', '', '/myinfo', 'system/user/MyInfo', '', 99, 1, 1);
INSERT INTO `sys_menu` VALUES (4, '2021-12-02 13:23:04', -1, 'admin', b'1', '2021-12-02 13:23:04', -1, 'admin', 0, 1, '角色管理', '', '/sysrole', 'system/sys-role/SysRole', 'el-icon-user-solid', 2, 1, 0);
INSERT INTO `sys_menu` VALUES (5, '2021-12-02 13:23:50', -1, 'admin', b'1', '2021-12-02 13:23:50', -1, 'admin', 0, 1, '用户管理', '', '/user', 'system/user/SysUser', 'el-icon-user-solid', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (6, '2021-12-02 13:24:24', -1, 'admin', b'1', '2021-12-02 13:24:24', -1, 'admin', 0, 1, '权限管理', '', '/permission', 'system/permission/SysPermission', 'el-icon-user-solid', 4, 1, 0);
INSERT INTO `sys_menu` VALUES (16, '2022-08-19 09:35:56', 19, 'admin', b'0', '2022-08-19 09:35:56', NULL, NULL, 0, -1, 'Test', '', 'javascript:void(0)', '', 'el-icon-setting', -99, 1, 0);
INSERT INTO `sys_menu` VALUES (17, '2022-08-19 09:36:41', 19, 'admin', b'0', '2022-08-19 09:36:41', 19, 'admin', 1, 16, 'Test-1', '', '/sysmenu', '/system/menu/SysMenu', 'el-icon-user-solid', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (18, '2022-08-25 15:59:34', 19, 'admin', b'1', '2022-08-25 15:59:34', 19, 'admin', 5, 1, '菜单权限管理', '', '/sysmenpermission', 'system/menu-permission/SysMenuPermission', 'el-icon-user-solid', 0, 1, 1);
INSERT INTO `sys_menu` VALUES (19, '2022-08-26 15:25:26', 19, 'admin', b'1', '2022-08-26 15:25:26', 19, 'admin', 3, 1, '角色授权管理', '', '/sysrmpc', 'system/role-menu-permission/SysRoleMenuPermission', 'el-icon-user-solid', 0, 1, 1);
INSERT INTO `sys_menu` VALUES (20, '2022-08-29 20:32:46', 19, 'admin', b'0', '2022-08-29 20:32:46', 19, 'admin', 1, -1, 'TSET', '', 'test', 'test', '333', 0, 1, 0);
INSERT INTO `sys_menu` VALUES (21, '2022-09-08 16:02:32', 19, 'admin', b'1', '2022-09-08 16:02:32', 19, 'admin', 2, -1, '组件管理', '', 'javascript:void(0)', '', 'el-icon-setting', 0, 1, 0);
INSERT INTO `sys_menu` VALUES (22, '2022-09-08 16:04:00', 19, 'admin', b'1', '2022-09-08 16:04:00', 19, 'admin', 2, 21, '文件管理', '', '/fileinfo', 'common/file/FileInfo', 'el-icon-user-solid', 0, 1, 0);
INSERT INTO `sys_menu` VALUES (23, '2022-09-09 10:35:07', 19, 'admin', b'1', '2022-09-09 10:35:07', 19, 'admin', 2, 21, '字典管理', '', '/dicttype', 'common/param/dict-type/DictType', 'el-icon-user-solid', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (24, '2022-09-10 07:52:29', 19, 'admin', b'1', '2022-09-10 07:52:29', 19, 'admin', 3, 21, '字典列表管理', '', '/dictdata', 'common/param/dict-data/DictData', 'el-icon-user-solid', 2, 1, 1);
INSERT INTO `sys_menu` VALUES (25, '2022-09-10 18:10:07', 19, 'admin', b'1', '2022-09-10 18:10:07', 19, 'admin', 1, 21, '字典树管理', '', '/dicttree', 'common/param/dict-tree/DictTree', 'el-icon-user-solid', 3, 1, 1);
INSERT INTO `sys_menu` VALUES (26, '2022-09-13 15:00:33', 24, '1111', b'0', '2022-09-13 15:00:33', NULL, NULL, 0, -1, 'est', '', 's', 'se', 's', 0, 1, 0);
INSERT INTO `sys_menu` VALUES (27, '2022-09-22 09:18:57', 19, 'admin', b'1', '2022-09-22 09:18:57', NULL, NULL, 0, -1, '认证管理', '', '', 'javascript:void(0)', 'el-icon-setting', 2, 1, 0);
INSERT INTO `sys_menu` VALUES (28, '2022-09-22 09:21:39', 19, 'admin', b'1', '2022-09-22 09:21:39', NULL, NULL, 0, 27, '客户管理', '', '/customer', 'business/customer/CustomerInfo', 'el-icon-user-solid', 0, 1, 0);
INSERT INTO `sys_menu` VALUES (29, '2022-09-23 11:23:32', 19, 'admin', b'1', '2022-09-23 11:23:32', NULL, NULL, 0, 27, '设备认证', '', '/device-auth', 'business/device/DeviceAuth', 'el-icon-user-solid', 0, 1, 0);
INSERT INTO `sys_menu` VALUES (30, '2022-09-26 16:47:10', 19, 'admin', b'1', '2022-09-26 16:47:10', NULL, NULL, 0, 21, '数据日志管理', '', '/data-log', 'common/data-log/DataLog', 'el-icon-user-solid', 0, 1, 0);

-- ----------------------------
-- Table structure for sys_menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_permission`;
CREATE TABLE `sys_menu_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `permission_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `checked` bit(1) NOT NULL,
  `permission_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 359 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu_permission
-- ----------------------------
INSERT INTO `sys_menu_permission` VALUES (107, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 30, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::USER::RESET:PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (108, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 29, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::USER::GRANTED:ROLE');
INSERT INTO `sys_menu_permission` VALUES (109, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 28, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::USER::GET:ON:LINE');
INSERT INTO `sys_menu_permission` VALUES (110, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 27, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (111, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 1, 26, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::DELETE');
INSERT INTO `sys_menu_permission` VALUES (112, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 1, 25, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (113, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 1, 24, 19, b'1', 'system::role-menu-permission::SysRoleMenuPermission::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (114, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 1, 23, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (115, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 22, 19, b'1', 'system::role-menu-permission::SysRoleMenuPermission::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (116, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 2, 21, 19, b'1', 'system::role-menu-permission::SysRoleMenuPermission::ADD');
INSERT INTO `sys_menu_permission` VALUES (117, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 30, 18, b'0', 'system::menu-permission::SysMenuPermission::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (118, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 29, 18, b'0', 'system::menu-permission::SysMenuPermission::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (119, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 28, 18, b'0', 'system::menu-permission::SysMenuPermission::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (120, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 27, 18, b'0', 'system::menu-permission::SysMenuPermission::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (121, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 1, 26, 18, b'0', 'system::menu-permission::SysMenuPermission::DELETE');
INSERT INTO `sys_menu_permission` VALUES (122, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 1, 25, 18, b'0', 'system::menu-permission::SysMenuPermission::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (123, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 1, 24, 18, b'1', 'system::menu-permission::SysMenuPermission::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (124, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 1, 23, 18, b'0', 'system::menu-permission::SysMenuPermission::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (125, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 22, 18, b'1', 'system::menu-permission::SysMenuPermission::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (126, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 2, 21, 18, b'1', 'system::menu-permission::SysMenuPermission::ADD');
INSERT INTO `sys_menu_permission` VALUES (127, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 30, 6, b'0', 'system::permission::SysPermission::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (128, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 29, 6, b'0', 'system::permission::SysPermission::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (129, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 28, 6, b'0', 'system::permission::SysPermission::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (130, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 27, 6, b'0', 'system::permission::SysPermission::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (131, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 26, 6, b'1', 'system::permission::SysPermission::DELETE');
INSERT INTO `sys_menu_permission` VALUES (132, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 25, 6, b'1', 'system::permission::SysPermission::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (133, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 2, 24, 6, b'0', 'system::permission::SysPermission::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (134, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 23, 6, b'1', 'system::permission::SysPermission::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (135, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 22, 6, b'1', 'system::permission::SysPermission::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (136, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 2, 21, 6, b'1', 'system::permission::SysPermission::ADD');
INSERT INTO `sys_menu_permission` VALUES (137, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 30, 5, b'1', 'system::user::SysUser::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (138, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 29, 5, b'1', 'system::user::SysUser::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (139, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 28, 5, b'1', 'system::user::SysUser::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (140, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 27, 5, b'1', 'system::user::SysUser::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (141, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 26, 5, b'1', 'system::user::SysUser::DELETE');
INSERT INTO `sys_menu_permission` VALUES (142, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 25, 5, b'1', 'system::user::SysUser::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (143, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 24, 5, b'0', 'system::user::SysUser::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (144, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 23, 5, b'1', 'system::user::SysUser::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (145, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 22, 5, b'1', 'system::user::SysUser::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (146, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 21, 5, b'1', 'system::user::SysUser::ADD');
INSERT INTO `sys_menu_permission` VALUES (147, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 30, 4, b'0', 'system::sys-role::SysRole::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (148, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 29, 4, b'0', 'system::sys-role::SysRole::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (149, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 28, 4, b'0', 'system::sys-role::SysRole::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (150, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 27, 4, b'0', 'system::sys-role::SysRole::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (151, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 26, 4, b'1', 'system::sys-role::SysRole::DELETE');
INSERT INTO `sys_menu_permission` VALUES (152, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 25, 4, b'1', 'system::sys-role::SysRole::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (153, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 24, 4, b'0', 'system::sys-role::SysRole::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (154, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 23, 4, b'1', 'system::sys-role::SysRole::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (155, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 22, 4, b'1', 'system::sys-role::SysRole::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (156, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 21, 4, b'1', 'system::sys-role::SysRole::ADD');
INSERT INTO `sys_menu_permission` VALUES (157, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 30, 3, b'0', 'system::user::MyInfo::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (158, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 29, 3, b'0', 'system::user::MyInfo::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (159, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 28, 3, b'0', 'system::user::MyInfo::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (160, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 27, 3, b'0', 'system::user::MyInfo::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (161, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 26, 3, b'0', 'system::user::MyInfo::DELETE');
INSERT INTO `sys_menu_permission` VALUES (162, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 25, 3, b'1', 'system::user::MyInfo::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (163, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 24, 3, b'0', 'system::user::MyInfo::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (164, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 23, 3, b'0', 'system::user::MyInfo::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (165, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 22, 3, b'0', 'system::user::MyInfo::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (166, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 21, 3, b'0', 'system::user::MyInfo::ADD');
INSERT INTO `sys_menu_permission` VALUES (167, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 30, 2, b'0', 'system::menu::SysMenu::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (168, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 29, 2, b'0', 'system::menu::SysMenu::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (169, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 28, 2, b'0', 'system::menu::SysMenu::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (170, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 27, 2, b'0', 'system::menu::SysMenu::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (171, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 26, 2, b'1', 'system::menu::SysMenu::DELETE');
INSERT INTO `sys_menu_permission` VALUES (172, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 25, 2, b'1', 'system::menu::SysMenu::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (173, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 1, 24, 2, b'1', 'system::menu::SysMenu::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (174, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 19, 'admin', 1, 23, 2, b'0', 'system::menu::SysMenu::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (175, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', NULL, NULL, 0, 22, 2, b'1', 'system::menu::SysMenu::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (176, '2022-08-28 12:20:54', 19, 'admin', b'1', '2022-08-28 12:20:54', 24, '1111', 2, 21, 2, b'1', 'system::menu::SysMenu::ADD');
INSERT INTO `sys_menu_permission` VALUES (177, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 32, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (178, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 31, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (179, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 32, 18, b'0', 'system::menu-permission::SysMenuPermission::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (180, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 31, 18, b'0', 'system::menu-permission::SysMenuPermission::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (181, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 32, 6, b'0', 'system::permission::SysPermission::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (182, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 31, 6, b'0', 'system::permission::SysPermission::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (183, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 32, 5, b'0', 'system::user::SysUser::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (184, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 31, 5, b'0', 'system::user::SysUser::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (185, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 32, 4, b'0', 'system::sys-role::SysRole::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (186, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', 19, 'admin', 1, 31, 4, b'1', 'system::sys-role::SysRole::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (187, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 32, 3, b'0', 'system::user::MyInfo::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (188, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 31, 3, b'0', 'system::user::MyInfo::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (189, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', 19, 'admin', 1, 32, 2, b'1', 'system::menu::SysMenu::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (190, '2022-08-29 09:09:43', 19, 'admin', b'1', '2022-08-29 09:09:43', NULL, NULL, 0, 31, 2, b'0', 'system::menu::SysMenu::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (191, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 25, b'0', 'common::param::dict-tree::DictTree::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (192, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 25, b'0', 'common::param::dict-tree::DictTree::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (193, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 25, b'0', 'common::param::dict-tree::DictTree::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (194, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 25, b'0', 'common::param::dict-tree::DictTree::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (195, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 32, 25, b'0', 'common::param::dict-tree::DictTree::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (196, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 31, 25, b'0', 'common::param::dict-tree::DictTree::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (197, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 30, 25, b'0', 'common::param::dict-tree::DictTree::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (198, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 29, 25, b'0', 'common::param::dict-tree::DictTree::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (199, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 28, 25, b'0', 'common::param::dict-tree::DictTree::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (200, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', 24, '1111', 1, 27, 25, b'1', 'common::param::dict-tree::DictTree::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (201, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 26, 25, b'1', 'common::param::dict-tree::DictTree::DELETE');
INSERT INTO `sys_menu_permission` VALUES (202, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 25, 25, b'1', 'common::param::dict-tree::DictTree::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (203, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', 24, '1111', 1, 24, 25, b'1', 'common::param::dict-tree::DictTree::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (204, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 23, 25, b'1', 'common::param::dict-tree::DictTree::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (205, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 22, 25, b'1', 'common::param::dict-tree::DictTree::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (206, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 21, 25, b'1', 'common::param::dict-tree::DictTree::ADD');
INSERT INTO `sys_menu_permission` VALUES (207, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 24, b'0', 'common::param::dict-data::DictData::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (208, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 24, b'0', 'common::param::dict-data::DictData::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (209, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 24, b'0', 'common::param::dict-data::DictData::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (210, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 24, b'0', 'common::param::dict-data::DictData::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (211, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 32, 24, b'0', 'common::param::dict-data::DictData::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (212, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 31, 24, b'0', 'common::param::dict-data::DictData::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (213, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 30, 24, b'0', 'common::param::dict-data::DictData::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (214, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 29, 24, b'0', 'common::param::dict-data::DictData::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (215, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 28, 24, b'0', 'common::param::dict-data::DictData::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (216, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', 24, '1111', 1, 27, 24, b'1', 'common::param::dict-data::DictData::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (217, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 26, 24, b'1', 'common::param::dict-data::DictData::DELETE');
INSERT INTO `sys_menu_permission` VALUES (218, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 25, 24, b'1', 'common::param::dict-data::DictData::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (219, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 24, 24, b'0', 'common::param::dict-data::DictData::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (220, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 23, 24, b'1', 'common::param::dict-data::DictData::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (221, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 22, 24, b'1', 'common::param::dict-data::DictData::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (222, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 21, 24, b'1', 'common::param::dict-data::DictData::ADD');
INSERT INTO `sys_menu_permission` VALUES (223, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 23, b'0', 'common::param::dict-type::DictType::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (224, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 23, b'0', 'common::param::dict-type::DictType::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (225, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 23, b'0', 'common::param::dict-type::DictType::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (226, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 23, b'0', 'common::param::dict-type::DictType::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (227, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 32, 23, b'0', 'common::param::dict-type::DictType::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (228, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 31, 23, b'0', 'common::param::dict-type::DictType::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (229, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 30, 23, b'0', 'common::param::dict-type::DictType::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (230, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 29, 23, b'0', 'common::param::dict-type::DictType::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (231, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 28, 23, b'0', 'common::param::dict-type::DictType::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (232, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', 24, '1111', 1, 27, 23, b'1', 'common::param::dict-type::DictType::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (233, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 26, 23, b'1', 'common::param::dict-type::DictType::DELETE');
INSERT INTO `sys_menu_permission` VALUES (234, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 25, 23, b'1', 'common::param::dict-type::DictType::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (235, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 24, 23, b'0', 'common::param::dict-type::DictType::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (236, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 23, 23, b'1', 'common::param::dict-type::DictType::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (237, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 22, 23, b'1', 'common::param::dict-type::DictType::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (238, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 21, 23, b'1', 'common::param::dict-type::DictType::ADD');
INSERT INTO `sys_menu_permission` VALUES (239, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 22, b'0', 'common::file::FileInfo::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (240, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 22, b'0', 'common::file::FileInfo::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (241, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 22, b'1', 'common::file::FileInfo::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (242, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 22, b'1', 'common::file::FileInfo::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (243, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 32, 22, b'0', 'common::file::FileInfo::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (244, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 31, 22, b'0', 'common::file::FileInfo::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (245, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 30, 22, b'0', 'common::file::FileInfo::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (246, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 29, 22, b'0', 'common::file::FileInfo::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (247, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 28, 22, b'0', 'common::file::FileInfo::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (248, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 27, 22, b'0', 'common::file::FileInfo::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (249, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', 19, 'admin', 1, 26, 22, b'1', 'common::file::FileInfo::DELETE');
INSERT INTO `sys_menu_permission` VALUES (250, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 25, 22, b'0', 'common::file::FileInfo::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (251, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 24, 22, b'0', 'common::file::FileInfo::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (252, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', 19, 'admin', 1, 23, 22, b'1', 'common::file::FileInfo::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (253, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 22, 22, b'0', 'common::file::FileInfo::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (254, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 21, 22, b'0', 'common::file::FileInfo::ADD');
INSERT INTO `sys_menu_permission` VALUES (255, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (256, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (257, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (258, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (259, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 18, b'0', 'system::menu-permission::SysMenuPermission::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (260, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 18, b'0', 'system::menu-permission::SysMenuPermission::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (261, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 18, b'0', 'system::menu-permission::SysMenuPermission::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (262, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 18, b'0', 'system::menu-permission::SysMenuPermission::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (263, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 6, b'0', 'system::permission::SysPermission::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (264, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 6, b'0', 'system::permission::SysPermission::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (265, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 6, b'0', 'system::permission::SysPermission::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (266, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 6, b'0', 'system::permission::SysPermission::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (267, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 5, b'0', 'system::user::SysUser::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (268, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 5, b'0', 'system::user::SysUser::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (269, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 5, b'0', 'system::user::SysUser::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (270, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 5, b'0', 'system::user::SysUser::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (271, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 4, b'0', 'system::sys-role::SysRole::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (272, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 4, b'0', 'system::sys-role::SysRole::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (273, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 4, b'0', 'system::sys-role::SysRole::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (274, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 4, b'0', 'system::sys-role::SysRole::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (275, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 3, b'0', 'system::user::MyInfo::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (276, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 3, b'0', 'system::user::MyInfo::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (277, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 3, b'0', 'system::user::MyInfo::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (278, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 3, b'0', 'system::user::MyInfo::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (279, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 38, 2, b'0', 'system::menu::SysMenu::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (280, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 37, 2, b'0', 'system::menu::SysMenu::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (281, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 36, 2, b'0', 'system::menu::SysMenu::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (282, '2022-09-13 14:35:50', 19, 'admin', b'1', '2022-09-13 14:35:50', NULL, NULL, 0, 35, 2, b'0', 'system::menu::SysMenu::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (283, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 25, b'0', 'common::param::dict-tree::DictTree::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (284, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 25, b'0', 'common::param::dict-tree::DictTree::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (285, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 24, b'0', 'common::param::dict-data::DictData::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (286, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 24, b'0', 'common::param::dict-data::DictData::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (287, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 23, b'1', 'common::param::dict-type::DictType::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (288, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 23, b'1', 'common::param::dict-type::DictType::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (289, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 22, b'0', 'common::file::FileInfo::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (290, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 22, b'0', 'common::file::FileInfo::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (291, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (292, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 19, b'0', 'system::role-menu-permission::SysRoleMenuPermission::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (293, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 18, b'0', 'system::menu-permission::SysMenuPermission::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (294, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 18, b'0', 'system::menu-permission::SysMenuPermission::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (295, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 6, b'0', 'system::permission::SysPermission::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (296, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 6, b'0', 'system::permission::SysPermission::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (297, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 5, b'0', 'system::user::SysUser::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (298, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 5, b'0', 'system::user::SysUser::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (299, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 4, b'0', 'system::sys-role::SysRole::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (300, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 4, b'0', 'system::sys-role::SysRole::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (301, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 3, b'0', 'system::user::MyInfo::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (302, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 3, b'0', 'system::user::MyInfo::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (303, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 40, 2, b'0', 'system::menu::SysMenu::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (304, '2022-09-14 11:32:15', 24, '1111', b'1', '2022-09-14 11:32:15', NULL, NULL, 0, 39, 2, b'0', 'system::menu::SysMenu::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (305, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 40, 29, b'0', 'business::device::DeviceAuth::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (306, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 39, 29, b'0', 'business::device::DeviceAuth::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (307, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 38, 29, b'0', 'business::device::DeviceAuth::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (308, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 37, 29, b'0', 'business::device::DeviceAuth::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (309, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 36, 29, b'0', 'business::device::DeviceAuth::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (310, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 35, 29, b'0', 'business::device::DeviceAuth::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (311, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 32, 29, b'0', 'business::device::DeviceAuth::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (312, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 31, 29, b'0', 'business::device::DeviceAuth::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (313, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 30, 29, b'0', 'business::device::DeviceAuth::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (314, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 29, 29, b'0', 'business::device::DeviceAuth::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (315, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 28, 29, b'0', 'business::device::DeviceAuth::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (316, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 27, 29, b'0', 'business::device::DeviceAuth::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (317, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 26, 29, b'1', 'business::device::DeviceAuth::DELETE');
INSERT INTO `sys_menu_permission` VALUES (318, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 25, 29, b'1', 'business::device::DeviceAuth::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (319, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 24, 29, b'0', 'business::device::DeviceAuth::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (320, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 23, 29, b'1', 'business::device::DeviceAuth::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (321, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 22, 29, b'1', 'business::device::DeviceAuth::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (322, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 21, 29, b'0', 'business::device::DeviceAuth::ADD');
INSERT INTO `sys_menu_permission` VALUES (323, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 40, 28, b'0', 'business::customer::CustomerInfo::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (324, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 39, 28, b'0', 'business::customer::CustomerInfo::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (325, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 38, 28, b'0', 'business::customer::CustomerInfo::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (326, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 37, 28, b'0', 'business::customer::CustomerInfo::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (327, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 36, 28, b'0', 'business::customer::CustomerInfo::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (328, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 35, 28, b'0', 'business::customer::CustomerInfo::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (329, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 32, 28, b'0', 'business::customer::CustomerInfo::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (330, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 31, 28, b'0', 'business::customer::CustomerInfo::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (331, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 30, 28, b'0', 'business::customer::CustomerInfo::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (332, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 29, 28, b'0', 'business::customer::CustomerInfo::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (333, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 28, 28, b'0', 'business::customer::CustomerInfo::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (334, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 27, 28, b'0', 'business::customer::CustomerInfo::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (335, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 26, 28, b'0', 'business::customer::CustomerInfo::DELETE');
INSERT INTO `sys_menu_permission` VALUES (336, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 25, 28, b'1', 'business::customer::CustomerInfo::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (337, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 24, 28, b'0', 'business::customer::CustomerInfo::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (338, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 23, 28, b'1', 'business::customer::CustomerInfo::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (339, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 22, 28, b'1', 'business::customer::CustomerInfo::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (340, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 21, 28, b'1', 'business::customer::CustomerInfo::ADD');
INSERT INTO `sys_menu_permission` VALUES (341, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 40, 30, b'0', 'common::data-log::DataLog::GRANT::DICT::TYPE::GO::TO::DICT::TREE');
INSERT INTO `sys_menu_permission` VALUES (342, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 39, 30, b'0', 'common::data-log::DataLog::GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_menu_permission` VALUES (343, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 38, 30, b'0', 'common::data-log::DataLog::EXPORT');
INSERT INTO `sys_menu_permission` VALUES (344, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 37, 30, b'0', 'common::data-log::DataLog::IMPORT');
INSERT INTO `sys_menu_permission` VALUES (345, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 36, 30, b'0', 'common::data-log::DataLog::DOWNLOAD');
INSERT INTO `sys_menu_permission` VALUES (346, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 35, 30, b'0', 'common::data-log::DataLog::UPLOAD');
INSERT INTO `sys_menu_permission` VALUES (347, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 32, 30, b'0', 'common::data-log::DataLog::GRANT::PAGE::PERMISSION');
INSERT INTO `sys_menu_permission` VALUES (348, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 31, 30, b'0', 'common::data-log::DataLog::ROLE::GRANT');
INSERT INTO `sys_menu_permission` VALUES (349, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 30, 30, b'0', 'common::data-log::DataLog::USER::RESET::PASSWORD');
INSERT INTO `sys_menu_permission` VALUES (350, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 29, 30, b'0', 'common::data-log::DataLog::USER::GRANTED::ROLE');
INSERT INTO `sys_menu_permission` VALUES (351, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 28, 30, b'0', 'common::data-log::DataLog::USER::GET::ON::LINE');
INSERT INTO `sys_menu_permission` VALUES (352, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 27, 30, b'0', 'common::data-log::DataLog::UPDATE::ENABLED');
INSERT INTO `sys_menu_permission` VALUES (353, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 26, 30, b'1', 'common::data-log::DataLog::DELETE');
INSERT INTO `sys_menu_permission` VALUES (354, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 25, 30, b'0', 'common::data-log::DataLog::QUERY::ID');
INSERT INTO `sys_menu_permission` VALUES (355, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 24, 30, b'0', 'common::data-log::DataLog::QUERY::LIST');
INSERT INTO `sys_menu_permission` VALUES (356, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 23, 30, b'1', 'common::data-log::DataLog::QUERY::PAGED');
INSERT INTO `sys_menu_permission` VALUES (357, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 22, 30, b'0', 'common::data-log::DataLog::MODIFY');
INSERT INTO `sys_menu_permission` VALUES (358, '2022-09-27 15:07:19', 24, '1111', b'1', '2022-09-27 15:07:19', NULL, NULL, 0, 21, 30, b'0', 'common::data-log::DataLog::ADD');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `permission` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (21, '2022-08-28 12:06:08', 19, 'admin', b'1', '2022-08-28 12:06:08', NULL, NULL, 0, '新增权限', 'ADD');
INSERT INTO `sys_permission` VALUES (22, '2022-08-28 12:06:49', 19, 'admin', b'1', '2022-08-28 12:06:49', NULL, NULL, 0, '修改权限', 'MODIFY');
INSERT INTO `sys_permission` VALUES (23, '2022-08-28 12:07:50', 19, 'admin', b'1', '2022-08-28 12:07:50', NULL, NULL, 0, '查询分页权限', 'QUERY::PAGED');
INSERT INTO `sys_permission` VALUES (24, '2022-08-28 12:08:33', 19, 'admin', b'1', '2022-08-28 12:08:33', NULL, NULL, 0, '查询列表权限', 'QUERY::LIST');
INSERT INTO `sys_permission` VALUES (25, '2022-08-28 12:10:21', 19, 'admin', b'1', '2022-08-28 12:10:21', NULL, NULL, 0, '根据ID获取数据权限', 'QUERY::ID');
INSERT INTO `sys_permission` VALUES (26, '2022-08-28 12:10:43', 19, 'admin', b'1', '2022-08-28 12:10:43', NULL, NULL, 0, '删除权限', 'DELETE');
INSERT INTO `sys_permission` VALUES (27, '2022-08-28 12:14:49', 19, 'admin', b'1', '2022-08-28 12:14:49', 19, 'admin', 1, '状态权限', 'UPDATE::ENABLED');
INSERT INTO `sys_permission` VALUES (28, '2022-08-28 12:15:45', 19, 'admin', b'1', '2022-08-28 12:15:45', NULL, NULL, 0, '获取所有在线用户权限', 'USER::GET::ON::LINE');
INSERT INTO `sys_permission` VALUES (29, '2022-08-28 12:17:28', 19, 'admin', b'1', '2022-08-28 12:17:28', NULL, NULL, 0, '用户设置角色权限', 'USER::GRANTED::ROLE');
INSERT INTO `sys_permission` VALUES (30, '2022-08-28 12:18:23', 19, 'admin', b'1', '2022-08-28 12:18:23', NULL, NULL, 0, '重置密码权限', 'USER::RESET::PASSWORD');
INSERT INTO `sys_permission` VALUES (31, '2022-08-28 13:16:53', 24, '1111', b'1', '2022-08-28 13:16:53', NULL, NULL, 0, '角色授权权限', 'ROLE::GRANT');
INSERT INTO `sys_permission` VALUES (32, '2022-08-28 15:13:34', 24, '1111', b'1', '2022-08-28 15:13:34', NULL, NULL, 0, '菜单页面权限', 'GRANT::PAGE::PERMISSION');
INSERT INTO `sys_permission` VALUES (35, '2022-09-13 13:40:58', 19, 'admin', b'1', '2022-09-13 13:40:58', NULL, NULL, 0, '上传权限', 'UPLOAD');
INSERT INTO `sys_permission` VALUES (36, '2022-09-13 13:41:04', 19, 'admin', b'1', '2022-09-13 13:41:04', NULL, NULL, 0, '下载权限', 'DOWNLOAD');
INSERT INTO `sys_permission` VALUES (37, '2022-09-13 13:41:10', 19, 'admin', b'1', '2022-09-13 13:41:10', NULL, NULL, 0, '导入权限', 'IMPORT');
INSERT INTO `sys_permission` VALUES (38, '2022-09-13 13:41:16', 19, 'admin', b'1', '2022-09-13 13:41:16', 24, '1111', 1, '导出权限', 'EXPORT');
INSERT INTO `sys_permission` VALUES (39, '2022-09-14 11:31:45', 24, '1111', b'1', '2022-09-14 11:31:45', NULL, NULL, 0, '授权字典列表权限', 'GRANT::DICT::TYPE::GO::TO::DICT::DATA');
INSERT INTO `sys_permission` VALUES (40, '2022-09-14 11:31:50', 24, '1111', b'1', '2022-09-14 11:31:50', NULL, NULL, 0, '授权字典树权限', 'GRANT::DICT::TYPE::GO::TO::DICT::TREE');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (16, '2022-08-19 09:38:48', 19, 'admin', b'1', '2022-08-19 09:38:48', NULL, NULL, 0, 'ADMIN', 'ADMIN');
INSERT INTO `sys_role` VALUES (17, '2022-08-27 14:38:12', 19, 'admin', b'1', '2022-08-27 14:38:12', 24, '1111', 1, 'Test', 'test');
INSERT INTO `sys_role` VALUES (18, '2022-08-29 20:36:30', 19, 'admin', b'0', '2022-08-29 20:36:30', 19, 'admin', 1, 'Test99', 'test11');

-- ----------------------------
-- Table structure for sys_role_menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_permission`;
CREATE TABLE `sys_role_menu_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `permission_id` int(11) NULL DEFAULT NULL,
  `menu_permission_id` int(11) NULL DEFAULT NULL,
  `checked` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 369 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu_permission
-- ----------------------------
INSERT INTO `sys_role_menu_permission` VALUES (231, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 19, 26, 111, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (232, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 19, 25, 112, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (233, '2022-08-28 12:21:42', 19, 'admin', b'0', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 19, 23, 114, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (234, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 19, 22, 115, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (235, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 19, 21, 116, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (236, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 19, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (237, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 18, 26, 121, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (238, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 18, 25, 122, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (239, '2022-08-28 12:21:42', 19, 'admin', b'0', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 18, 23, 124, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (240, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 18, 22, 125, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (241, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 18, 21, 126, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (242, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 18, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (243, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 6, 26, 131, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (244, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 6, 25, 132, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (245, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 6, 23, 134, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (246, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 6, 22, 135, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (247, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 6, 21, 136, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (248, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 6, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (249, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 5, 30, 137, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (250, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 5, 29, 138, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (251, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 5, 28, 139, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (252, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 5, 27, 140, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (253, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 5, 26, 141, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (254, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 5, 25, 142, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (255, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 5, 23, 144, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (256, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 5, 22, 145, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (257, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', 19, 'admin', 1, -1, 5, 21, 146, b'0');
INSERT INTO `sys_role_menu_permission` VALUES (258, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 5, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (259, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 4, 26, 151, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (260, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 4, 25, 152, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (261, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 4, 23, 154, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (262, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 4, 22, 155, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (263, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 4, 21, 156, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (264, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 4, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (265, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 3, 25, 162, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (266, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 3, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (267, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 2, 26, 171, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (268, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 2, 25, 172, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (269, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 2, 23, 174, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (270, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 2, 22, 175, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (271, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 2, 21, 176, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (272, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 2, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (273, '2022-08-28 12:21:42', 19, 'admin', b'1', '2022-08-28 12:21:42', NULL, NULL, 0, -1, 1, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (274, '2022-08-28 12:59:45', 19, 'admin', b'0', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 19, 26, 111, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (275, '2022-08-28 12:59:45', 19, 'admin', b'0', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 19, 25, 112, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (276, '2022-08-28 12:59:45', 19, 'admin', b'0', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 19, 23, 114, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (277, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 19, 22, 115, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (278, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 19, 21, 116, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (279, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 19, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (280, '2022-08-28 12:59:45', 19, 'admin', b'0', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 18, 26, 121, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (281, '2022-08-28 12:59:45', 19, 'admin', b'0', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 18, 25, 122, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (282, '2022-08-28 12:59:45', 19, 'admin', b'0', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 18, 23, 124, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (283, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 18, 22, 125, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (284, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 18, 21, 126, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (285, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 18, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (286, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 6, 26, 131, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (287, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 6, 25, 132, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (288, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 6, 23, 134, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (289, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 6, 22, 135, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (290, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 6, 21, 136, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (291, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 6, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (292, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 5, 30, 137, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (293, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 5, 29, 138, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (294, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 5, 28, 139, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (295, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', 24, '1111', 2, 16, 5, 27, 140, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (296, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 5, 26, 141, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (297, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 5, 25, 142, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (298, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', 19, 'admin', 2, 16, 5, 23, 144, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (299, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 5, 22, 145, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (300, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', 24, '1111', 5, 16, 5, 21, 146, b'0');
INSERT INTO `sys_role_menu_permission` VALUES (301, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 5, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (302, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 4, 26, 151, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (303, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 4, 25, 152, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (304, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 4, 23, 154, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (305, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 4, 22, 155, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (306, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 4, 21, 156, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (307, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 4, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (308, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 3, 25, 162, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (309, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 3, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (310, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 2, 26, 171, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (311, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 2, 25, 172, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (312, '2022-08-28 12:59:45', 19, 'admin', b'0', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 2, 23, 174, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (313, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 2, 22, 175, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (314, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 2, 21, 176, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (315, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 2, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (316, '2022-08-28 12:59:45', 19, 'admin', b'1', '2022-08-28 12:59:45', NULL, NULL, 0, 16, 1, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (317, '2022-08-29 09:17:02', 19, 'admin', b'1', '2022-08-29 09:17:02', NULL, NULL, 0, -1, 19, 24, 113, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (318, '2022-08-29 09:17:02', 19, 'admin', b'1', '2022-08-29 09:17:02', NULL, NULL, 0, -1, 18, 24, 123, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (319, '2022-08-29 09:17:02', 19, 'admin', b'1', '2022-08-29 09:17:02', NULL, NULL, 0, -1, 6, 24, 133, b'0');
INSERT INTO `sys_role_menu_permission` VALUES (320, '2022-08-29 09:21:41', 19, 'admin', b'1', '2022-08-29 09:21:41', NULL, NULL, 0, 16, 19, 24, 113, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (321, '2022-08-29 09:21:41', 19, 'admin', b'1', '2022-08-29 09:21:41', NULL, NULL, 0, 16, 18, 24, 123, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (322, '2022-08-29 09:21:41', 19, 'admin', b'1', '2022-08-29 09:21:41', 19, 'admin', 1, 16, 2, 24, 173, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (323, '2022-09-08 16:04:33', 19, 'admin', b'1', '2022-09-08 16:04:33', NULL, NULL, 0, 16, 22, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (324, '2022-09-08 16:04:33', 19, 'admin', b'1', '2022-09-08 16:04:33', NULL, NULL, 0, 16, 21, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (325, '2022-09-13 11:17:37', 19, 'admin', b'1', '2022-09-13 11:17:37', 19, 'admin', 1, 16, 25, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (326, '2022-09-13 11:17:37', 19, 'admin', b'1', '2022-09-13 11:17:37', 19, 'admin', 1, 16, 24, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (327, '2022-09-13 11:17:37', 19, 'admin', b'1', '2022-09-13 11:17:37', 19, 'admin', 1, 16, 23, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (328, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 25, 26, 201, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (329, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 25, 25, 202, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (330, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 25, 23, 204, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (331, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 25, 22, 205, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (332, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 25, 21, 206, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (333, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 24, 26, 217, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (334, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 24, 25, 218, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (335, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 24, 23, 220, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (336, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 24, 22, 221, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (337, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 24, 21, 222, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (338, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 23, 26, 233, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (339, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 23, 25, 234, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (340, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 23, 23, 236, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (341, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 23, 22, 237, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (342, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 23, 21, 238, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (343, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 22, 36, 241, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (344, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', 24, '1111', 2, 16, 22, 35, 242, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (345, '2022-09-13 14:45:06', 19, 'admin', b'1', '2022-09-13 14:45:06', NULL, NULL, 0, 16, 4, 31, 186, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (346, '2022-09-13 14:50:11', 19, 'admin', b'1', '2022-09-13 14:50:11', NULL, NULL, 0, 16, 2, 32, 189, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (347, '2022-09-13 17:21:49', 19, 'admin', b'1', '2022-09-13 17:21:49', NULL, NULL, 0, 16, 22, 26, 249, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (348, '2022-09-13 17:34:52', 19, 'admin', b'1', '2022-09-13 17:34:52', NULL, NULL, 0, 16, 22, 23, 252, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (349, '2022-09-14 11:32:38', 24, '1111', b'1', '2022-09-14 11:32:38', NULL, NULL, 0, 16, 23, 40, 287, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (350, '2022-09-14 11:32:38', 24, '1111', b'1', '2022-09-14 11:32:38', NULL, NULL, 0, 16, 23, 39, 288, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (351, '2022-09-14 11:40:03', 24, '1111', b'1', '2022-09-14 11:40:03', NULL, NULL, 0, 16, 25, 24, 203, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (352, '2022-09-14 15:12:44', 24, '1111', b'1', '2022-09-14 15:12:44', 24, '1111', 2, 16, 25, 27, 200, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (353, '2022-09-14 15:12:44', 24, '1111', b'1', '2022-09-14 15:12:44', NULL, NULL, 0, 16, 24, 27, 216, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (354, '2022-09-14 15:12:44', 24, '1111', b'1', '2022-09-14 15:12:44', NULL, NULL, 0, 16, 23, 27, 232, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (355, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 29, 26, 317, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (356, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 29, 25, 318, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (357, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 29, 23, 320, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (358, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 29, 22, 321, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (359, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 29, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (360, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 28, 25, 336, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (361, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 28, 23, 338, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (362, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 28, 22, 339, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (363, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 28, 21, 340, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (364, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 28, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (365, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 27, NULL, NULL, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (366, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 30, 26, 353, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (367, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 30, 23, 356, b'1');
INSERT INTO `sys_role_menu_permission` VALUES (368, '2022-09-27 15:07:57', 24, '1111', b'1', '2022-09-27 15:07:57', NULL, NULL, 0, 16, 30, NULL, NULL, b'1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `sex` int(11) NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `head_img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(11) NOT NULL,
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '2021-12-20 15:13:02', -1, 'anonymous', b'1', '2021-12-22 09:19:55', -1, 'anonymous', 11, '用户1', '21232f297a57a5a743894a0e4a801fc3', '用户1', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (2, '2021-12-20 15:13:23', -1, 'anonymous', b'1', '2021-12-21 15:16:33', -1, 'anonymous', 1, '用户2', '21232f297a57a5a743894a0e4a801fc3', '用户2', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (3, '2021-12-20 15:18:00', -1, 'anonymous', b'1', '2021-12-22 09:26:41', -1, 'anonymous', 2, '用户3', '21232f297a57a5a743894a0e4a801fc3', '用户3', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (4, '2021-12-20 15:20:47', -1, 'anonymous', b'1', '2021-12-20 15:20:47', -1, NULL, 0, '用户4', '21232f297a57a5a743894a0e4a801fc3', '用户4', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (5, '2021-12-20 15:29:13', -1, 'anonymous', b'1', '2021-12-22 08:57:17', -1, 'anonymous', 2, '用户1-1', '21232f297a57a5a743894a0e4a801fc3', '用户1-1', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (6, '2021-12-20 15:29:43', -1, 'anonymous', b'1', '2021-12-22 09:24:34', -1, 'anonymous', 1, '用户1-2', '21232f297a57a5a743894a0e4a801fc3', '用户1-2', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (7, '2021-12-20 15:30:08', -1, 'anonymous', b'1', '2021-12-22 08:55:31', -1, 'anonymous', 1, '用户1-3', '21232f297a57a5a743894a0e4a801fc3', '用户1-3', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (8, '2021-12-20 15:51:03', -1, 'anonymous', b'1', '2021-12-22 08:55:50', -1, 'anonymous', 1, '用户1-4', '21232f297a57a5a743894a0e4a801fc3', '用户1-4', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (9, '2021-12-20 15:51:45', -1, 'anonymous', b'1', '2021-12-22 09:25:00', -1, 'anonymous', 1, '用户1-5', '21232f297a57a5a743894a0e4a801fc3', '用户1-5', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (10, '2021-12-20 15:52:12', -1, 'anonymous', b'1', '2021-12-22 08:56:14', -1, 'anonymous', 1, '用户1-6', '21232f297a57a5a743894a0e4a801fc3', '用户1-6', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (11, '2021-12-20 15:53:13', -1, 'anonymous', b'0', '2021-12-20 15:53:13', -1, NULL, 0, '用户1-7', '21232f297a57a5a743894a0e4a801fc3', '用户1-7', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (12, '2021-12-20 15:54:05', -1, 'anonymous', b'0', '2021-12-20 15:54:05', -1, NULL, 0, '用户1-8', '21232f297a57a5a743894a0e4a801fc3', '用户1-8', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (13, '2021-12-20 15:54:31', -1, 'anonymous', b'1', '2021-12-20 15:54:31', -1, NULL, 0, '用户1-9', '21232f297a57a5a743894a0e4a801fc3', '用户1-9', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (14, '2021-12-20 16:04:08', -1, 'anonymous', b'1', '2021-12-20 16:04:08', -1, NULL, 0, '用户21', '21232f297a57a5a743894a0e4a801fc3', '用户21', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (15, '2021-12-20 16:04:28', -1, 'anonymous', b'1', '2021-12-20 16:04:28', -1, NULL, 0, '用户22', '21232f297a57a5a743894a0e4a801fc3', '用户22', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (16, '2021-12-20 17:25:16', -1, 'anonymous', b'1', '2021-12-22 09:26:34', -1, 'anonymous', 1, '用户31', '21232f297a57a5a743894a0e4a801fc3', '用户31', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (17, '2021-12-22 09:21:34', -1, 'anonymous', b'0', '2021-12-22 09:23:26', -1, 'anonymous', 1, '去微软', '21232f297a57a5a743894a0e4a801fc3', 'qwer', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (18, '2021-12-22 09:28:50', -1, 'anonymous', b'1', '2021-12-22 09:28:50', -1, NULL, 0, '用户4-1', '21232f297a57a5a743894a0e4a801fc3', '用户4-1', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (19, '2021-12-22 13:54:58', -1, 'anonymous', b'1', '2021-12-22 13:54:58', -1, 'admin', 4, 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', b'1', 0, NULL, NULL, 1, NULL, NULL);
INSERT INTO `sys_user` VALUES (20, '2021-12-24 09:43:04', -1, 'admin', b'0', '2021-12-24 09:44:20', -1, 'admin', 1, '管理员1', '21232f297a57a5a743894a0e4a801fc3', 'admin1', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (21, '2021-12-24 09:44:55', -1, 'admin', b'1', '2021-12-24 09:44:55', -1, NULL, 0, 'qwer', '21232f297a57a5a743894a0e4a801fc3', 'qwer', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (22, '2022-01-25 16:49:07', -1, 'anonymous', b'1', '2022-01-25 16:49:07', -1, NULL, 0, 'admin89', '21232f297a57a5a743894a0e4a801fc3', 'admin89', b'1', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (23, '2022-01-27 09:12:19', -1, 'anonymous', b'1', '2022-02-23 18:01:56', -1, 'admin', 19, 'admin88', '21232f297a57a5a743894a0e4a801fc3', 'admin88', b'1', 1, '13524555395', '', 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (24, '2022-02-23 16:39:22', -1, 'admin', b'1', '2022-02-23 16:42:00', -1, 'admin', 20, '1111', 'e10adc3949ba59abbe56e057f20f883e', '1111', b'1', 1, '13524555395', '', 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (25, '2022-08-29 20:34:12', 19, 'admin', b'1', '2022-08-29 20:34:12', 19, 'admin', 18, 'Test', 'e10adc3949ba59abbe56e057f20f883e', 'Test', b'1', 1, '13524555395', NULL, 0, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `user_id` int(11) NOT NULL COMMENT '用户Id',
  `group_id` int(11) NOT NULL COMMENT '组Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (18, '2022-08-29 20:33:44', 19, 'admin', b'1', NULL, NULL, NULL, 0, 24, 16);
INSERT INTO `sys_user_role` VALUES (23, '2022-09-14 10:37:05', 24, '1111', b'1', NULL, NULL, NULL, 0, 25, 16);
SET FOREIGN_KEY_CHECKS = 1;