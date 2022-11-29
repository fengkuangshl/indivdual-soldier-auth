SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


CREATE DATABASE log_center;
USE log_center;

-- ----------------------------
-- Table structure for sys_data_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_log`;
CREATE TABLE `sys_data_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `content` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fk_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_log
-- ----------------------------
INSERT INTO `sys_data_log` VALUES (1, '2022-09-27 14:26:17', 1, '1', b'0', '2022-09-27 14:26:24', NULL, NULL, 1, '1', '1');
INSERT INTO `sys_data_log` VALUES (105, '2022-09-27 14:19:59', 19, 'admin', b'1', '2022-09-27 14:19:59', NULL, NULL, 0, '修改表：[DEVICE_CUSTOMER_INFO]id：[18]\n把字段[expireDeviceDate]从[Fri Sep 30 00:00:00 CST 2022]改为[Fri Oct 07 00:00:00 CST 2022]', 'DEVICE_CUSTOMER_INFO::18');
INSERT INTO `sys_data_log` VALUES (106, '2022-09-27 14:20:09', 19, 'admin', b'1', '2022-09-27 14:20:09', NULL, NULL, 0, '修改表：[DEVICE_CUSTOMER_INFO]id：[18]\n把字段[authDeviceNum]从[10]改为[100]', 'DEVICE_CUSTOMER_INFO::18');
INSERT INTO `sys_data_log` VALUES (107, '2022-09-27 14:20:59', 19, 'admin', b'1', '2022-09-27 14:20:59', NULL, NULL, 0, '更新设备授权到期日期：2022-09-30]', 'DEVICE_AUTH::1');
INSERT INTO `sys_data_log` VALUES (108, '2022-09-27 14:20:59', 19, 'admin', b'1', '2022-09-27 14:20:59', NULL, NULL, 0, '正常授权下发，需要校验日期[2022-09-30]', 'DEVICE_AUTH::1');
INSERT INTO `sys_data_log` VALUES (109, '2022-09-27 14:31:55', 19, 'admin', b'1', '2022-09-27 14:31:55', NULL, NULL, 0, '修改表：[DEVICE_CUSTOMER_INFO]id：[18]\n把字段[expireDeviceDate]从[Fri Oct 07 00:00:00 CST 2022]改为[null]\n把字段[isVerify]从[true]改为[false]', 'DEVICE_CUSTOMER_INFO::18');
INSERT INTO `sys_data_log` VALUES (110, '2022-09-27 14:37:07', 19, 'admin', b'1', '2022-09-27 14:37:07', NULL, NULL, 0, '新插入数据：[DEVICE_CUSTOMER_INFO]\n{\"sequence\":\"CNO.606160180316098562\",\"authDeviceCode\":\"9999\",\"expireDeviceDate\":\"2022-09-3000:00:00.0\",\"authDeviceNum\":\"999\",\"isVerify\":\"1\",\"companyName\":\"999\",\"companyAddress\":\"999\",\"companyPhone\":\"999\",\"leadName\":\"999\",\"leadMobile\":\"13599999999\",\"leadPhone\":\"\",\"projectNo\":\"999\",\"projectName\":\"999\",\"createDate\":\"2022-09-2714:37:06.436\",\"updateDate\":\"2022-09-2714:37:06.436\",\"createUserId\":\"19\",\"enableFlag\":\"1\",\"createUserName\":\"admin\",\"version\":\"0\",\"id\":\"21\"}', 'DEVICE_CUSTOMER_INFO::21');
INSERT INTO `sys_data_log` VALUES (111, '2022-09-27 15:14:37', 24, '1111', b'1', '2022-09-27 15:14:37', NULL, NULL, 0, '新插入数据：[DEVICE_CUSTOMER_INFO]\n{\"sequence\":\"CNO.606169619861491713\",\"authDeviceCode\":\"8888\",\"expireDeviceDate\":\"2022-09-3000:00:00.0\",\"authDeviceNum\":\"888\",\"isVerify\":\"1\",\"companyName\":\"888\",\"companyAddress\":\"888\",\"companyPhone\":\"888\",\"leadName\":\"888\",\"leadMobile\":\"13488888888\",\"leadPhone\":\"\",\"projectNo\":\"888\",\"projectName\":\"888\",\"createDate\":\"2022-09-2715:14:37.269\",\"updateDate\":\"2022-09-2715:14:37.269\",\"createUserId\":\"24\",\"enableFlag\":\"1\",\"createUserName\":\"1111\",\"version\":\"0\",\"id\":\"22\"}', 'DEVICE_CUSTOMER_INFO::22');
INSERT INTO `sys_data_log` VALUES (112, '2022-09-27 15:15:07', 24, '1111', b'1', '2022-09-27 15:15:07', NULL, NULL, 0, '修改表：[DEVICE_CUSTOMER_INFO]id：[22]\n把字段[companyName]从[888]改为[888999]', 'DEVICE_CUSTOMER_INFO::22');
INSERT INTO `sys_data_log` VALUES (113, '2022-09-27 15:27:40', 24, '1111', b'1', '2022-09-27 15:27:40', NULL, NULL, 0, '修改表：[DEVICE_CUSTOMER_INFO]id：[17]\n把字段[companyAddress]从[TEST]改为[TEST8]', 'DEVICE_CUSTOMER_INFO::17');
-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
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
   `module` VARCHAR (255) COMMENT '模块名',
   `params` text COMMENT '方法参数',
   `remark` text COMMENT '备注',
   `flag` TINYINT (1) NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;