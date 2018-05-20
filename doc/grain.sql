/*
Navicat MySQL Data Transfer

Source Server         : 阿里云
Source Server Version : 50173
Source Host           : 106.15.89.145:3306
Source Database       : grain

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2018-05-20 20:42:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('1', '薇薇', '15950698318', null, '2018-05-20 20:37:44');

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `in_no` varchar(30) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `member_name` varchar(50) DEFAULT NULL,
  `gross` int(11) DEFAULT NULL,
  `tare` int(11) DEFAULT NULL,
  `suttle` int(11) DEFAULT NULL,
  `price` decimal(10,1) DEFAULT NULL,
  `money` decimal(10,1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `state` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES ('1', 'D863C172C26318052020', '1', null, '1000', '200', '800', '1.2', '960.0', '2018-05-20 20:39:21', '2018-05-20 20:40:44', '2018-05-20 20:41:17', '1');
