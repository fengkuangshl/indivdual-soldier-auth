
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dic_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic_data`;
CREATE TABLE `sys_dic_data`  (
  `id` BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `is_Default` bit(1) NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attr1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attr3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attr4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attr5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remark` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dic_data
-- ----------------------------
INSERT INTO `sys_dic_data` VALUES (16, '2022-09-11 13:21:37', 19, 'admin', b'1', '2022-09-11 13:21:37', 24, '1111', 2, 'qwq', '18', 0, b'0', b'1', 'qqw', '', '', '', '', '', '');
INSERT INTO `sys_dic_data` VALUES (17, '2022-09-13 15:30:07', 24, '1111', b'0', '2022-09-13 15:30:07', 24, '1111', 1, 'qqw', '19', 0, b'0', b'1', 'wq', '', '', '', '', '', '');
INSERT INTO `sys_dic_data` VALUES (18, '2022-09-14 11:40:47', 24, '1111', b'1', '2022-09-14 11:40:47', 24, '1111', 4, '99', '18', 0, b'0', b'1', '99', '', '', '', '', '', '');

-- ----------------------------
-- Table structure for sys_dic_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic_type`;
CREATE TABLE `sys_dic_type`  (
  `id` BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `remark` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dic_type
-- ----------------------------
INSERT INTO `sys_dic_type` VALUES (16, '2022-09-09 10:36:41', 19, 'admin', b'0', '2022-09-09 10:36:41', 19, 'admin', 1, 'Test', 'Test', 1, 0, 'Test');
INSERT INTO `sys_dic_type` VALUES (17, '2022-09-09 10:46:55', 19, 'admin', b'1', '2022-09-09 10:46:55', 19, 'admin', 4, 'Test1231', 'Test', 1, 1, '123123');
INSERT INTO `sys_dic_type` VALUES (18, '2022-09-09 11:00:00', 19, 'admin', b'1', '2022-09-09 11:00:00', 19, 'admin', 7, '123123', '123', 0, 1, '12312312');
INSERT INTO `sys_dic_type` VALUES (19, '2022-09-13 15:29:54', 24, '1111', b'0', '2022-09-13 15:29:54', NULL, NULL, 0, 'qwqwqw', 'qwwqwq', 0, 1, '');

-- ----------------------------
-- Table structure for sys_dict_tree
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_tree`;
CREATE TABLE `sys_dict_tree`  (
  `id` BIGINT NOT NULL  AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `parent_Id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `cascade_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attr1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attr3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attr4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attr5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remark` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_tree
-- ----------------------------
INSERT INTO `sys_dict_tree` VALUES (16, '2022-09-11 13:16:40', 19, 'admin', b'1', '2022-09-11 13:16:40', 19, 'admin', 4, 'Test', '17', 0, '-1', 'Test', b'1', 'Test', '', '', '', '', '', '');
INSERT INTO `sys_dict_tree` VALUES (17, '2022-09-11 13:16:53', 19, 'admin', b'1', '2022-09-11 13:16:53', 19, 'admin', 29, '21', '17', 0, '16', '21', b'1', 'Test::21', '', '', '', '', '', '');
INSERT INTO `sys_dict_tree` VALUES (18, '2022-09-11 14:21:02', 19, 'admin', b'1', '2022-09-11 14:21:02', 19, 'admin', 9, '2111', '17', 0, '17', '2111', b'1', 'Test::21::2111', '', '', '', '', '', '');
INSERT INTO `sys_dict_tree` VALUES (19, '2022-09-11 14:23:18', 19, 'admin', b'1', '2022-09-11 14:23:18', 19, 'admin', 6, '4322', '17', 0, '16', '5432', b'1', 'Test::5432', '', '', '', '', '', '');
INSERT INTO `sys_dict_tree` VALUES (20, '2022-09-11 14:39:35', 19, 'admin', b'1', '2022-09-11 14:39:35', 19, 'admin', 1, '322234qq', '17', 0, '17', '23453', b'1', 'Test::21::23453', '', '', '', '', '', '');
INSERT INTO `sys_dict_tree` VALUES (21, '2022-09-11 14:40:46', 19, 'admin', b'1', '2022-09-11 14:40:46', 19, 'admin', 61, '788', '17', 0, '17', '1vvvvvv', b'1', 'Test::21::1vvvvvv', '', '', '', '', '', '');
INSERT INTO `sys_dict_tree` VALUES (22, '2022-09-11 16:09:32', 19, 'admin', b'1', '2022-09-11 16:09:32', 19, 'admin', 4, 'etert', '17', 0, '19', 'ert', b'1', 'Test::5432::ert', '', '', '', '', '', '');
INSERT INTO `sys_dict_tree` VALUES (23, '2022-09-11 16:10:09', 19, 'admin', b'1', '2022-09-11 16:10:09', 19, 'admin', 2, 'dfdd', '17', 0, '19', 'vvf', b'1', 'Test::5432::vvf', '', '', '', '', '', '');
INSERT INTO `sys_dict_tree` VALUES (24, '2022-09-11 16:11:48', 19, 'admin', b'1', '2022-09-11 16:11:48', 19, 'admin', 77, 'ddd', '17', 0, '22', 'sdd', b'1', 'Test::5432::ert::sdd', '', '', '', '', '', '');
INSERT INTO `sys_dict_tree` VALUES (25, '2022-09-13 17:05:29', 19, 'admin', b'1', '2022-09-13 17:05:29', 19, 'admin', 4, '1232221111', '17', 1000, '17', '123', b'1', 'Test::21::123', '', '', '', '', '', '');
SET FOREIGN_KEY_CHECKS = 1;