/*
Navicat MySQL Data Transfer

Source Server         : Dolly
Source Server Version : 100120
Source Host           : 192.168.180.156:3306
Source Database       : switching

Target Server Type    : MYSQL
Target Server Version : 100120
File Encoding         : 65001

Date: 2017-08-29 10:48:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for adapterbillermappingparam
-- ----------------------------
DROP TABLE IF EXISTS `adapterbillermappingparam`;
CREATE TABLE `adapterbillermappingparam` (
  `Id` int(4) NOT NULL AUTO_INCREMENT,
  `IdAdapterBillerConfiguration` int(4) NOT NULL,
  `ParamType` varchar(10) NOT NULL,
  `ParamCore` varchar(30) NOT NULL,
  `ParamBiller` varchar(30) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of adapterbillermappingparam
-- ----------------------------
INSERT INTO `adapterbillermappingparam` VALUES ('14', '1', 'request', 'USER_ID', 'userid');
INSERT INTO `adapterbillermappingparam` VALUES ('16', '1', 'response', 'INQUIRY_ID', 'inquirycode');
INSERT INTO `adapterbillermappingparam` VALUES ('17', '1', 'request', 'TRX_ID', 'referencecode');
INSERT INTO `adapterbillermappingparam` VALUES ('18', '1', 'request', 'PRODUCT_CODE', 'productcode');
INSERT INTO `adapterbillermappingparam` VALUES ('19', '1', 'request', 'SUBSCRIBER_NUMBER', 'accnumber1');
INSERT INTO `adapterbillermappingparam` VALUES ('20', '1', 'response', 'INQUIRY_STATUS', 'responsecode');
INSERT INTO `adapterbillermappingparam` VALUES ('21', '1', 'response', 'TRX_ID', 'referencecode');
INSERT INTO `adapterbillermappingparam` VALUES ('22', '1', 'response', 'INQUIRY_STATUS_DESC', 'responsestatus');
INSERT INTO `adapterbillermappingparam` VALUES ('23', '1', 'response', 'SUBSCRIBER_NUMBER', 'accnumber1');
INSERT INTO `adapterbillermappingparam` VALUES ('24', '1', 'response', 'TRX_AMOUNT', 'nominal');
INSERT INTO `adapterbillermappingparam` VALUES ('25', '1', 'response', 'ADMIN_FEE', 'adminfee');
INSERT INTO `adapterbillermappingparam` VALUES ('26', '1', 'response', 'SUBSCRIBER_NAME', 'subscribername');
INSERT INTO `adapterbillermappingparam` VALUES ('27', '1', 'request', 'CHECKSUMHASH', 'signature');
INSERT INTO `adapterbillermappingparam` VALUES ('42', '2', 'request', 'USER_ID', 'userid');
INSERT INTO `adapterbillermappingparam` VALUES ('43', '2', 'request', 'SHARED_KEY', 'userpin');
INSERT INTO `adapterbillermappingparam` VALUES ('44', '2', 'request', 'CHECKSUMHASH', 'signature');
INSERT INTO `adapterbillermappingparam` VALUES ('45', '2', 'request', 'TRX_ID', 'referencecode');
INSERT INTO `adapterbillermappingparam` VALUES ('46', '2', 'request', 'PRODUCT_CODE', 'productcode');
INSERT INTO `adapterbillermappingparam` VALUES ('47', '2', 'request', 'SUBSCRIBER_NUMBER', 'accnumber1');
INSERT INTO `adapterbillermappingparam` VALUES ('48', '2', 'request', 'TRX_AMOUNT', 'nominal');
INSERT INTO `adapterbillermappingparam` VALUES ('49', '2', 'request', 'ADMIN_FEE', 'adminfee');
INSERT INTO `adapterbillermappingparam` VALUES ('50', '2', 'request', 'INQUIRY_ID', 'inquirycode');
INSERT INTO `adapterbillermappingparam` VALUES ('51', '2', 'response', 'REFERENCE_NUMBER', 'tracecode');
INSERT INTO `adapterbillermappingparam` VALUES ('52', '2', 'response', 'PURCHASE_STATUS', 'responsecode');
INSERT INTO `adapterbillermappingparam` VALUES ('53', '2', 'response', 'PURCHASE_TIME', 'transactiontime');
INSERT INTO `adapterbillermappingparam` VALUES ('54', '2', 'response', 'PURCHASE_STATUS_DESC', 'responsestatus');
INSERT INTO `adapterbillermappingparam` VALUES ('55', '7', 'request', 'REFERENCE_NUMBER', 'tracecode');
INSERT INTO `adapterbillermappingparam` VALUES ('56', '7', 'request', 'TRX_ID', 'referencecode');
INSERT INTO `adapterbillermappingparam` VALUES ('57', '7', 'response', 'REFERENCE_NUMBER', 'tracecode');
INSERT INTO `adapterbillermappingparam` VALUES ('58', '7', 'response', 'PURCHASE_TIME', 'transactiontime');
INSERT INTO `adapterbillermappingparam` VALUES ('59', '7', 'response', 'PURCHASE_STATUS', 'statuscode');
INSERT INTO `adapterbillermappingparam` VALUES ('60', '7', 'request', 'CHECKSUMHASH', 'signature');
INSERT INTO `adapterbillermappingparam` VALUES ('61', '7', 'request', 'SHARED_KEY', 'userpin');
INSERT INTO `adapterbillermappingparam` VALUES ('62', '7', 'request', 'USER_ID', 'userid');
INSERT INTO `adapterbillermappingparam` VALUES ('63', '7', 'response', 'PURCHASE_STATUS_DESC', 'transactionstatus');
INSERT INTO `adapterbillermappingparam` VALUES ('64', '1', 'response', 'BILL_ID', '');
INSERT INTO `adapterbillermappingparam` VALUES ('65', '5', 'request', 'USER_ID', 'userid');
INSERT INTO `adapterbillermappingparam` VALUES ('66', '5', 'request', 'TRX_ID', 'referencecode');
INSERT INTO `adapterbillermappingparam` VALUES ('67', '5', 'request', 'PRODUCT_CODE', 'productcode');
INSERT INTO `adapterbillermappingparam` VALUES ('68', '5', 'request', 'SUBSCRIBER_NUMBER', 'accnumber1');
INSERT INTO `adapterbillermappingparam` VALUES ('69', '5', 'request', 'CHECKSUMHASH', 'signature');
INSERT INTO `adapterbillermappingparam` VALUES ('70', '5', 'response', 'INQUIRY_ID', 'inquirycode');
INSERT INTO `adapterbillermappingparam` VALUES ('71', '71', '71', '71', '71');
INSERT INTO `adapterbillermappingparam` VALUES ('72', '5', 'response', 'TRX_ID', 'referencecode');
INSERT INTO `adapterbillermappingparam` VALUES ('73', '5', 'response', 'INQUIRY_STATUS_DESC', 'responsestatus');
INSERT INTO `adapterbillermappingparam` VALUES ('74', '5', 'response', 'SUBSCRIBER_NUMBER', 'accnumber1');
INSERT INTO `adapterbillermappingparam` VALUES ('75', '5', 'response', 'TRX_AMOUNT', 'nominal');
INSERT INTO `adapterbillermappingparam` VALUES ('76', '5', 'response', 'ADMIN_FEE', 'adminfee');
INSERT INTO `adapterbillermappingparam` VALUES ('77', '5', 'response', 'SUBSCRIBER_NAME', 'subscribername');
INSERT INTO `adapterbillermappingparam` VALUES ('78', '5', 'response', 'BILL_ID', '');
INSERT INTO `adapterbillermappingparam` VALUES ('79', '5', 'response', 'INQUIRY_STATUS', 'responsecode');
INSERT INTO `adapterbillermappingparam` VALUES ('89', '3', 'response', 'INQUIRY_STATUS', 'RESPONSECODE');
INSERT INTO `adapterbillermappingparam` VALUES ('90', '3', 'response', 'INQUIRY_STATUS_DESC', 'RESPONSEMSG');
INSERT INTO `adapterbillermappingparam` VALUES ('91', '3', 'response', 'SUBSCRIBER_NUMBER', 'SUBSCRIBER_ID');
INSERT INTO `adapterbillermappingparam` VALUES ('92', '3', 'response', 'INQUIRY_ID', 'INQUIRY_ID');
INSERT INTO `adapterbillermappingparam` VALUES ('93', '3', 'response', 'SUBSCRIBER_NAME', 'SUBSCRIBER_NAME');
INSERT INTO `adapterbillermappingparam` VALUES ('94', '3', 'response', 'CURRENCY', 'CURENCY');
INSERT INTO `adapterbillermappingparam` VALUES ('95', '3', 'response', 'TRX_AMOUNT', 'TOTALAMOUNT');
INSERT INTO `adapterbillermappingparam` VALUES ('96', '3', 'response', 'ADMIN_FEE', 'ADMINFEE');
INSERT INTO `adapterbillermappingparam` VALUES ('98', '3', 'request', 'PRODUCT_CODE', 'BILLERID');
INSERT INTO `adapterbillermappingparam` VALUES ('99', '3', 'request', 'SUBSCRIBER_NUMBER', 'ACCOUNT_NUMBER');
INSERT INTO `adapterbillermappingparam` VALUES ('100', '3', 'request', 'TRX_ID', 'SYSTRACE');
INSERT INTO `adapterbillermappingparam` VALUES ('101', '3', 'request', 'CHECKSUMHASH', 'WORDS');
INSERT INTO `adapterbillermappingparam` VALUES ('102', '3', 'response', 'BILL_ID', 'BILLID');
INSERT INTO `adapterbillermappingparam` VALUES ('106', '4', 'request', 'TRX_ID', 'SYSTRACE');
INSERT INTO `adapterbillermappingparam` VALUES ('107', '4', 'request', 'PRODUCT_CODE', 'BILLERID');
INSERT INTO `adapterbillermappingparam` VALUES ('108', '4', 'request', 'SUBSCRIBER_NUMBER', 'ACCOUNT_NUMBER');
INSERT INTO `adapterbillermappingparam` VALUES ('109', '4', 'request', 'TOTAL_TRX_AMOUNT', 'AMOUNT');
INSERT INTO `adapterbillermappingparam` VALUES ('110', '4', 'request', 'INQUIRY_ID', 'INQUIRYID');
INSERT INTO `adapterbillermappingparam` VALUES ('111', '4', 'request', 'BILL_ID', 'BILL_ID');
INSERT INTO `adapterbillermappingparam` VALUES ('112', '4', 'request', 'CHECKSUMHASH', 'WORDS');
INSERT INTO `adapterbillermappingparam` VALUES ('113', '4', 'response', 'PURCHASE_STATUS', 'RESPONSECODE');
INSERT INTO `adapterbillermappingparam` VALUES ('114', '4', 'response', 'PURCHASE_STATUS_DESC', 'RESPONSEMSG');
INSERT INTO `adapterbillermappingparam` VALUES ('115', '4', 'response', 'REFERENCE_NUMBER', 'HOST_REFERENCE_NUMBER');
INSERT INTO `adapterbillermappingparam` VALUES ('116', '4', 'response', 'PURCHASE_TIME', 'DATE_OF_SETTLEMENT');
INSERT INTO `adapterbillermappingparam` VALUES ('117', '8', 'request', 'PRODUCT_CODE', 'BILLERID');
INSERT INTO `adapterbillermappingparam` VALUES ('118', '8', 'request', 'SUBSCRIBER_NUMBER', 'ACCOUNT_NUMBER');
INSERT INTO `adapterbillermappingparam` VALUES ('119', '8', 'request', 'TRX_AMOUNT', 'AMOUNT');
INSERT INTO `adapterbillermappingparam` VALUES ('120', '8', 'request', 'CHECKSUMHASH', 'WORDS');
INSERT INTO `adapterbillermappingparam` VALUES ('121', '8', 'request', 'TRX_ID', 'SYSTRACE');
INSERT INTO `adapterbillermappingparam` VALUES ('122', '8', 'request', 'INQUIRY_ID', 'HOST_REFERENCE_NUMBER');
INSERT INTO `adapterbillermappingparam` VALUES ('123', '8', 'request', 'BILL_ID', 'BILL_ID');
INSERT INTO `adapterbillermappingparam` VALUES ('124', '8', 'response', 'PURCHASE_STATUS', 'RESPONSECODE');
INSERT INTO `adapterbillermappingparam` VALUES ('125', '8', 'response', 'PURCHASE_STATUS_DESC', 'RESPONSEMSG');
INSERT INTO `adapterbillermappingparam` VALUES ('126', '8', 'response', 'REFERENCE_NUMBER', 'HOST_REFERENCE_NUMBER');
INSERT INTO `adapterbillermappingparam` VALUES ('127', '8', 'response', 'PURCHASE_TIME', 'DATE_OF_SETTLEMENT');
INSERT INTO `adapterbillermappingparam` VALUES ('136', '11', 'request', 'CHECKSUMHASH', 'signature');
INSERT INTO `adapterbillermappingparam` VALUES ('137', '11', 'request', 'TRX_ID', 'referencecode');
INSERT INTO `adapterbillermappingparam` VALUES ('138', '11', 'request', 'PRODUCT_CODE', 'productcode');
INSERT INTO `adapterbillermappingparam` VALUES ('139', '11', 'request', 'SUBSCRIBER_NUMBER', 'accnumber');
INSERT INTO `adapterbillermappingparam` VALUES ('140', '11', 'response', 'REFERENCE_NUMBER', 'tracecode');
INSERT INTO `adapterbillermappingparam` VALUES ('141', '11', 'response', 'PURCHASE_STATUS', 'transactionstatus');
INSERT INTO `adapterbillermappingparam` VALUES ('142', '11', 'response', 'PURCHASE_TIME', 'transactiontime');
INSERT INTO `adapterbillermappingparam` VALUES ('143', '11', 'response', 'PURCHASE_STATUS_DESC', 'responsestatus');
INSERT INTO `adapterbillermappingparam` VALUES ('158', '6', 'request', 'REFERENCE_NUMBER', 'tracecode');
INSERT INTO `adapterbillermappingparam` VALUES ('159', '6', 'request', 'TRX_ID', 'referencecode');
INSERT INTO `adapterbillermappingparam` VALUES ('160', '6', 'request', 'CHECKSUMHASH', 'signature');
INSERT INTO `adapterbillermappingparam` VALUES ('161', '6', 'request', 'SHARED_KEY', 'userpin');
INSERT INTO `adapterbillermappingparam` VALUES ('162', '6', 'request', 'USER_ID', 'userid');
INSERT INTO `adapterbillermappingparam` VALUES ('163', '6', 'response', 'REFERENCE_NUMBER', 'tracecode');
INSERT INTO `adapterbillermappingparam` VALUES ('164', '6', 'response', 'PURCHASE_TIME', 'transactiontime');
INSERT INTO `adapterbillermappingparam` VALUES ('165', '6', 'response', 'PURCHASE_STATUS', 'statuscode');
INSERT INTO `adapterbillermappingparam` VALUES ('166', '6', 'response', 'PURCHASE_STATUS_DESC', 'transactionstatus');
SET FOREIGN_KEY_CHECKS=1;
