/*
Navicat MySQL Data Transfer

Source Server         : Dolly
Source Server Version : 100120
Source Host           : 192.168.180.156:3306
Source Database       : switching

Target Server Type    : MYSQL
Target Server Version : 100120
File Encoding         : 65001

Date: 2017-08-29 10:25:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for biller
-- ----------------------------
DROP TABLE IF EXISTS `biller`;
CREATE TABLE `biller` (
  `BillerId` int(11) NOT NULL AUTO_INCREMENT,
  `ARMId` int(11) DEFAULT NULL,
  `BillerName` varchar(200) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT NULL,
  `CreatedBy` varchar(50) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `ModifiedBy` varchar(50) DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`BillerId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of biller
-- ----------------------------
INSERT INTO `biller` VALUES ('1', null, 'Pesona Cipta Utama', '1', 'lorensius.simanjuntak', '2017-07-24 15:43:23', null, null);
INSERT INTO `biller` VALUES ('2', null, 'DOKU', '1', 'lorensius.simanjuntak', '2017-07-24 15:44:26', null, null);
INSERT INTO `biller` VALUES ('3', null, 'Telesindo', '1', 'lorensius.simanjuntak', '2017-03-20 17:14:00', null, null);
INSERT INTO `biller` VALUES ('4', null, 'Pesona Postpaid', '1', 'yogie.yabie', '2017-08-25 15:52:22', null, null);
INSERT INTO `biller` VALUES ('5', null, 'Jatelindo', '1', 'dika.atrariksa', '2017-07-24 15:46:07', null, null);
INSERT INTO `biller` VALUES ('6', null, 'Jatelindo Kartu Halo', '1', 'dika.atrariksa', '2017-08-25 16:10:23', null, null);
SET FOREIGN_KEY_CHECKS=1;
