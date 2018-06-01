/*
Navicat MySQL Data Transfer

Source Server         : Dolly
Source Server Version : 100120
Source Host           : 192.168.180.156:3306
Source Database       : switching

Target Server Type    : MYSQL
Target Server Version : 100120
File Encoding         : 65001

Date: 2017-08-29 10:52:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for adapterbillerconfiguration
-- ----------------------------
DROP TABLE IF EXISTS `adapterbillerconfiguration`;
CREATE TABLE `adapterbillerconfiguration` (
  `Id` int(4) NOT NULL AUTO_INCREMENT,
  `BillerId` int(4) NOT NULL,
  `Url` varchar(100) NOT NULL,
  `Type` varchar(20) NOT NULL,
  `Format` varchar(10) NOT NULL,
  `Method` varchar(10) NOT NULL,
  `RequestTemplate` varchar(1000) NOT NULL,
  `ResponseTemplate` varchar(1000) NOT NULL,
  `RequestActor` varchar(100) NOT NULL,
  `ChecksumType` varchar(20) DEFAULT NULL,
  `ChecksumComponent` varchar(50) DEFAULT NULL,
  `Uid` varchar(20) DEFAULT NULL,
  `SharedKey` varchar(20) DEFAULT NULL,
  `AdditionalData1` text,
  `AdditionalData2` varchar(20) DEFAULT NULL,
  `AdditionalData3` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of adapterbillerconfiguration
-- ----------------------------
INSERT INTO `adapterbillerconfiguration` VALUES ('1', '1', 'http://localhost:8087/Dummy_For_AKKA_Switching/biller_inquiry.php', 'inquiry', 'xml', 'post', '<transaction><transactioncode>pasti-inquiry</transactioncode><userid>JTP</userid><userpin>292900</userpin><signature>signature</signature><referencecode>referencecode</referencecode><productcode>productcode</productcode><accnumber1>accnumber1</accnumber1><accnumber2>accnumber2</accnumber2><accnumber3>accnumber3</accnumber3><contactnumber>contactnumber</contactnumber><ref1>ref1</ref1><ref2>ref2</ref2><ref3>ref3</ref3></transaction>', '<transaction><tracecode>1000911617</tracecode><inquirycode>08yu84829001023</inquirycode><Inquiryexpired>170125175959</Inquiryexpired><responsecode>76ghjsfhwegfaf</responsecode><referencecode>109092gjf0ijrwmg092jjg</referencecode><responsestatus>SUCCESS</responsestatus><responsetime>100</responsetime><accnumber1>085795990256</accnumber1><accnumber2></accnumber2><accnumber3></accnumber3><contactnumber></contactnumber><nominal>50200</nominal><adminfee>200</adminfee><ref1></ref1><ref2></ref2><ref3></ref3><additionaldata><caid></caid><billingstatus></billingstatus><paymentstatus></paymentstatus><totalmonthoutstanding></totalmonthoutstanding><swreferenceid></swreferenceid><subscribername></subscribername><serviceunit></serviceunit><serviceunitphone></serviceunitphone><subscribersegmentation></subscribersegmentation><subscribercategory></subscribercategory><totaladmincharge></totaladmincharge><blth1></blth1><duedate1></duedate1><meterreaddate1></meterreaddate1><RPTAG1></RPTAG1><incentive1></i', '1', 'MD5', 'Uid|SharedKey|TRX_ID', 'UAT', '12345', '', 'asdf', 'ggg');
INSERT INTO `adapterbillerconfiguration` VALUES ('2', '4', 'http://localhost:8087/Dummy_For_AKKA_Switching/biller_purchase.php', 'purchase', 'xml', 'post', '<transaction><transactioncode>pasti-pembayaran</transactioncode><userid>JTP</userid><userpin>292900</userpin><signature>signature</signature><referencecode>referencecode</referencecode><productcode>productcode</productcode><accnumber1>accnumber1</accnumber1><accnumber2>accnumber2</accnumber2><accnumber3>accnumber3</accnumber3><contactnumber>contactnumber</contactnumber><nominal>nominal</nominal><adminfee>adminfee</adminfee><ref1>ref1</ref1><ref2>ref2</ref2><ref3>ref3</ref3><inquirycode>inquirycode</inquirycode></transaction>', '<transaction-status><tracecode>tracecode</tracecode><transactionstatus>transactionstatus</transactionstatus><transactiontime>transactiontime</transactiontime><serialnumber>serialnumber</serialnumber><harga>harga</harga><balance>balance</balance><productcode>productcode</productcode><accnumber>accnumber</accnumber><userid>userid</userid><info>info</info><referencecode>referencecode</referencecode></transaction-status>', '1', 'MD5', 'Uid|SharedKey|TRX_ID', 'UAT', '12345', '', '', '');
INSERT INTO `adapterbillerconfiguration` VALUES ('3', '2', 'http://103.10.129.109/api/PartnerAPI', 'inquiry', 'wsdl', 'post', '<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:par=\"http://partner.wsdl.com/\"><soapenv:Header/><soapenv:Body><par:doInquiry><!--Optional:--><MALLID>281</MALLID><!--Optional:--><BILLERID>BILLERID</BILLERID><!--Optional:--><ACCOUNT_NUMBER>ACCOUNT_NUMBER</ACCOUNT_NUMBER><SYSTRACE>SYSTRACE</SYSTRACE><!--Optional:--><WORDS>WORDS</WORDS><!--Optional:--><ADDITIONALDATA1>6012</ADDITIONALDATA1><!--Optional:--><ADDITIONALDATA2>ADDITIONALDATA2</ADDITIONALDATA2><!--Optional:--><ADDITIONALDATA3>ADDITIONALDATA3</ADDITIONALDATA3></par:doInquiry></soapenv:Body></soapenv:Envelope>', '', '2', 'SHA1', 'Uid|SharedKey|SUBSCRIBER_NUMBER', '281', 'shared123', '', '', '');
INSERT INTO `adapterbillerconfiguration` VALUES ('4', '2', 'http://103.10.129.109/api/PartnerAPI', 'purchase', 'wsdl', 'post', '<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:par=\"http://partner.wsdl.com/\"><soapenv:Header/><soapenv:Body><par:doPayment><!--Optional:--><MALLID>281</MALLID><!--Optional:--><BILLERID>BILLERID</BILLERID><!--Optional:--><ACCOUNT_NUMBER>ACCOUNT_NUMBER</ACCOUNT_NUMBER><!--Optional:--><INQUIRYID>INQUIRYID</INQUIRYID><!--Optional:--><AMOUNT>AMOUNT</AMOUNT><!--Optional:--><WORDS>WORDS</WORDS><SYSTRACE>SYSTRACE</SYSTRACE><!--Optional:--><BILL_ID>BILL_ID</BILL_ID><!--Optional:--><ADDITIONALDATA1>6012</ADDITIONALDATA1><!--Optional:--><ADDITIONALDATA2>ADDITIONALDATA2</ADDITIONALDATA2><!--Optional:--><ADDITIONALDATA3>ADDITIONALDATA3</ADDITIONALDATA3></par:doPayment></soapenv:Body></soapenv:Envelope>', '', '2', 'SHA1', 'Uid|SharedKey|INQUIRY_ID|BILL_ID|TOTAL_TRX_AMOUNT', '281', 'shared123', '', '', '');
INSERT INTO `adapterbillerconfiguration` VALUES ('5', '4', 'http://localhost:8087/Dummy_For_AKKA_Switching/biller_inquiry.php', 'inquiry', 'xml', 'post', '<transaction><transactioncode>pasti-inquiry</transactioncode><userid>JTP</userid><userpin>292900</userpin><signature>signature</signature><referencecode>referencecode</referencecode><productcode>productcode</productcode><accnumber1>accnumber1</accnumber1><accnumber2>accnumber2</accnumber2><accnumber3>accnumber3</accnumber3><contactnumber>contactnumber</contactnumber><ref1>ref1</ref1><ref2>ref2</ref2><ref3>ref3</ref3></transaction>', '<transaction><tracecode>1000911617</tracecode><inquirycode>08yu84829001023</inquirycode><Inquiryexpired>170125175959</Inquiryexpired><responsecode>76ghjsfhwegfaf</responsecode><referencecode>109092gjf0ijrwmg092jjg</referencecode><responsestatus>SUCCESS</responsestatus><responsetime>100</responsetime><accnumber1>085795990256</accnumber1><accnumber2></accnumber2><accnumber3></accnumber3><contactnumber></contactnumber><nominal>50200</nominal><adminfee>200</adminfee><ref1></ref1><ref2></ref2><ref3></ref3><additionaldata><caid></caid><billingstatus></billingstatus><paymentstatus></paymentstatus><totalmonthoutstanding></totalmonthoutstanding><swreferenceid></swreferenceid><subscribername></subscribername><serviceunit></serviceunit><serviceunitphone></serviceunitphone><subscribersegmentation></subscribersegmentation><subscribercategory></subscribercategory><totaladmincharge></totaladmincharge><blth1></blth1><duedate1></duedate1><meterreaddate1></meterreaddate1><RPTAG1></RPTAG1><incentive1></i', '1', 'MD5', 'USER_ID|SHARED_KEY|TRX_ID', 'uid12345', 'sharedKey12345', '', 'asdf', 'ggg');
INSERT INTO `adapterbillerconfiguration` VALUES ('6', '4', 'http://localhost:8087/Dummy_For_AKKA_Switching/biller_advice.php', 'advice', 'xml', 'post', '<transaction><transactioncode>pasti-cek-status</transactioncode><tracecode>tracecode</tracecode><userid>JTP</userid><userpin>292900</userpin><signature>signature</signature><referencecode></referencecode></transaction>', '', '1', 'MD5', 'USER_ID|SHARED_KEY|TRX_ID', 'uid12345', 'sharedKey12345', '', '', '');
INSERT INTO `adapterbillerconfiguration` VALUES ('7', '1', 'http://localhost:8087/Dummy_For_AKKA_Switching/biller_advice.php', 'advice', 'xml', 'post', '<transaction><transactioncode>pasti-cek-status</transactioncode><tracecode>tracecode</tracecode><userid>JTP</userid><userpin>292900</userpin><signature>signature</signature><referencecode></referencecode></transaction>', '', '1', 'MD5', 'USER_ID|SHARED_KEY|TRX_ID', 'UAT', '12345', null, null, null);
INSERT INTO `adapterbillerconfiguration` VALUES ('8', '2', 'http://103.10.129.109/api/PartnerAPI', 'advice', 'wsdl', 'post', '<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:par=\"http://partner.wsdl.com/\"><soapenv:Header/><soapenv:Body><par:doAdvise><!--Optional:--><MALLID>281</MALLID><!--Optional:--><BILLERID>BILLERID</BILLERID><!--Optional:--><ACCOUNT_NUMBER>ACCOUNT_NUMBER</ACCOUNT_NUMBER><!--Optional:--><AMOUNT>AMOUNT</AMOUNT><!--Optional:--><WORDS>WORDS</WORDS><SYSTRACE>SYSTRACE</SYSTRACE><!--Optional:--><HOST_REFERENCE_NUMBER>HOST_REFERENCE_NUMBER</HOST_REFERENCE_NUMBER><!--Optional:--><BILL_ID>BILL_ID</BILL_ID><!--Optional:--><ADDITIONALDATA1>6012</ADDITIONALDATA1><!--Optional:--><ADDITIONALDATA2></ADDITIONALDATA2><!--Optional:--><ADDITIONALDATA3></ADDITIONALDATA3></par:doAdvise></soapenv:Body></soapenv:Envelope>', '', '2', 'SHA1', 'Uid|SharedKey|TRX_ID|INQUIRY_ID', '281', 'shared123', null, null, null);
INSERT INTO `adapterbillerconfiguration` VALUES ('9', '3', 'https://202.158.79.245:6787', 'purchase', 'xml', 'post', '<?xml version=\"1.0\"?><methodCall><methodName>topUpRequest</methodName><params><param><value><struct><member><name>MSISDN</name><value><string>081081081081</string></value></member><member><name>REQUESTID</name><value><string>TRX_ID</string></value></member><member><name>PIN</name><value><string>jejeSIT</string></value></member><member><name>NOHP</name><value><string>SUBSCRIBER_NUMBER</string></value></member><member><name>NOM</name><value><string>PRODUCT_CODE</string></value></member></struct></value></param></params></methodCall>', '<?xml version=\"1.0\" encoding=\"iso-8859-1\"?><methodResponse><params><param><value><struct><member><name>RESPONSECODE</name><value><string>PURCHASE_STATUS</string></value></member><member><name>REQUESTID</name><value><string>TRX_ID</string></value></member><member><name>MESSAGE</name><value><string>PURCHASE_STATUS_DESC</string></value></member><member><name>SN</name><value><string>137573894</string></value></member><member><name>TRANSACTIONID</name><value><string>REFERENCE_NUMBER</string></value></member></struct></value></param></params></methodResponse>', '4', '', '', '', '', '202.158.79.245', null, null);
INSERT INTO `adapterbillerconfiguration` VALUES ('10', '3', 'https://202.158.79.245:6787', 'advice', 'xml', 'post', '<?xml version=\"1.0\"?>\r\n	<methodCall>\r\n		<methodName>topUpInquiry</methodName>\r\n		<params>\r\n			<param>\r\n				<value>\r\n					<struct>\r\n						<member><name>MSISDN</name><value><string>628123456789</string></value></member>\r\n						<member><name>REQUESTID</name><value><string>TRX_ID</string></value></member>\r\n						<member><name>PIN</name><value><string>uhwawxixixi</string></value></member>\r\n						<member><name>NOHP</name><value><string>SUBSCRIBER_NUMBER</string></value></member>\r\n					</struct>\r\n				</value>\r\n			</param>\r\n		</params>\r\n	</methodCall>', '<?xml version=\"1.0\"?><methodResponse>\r\n		<params>\r\n		<param>\r\n		<value>\r\n		<struct>\r\n		<member>\r\n		<name>RESPONSECODE</name>\r\n		<value><string>PURCHASE_STATUS</string></value>\r\n		</member>\r\n		<member>\r\n		<name>MESSAGE</name>\r\n		<value><string>PURCHASE_STATUS_DESC</string></value>\r\n		</member>\r\n		</struct>\r\n		</value>\r\n		</param>\r\n		</params>\r\n		</methodResponse>', '4', '', '', '', '', '202.158.79.245', null, null);
INSERT INTO `adapterbillerconfiguration` VALUES ('11', '1', 'http://localhost:8087/Dummy_For_AKKA_Switching/biller_purchase_pra.php', 'purchase', 'xml', 'post', '<transaction><transactioncode>pasti-prabayar</transactioncode><productcode>productcode</productcode><accnumber>accnumber</accnumber><userid>JTP</userid><userpin>292900</userpin><signature>signature</signature><referencecode>referencecode</referencecode></transaction>', 'wer', '1', 'MD5', 'Uid|SharedKey|TRX_ID', 'UAT', '12345', null, null, null);
INSERT INTO `adapterbillerconfiguration` VALUES ('12', '5', '202.152.22.118:1423', 'inquiry', 'iso', 'post', '{  \r\n   \"0\":\"0200\",\r\n   \"2\":\"PRODUCT_CODE\",\r\n   \"3\":\"380000\",\r\n   \"7\":\"TRANSMISSION_DATE\",\r\n   \"11\":\"STAN\",\r\n   \"12\":\"LOCAL_TIME\",\r\n   \"13\":\"LOCAL_DATE\",\r\n   \"15\":\"SETTLEMENT_DATE\",\r\n   \"18\":\"6012\",\r\n   \"32\":\"008\",\r\n   \"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n   \"41\":\"DEVJATIS\",\r\n   \"42\":\"200900100800000\",\r\n  \"48\":{  \r\n	 \"ITEM\":[  \r\n		{  \r\n		   \"VALUE\":\"SUBSCRIBER_NUMBER|PERIOD\",\r\n		   \"IS_PHONE_NUMBER\":false\r\n		}\r\n	 ]\r\n  },\r\n   \"49\":\"360\"\r\n}', '{  \r\n   \"48\":{\r\n      \"ITEM\":\"No_VA_Keluarga:16|Jumlah_Bulan:2|No_VA_Kepala_Keluarga:16|Total_Premi:12|Biaya_Admin:8|Jumlah_anggota_keluarga:2|LIST:List_anggota_keluarga\",\r\n      \"List_anggota_keluarga\":{  \r\n         \"SIZE\":\"Jumlah_anggota_keluarga\",\r\n         \"ITEM\":\"Kode_premi_anggota_keluarga:16|No_Va_anggota_keluarga:16|Nama:30|kdCabang:6|nmCabang:20|Biaya_Premi_yang_harus_dibayar:12|Biaya_Premi_bulan_ini:12|Premi_di_muka:12\"\r\n      }\r\n   },\r\n   \"SUBSCRIBER_NAME\":\"LIST|List_anggota_keluarga|Nama\",\r\n   \"ADMIN_FEE\":\"Biaya_Admin\",\r\n   \"TRX_AMOUNT\":\"Total_Premi\"\r\n}', '3', null, '', null, null, '<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<!DOCTYPE isopackager SYSTEM \"genericpackager.dtd\">\r\n\r\n<isopackager>\r\n<isofield id=\"0\" length=\"4\" name=\"MESSAGE TYPE INDICATOR\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"1\" length=\"16\" name=\"BIT MAP\" class=\"org.jpos.iso.IFA_BITMAP\"/>\r\n<isofield id=\"2\" length=\"99\" name=\"SECRET ID\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"3\" length=\"6\" name=\"PROCESSING CODE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"4\" length=\"12\" name=\"AMOUNT, TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"7\" length=\"10\" name=\"TRANSMISSION DATE AND TIME\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"11\" length=\"6\" name=\"SYSTEM TRACE AUDIT NUMBER\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"12\" length=\"6\" name=\"TIME, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"13\" length=\"4\" name=\"DATE, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"15\" length=\"4\" name=\"DATE, SETTLEMENT\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"18\" length=\"4\" name=\"MERCHANTS TYPE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"32\" length=\"99\" name=\"ACQUIRING INSTITUTION IDENT CODE\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"37\" length=\"12\" name=\"RETRIEVAL REFERENCE NUMBER\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"39\" length=\"2\" name=\"RESPONSE CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"41\" length=\"8\" name=\"CARD ACCEPTOR TERMINAL IDENTIFICACION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"42\" length=\"15\" name=\"CARD ACCEPTOR IDENTIFICATION CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"48\" length=\"999\" name=\"ADITIONAL DATA - PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"49\" length=\"3\" name=\"CURRENCY CODE, TRANSACTION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"60\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"61\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"62\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"63\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"90\" length=\"42\" name=\"ORIGINAL DATA ELEMENTS\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n</isopackager>', null, null);
INSERT INTO `adapterbillerconfiguration` VALUES ('13', '5', '202.152.22.118:1423', 'purchase', 'iso', 'post', '{  \r\n   \"0\":\"0200\",\r\n   \"2\":\"PRODUCT_CODE\",\r\n   \"3\":\"170000\",\r\n   \"7\":\"TRANSMISSION_DATE\",\r\n   \"11\":\"STAN\",\r\n   \"12\":\"LOCAL_TIME\",\r\n   \"13\":\"LOCAL_DATE\",\r\n   \"15\":\"SETTLEMENT_DATE\",\r\n   \"18\":\"6012\",\r\n   \"32\":\"008\",\r\n   \"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n   \"41\":\"DEVJATIS\",\r\n   \"42\":\"200900100800000\",\r\n   \"48\":{  \r\n      \"ITEM\":[  \r\n         {  \r\n            \"VALUE\":\"SUBSCRIBER_NUMBER|PERIOD\",\r\n            \"IS_PHONE_NUMBER\":false\r\n         }\r\n      ]\r\n   },\r\n   \"49\":\"360\"\r\n}', '{  \r\n   \"48\":{\r\n      \"ITEM\":\"No_VA_Keluarga:16|Jumlah_Bulan:2|No_VA_Kepala_Keluarga:16|Total_Premi:12|Biaya_Admin:8|JPA_Refnum:32|Jumlah_anggota_keluarga:2|LIST:List_anggota_keluarga|Tanggal_Lunas:14\",\r\n      \"List_anggota_keluarga\":{  \r\n         \"SIZE\":\"Jumlah_anggota_keluarga\",\r\n         \"ITEM\":\"Kode_premi_anggota_keluarga:16|No_Va_anggota_keluarga:16|Nama:30|kdCabang:6|nmCabang:20|Biaya_Premi_yang_harus_dibayar:12|Biaya_Premi_bulan_ini:12|Premi_di_muka:12\"\r\n      }\r\n   },\r\n   \"PURCHASE_TIME\":\"Tanggal_Lunas\"\r\n}', '5', null, '', null, null, '<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<!DOCTYPE isopackager SYSTEM \"genericpackager.dtd\">\r\n\r\n<isopackager>\r\n<isofield id=\"0\" length=\"4\" name=\"MESSAGE TYPE INDICATOR\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"1\" length=\"16\" name=\"BIT MAP\" class=\"org.jpos.iso.IFA_BITMAP\"/>\r\n<isofield id=\"2\" length=\"99\" name=\"SECRET ID\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"3\" length=\"6\" name=\"PROCESSING CODE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"4\" length=\"12\" name=\"AMOUNT, TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"7\" length=\"10\" name=\"TRANSMISSION DATE AND TIME\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"11\" length=\"6\" name=\"SYSTEM TRACE AUDIT NUMBER\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"12\" length=\"6\" name=\"TIME, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"13\" length=\"4\" name=\"DATE, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"15\" length=\"4\" name=\"DATE, SETTLEMENT\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"18\" length=\"4\" name=\"MERCHANTS TYPE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"32\" length=\"99\" name=\"ACQUIRING INSTITUTION IDENT CODE\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"37\" length=\"12\" name=\"RETRIEVAL REFERENCE NUMBER\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"39\" length=\"2\" name=\"RESPONSE CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"41\" length=\"8\" name=\"CARD ACCEPTOR TERMINAL IDENTIFICACION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"42\" length=\"15\" name=\"CARD ACCEPTOR IDENTIFICATION CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"48\" length=\"999\" name=\"ADITIONAL DATA - PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"49\" length=\"3\" name=\"CURRENCY CODE, TRANSACTION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"60\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"61\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"62\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"63\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"90\" length=\"42\" name=\"ORIGINAL DATA ELEMENTS\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n</isopackager>', null, null);
INSERT INTO `adapterbillerconfiguration` VALUES ('14', '5', '202.152.22.118:1423', 'advice', 'iso', 'post', '{  \r\n   \"0\":\"0200\",\r\n   \"2\":\"VENDOR_BILLER_ID\",\r\n   \"3\":\"171000\",\r\n   \"7\":\"TRANSMISSION_DATE\",\r\n   \"11\":\"STAN\",\r\n   \"12\":\"LOCAL_TIME\",\r\n   \"13\":\"LOCAL_DATE\",\r\n   \"15\":\"SETTLEMENT_DATE\",\r\n   \"18\":\"6012\",\r\n   \"32\":\"008\",\r\n   \"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n   \"41\":\"DEVJATIS\",\r\n   \"42\":\"200900100800000\",\r\n   \"48\":{  \r\n      \"ITEM\":[  \r\n         {  \r\n            \"VALUE\":\"SUBSCRIBER_NUMBER|PERIOD\",\r\n            \"IS_PHONE_NUMBER\":false\r\n         }\r\n      ]\r\n   },\r\n   \"49\":\"360\"\r\n}', '{  \r\n   \"48\":{\r\n      \"ITEM\":\"No_VA_Keluarga:16|Jumlah_Bulan:2|No_VA_Kepala_Keluarga:16|Total_Premi:12|Biaya_Admin:8|JPA_Refnum:32|Jumlah_anggota_keluarga:2|LIST:List_anggota_keluarga|Tanggal_Lunas:14\",\r\n      \"List_anggota_keluarga\":{  \r\n         \"SIZE\":\"Jumlah_anggota_keluarga\",\r\n         \"ITEM\":\"Kode_premi_anggota_keluarga:16|No_Va_anggota_keluarga:16|Nama:30|kdCabang:6|nmCabang:20|Biaya_Premi_yang_harus_dibayar:12|Biaya_Premi_bulan_ini:12|Premi_di_muka:12\"\r\n      }\r\n   },\r\n   \"PURCHASE_TIME\":\"Tanggal_Lunas\"\r\n}', '5', null, '', null, null, '<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<!DOCTYPE isopackager SYSTEM \"genericpackager.dtd\">\r\n\r\n<isopackager>\r\n<isofield id=\"0\" length=\"4\" name=\"MESSAGE TYPE INDICATOR\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"1\" length=\"16\" name=\"BIT MAP\" class=\"org.jpos.iso.IFA_BITMAP\"/>\r\n<isofield id=\"2\" length=\"99\" name=\"SECRET ID\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"3\" length=\"6\" name=\"PROCESSING CODE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"4\" length=\"12\" name=\"AMOUNT, TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"7\" length=\"10\" name=\"TRANSMISSION DATE AND TIME\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"11\" length=\"6\" name=\"SYSTEM TRACE AUDIT NUMBER\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"12\" length=\"6\" name=\"TIME, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"13\" length=\"4\" name=\"DATE, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"15\" length=\"4\" name=\"DATE, SETTLEMENT\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"18\" length=\"4\" name=\"MERCHANTS TYPE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"32\" length=\"99\" name=\"ACQUIRING INSTITUTION IDENT CODE\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"37\" length=\"12\" name=\"RETRIEVAL REFERENCE NUMBER\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"39\" length=\"2\" name=\"RESPONSE CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"41\" length=\"8\" name=\"CARD ACCEPTOR TERMINAL IDENTIFICACION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"42\" length=\"15\" name=\"CARD ACCEPTOR IDENTIFICATION CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"48\" length=\"999\" name=\"ADITIONAL DATA - PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"49\" length=\"3\" name=\"CURRENCY CODE, TRANSACTION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"60\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"61\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"62\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"63\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"90\" length=\"42\" name=\"ORIGINAL DATA ELEMENTS\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n</isopackager>', null, null);
INSERT INTO `adapterbillerconfiguration` VALUES ('15', '6', '202.152.22.118:1423', 'inquiry', 'iso', 'post', '{  \r\n   \"0\":\"0200\",\r\n   \"2\":\"PRODUCT_CODE\",\r\n   \"3\":\"380000\",\r\n   \"7\":\"TRANSMISSION_DATE\",\r\n   \"11\":\"STAN\",\r\n   \"12\":\"LOCAL_TIME\",\r\n   \"13\":\"LOCAL_DATE\",\r\n   \"15\":\"SETTLEMENT_DATE\",\r\n   \"18\":\"6012\",\r\n   \"32\":\"008\",\r\n   \"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n   \"41\":\"DEVJATIS\",\r\n   \"42\":\"200900100800000\",\r\n	\"48\":\r\n		{\r\n			\"ITEM\":\r\n				[\r\n					{\"VALUE\":\"0001\", \"LENGTH\":\"4\", \"IS_PHONE_NUMBER\":false, \"PADDING\":\"FIXED\"},\r\n					{\"VALUE\":\"SUBSCRIBER_NUMBER\", \"LENGTH\":\"4\", \"IS_PHONE_NUMBER\":true, \"CUT\":\"LEFT\", \"PADDING\":\"RIGHT_JUSTIFIED\", \"PADDER\":\"0\"},\r\n					{\"VALUE\":\"SUBSCRIBER_NUMBER\", \"LENGTH\":\"10\", \"IS_PHONE_NUMBER\":true, \"CUT\":\"RIGHT\", \"PADDING\":\"LEFT_JUSTIFIED\", \"PADDER\":\"SPACE\"}\r\n				]\r\n		},\r\n   \"49\":\"360\"\r\n}', '{  \r\n   \"48\":{  \r\n      \"ITEM\":\"Product_ID:4|Kode_Area:4|Phone_Number:10|Customer_Name:30|No_Referensi_Switching:32|Total_Bill:1|Biaya_Admin:8|Expire:8|Bill_Ref_#1:11|Bill_Amount_#1:12|Bill_Ref_#2:11|Bill_Amount_#2:12|Bill_Ref_#3:11|Bill_Amount_#3:12\"\r\n   },\r\n   \"SUBSCRIBER_NAME\":\"Customer_Name\",\r\n   \"ADMIN_FEE\":\"Biaya_Admin\",\r\n   \"TRX_AMOUNT\":\"MAP|Total_Bill|Bill_Amount_#\"\r\n}', '3', null, '', null, null, '<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<!DOCTYPE isopackager SYSTEM \"genericpackager.dtd\">\r\n\r\n<isopackager>\r\n<isofield id=\"0\" length=\"4\" name=\"MESSAGE TYPE INDICATOR\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"1\" length=\"16\" name=\"BIT MAP\" class=\"org.jpos.iso.IFA_BITMAP\"/>\r\n<isofield id=\"2\" length=\"99\" name=\"SECRET ID\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"3\" length=\"6\" name=\"PROCESSING CODE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"4\" length=\"12\" name=\"AMOUNT, TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"7\" length=\"10\" name=\"TRANSMISSION DATE AND TIME\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"11\" length=\"6\" name=\"SYSTEM TRACE AUDIT NUMBER\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"12\" length=\"6\" name=\"TIME, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"13\" length=\"4\" name=\"DATE, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"15\" length=\"4\" name=\"DATE, SETTLEMENT\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"18\" length=\"4\" name=\"MERCHANTS TYPE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"32\" length=\"99\" name=\"ACQUIRING INSTITUTION IDENT CODE\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"37\" length=\"12\" name=\"RETRIEVAL REFERENCE NUMBER\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"39\" length=\"2\" name=\"RESPONSE CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"41\" length=\"8\" name=\"CARD ACCEPTOR TERMINAL IDENTIFICACION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"42\" length=\"15\" name=\"CARD ACCEPTOR IDENTIFICATION CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"48\" length=\"999\" name=\"ADITIONAL DATA - PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"49\" length=\"3\" name=\"CURRENCY CODE, TRANSACTION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"60\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"61\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"62\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"63\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"90\" length=\"42\" name=\"ORIGINAL DATA ELEMENTS\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n</isopackager>', null, null);
INSERT INTO `adapterbillerconfiguration` VALUES ('16', '6', '202.152.22.118:1423', 'purchase', 'iso', 'post', '{  \r\n   \"0\":\"0200\",\r\n   \"2\":\"PRODUCT_CODE\",\r\n   \"3\":\"170000\",\r\n   \"7\":\"TRANSMISSION_DATE\",\r\n   \"11\":\"STAN\",\r\n   \"12\":\"LOCAL_TIME\",\r\n   \"13\":\"LOCAL_DATE\",\r\n   \"15\":\"SETTLEMENT_DATE\",\r\n   \"18\":\"6012\",\r\n   \"32\":\"008\",\r\n   \"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n   \"41\":\"DEVJATIS\",\r\n   \"42\":\"200900100800000\",\r\n	\"48\":\r\n		{\r\n			\"ITEM\":\r\n				[\r\n					{\"VALUE\":\"0001\", \"LENGTH\":\"4\", \"IS_PHONE_NUMBER\":false, \"PADDING\":\"FIXED\"},\r\n					{\"VALUE\":\"SUBSCRIBER_NUMBER\", \"LENGTH\":\"4\", \"IS_PHONE_NUMBER\":true, \"CUT\":\"LEFT\", \"PADDING\":\"RIGHT_JUSTIFIED\", \"PADDER\":\"0\"},\r\n					{\"VALUE\":\"SUBSCRIBER_NUMBER\", \"LENGTH\":\"10\", \"IS_PHONE_NUMBER\":true, \"CUT\":\"RIGHT\", \"PADDING\":\"LEFT_JUSTIFIED\", \"PADDER\":\"SPACE\"}\r\n				]\r\n		},\r\n   \"49\":\"360\"\r\n}', '{  \r\n   \"48\":{  \r\n      \"ITEM\":\"Product_ID:4|Kode_Area:4|Phone_Number:10|Customer_Name:30|No_Referensi_Switching:32|Total_Bill:1|Biaya_Admin:8|Expire:8|Bill_Ref_#1:11|Bill_Amount_#1:12|Bill_Ref_#2:11|Bill_Amount_#2:12|Bill_Ref_#3:11|Bill_Amount_#3:12|Waktu_Lunas:14\"\r\n   },\r\n   \"PURCHASE_TIME\":\"Waktu_Lunas\"\r\n}', '5', null, '', null, null, '<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<!DOCTYPE isopackager SYSTEM \"genericpackager.dtd\">\r\n\r\n<isopackager>\r\n<isofield id=\"0\" length=\"4\" name=\"MESSAGE TYPE INDICATOR\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"1\" length=\"16\" name=\"BIT MAP\" class=\"org.jpos.iso.IFA_BITMAP\"/>\r\n<isofield id=\"2\" length=\"99\" name=\"SECRET ID\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"3\" length=\"6\" name=\"PROCESSING CODE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"4\" length=\"12\" name=\"AMOUNT, TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"7\" length=\"10\" name=\"TRANSMISSION DATE AND TIME\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"11\" length=\"6\" name=\"SYSTEM TRACE AUDIT NUMBER\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"12\" length=\"6\" name=\"TIME, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"13\" length=\"4\" name=\"DATE, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"15\" length=\"4\" name=\"DATE, SETTLEMENT\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"18\" length=\"4\" name=\"MERCHANTS TYPE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"32\" length=\"99\" name=\"ACQUIRING INSTITUTION IDENT CODE\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"37\" length=\"12\" name=\"RETRIEVAL REFERENCE NUMBER\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"39\" length=\"2\" name=\"RESPONSE CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"41\" length=\"8\" name=\"CARD ACCEPTOR TERMINAL IDENTIFICACION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"42\" length=\"15\" name=\"CARD ACCEPTOR IDENTIFICATION CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"48\" length=\"999\" name=\"ADITIONAL DATA - PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"49\" length=\"3\" name=\"CURRENCY CODE, TRANSACTION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"60\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"61\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"62\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"63\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"90\" length=\"42\" name=\"ORIGINAL DATA ELEMENTS\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n</isopackager>', null, null);
INSERT INTO `adapterbillerconfiguration` VALUES ('17', '6', '202.152.22.118:1423', 'advice', 'iso', 'post', '{  \r\n   \"0\":\"0200\",\r\n   \"2\":\"VENDOR_BILLER_ID\",\r\n   \"3\":\"171000\",\r\n   \"7\":\"TRANSMISSION_DATE\",\r\n   \"11\":\"STAN\",\r\n   \"12\":\"LOCAL_TIME\",\r\n   \"13\":\"LOCAL_DATE\",\r\n   \"15\":\"SETTLEMENT_DATE\",\r\n   \"18\":\"6012\",\r\n   \"32\":\"008\",\r\n   \"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n   \"41\":\"DEVJATIS\",\r\n   \"42\":\"200900100800000\",\r\n	\"48\":\r\n		{\r\n			\"ITEM\":\r\n				[\r\n					{\"VALUE\":\"0001\", \"LENGTH\":\"4\", \"IS_PHONE_NUMBER\":false, \"PADDING\":\"FIXED\"},\r\n					{\"VALUE\":\"SUBSCRIBER_NUMBER\", \"LENGTH\":\"4\", \"IS_PHONE_NUMBER\":true, \"CUT\":\"LEFT\", \"PADDING\":\"RIGHT_JUSTIFIED\", \"PADDER\":\"0\"},\r\n					{\"VALUE\":\"SUBSCRIBER_NUMBER\", \"LENGTH\":\"10\", \"IS_PHONE_NUMBER\":true, \"CUT\":\"RIGHT\", \"PADDING\":\"LEFT_JUSTIFIED\", \"PADDER\":\"SPACE\"}\r\n				]\r\n		},\r\n   \"49\":\"360\"\r\n}', '{  \r\n   \"48\":{  \r\n      \"ITEM\":\"Product_ID:4|Kode_Area:4|Phone_Number:10|Customer_Name:30|No_Referensi_Switching:32|Total_Bill:1|Biaya_Admin:8|Expire:8|Bill_Ref_#1:11|Bill_Amount_#1:12|Bill_Ref_#2:11|Bill_Amount_#2:12|Bill_Ref_#3:11|Bill_Amount_#3:12|Waktu_Lunas:14\"\r\n   },\r\n   \"PURCHASE_TIME\":\"Waktu_Lunas\"\r\n}', '5', null, '', null, null, '<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<!DOCTYPE isopackager SYSTEM \"genericpackager.dtd\">\r\n\r\n<isopackager>\r\n<isofield id=\"0\" length=\"4\" name=\"MESSAGE TYPE INDICATOR\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"1\" length=\"16\" name=\"BIT MAP\" class=\"org.jpos.iso.IFA_BITMAP\"/>\r\n<isofield id=\"2\" length=\"99\" name=\"SECRET ID\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"3\" length=\"6\" name=\"PROCESSING CODE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"4\" length=\"12\" name=\"AMOUNT, TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"7\" length=\"10\" name=\"TRANSMISSION DATE AND TIME\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"11\" length=\"6\" name=\"SYSTEM TRACE AUDIT NUMBER\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"12\" length=\"6\" name=\"TIME, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"13\" length=\"4\" name=\"DATE, LOCAL TRANSACTION\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"15\" length=\"4\" name=\"DATE, SETTLEMENT\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"18\" length=\"4\" name=\"MERCHANTS TYPE\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n<isofield id=\"32\" length=\"99\" name=\"ACQUIRING INSTITUTION IDENT CODE\" class=\"org.jpos.iso.IFA_LLNUM\"/>\r\n<isofield id=\"37\" length=\"12\" name=\"RETRIEVAL REFERENCE NUMBER\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"39\" length=\"2\" name=\"RESPONSE CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"41\" length=\"8\" name=\"CARD ACCEPTOR TERMINAL IDENTIFICACION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"42\" length=\"15\" name=\"CARD ACCEPTOR IDENTIFICATION CODE\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"48\" length=\"999\" name=\"ADITIONAL DATA - PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"49\" length=\"3\" name=\"CURRENCY CODE, TRANSACTION\" class=\"org.jpos.iso.IF_CHAR\"/>\r\n<isofield id=\"60\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"61\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"62\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"63\" length=\"999\" name=\"RESERVED PRIVATE\" class=\"org.jpos.iso.IFA_LLLCHAR\"/>\r\n<isofield id=\"90\" length=\"42\" name=\"ORIGINAL DATA ELEMENTS\" class=\"org.jpos.iso.IFA_NUMERIC\"/>\r\n</isopackager>', null, null);
SET FOREIGN_KEY_CHECKS=1;
