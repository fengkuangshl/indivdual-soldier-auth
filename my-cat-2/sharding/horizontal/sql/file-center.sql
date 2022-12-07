SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE file_center;
USE file_center;
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


SET FOREIGN_KEY_CHECKS = 1;