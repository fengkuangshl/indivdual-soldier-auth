DROP DATABASE IF EXISTS `individual-soldier-auth`;
CREATE DATABASE `individual-soldier-auth`;
USE `individual-soldier-auth`;
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
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (21, '2022-08-28 12:06:08', 19, 'admin', b'1', '2022-08-28 12:06:08', NULL, NULL, 0, '新增权限', 'ADD');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (22, '2022-08-28 12:06:49', 19, 'admin', b'1', '2022-08-28 12:06:49', NULL, NULL, 0, '修改权限', 'MODIFY');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (23, '2022-08-28 12:07:50', 19, 'admin', b'1', '2022-08-28 12:07:50', NULL, NULL, 0, '查询分页权限', 'QUERY::PAGED');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (24, '2022-08-28 12:08:33', 19, 'admin', b'1', '2022-08-28 12:08:33', NULL, NULL, 0, '查询列表权限', 'QUERY::LIST');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (25, '2022-08-28 12:10:21', 19, 'admin', b'1', '2022-08-28 12:10:21', NULL, NULL, 0, '根据ID获取数据权限', 'QUERY::ID');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (26, '2022-08-28 12:10:43', 19, 'admin', b'1', '2022-08-28 12:10:43', NULL, NULL, 0, '删除权限', 'DELETE');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (27, '2022-08-28 12:14:49', 19, 'admin', b'1', '2022-08-28 12:14:49', 19, 'admin', 1, '修改用户状态权限', 'USER::UPDATE::ENABLED');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (28, '2022-08-28 12:15:45', 19, 'admin', b'1', '2022-08-28 12:15:45', NULL, NULL, 0, '获所有在线用户权限', 'USER::GET::ON::LINE');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (29, '2022-08-28 12:17:28', 19, 'admin', b'1', '2022-08-28 12:17:28', NULL, NULL, 0, '用户设置角色权限', 'USER::GRANTED::ROLE');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (30, '2022-08-28 12:18:23', 19, 'admin', b'1', '2022-08-28 12:18:23', NULL, NULL, 0, '重置密码权限', 'USER::RESET::PASSWORD');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (31, '2022-08-28 13:16:53', 24, '1111', b'1', '2022-08-28 13:16:53', NULL, NULL, 0, '授权权限', 'ROLE::GRANT');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (32, '2022-08-28 15:13:34', 24, '1111', b'1', '2022-08-28 15:13:34', NULL, NULL, 0, '菜单页面权限', 'GRANT::PAGE::PERMISSION');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (35, '2022-09-13 13:40:58', 19, 'admin', b'1', '2022-09-13 13:40:58', NULL, NULL, 0, '上传权限', 'UPLOAD');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (36, '2022-09-13 13:41:04', 19, 'admin', b'1', '2022-09-13 13:41:04', NULL, NULL, 0, '下载权限', 'DOWNLOAD');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (37, '2022-09-13 13:41:10', 19, 'admin', b'1', '2022-09-13 13:41:10', NULL, NULL, 0, '导入权限', 'IMPORT');
INSERT INTO `sys_permission` (`id`, `create_date`, `create_user_id`, `create_user_name`, `enable_flag`, `update_date`, `update_user_id`, `update_user_name`, `version`, `name`, `permission`) VALUES (38, '2022-09-13 13:41:16', 19, 'admin', b'1', '2022-09-13 13:41:16', NULL, NULL, 0, '导出权限', 'EXPORT');
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
-- ---------sys_menu_permission-------------------------
DROP TABLE IF EXISTS `sys_menu_permission`;
CREATE TABLE `sys_menu_permission` (
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
   `menu_id` int(11) NOT NULL,
   `checked` bit not null,
   `permission_code` varchar(60) NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
-- ----------------------------------------------------
-- ---------sys_role_menu_permission-------------------------
DROP TABLE IF EXISTS `sys_role_menu_permission`;
CREATE TABLE `sys_role_menu_permission` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
   `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
   `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
   `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
   `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
   `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
   `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
   `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
   `version` int(3) NOT NULL COMMENT '版本号',
   `role_id` int(11) NOT NULL,
   `menu_id` int(11) NOT NULL,
   `permission_id` int(11) DEFAULT NULL,
   `menu_permission_id` int(11) DEFAULT NULL,
   `checked` bit DEFAULT null,
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
-- ---------sys_file_info-----------------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info` (
     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
     `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
     `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
     `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
     `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
     `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
     `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
     `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
     `version` int(3) NOT NULL COMMENT '版本号',
     `name` VARCHAR (255),
     `md5` VARCHAR (255),
     `content_Type` VARCHAR (255),
     `biz_Type` VARCHAR (255),
     `size` INT,
     `physical_path` VARCHAR (500),
     `path` VARCHAR (500),
     PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4;
-- ---------------------------------------------------------
-- ---------sys_file_info-----------------------------------
DROP TABLE IF EXISTS `sys_chunk_file`;
CREATE TABLE `sys_chunk_file` (
     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
     `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
     `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
     `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
     `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
     `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
     `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
     `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
     `version` int(3) NOT NULL COMMENT '版本号',
     `chunk_number` int(3) NOT NULL COMMENT '当前文件块，从1开始',
     `chunk_size` int(11) NOT NULL COMMENT '分块大小',
     `current_chunk_size` int(11) NOT NULL COMMENT '当前分块大小',
     `total_Size` int(11) NOT NULL COMMENT '总大小',
     `total_chunks` int(3) NOT NULL COMMENT '总块数',
     `identifier` VARCHAR (255) NOT NULL COMMENT '文件标识',
     `chunk_File_Name` VARCHAR (255) NOT NULL COMMENT '文件名',
     `biz_Type` VARCHAR (255)  NOT NULL COMMENT '业务类型',
     `physical_path` VARCHAR (500)  NOT NULL COMMENT '物理路径',
     `relative_Path` VARCHAR (500)  NOT NULL COMMENT '相对路径',
     `file_type`  VARCHAR (255),
     PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4;
-- ---------------------------------------------------------
-- ---------sys_dic_type-----------------------------------
DROP TABLE IF EXISTS `sys_dic_type`;
CREATE TABLE `sys_dic_type` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
    `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
    `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
    `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
    `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
    `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
    `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
    `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
    `version` int(3) NOT NULL COMMENT '版本号',
    `name` VARCHAR (255),
    `code` VARCHAR (255),
    `type` INT,
    `status`  bit(1),
    `remark` VARCHAR (2000),
    PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4;
-- ---------------------------------------------------------
-- ---------sys_dic_data-----------------------------------
DROP TABLE IF EXISTS `sys_dic_data`;
CREATE TABLE `sys_dic_data` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
    `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
    `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
    `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
    `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
    `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
    `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
    `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
    `version` int(3) NOT NULL COMMENT '版本号',
    `label` VARCHAR (255),
    `type` VARCHAR (255),
    `sort` int(11),
    `is_Default` bit(1),
    `status` bit(1),
    `value` VARCHAR (255),
    `attr1` VARCHAR (255),
    `attr2` VARCHAR (255),
    `attr3` VARCHAR (255),
    `attr4` VARCHAR (255),
    `attr5` VARCHAR (255),
    `remark` VARCHAR (2000),
    PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4;
-- ---------------------------------------------------------
-- ---------sys_dict_tree-----------------------------------
DROP TABLE IF EXISTS `sys_dict_tree`;
CREATE TABLE `sys_dict_tree` (
     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
     `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
     `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
     `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
     `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
     `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
     `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
     `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
     `version` int(3) NOT NULL COMMENT '版本号',
     `label` VARCHAR (255),
     `type` VARCHAR (36),
     `sort` int(11),
     `parent_Id` VARCHAR (36),
     `value` VARCHAR (255),
     `status` bit(1),
     `cascade_code` VARCHAR (255),
     `attr1` VARCHAR (255),
     `attr2` VARCHAR (255),
     `attr3` VARCHAR (255),
     `attr4` VARCHAR (255),
     `attr5` VARCHAR (255),
     `remark` VARCHAR (2000),
     PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4;
-- ---------------------------------------------------------
-- ---------DEVICE_CUSTOMER_INFO-----------------------------------
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
-- ---------------------------------------------------------
-- ---------DEVICE_AUTH-----------------------------------
DROP TABLE IF EXISTS `DEVICE_AUTH`;
CREATE TABLE `DEVICE_AUTH` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
    `create_date` datetime  DEFAULT NULL COMMENT '创建时间',
    `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
    `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建者名称',
    `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
    `update_date` datetime  DEFAULT NULL COMMENT '更新时间',
    `update_user_id` int(11)  DEFAULT NULL COMMENT '更新者Id',
    `update_user_name` varchar(255)  DEFAULT NULL COMMENT '更新者名称',
    `version` int(3) NOT NULL COMMENT '版本号',
    `api_Level` varchar(25) NOT NULL COMMENT 'android APILevel',
    `density_Dpi` varchar(50) NOT NULL COMMENT '屏幕对角线的像素值/对角线的尺寸',
    `height_Pixels` varchar(25)  DEFAULT NULL COMMENT  '高',
    `width_Pixels` varchar(25) NOT NULL COMMENT '宽',
    `android_Id` varchar(255) NOT NULL COMMENT 'androidId',
    `board` varchar(255) NOT NULL COMMENT '主板名称',
    `brand` varchar(255) DEFAULT NULL COMMENT '厂商名称',
    `build_Time` varchar(15) DEFAULT NULL COMMENT '公司电话',
    `finger_Print` varchar(255) DEFAULT NULL COMMENT '硬件识别码',
    `hard_ware` varchar(255) DEFAULT NULL COMMENT '硬件名称',
    `mac_Address` varchar(255) DEFAULT NULL COMMENT 'Mac地址',
    `radio` varchar(255) NOT NULL COMMENT '无线电固件版本号',
    `serial_Number` varchar(255) DEFAULT NULL COMMENT 'serialNumber',
    `software_Version` varchar(255) DEFAULT NULL COMMENT '软件版本',
    `auth_Code` varchar(25) DEFAULT NULL COMMENT '授权code',
    `unique_Code` varchar(255) DEFAULT NULL COMMENT '设备唯一码',
    `verify_Code` varchar(255) DEFAULT NULL COMMENT '验证码',
    `expire_Device_Date` datetime DEFAULT NULL COMMENT '客户设备授权到期日',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;