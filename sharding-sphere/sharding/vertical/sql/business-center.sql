SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE business_center;
USE business_center;



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
SET FOREIGN_KEY_CHECKS = 1;