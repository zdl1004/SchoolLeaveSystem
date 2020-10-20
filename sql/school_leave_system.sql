/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : school_leave_system

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 21/04/2020 09:28:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` int(1) NOT NULL COMMENT '专业内分班编号，如软件1班',
  `grade_id` int(11) NOT NULL COMMENT '所属年级',
  `major_id` int(11) NOT NULL COMMENT '所属专业',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `class_ibfk_1`(`grade_id`) USING BTREE,
  INDEX `class_ibfk_2`(`major_id`) USING BTREE,
  CONSTRAINT `clazz_ibfk_1` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `clazz_ibfk_2` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '班级表\r\n/*\r\nBEGIN\r\n    SET @count = (SELECT COUNT(*) FROM class WHERE grade_year = NEW.grade_year AND major_id = NEW.major_id AND no = NEW.no);\r\n    IF @count != 0 THEN\r\n        SIGNAL SQLSTATE \'45000\'\r\n SET MESSAGE_TEXT = \'试图往班级表中插入重复的班级\';\r\n    END IF;\r\nEND\r' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES (4, 1, 3, 3);
INSERT INTO `clazz` VALUES (7, 1, 2, 4);
INSERT INTO `clazz` VALUES (8, 2, 2, 4);
INSERT INTO `clazz` VALUES (9, 1, 2, 3);
INSERT INTO `clazz` VALUES (10, 1, 2, 5);
INSERT INTO `clazz` VALUES (11, 2, 2, 5);
INSERT INTO `clazz` VALUES (12, 1, 3, 5);
INSERT INTO `clazz` VALUES (13, 2, 3, 5);
INSERT INTO `clazz` VALUES (14, 1, 2, 6);
INSERT INTO `clazz` VALUES (15, 2, 2, 6);
INSERT INTO `clazz` VALUES (26, 2, 2, 3);
INSERT INTO `clazz` VALUES (27, 1, 4, 3);
INSERT INTO `clazz` VALUES (28, 2, 4, 3);
INSERT INTO `clazz` VALUES (29, 1, 5, 3);
INSERT INTO `clazz` VALUES (30, 1, 3, 4);
INSERT INTO `clazz` VALUES (31, 2, 3, 4);
INSERT INTO `clazz` VALUES (32, 1, 4, 4);
INSERT INTO `clazz` VALUES (33, 2, 4, 4);
INSERT INTO `clazz` VALUES (34, 1, 5, 4);
INSERT INTO `clazz` VALUES (35, 2, 5, 4);
INSERT INTO `clazz` VALUES (36, 1, 4, 5);
INSERT INTO `clazz` VALUES (37, 2, 4, 5);
INSERT INTO `clazz` VALUES (46, 1, 5, 5);
INSERT INTO `clazz` VALUES (47, 2, 5, 5);
INSERT INTO `clazz` VALUES (48, 2, 5, 3);
INSERT INTO `clazz` VALUES (49, 2, 2, 9);

-- ----------------------------
-- Table structure for collage
-- ----------------------------
DROP TABLE IF EXISTS `collage`;
CREATE TABLE `collage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学院表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of collage
-- ----------------------------
INSERT INTO `collage` VALUES (2, '计算机与信息工程学院');
INSERT INTO `collage` VALUES (3, '地理信息与旅游学院');
INSERT INTO `collage` VALUES (4, '机械与电气工程学院');
INSERT INTO `collage` VALUES (5, '材料与化学工程学院');
INSERT INTO `collage` VALUES (6, '生物与食品工程学院');
INSERT INTO `collage` VALUES (7, '土木与建筑工程学院');
INSERT INTO `collage` VALUES (8, '数学与金融学院');
INSERT INTO `collage` VALUES (9, '经济与管理学院');
INSERT INTO `collage` VALUES (10, '文学与传媒学院');
INSERT INTO `collage` VALUES (11, '外国语学院');
INSERT INTO `collage` VALUES (12, '教育科学学院');
INSERT INTO `collage` VALUES (13, '音乐学院');
INSERT INTO `collage` VALUES (14, '美术与设计学院');
INSERT INTO `collage` VALUES (15, '体育学院');
INSERT INTO `collage` VALUES (16, '马克思主义学院');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(5) NOT NULL COMMENT '年级的年份',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '年级表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (2, 2016);
INSERT INTO `grade` VALUES (3, 2017);
INSERT INTO `grade` VALUES (4, 2018);
INSERT INTO `grade` VALUES (5, 2019);

-- ----------------------------
-- Table structure for leave
-- ----------------------------
DROP TABLE IF EXISTS `leave`;
CREATE TABLE `leave`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '请假用户的id',
  `clazz_id` int(11) NOT NULL COMMENT '请假学生请假当时的班级id',
  `telephone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请假学生请假时的电话',
  `start_date` date NOT NULL COMMENT '请假的开始日期',
  `start_lesson` int(2) NOT NULL COMMENT '从第X节课开始请假',
  `end_date` date NOT NULL COMMENT '请假的结束日期',
  `end_lesson` int(2) NOT NULL COMMENT '请假到第X节课为止',
  `reason` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请假理由',
  `create_time` datetime(0) NOT NULL COMMENT '请假申请创建的时间',
  `type` int(1) NOT NULL COMMENT '假条状态\r\n0-等待审核\r\n1-未通过\r\n2-通过',
  `reviewer_id` int(11) NULL DEFAULT NULL COMMENT '审核人的id',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审核的时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `reviewer_id`(`reviewer_id`) USING BTREE,
  INDEX `clazz_id`(`clazz_id`) USING BTREE,
  CONSTRAINT `leave_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `leave_ibfk_2` FOREIGN KEY (`reviewer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `leave_ibfk_3` FOREIGN KEY (`clazz_id`) REFERENCES `clazz` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of leave
-- ----------------------------
INSERT INTO `leave` VALUES (1, 64, 4, '17856006821', '2020-02-17', 1, '2020-02-17', 2, '感冒，需要输液', '2020-02-17 19:40:01', 2, NULL, NULL);
INSERT INTO `leave` VALUES (2, 64, 4, '17856006821', '2020-02-17', 3, '2020-02-17', 4, '嗓子疼，请假', '2020-02-17 21:28:01', 2, NULL, NULL);
INSERT INTO `leave` VALUES (3, 64, 4, '17856006821', '2020-02-17', 3, '2020-02-17', 4, '嗓子疼，请假', '2020-02-17 21:28:01', 0, NULL, NULL);
INSERT INTO `leave` VALUES (4, 64, 4, '17856006821', '2020-02-18', 3, '2020-02-18', 4, '嗓子疼，请假', '2020-02-18 21:28:01', 1, NULL, NULL);
INSERT INTO `leave` VALUES (5, 64, 4, '17856006821', '2020-02-19', 3, '2020-02-19', 4, '嗓子疼，请假', '2020-02-19 21:28:01', 2, NULL, NULL);
INSERT INTO `leave` VALUES (6, 64, 4, '17856006821', '2020-02-20', 3, '2020-02-20', 4, '嗓子疼，请假', '2020-02-20 21:28:01', 1, NULL, NULL);
INSERT INTO `leave` VALUES (7, 64, 4, '17856006821', '2020-02-21', 3, '2020-02-21', 4, '嗓子疼，请假', '2020-02-21 21:28:01', 1, NULL, NULL);
INSERT INTO `leave` VALUES (8, 64, 4, '17856006821', '2020-03-17', 1, '2020-02-17', 2, '感冒，需要输液', '2020-02-17 19:40:01', 2, NULL, NULL);
INSERT INTO `leave` VALUES (9, 64, 4, '17856006821', '2020-03-18', 3, '2020-02-17', 4, '嗓子疼，请假', '2020-02-17 21:28:01', 2, NULL, NULL);
INSERT INTO `leave` VALUES (10, 121, 4, '17856006822', '2020-03-19', 3, '2020-02-18', 4, '嗓子疼，请假', '2020-02-18 21:28:01', 2, NULL, NULL);
INSERT INTO `leave` VALUES (11, 122, 4, '17856006823', '2020-03-20', 3, '2020-02-19', 4, '嗓子疼，请假', '2020-02-19 21:28:01', 2, NULL, NULL);
INSERT INTO `leave` VALUES (12, 121, 4, '17856006824', '2020-03-21', 3, '2020-02-20', 4, '嗓子疼，请假', '2020-02-20 21:28:01', 1, NULL, NULL);
INSERT INTO `leave` VALUES (13, 124, 4, '17856006825', '2020-03-22', 3, '2020-02-21', 4, '嗓子疼，请假', '2020-02-21 21:28:01', 0, NULL, NULL);
INSERT INTO `leave` VALUES (14, 125, 4, '17856006826', '2020-03-23', 3, '2020-02-22', 4, '嗓子疼，请假', '2020-02-22 21:28:01', 2, NULL, NULL);
INSERT INTO `leave` VALUES (15, 126, 4, '17856006827', '2020-03-24', 3, '2020-02-23', 4, '嗓子疼，请假', '2020-02-23 21:28:01', 1, NULL, NULL);
INSERT INTO `leave` VALUES (16, 121, 4, '17856006828', '2020-03-25', 3, '2020-02-24', 4, '嗓子疼，请假', '2020-02-24 21:28:01', 1, NULL, NULL);
INSERT INTO `leave` VALUES (26, 64, 4, '17856006821', '2020-04-02', 1, '2020-04-02', 4, '身体不适,需要医院就诊', '2020-04-01 20:13:02', 0, NULL, NULL);
INSERT INTO `leave` VALUES (27, 64, 4, '17856006821', '2020-04-13', 1, '2020-04-20', 1, '因身体不适，特请假检查身体', '2020-04-13 16:54:18', 0, NULL, NULL);
INSERT INTO `leave` VALUES (29, 64, 4, '17856006821', '2020-04-14', 1, '2020-05-14', 1, '身体发生重大变故，需要入院观察', '2020-04-14 16:26:58', 0, NULL, NULL);

-- ----------------------------
-- Table structure for leave_image
-- ----------------------------
DROP TABLE IF EXISTS `leave_image`;
CREATE TABLE `leave_image`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `leave_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `leave_id`(`leave_id`) USING BTREE,
  CONSTRAINT `leave_image_ibfk_1` FOREIGN KEY (`leave_id`) REFERENCES `leave` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of leave_image
-- ----------------------------
INSERT INTO `leave_image` VALUES (1, 'upload/46979be2e1da940b8e957e27096d820e8bf2268f6e34c862e5b719bbb6b86a9c.jpg', 1);
INSERT INTO `leave_image` VALUES (2, '9b1def3d31c99fbca45fc3b2a45f54d065d5da6a4e6e179eeb7466374e7db965.png', 2);
INSERT INTO `leave_image` VALUES (3, 'upload/30a758339d83764442ed005beda59dba8600d7850085859a9d25ca3a92578031.jpg', 26);
INSERT INTO `leave_image` VALUES (4, '2a6a65951a0d58460c9398e622e711902f2d6efdd878dc07c304f90a2005c980.png', 27);
INSERT INTO `leave_image` VALUES (6, 'upload/6852cabbaef9524fb8e1572f6545337771726acfff0f3c07e1947c78665240e3.jpg', 29);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `action` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业名称',
  `collage_id` int(11) NOT NULL COMMENT '所属学院',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `belong_collage_id`(`collage_id`) USING BTREE,
  CONSTRAINT `major_ibfk_1` FOREIGN KEY (`collage_id`) REFERENCES `collage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '专业表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (3, '通信工程', 2);
INSERT INTO `major` VALUES (4, '计算机科学与技术', 2);
INSERT INTO `major` VALUES (5, '空间信息与技术', 2);
INSERT INTO `major` VALUES (6, '软件工程', 2);
INSERT INTO `major` VALUES (7, '网络工程', 2);
INSERT INTO `major` VALUES (9, '导航工程', 3);

-- ----------------------------
-- Table structure for permission_clazz
-- ----------------------------
DROP TABLE IF EXISTS `permission_clazz`;
CREATE TABLE `permission_clazz`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '给予权限的用户id',
  `clazz_id` int(11) NOT NULL COMMENT '给予X班级的权限',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `class_id`(`clazz_id`) USING BTREE,
  CONSTRAINT `permission_clazz_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `permission_clazz_ibfk_2` FOREIGN KEY (`clazz_id`) REFERENCES `clazz` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission_clazz
-- ----------------------------
INSERT INTO `permission_clazz` VALUES (21, 27, 4);
INSERT INTO `permission_clazz` VALUES (22, 26, 9);

-- ----------------------------
-- Table structure for permission_collage
-- ----------------------------
DROP TABLE IF EXISTS `permission_collage`;
CREATE TABLE `permission_collage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '给予权限的用户id',
  `collage_id` int(11) NOT NULL COMMENT '给予X学院的权限',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `collage_id`(`collage_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `permission_collage_ibfk_1` FOREIGN KEY (`collage_id`) REFERENCES `collage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `permission_collage_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission_collage
-- ----------------------------
INSERT INTO `permission_collage` VALUES (2, 2, 2);
INSERT INTO `permission_collage` VALUES (3, 3, 3);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'sha256加密后的密码',
  `type` int(1) NOT NULL COMMENT '用户类型\r\n0-超级管理员\r\n1-院级管理员\r\n2-班级管理员\r\n3-普通用户（学生）',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `telephone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `clazz_id` int(11) NULL DEFAULT NULL,
  `client_token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端保持登录验证用的token',
  `client_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端的唯一id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `clazz_id`(`clazz_id`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`clazz_id`) REFERENCES `clazz` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '20200202', 'db2b1f87ea0a95fdaa2b321ceb6b571c5730d30532fc5e018a156fd6d6e9f6f1', 0, '滁州学院', '15156232982', NULL, NULL, NULL);
INSERT INTO `user` VALUES (2, '20200001', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d7b', 1, '计算机与信息工程学院', '15156232982', NULL, NULL, NULL);
INSERT INTO `user` VALUES (3, '20200002', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d8b', 1, '地理信息与旅游学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (4, '20200003', 'db2b1f87ea0a95fdaa2b321ceb6b571c5730d30532fc5e018a156fd6d6e9f6f1', 3, '机械与电气工程学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (5, '20200004', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d10b', 3, '材料与化学工程学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (6, '20200005', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d11b', 3, '生物与食品工程学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (7, '20200006', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d12b', 3, '土木与建筑工程学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (8, '20200007', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d13b', 3, '数学与金融学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (9, '20200008', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d14b', 3, '经济与管理学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (10, '20200009', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d15b', 3, '文学与传媒学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (11, '20200010', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d16b', 3, '外国语学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (12, '20200011', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d17b', 3, '教育科学学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (13, '20200012', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d18b', 3, '音乐学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (14, '20200013', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d19b', 3, '美术与设计学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (15, '20200014', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d20b', 3, '体育学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (16, '20200015', '617211b33c16fd99e95cf27278ce93d93713880ea573b5d3e7a6d547573a6d21b', 3, '马克思主义学院', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (17, '20201000', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级物联网1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (18, '20201001', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级物联网2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (19, '20201002', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '17级物联网1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (20, '20201003', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '17级物联网2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (21, '20201004', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级物联网1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (22, '20201005', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级物联网2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (23, '20201006', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级物联网1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (24, '20201007', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级物联网2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (25, '20201008', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级通信工程1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (26, '20201009', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 2, '16级通信工程2班管理员', '', 9, NULL, NULL);
INSERT INTO `user` VALUES (27, '20201010', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 2, '17级通信工程1班管理员', '', 4, NULL, NULL);
INSERT INTO `user` VALUES (28, '20201011', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级通信工程1班管理员', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (29, '20201012', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级通信工程2班管理员', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (30, '20201013', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级通信工程1班管理员', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (31, '20201014', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级通信工程2班管理员', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (32, '20201015', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级计算机科学与技术1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (33, '20201016', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级计算机科学与技术2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (34, '20201017', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '17级计算机科学与技术1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (35, '20201018', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '17级计算机科学与技术2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (36, '20201019', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级计算机科学与技术1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (37, '20201020', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级计算机科学与技术2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (38, '20201021', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级计算机科学与技术1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (39, '20201022', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级计算机科学与技术2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (40, '20201023', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级空间信息与技术1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (41, '20201024', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级空间信息与技术2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (42, '20201025', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '17级空间信息与技术1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (43, '20201026', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '17级空间信息与技术2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (44, '20201027', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级空间信息与技术1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (45, '20201028', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级空间信息与技术2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (46, '20201029', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级空间信息与技术1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (47, '20201030', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级空间信息与技术2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (48, '20201031', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级软件工程1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (49, '20201032', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级软件工程2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (50, '20201033', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '17级软件工程1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (51, '20201034', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '17级软件工程2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (52, '20201035', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级软件工程1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (53, '20201036', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级软件工程2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (54, '20201037', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级软件工程1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (55, '20201038', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级软件工程2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (56, '20201039', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级网络工程1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (57, '20201040', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '16级网络工程2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (58, '20201041', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '17级网络工程1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (59, '20201042', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '17级网络工程2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (60, '20201043', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级网络工程1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (61, '20201044', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '18级网络工程2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (62, '20201045', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级网络工程1班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (63, '20201046', '1227dc705f15a35671234f31ae33378e508bdc1703202a2a1d6a624b3a607d22', 3, '19级网络工程2班', '', NULL, NULL, NULL);
INSERT INTO `user` VALUES (64, '20209999', '0c72bf07ab0255d6fed9d55ca8353f640bebc4187d04894399415fe98383f51c', 3, '张得良', '17856006821', 4, NULL, NULL);
INSERT INTO `user` VALUES (121, '2017211856', 'babbfacc45347ac0bc0f011377f0f7c788f687d5987ed03c5574c4301e50a2b1', 3, '张三', '15156232981', 4, NULL, NULL);
INSERT INTO `user` VALUES (122, '2017211857', 'babbfacc45347ac0bc0f011377f0f7c788f687d5987ed03c5574c4301e50a2b1', 3, '李四', '15156232982', 4, NULL, NULL);
INSERT INTO `user` VALUES (123, '2017211858', 'babbfacc45347ac0bc0f011377f0f7c788f687d5987ed03c5574c4301e50a2b1', 3, '王五', '15156232983', 4, NULL, NULL);
INSERT INTO `user` VALUES (124, '2017211859', 'babbfacc45347ac0bc0f011377f0f7c788f687d5987ed03c5574c4301e50a2b1', 3, '刘能', '15156232984', 4, NULL, NULL);
INSERT INTO `user` VALUES (125, '2017211860', 'babbfacc45347ac0bc0f011377f0f7c788f687d5987ed03c5574c4301e50a2b1', 3, '张飞', '15156232985', 4, NULL, NULL);
INSERT INTO `user` VALUES (126, '2017211861', 'babbfacc45347ac0bc0f011377f0f7c788f687d5987ed03c5574c4301e50a2b1', 3, '齐云', '15156232986', 4, NULL, NULL);
INSERT INTO `user` VALUES (127, '2017211862', 'babbfacc45347ac0bc0f011377f0f7c788f687d5987ed03c5574c4301e50a2b1', 3, '李阳', '15156232987', 4, NULL, NULL);
INSERT INTO `user` VALUES (128, '2017211884', 'db2b1f87ea0a95fdaa2b321ceb6b571c5730d30532fc5e018a156fd6d6e9f6f1', 3, '少帅', '17856075782', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
