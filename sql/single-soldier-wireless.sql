DROP DATABASE IF EXISTS `indivdual-soldier-auth`;
CREATE DATABASE `indivdual-soldier-auth`;
USE `indivdual-soldier-auth`;
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `user_name` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `enabled` bit not null,
  `sex` int not null,
  `phone` varchar (255),
  `head_img_url` varchar (255),
  `type` int not null,
  `sign` varchar (255) DEFAULT NULL,
  `email` varchar (255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (1, '2021-12-20 15:13:02',  -1, 'anonymous', '', '2021-12-22 09:19:55', -1, 'anonymous', 7, '用户1', '21232f297a57a5a743894a0e4a801fc3', '用户1', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (2, '2021-12-20 15:13:23',  -1, 'anonymous', '', '2021-12-21 15:16:33', -1, 'anonymous', 1, '用户2', '21232f297a57a5a743894a0e4a801fc3', '用户2', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (3, '2021-12-20 15:18:00',  -1, 'anonymous', '', '2021-12-22 09:26:41', -1, 'anonymous', 2, '用户3', '21232f297a57a5a743894a0e4a801fc3', '用户3', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (4, '2021-12-20 15:20:47',  -1, 'anonymous', '', '2021-12-20 15:20:47', -1, NULL, 0, '用户4', '21232f297a57a5a743894a0e4a801fc3', '用户4', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (5, '2021-12-20 15:29:13',  -1, 'anonymous', '', '2021-12-22 08:57:17', -1, 'anonymous', 2, '用户1-1', '21232f297a57a5a743894a0e4a801fc3', '用户1-1', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (6, '2021-12-20 15:29:43',  -1, 'anonymous', '', '2021-12-22 09:24:34', -1, 'anonymous', 1, '用户1-2', '21232f297a57a5a743894a0e4a801fc3', '用户1-2', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (7, '2021-12-20 15:30:08',  -1, 'anonymous', '', '2021-12-22 08:55:31', -1, 'anonymous', 1, '用户1-3', '21232f297a57a5a743894a0e4a801fc3', '用户1-3', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (8, '2021-12-20 15:51:03',  -1, 'anonymous', '', '2021-12-22 08:55:50', -1, 'anonymous', 1, '用户1-4', '21232f297a57a5a743894a0e4a801fc3', '用户1-4', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (9, '2021-12-20 15:51:45',  -1, 'anonymous', '', '2021-12-22 09:25:00', -1, 'anonymous', 1, '用户1-5', '21232f297a57a5a743894a0e4a801fc3', '用户1-5', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (10, '2021-12-20 15:52:12', -1, 'anonymous', '', '2021-12-22 08:56:14', -1, 'anonymous', 1, '用户1-6', '21232f297a57a5a743894a0e4a801fc3', '用户1-6', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (11, '2021-12-20 15:53:13', -1, 'anonymous', '', '2021-12-20 15:53:13', -1, NULL, 0, '用户1-7', '21232f297a57a5a743894a0e4a801fc3', '用户1-7', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (12, '2021-12-20 15:54:05', -1, 'anonymous', '', '2021-12-20 15:54:05', -1, NULL, 0, '用户1-8', '21232f297a57a5a743894a0e4a801fc3', '用户1-8', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (13, '2021-12-20 15:54:31', -1, 'anonymous', '', '2021-12-20 15:54:31', -1, NULL, 0, '用户1-9', '21232f297a57a5a743894a0e4a801fc3', '用户1-9', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (14, '2021-12-20 16:04:08', -1, 'anonymous', '', '2021-12-20 16:04:08', -1, NULL, 0, '用户21', '21232f297a57a5a743894a0e4a801fc3', '用户21', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (15, '2021-12-20 16:04:28', -1, 'anonymous', '', '2021-12-20 16:04:28', -1, NULL, 0, '用户22', '21232f297a57a5a743894a0e4a801fc3', '用户22', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (16, '2021-12-20 17:25:16', -1, 'anonymous', '', '2021-12-22 09:26:34', -1, 'anonymous', 1, '用户31', '21232f297a57a5a743894a0e4a801fc3', '用户31', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (17, '2021-12-22 09:21:34', -1, 'anonymous', '', '2021-12-22 09:23:26', -1, 'anonymous', 1, '去微软', '21232f297a57a5a743894a0e4a801fc3', 'qwer', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (18, '2021-12-22 09:28:50', -1, 'anonymous', '', '2021-12-22 09:28:50', -1, NULL, 0, '用户4-1', '21232f297a57a5a743894a0e4a801fc3', '用户4-1', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (19, '2021-12-22 13:54:58', -1, 'anonymous', '', '2021-12-22 13:54:58', -1, 'admin', 4, 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', '', 0, NULL, NULL, 1);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (20, '2021-12-24 09:43:04', -1, 'admin', '', '2021-12-24 09:44:20', -1, 'admin', 1, '管理员1', '21232f297a57a5a743894a0e4a801fc3', 'admin1', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (21, '2021-12-24 09:44:55', -1, 'admin', '', '2021-12-24 09:44:55', -1, NULL, 0, 'qwer', '21232f297a57a5a743894a0e4a801fc3', 'qwer', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (22, '2022-1-25 16:49:07', -1, 'anonymous', '', '2022-1-25 16:49:07', -1, NULL, 0, 'admin89', '21232f297a57a5a743894a0e4a801fc3', 'admin89', '', 0, NULL, NULL, 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (23, '2022-1-27 09:12:19', -1, 'anonymous', '', '2022-2-23 18:01:56', -1, 'admin', 19, 'admin88', '21232f297a57a5a743894a0e4a801fc3', 'admin88', '', 1, '13524555395', '', 0);
INSERT INTO `sys_user` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `user_name`, `password`, `nick_name`, `enabled`, `sex`, `phone`, `head_img_url`, `type`) VALUES (24, '2022-2-23 16:39:22', -1, 'admin', '', '2022-2-23 16:42:00', -1, 'admin', 7, '1111', '21232f297a57a5a743894a0e4a801fc3', '1111', '', 1, '13524555395', '', 0);
 -- -----------------------------------
 -- ---------sys_role-------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `name` varchar(60) NOT NULL,
  `code` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
 -- ------------------------------------------
 -- --------sys_user_role-------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
 -- -----------------------------------------------
 -- ---------sys_permission-------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `name` varchar(60) NOT NULL,
  `permission` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
 -- ----------------------------------------------------
 -- ---------sys_role_permission-------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `permission_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
 -- ----------------------------------------------------
 -- --------sys_menu-----------------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `parent_id` int(11)NOT NULL,
  `name` varchar(64) NOT NULL,
  `title` varchar(64) NOT NULL,
  `url` varchar(1024) DEFAULT NULL,
  `path` varchar(1024) DEFAULT NULL,
  `css` varchar(32) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `is_menu` int(11) DEFAULT NULL COMMENT '是否菜单 1 是 2 不是',
  `is_hidden` int(11) DEFAULT NULL COMMENT '是否隐藏,0 false 1 true',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
INSERT INTO `sys_menu` VALUES (1, '2021-12-02 11:27:17', -1, 'admin', b'1', '2021-12-02 11:27:17', NULL, NULL, 0, '-1', '后台管理', '', 'javascript:;', '', 'el-icon-setting', -100, 1, 0);
INSERT INTO `sys_menu` VALUES (2, '2021-12-02 11:28:47', -1, 'admin', b'1', '2021-12-02 11:28:47', -1, 'admin', 0, 1, '菜单管理', '', '/sysmenu', 'system/menu/SysMenu', 'el-icon-user-solid', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (3, '2021-12-02 13:22:05', -1, 'admin', b'1', '2021-12-02 13:22:05', -1, 'admin', 1, 1, '我的信息', '', '/myinfo', 'system/user/MyInfo', '', 99, 1, 1);
INSERT INTO `sys_menu` VALUES (4, '2021-12-02 13:23:04', -1, 'admin', b'1', '2021-12-02 13:23:04', -1, 'admin', 0, 1, '角色管理', '', '/sysrole', 'system/sys-role/SysRole', 'el-icon-user-solid', 2, 1, 0);
INSERT INTO `sys_menu` VALUES (5, '2021-12-02 13:23:50', -1, 'admin', b'1', '2021-12-02 13:23:50', -1, 'admin', 0, 1, '用户管理', '', '/user', 'system/user/SysUser', 'el-icon-user-solid', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (6, '2021-12-02 13:24:24', -1, 'admin', b'1', '2021-12-02 13:24:24', -1, 'admin', 0, 1, '权限管理', '', '/permission', 'system/permission/SysPermission', 'el-icon-user-solid', 4, 1, 0);
 -- -------------------------------------------------------
 -- ---------sys_role_menu-----------------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `menu_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
 -- -------------------------------------------------------
 -- ---------sys_group------------------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `code` varchar(50) NOT NULL,
  `name` varchar(60) NOT NULL,
  `parent_Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
 -- ---------------------------------------------------------
 -- ---------sys_user_group-----------------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `user_id` int(11) NOT NULL COMMENT '用户Id',
  `group_id` int(11) NOT NULL COMMENT '组Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
-- ---------------------------------------------------------
-- -----------------------sys_log----------------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
   `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
   `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
   `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
   `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
   `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
   `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
   `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
   `version` int(3) NOT NULL COMMENT '版本号',
   `module` VARCHAR (3000) COMMENT '模块名',
   `params` text COMMENT '方法参数',
   `remark` text COMMENT '备注',
   `flag` TINYINT (1) NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4;

-- ---------------------------------------------------------
-- ---------data_log-----------------------------------
DROP TABLE IF EXISTS `sys_data_log`;
CREATE TABLE `sys_data_log` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
    `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
    `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
    `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
    `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
    `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
    `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
    `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
    `version` int(3) NOT NULL COMMENT '版本号',
    `content` VARCHAR(2000),
    `fk_id` VARCHAR(255),
    PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4;
-- ---------------------------------------------------------
-- ---------sys_user_group-----------------------------------
DROP TABLE IF EXISTS `DEVICE_CUSTOMER_INFO`;
CREATE TABLE `DEVICE_CUSTOMER_INFO` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `sequence` varchar(25) NOT NULL COMMENT '客户编号',
  `auth_device_code` varchar(8) NOT NULL COMMENT '授权码',
  `expire_device_date` datetime  DEFAULT NULL COMMENT  '客户设备授权到期日',
  `auth_device_Num` int(3) NOT NULL COMMENT '授权设备数',
  `enabled_verification` bit(1) NOT NULL COMMENT '是否校验日期：1-启用，0-禁用',
  `company_name` varchar(50) NOT NULL COMMENT '公司名称',
  `company_address` varchar(255) DEFAULT NULL COMMENT '公司地址',
  `company_phone` varchar(15) DEFAULT NULL COMMENT '公司电话',
  `lead_name` varchar(255) DEFAULT NULL COMMENT '负责人姓名',
  `lead_mobile` varchar(255) DEFAULT NULL COMMENT '负责人手机',
  `lead_phone` varchar(255) DEFAULT NULL COMMENT '负责人座机',
  `project_no` varchar(25) NOT NULL COMMENT '项目号',
  `project_name` varchar(25) DEFAULT NULL COMMENT '项目名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;