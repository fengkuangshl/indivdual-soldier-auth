/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1-3307
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 127.0.0.1:3307
 Source Schema         : individual-soldier-auth

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 27/09/2022 15:49:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for device_auth
-- ----------------------------
DROP TABLE IF EXISTS `device_auth`;
CREATE TABLE `device_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `api_Level` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'android APILevel',
  `density_Dpi` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '屏幕对角线的像素值/对角线的尺寸',
  `height_Pixels` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高',
  `width_Pixels` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '宽',
  `android_Id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'androidId',
  `board` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主板名称',
  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂商名称',
  `build_Time` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司电话',
  `finger_Print` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '硬件识别码',
  `hardware` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '硬件名称',
  `mac_Address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Mac地址',
  `radio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '无线电固件版本号',
  `serial_Number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'serialNumber',
  `software_Version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '软件版本',
  `auth_Code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权code',
  `unique_Code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备唯一码',
  `verify_Code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '验证码',
  `expire_Device_Date` datetime NULL DEFAULT NULL COMMENT '客户设备授权到期日',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device_auth
-- ----------------------------
INSERT INTO `device_auth` VALUES (1, '2022-09-23 13:49:41', 1, '1', b'1', '2022-09-23 13:49:48', 1, '1', 25, '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', 'HZ05CD', '6512bd43d9caa6e02c990b0a82652dca', 'HZ05CD', '2022-09-30 13:50:05');

-- ----------------------------
-- Table structure for device_customer_info
-- ----------------------------
DROP TABLE IF EXISTS `device_customer_info`;
CREATE TABLE `device_customer_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `sequence` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户编号',
  `auth_device_code` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权码',
  `expire_device_date` datetime NULL DEFAULT NULL COMMENT '客户设备授权到期日',
  `auth_device_Num` int(3) NOT NULL COMMENT '授权设备数',
  `is_verify` bit(1) NOT NULL COMMENT '是否校验日期：1-启用，0-禁用',
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司名称',
  `company_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司地址',
  `company_phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司电话',
  `lead_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人姓名',
  `lead_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人手机',
  `lead_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人座机',
  `project_no` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目号',
  `project_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device_customer_info
-- ----------------------------
INSERT INTO `device_customer_info` VALUES (16, '2022-09-22 09:29:44', 19, 'admin', b'0', '2022-09-22 09:29:44', 19, 'admin', 2, 'CNO.604270886857125889', 'CXD5', '2022-09-23 00:00:00', 10, b'1', '2323', '23', '23', '23', '13523456781', '23', 'QR19002', 'XXXXXX');
INSERT INTO `device_customer_info` VALUES (17, '2022-09-22 16:15:47', 19, 'admin', b'1', '2022-09-22 16:15:47', 19, 'admin', 30, 'CNO.604373073608237058', 'TEST', '2022-09-30 00:00:00', 100, b'1', '当elementUi自动升级到2.15.6以上版本时,浏览器控制台会出现此报错', 'TEST8', 'TEST', 'TEST', '13524555395', '', 'TEST', 'TEST');
INSERT INTO `device_customer_info` VALUES (18, '2022-09-23 10:00:50', 19, 'admin', b'1', '2022-09-23 10:00:50', 19, 'admin', 9, 'CNO.604641103579889665', 'HZ05CD', NULL, 100, b'0', '上海奇融信息科技有限公司', '陕西省西安市高新区丈八路街道宝德云谷', '029XXXX', 'D@Z', '13456789023', '', 'QR091200', '杭州地铁信息服务项目');
INSERT INTO `device_customer_info` VALUES (19, '2022-09-23 10:04:52', 19, 'admin', b'1', '2022-09-23 10:04:52', NULL, NULL, 0, 'CNO.604642117141839873', '998cd', '2022-09-29 00:00:00', 2, b'1', '998', '998', '998', '998', '13234567890', '', '998', '998');
INSERT INTO `device_customer_info` VALUES (20, '2022-09-23 10:05:34', 19, 'admin', b'1', '2022-09-23 10:05:34', NULL, NULL, 0, 'CNO.604642291599720450', '1231', NULL, 1, b'0', '123', '123', '123', '123', '13123456789', '', '123', '123');
INSERT INTO `device_customer_info` VALUES (21, '2022-09-27 14:37:06', 19, 'admin', b'1', '2022-09-27 14:37:06', NULL, NULL, 0, 'CNO.606160180316098562', '9999', '2022-09-30 00:00:00', 999, b'1', '999', '999', '999', '999', '13599999999', '', '999', '999');
INSERT INTO `device_customer_info` VALUES (22, '2022-09-27 15:14:37', 24, '1111', b'1', '2022-09-27 15:14:37', 24, '1111', 1, 'CNO.606169619861491713', '8888', '2022-09-30 00:00:00', 888, b'1', '888999', '888', '888', '888', '13488888888', '', '888', '888');

-- ----------------------------
-- Table structure for sys_chunk_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_chunk_file`;
CREATE TABLE `sys_chunk_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `chunk_number` int(3) NOT NULL COMMENT '当前文件块，从1开始',
  `chunk_size` int(11) NOT NULL COMMENT '分块大小',
  `current_chunk_size` int(11) NOT NULL COMMENT '当前分块大小',
  `total_Size` int(18) NOT NULL COMMENT '总大小',
  `total_chunks` int(3) NOT NULL COMMENT '总块数',
  `identifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件标识',
  `chunk_File_Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
  `biz_Type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务类型',
  `physical_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物理路径',
  `relative_Path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '相对路径',
  `file_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4195 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_chunk_file
-- ----------------------------

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
-- Table structure for sys_dic_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic_data`;
CREATE TABLE `sys_dic_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
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
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
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
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
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
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者Id',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `enable_flag` bit(1) NOT NULL COMMENT '是否删除1:正常，0：删除',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新者Id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者名称',
  `version` int(3) NOT NULL COMMENT '版本号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content_Type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `biz_Type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `size` int(11) NULL DEFAULT NULL,
  `physical_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file_info
-- ----------------------------
INSERT INTO `sys_file_info` VALUES (122, '2022-09-20 17:48:49', 19, 'admin', b'1', '2022-09-20 17:48:49', NULL, NULL, 0, 'SW_DVD5_Visio_Pro_2013_64Bit_ChnSimp_MLF_X18-61013.ISO', '081eb32b010f71d0c67ca95b8f513de7', '', 'default', 589129728, '\\individual-soldier-auth\\default\\2022\\9\\20\\SW_DVD5_Visio_Pro_2013_64Bit_ChnSimp_MLF_X18-61013.ISO', '\\default\\2022\\9\\20\\SW_DVD5_Visio_Pro_2013_64Bit_ChnSimp_MLF_X18-61013.ISO');
INSERT INTO `sys_file_info` VALUES (123, '2022-09-20 17:51:34', 19, 'admin', b'1', '2022-09-20 17:51:34', NULL, NULL, 0, '说明.txt', 'ca319e2d29136d81ffb086221815c666', 'text/plain', 'default', 374, '\\individual-soldier-auth\\default\\2022\\9\\20\\说明.txt', '\\default\\2022\\9\\20\\说明.txt');
INSERT INTO `sys_file_info` VALUES (124, '2022-09-20 17:51:39', 19, 'admin', b'1', '2022-09-20 17:51:39', NULL, NULL, 0, 'VNO3RI2V', 'f01a1f190d4cc153700861379f0b3664', '', 'default', 1882, '\\individual-soldier-auth\\default\\2022\\9\\20\\VNO3RI2V', '\\default\\2022\\9\\20\\VNO3RI2V');
INSERT INTO `sys_file_info` VALUES (125, '2022-09-21 10:47:30', 24, '1111', b'1', '2022-09-21 10:47:30', NULL, NULL, 0, 'java_error_in_idea_4588.log', '5e7e665ab371913434d80ebab229c9d5', '', 'default', 59489, '\\individual-soldier-auth\\default\\2022\\9\\21\\java_error_in_idea_4588.log', '\\default\\2022\\9\\21\\java_error_in_idea_4588.log');
INSERT INTO `sys_file_info` VALUES (126, '2022-09-21 13:50:54', 24, '1111', b'1', '2022-09-21 13:50:54', NULL, NULL, 0, '年报模板 (3).xlsx', 'f23aa07a067b981642f477440a568aac', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'default', 11280, '\\individual-soldier-auth\\default\\2022\\9\\21\\年报模板 (3).xlsx', '\\default\\2022\\9\\21\\年报模板 (3).xlsx');
INSERT INTO `sys_file_info` VALUES (127, '2022-09-21 13:50:54', 24, '1111', b'1', '2022-09-21 13:50:54', NULL, NULL, 0, '月报导出 (4).xlsx', '87d28d0635aa1198127926c33be4c4ea', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'default', 11430, '\\individual-soldier-auth\\default\\2022\\9\\21\\月报导出 (4).xlsx', '\\default\\2022\\9\\21\\月报导出 (4).xlsx');
INSERT INTO `sys_file_info` VALUES (128, '2022-09-24 18:27:16', 19, 'admin', b'1', '2022-09-24 18:27:16', NULL, NULL, 0, '上海奇融&杭州-统计分析项目-功能设计说明书FDS-V1.0_20220426.docx', 'b72a03855f62ad073ea5687fce542fd1', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'default', 607548, '\\individual-soldier-auth\\default\\2022\\9\\24\\上海奇融&杭州-统计分析项目-功能设计说明书FDS-V1.0_20220426.docx', '\\default\\2022\\9\\24\\上海奇融&杭州-统计分析项目-功能设计说明书FDS-V1.0_20220426.docx');

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
