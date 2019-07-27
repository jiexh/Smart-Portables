-- MySQL dump 10.13  Distrib 8.0.12, for osx10.13 (x86_64)
--
-- Host: localhost    Database: SmartPortables
-- ------------------------------------------------------
-- Server version	5.7.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Orders` (
  `orderId` int(11) NOT NULL,
  `itemId` varchar(45) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `itemPrice` double NOT NULL,
  `userAddress` varchar(45) NOT NULL,
  `creditCardNo` varchar(45) NOT NULL,
  `deliveryTime` bigint(8) NOT NULL,
  `orderTime` bigint(8) DEFAULT '1539648000000',
  PRIMARY KEY (`orderId`,`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
INSERT INTO `Orders` VALUES (241866002,'MacBookPro512Storage','aa',2799.99,'qewq','ewq',1540711701513,1540080000000),(241866002,'MacUSBtoUSBAdapter','aa',19.99,'qewq','ewq',1540711701513,1540080000000),(275708120,'Home','aa',129.99,'4006 223rd Pl SE','1234',1541660334007,1540450734007),(430128850,'MacBookPro512Storage','aa',2799.99,'12','ewq',1540711735673,1539993600000),(430128850,'MacUSBtoUSBAdapter','aa',19.99,'12','ewq',1540711735673,1539993600000),(430128850,'samPhoneCharger','aa',29.99,'12','ewq',1540711735673,1539993600000),(430128850,'Samsung Phone9','aa',149.99,'12','ewq',1540711735673,1539993600000),(455893564,'Home','aa',129.99,'4006 223rd Pl SE','1234',1541661328986,1540451728986),(720081512,'Home','aa',129.99,'4006 223rd Pl SE','1234',1541662449608,1540452849608),(806096518,'MacUSBtoUSBAdapter','aa',19.99,'South Loop','as',1541656672775,1540447072775),(855583086,'MacBook512Storage','aa',1499.99,'ew','dsa',1540774155479,1539648000000),(855583086,'Samsung Phone6','aa',999.99,'ew','dsa',1540774155479,1539648000000),(870394889,'Iphone8Plus2','a4',799.99,'ewq','dsa',1541571840044,1540362240044),(870394889,'samAdaptor','a4',59.99,'ewq','dsa',1541571840044,1540362240044),(870394889,'SamsungNotebook5','a4',799.99,'ewq','dsa',1541571840044,1540362240044),(1115409667,'Home','aa',129.99,'4006 223rd Pl SE','1234',1541662554198,1540452954198),(1149611145,'Motorola1','aa',199.99,'4006 223rd Pl SE','123',1541661167666,1540451567666),(1214004913,'Home','aa',129.99,'4006 223rd Pl SE','1234',1541660250764,1540450650764),(1246072052,'Home','aa',129.99,'4006 223rd Pl SE','1234',1541661253016,1540451653016),(1334828432,'SonyVR1','aa',99.99,'ewq','ewq',1540711666919,1539648000000),(1432902564,'Samsung Phone4','a2',239.99,'dsa','qw',1541570958829,1539648000000),(1432902564,'SamsungNotebook7512Storage','a2',1499.99,'dsa','qw',1541570958829,1539648000000),(1432902564,'Sony2','a2',349.99,'dsa','qw',1541570958829,1539648000000),(1456067055,'Home','aa',129.99,'4006 223rd Pl SE','123',1541660177495,1540450577495),(1539145551,'Chromebook256Storage','jx5',799.99,'4006 223rd Pl SE','1234',1540786572065,1539648000000),(1539145551,'MacBookAir512Storage','jx5',899.99,'4006 223rd Pl SE','1234',1540786572065,1539648000000),(1539145551,'SamsungNotebook5','jx5',799.99,'4006 223rd Pl SE','1234',1540786572065,1539648000000),(1556961143,'MacBookAir512Storage','aa',899.99,'4006 223rd Pl SE','123',1541658527216,1540448927216),(1950392162,'bose1','a4',149.99,'ewq','dsa',1541571863865,1540362263865),(1950392162,'EchoShow','a4',229.99,'ewq','dsa',1541571863865,1540362263865),(1950392162,'SonyVR1','a4',99.99,'ewq','dsa',1541571863865,1540362263865),(1960791706,'MacBookAir512Storage','jie6',899.99,'4006 223rd Pl SE','123',1541656750709,1540447150709),(2010403634,'Tile Spot','aa',34.99,'ewq','sd',1541656626251,1540447026251),(2014126071,'Garmin1','aa',599.99,'ew','dsa',1541573059622,1540363459622),(2014126071,'Iphone6s','aa',599.99,'ew','dsa',1541573059622,1540363459622),(2014126071,'Polar1','aa',249.95,'ew','dsa',1541573059622,1540363459622),(2030032029,'IphoneXS','aa',1099.99,'ewq','ewq',1540784536615,1540166400000),(2030032029,'Polar1','aa',249.95,'ewq','ewq',1540784536615,1540166400000),(2114800770,'Chromebook512Storage','jie6',999.99,'Dowtown Chicago','123',1541656734081,1540447134081);
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Products`
--

DROP TABLE IF EXISTS `Products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Products` (
  `id` varchar(40) NOT NULL DEFAULT '40',
  `name` varchar(40) NOT NULL DEFAULT '40',
  `price` double NOT NULL,
  `image` varchar(45) NOT NULL DEFAULT '50',
  `category` varchar(45) NOT NULL,
  `subcategory` varchar(45) DEFAULT NULL,
  `condition` varchar(20) NOT NULL,
  `discount` double NOT NULL,
  `accessoryIds` varchar(100) DEFAULT NULL,
  `manufacturer` varchar(45) DEFAULT NULL,
  `manufacturerRebate` double DEFAULT '0',
  `inventoryCount` int(4) NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Products`
--

LOCK TABLES `Products` WRITE;
/*!40000 ALTER TABLE `Products` DISABLE KEYS */;
INSERT INTO `Products` VALUES ('Apple1','Series 1',209.99,'series1.jpg','Wearable','Smart Watches','New',0,'','',33,66),('Apple2','Nike Series 3',309.99,'nikeSeries3.jpg','Wearable','Smart Watches','New',50,'','',0,45),('Apple3','Series 3',299.99,'series3.jpg','Wearable','Smart Watches','New',0,'','',0,66),('Apple4','Series 4',429.99,'series4.jpg','Wearable','Smart Watches','New',0,'','',24.99,34),('bose1','Sound Sport',149.99,'boseSoundSport.jpg','Wearable','Headphone','New',0,'HeadphoneAcc1','',29.99,66),('bose2','Sound Link',249.99,'boseSoundLink.jpg','Wearable','Head phone','New',10,'HeadphoneAcc1','',0,87),('Bose3','QuiteComfort 35',379.99,'quiteComfort35.jpg','Wearable','Headphone','New',20,'HeadphoneAcc1','',0,100),('Chromebook256Storage','Chromebook 256GB',799.99,'samChromebook256.jpg','Laptop','','Used',200,'samAdaptor','Samsung',0,100),('Chromebook512Storage','Chromebook 512GB',999.99,'samChromebook512.jpg','Laptop','','New',0,'','Samsung',9.99,99),('DellVR','Mirage Solo',449.99,'dellGeekSquad.jpg','Wearable','Virtual Reality','New',149,'','',0,100),('Echo','Echo',99.99,'Echo.jpg','Speaker',NULL,'New',0,'TP-Link-Echo-Home','Amazon',0,100),('EchoPlus','Echo Plus',119.99,'echoPlus.jpg','Speaker',NULL,'New',10,'TP-Link-Echo-Home','Amazon',0,100),('EchoShow','Echo Show',229.99,'echoShow.jpg','Speaker',NULL,'New',0,'TP-Link-Echo-Home','Amazon',0,100),('EchoSport','Echo Sport',129.99,'echoSport.jpg','Speaker',NULL,'New',0,'TP-Link-Echo-Home','Amazon',0,100),('Findster1','Findster',149,'findsterTracker.jpg','Wearable','Pet Tracker','New',0,'','',0,100),('Fitbit-1','Fitbit Alta HR',149.95,'fitbitAltaHR.jpg','Wearable','Fitness Watches','New',10,'FitbitAcc1','',0,100),('Fitbit-2','Charge3',119.95,'charge3.jpg','Wearable','Fitness Watches','New',5,'FitbitAcc1','',0,100),('Fitbit-3','Versa',149.95,'versa.jpg','Wearable','Fitness Watches','New',10,'FitbitAcc1','',0,100),('Fitbit-4','Ace',139.95,'ace.jpg','Wearable','Fitness Watches','New',10,'FitbitAcc1','',0,100),('Fitbit-5','Iconic',249.95,'iconic.jpg','Wearable','Fitness Watches','New',30,'FitbitAcc1','',0,100),('Fitbit-6','Flex2',59.95,'flex2.jpg','Wearable','Fitness Watches','New',0,'FitbitAcc1','',0,100),('FitbitAcc1','Band',69.99,'fitbitAcc.jpg','Accessory','','New',0,'','Fitbit',0,100),('Garmin1','Fenix 5',599.99,'fenix5.jpg','Wearable','Smart Watches','New',0,'','',0,100),('Garmin2','Fenix 5S',799.99,'fenix5S.jpg','Wearable','Smart Watches','New',0,'','',0,100),('Go Findr','GoFindr',249,'gofindr.jpg','Wearable','Pet Tracker','New',0,'','',0,100),('HeadphoneAcc1','Headphone Adapter',16,'wearableAcc.jpg','Accessory','','New',0,'','DTWest',0,100),('Home','Google Home',129.99,'Home.jpg','Speaker',NULL,'New',0,'TP-Link-Echo-Home','Google',0,93),('HomeMax','Home Max',399.99,'homeMax.jpg','Speaker',NULL,'New',0,'TP-Link-Echo-Home','Google',0,100),('HomeMini','Home Mini',49.99,'HomeMini.jpg','Speaker',NULL,'New',0,'TP-Link-Echo-Home','Google',0,100),('HTCVR1','HTC Virtual Reality Bundles',499.99,'HTC.jpg','Wearable','Virtual Reality','New',100,'','',0,100),('HTCVR2','Vive Pro',1399.99,'HTCVivePro.jpg','Wearable','Virtual Reality','New',0,'','',0,100),('Hyper1','WiredX2',59.99,'hyperX2.jpg','Wearable','Headphone','New',0,'HeadphoneAcc1','',0,100),('Iphone6s','Ihone 6S 64G',599.99,'6s.jpg','Phone','','New',100,'IPhoneUSB','Apple',0,100),('Iphone6sPlus','Ihone 6S Plus 64G',599.99,'6sPlus.jpg','Phone','','Used',200,'IPhoneUSB','Apple',0,100),('Iphone7','Ihone 7 32G',599.99,'iphone7.jpg','Phone','','New',50,'IPhoneUSB','Apple',0,100),('Iphone7Plus','Ihone 7 Plus 64G',719.99,'7plus.jpg','Phone','','New',150,'IPhoneUSB','Apple',0,100),('Iphone8Plus1','Ihone 8 Plus 32G',699.99,'8plus.jpg','Phone','','New',0,'IPhoneUSB','Apple',0,100),('Iphone8Plus2','Ihone 8 PLus 64G',799.99,'8plus64G.jpg','Phone','','New',50,'IPhoneUSB','Apple',0,100),('IPhoneUSB','IPhone USB Cable',29.99,'iphoneUSBCable.jpg','Accessory','','New',0,'','Apple',0,100),('IphoneX','Ihone X',899.99,'iphoneX.jpg','Phone','','New',0,'IPhoneUSB','Apple',0,100),('IphoneXR','Ihone XR',999.99,'iphoneXR.jpg','Phone','','New',0,'IPhoneUSB','Apple',0,100),('IphoneXS','Ihone XS',1099.99,'iphoneXS.jpg','Phone','','New',0,'IPhoneUSB','Apple',0,100),('IphoneXSMax','Ihone XS MAX',1199.99,'iphoneXSMax.jpg','Phone','','New',0,'IPhoneUSB','Apple',0,100),('LenovoVR','Mirage Solo',399.99,'lenovoMirage.jpg','Wearable','Virtual Reality','New',50,'','',0,100),('Logitech1','G 43',179.99,'g43.jpg','Wearable','Headphone','New',0,'HeadphoneAcc1','',0,100),('Logitech2','G 233',79.99,'g233.jpg','Wearable','Headphone','New',5,'HeadphoneAcc1','',0,100),('Logitech3','G 433',99.99,'g433.jpg','Wearable','Headphone','New',0,'HeadphoneAcc1','',0,100),('MacAdapter','USB-C Digital Plug',99.99,'macAdapter.jpg','Accessory','','New',0,'','Apple',0,100),('MacBook256Storage','MacBook 256GB',1299.99,'macbook256.jpg','Laptop','','New',100,'MacUSBtoUSBAdapter,MacAdapter','Apple',0,100),('MacBook512Storage','MacBook 512GB',1499.99,'macbook256.jpg','Laptop','','New',50,'MacUSBtoUSBAdapter,MacAdapter','Apple',0,100),('MacBookAir256Storage','MacBook Air 256GB',799.99,'macbookAir256.jpg','Laptop','','used',200,'MacUSBtoUSBAdapter,MacAdapter','Apple',0,100),('MacBookAir512Storage','MacBook 512GB',899.99,'macbookAir512.jpg','Laptop','','New',100,'MacUSBtoUSBAdapter,MacAdapter','Apple',0,98),('MacBookPro256Storage','MacBook Pro 256GB',2399.99,'macbookPro256.jpg','Laptop','','New',0,'MacUSBtoUSBAdapter,MacAdapter','Apple',0,100),('MacBookPro512Storage','MacBook Pro 512GB',2799.99,'macbookPro512.jpg','Laptop','','New',200,'MacUSBtoUSBAdapter,MacAdapter','Apple',0,100),('MacUSBtoUSBAdapter','USB Adapter',19.99,'MacUSBtoUSBAdapter.jpg','Accessory','','New',0,'','Apple',0,99),('Motorola1','Moto 360',199.99,'moto360.jpg','Wearable','Smart Watches','New',10,'','',0,99),('NewLock','Loc8tor',99,'NewLock.jpg','Wearable','Pet Tracker','New',0,'','',0,100),('NvidiaVR','GeForce GTX',299.99,'nvidiaGeForce.jpg','Wearable','Virtual Reality','New',30,'','',0,100),('Oculus1','Rift',399.99,'oculusRift.jpg','Wearable','Virtual Reality','New',30,'','',0,100),('Oculus2','Oculus Go',249.99,'oculusGo.jpg','Wearable','Virtual Reality','New',0,'','',0,100),('Oculus3','Sensor for Rift',59.99,'oculusSensor.jpg','Wearable','Virtual Reality','New',0,'','',0,100),('Polar1','M430',249.95,'polarM430.jpg','Wearable','Fitness Watches','New',10,'','',0,100),('Polar2','M200',99.95,'polarM200.jpg','Wearable','Fitness Watches','New',10,'','',0,100),('PortableCharger','Portable Charger',99.99,'portableCharger.jpg','Accessory','','New',5,'','DTWest',0,100),('Prooftracker1','Bean Pet',49,'proofBeanPet.jpg','Wearable','Pet Tracker','New',0,'','',0,100),('Prooftracker2','Pea Pet',39,'proofPeaPet.jpg','Wearable','Pet Tracker','New',0,'','',0,100),('randrandrand','Random Pet Tracker',2321,'notfound.png','wearable','Pet Tracker','Mint',10,'samPhoneCharger,MacUSBtoUSBAdapter','SH',0,100),('Razer','Pro 2',79.99,'pro2.jpg','Wearable','Headphone','New',10,'HeadphoneAcc1','',0,100),('samAdaptor','PC Adpator',59.99,'samAdaptor.jpg','Accessory','','New',0,'','Insignia',0,100),('samPhoneCharger','Phone Adapter',29.99,'samAdapter.jpg','Accessory','','New',0,'','Samsung',0,100),('Samsung Phone1','Galaxy S8',599.99,'samPhone1.jpg','Phone','','New',149,'samPhoneCharger','Samsung',0,100),('Samsung Phone10','Samsung Simple',99.99,'samPhoneSimple.jpg','Phone','','New',0,'samPhoneCharger','Samsung',0,100),('Samsung Phone2','Galaxy Note8',849.99,'samPhoneNote8.jpg','Phone','','Used',200,'samPhoneCharger','Samsung',0,100),('Samsung Phone3','Galaxy S9',839.99,'samPhoneS9.jpg','Phone','','New',0,'samPhoneCharger','Samsung',0,100),('Samsung Phone4','Galaxy J7',239.99,'samPhoneJ7.jpg','Phone','','New',0,'samPhoneCharger','Samsung',0,100),('Samsung Phone5','Galaxy J3',149.99,'samPhoneJ3.jpg','Phone','','New',0,'samPhoneCharger','Samsung',0,100),('Samsung Phone6','Galaxy Note9',999.99,'samPhoneNote9.jpg','Phone','','Used',300,'samPhoneCharger','Samsung',0,100),('Samsung Phone7','J7 Pro',149.99,'samPhoneJ7Pro.jpg','Phone','','New',0,'samPhoneCharger','Samsung',0,100),('Samsung Phone8','J5 Pro',219.99,'samPhoneJ5Pro.jpg','Phone','','New',0,'samPhoneCharger','Samsung',0,100),('Samsung Phone9','J5',149.99,'samPhoneJ5.jpg','Phone','','New',0,'samPhoneCharger','Samsung',0,100),('Samsung1','Gear S',399.99,'gearS3.jpg','Wearable','Smart Watches','New',0,'samWatchCharger','',0,100),('Samsung2','Galaxy',399.99,'galaxy.jpg','Wearable','Smart Watches','New',20,'samWatchCharger','',0,100),('SamsungNotebook5','Notebook 5 256GB',799.99,'samNotebook5256.jpg','Laptop','','New',100,'samAdaptor','Samsung',0,100),('SamsungNotebook7256Storage','Notebook 7 256GB',899.99,'samNotebook7256.jpg','Laptop','','Used',300,'samAdaptor','Samsung',0,100),('SamsungNotebook7512Storage','Notebook 7 512GB',1499.99,'samNotebook7256.jpg','Laptop','','Used',300,'samAdaptor','Samsung',0,100),('SamsungNotebook9Pro256Storage','Notebook 9 Pro 256GB',1399.99,'samNotebook9Pro.jpg','Laptop','','New',0,'samAdaptor','Samsung',0,100),('SamsungVR1','HMD',399.99,'samsungHMD.jpg','Wearable','Virtual Reality','New',0,'','',0,100),('samWatchCharger','Samsung Watch Charger',19.99,'samWatchCharger.jpg','Accessory','','New',0,'','Samsung',0,100),('SmartDog','Smart Dog',49,'smartDog.jpg','Wearable','Pet Tracker','New',5,'','',0,100),('smartisan101','Smartisan T1',799,'notfound.png','phone','Smart Phone','New',0,'PortableCharger,samPhoneCharger','Smartisan',99.99,77),('SmartKey','Smart Key',16.49,'smartKey.jpg','Wearable','Pet Tracker','New',0,'','',0,100),('SmartTracker','Smart Tracker',19.9,'smartTracker.jpg','Wearable','Pet Tracker','New',0,'','',0,100),('Sony1','H900N',299.99,'sonyH900N.jpg','Wearable','Headphone','New',0,'HeadphoneAcc1','',0,100),('Sony2','1000XM2',349.99,'sony1000XM2.jpg','Wearable','Headphone','New',0,'HeadphoneAcc1','',0,100),('SonyVR1','Play Station',99.99,'sonyPlayStation.jpg','Wearable','Virtual Reality','New',0,'','',0,100),('SPlus','Cruz Hybrid wearable',99.99,'sPlus.jpg','Wearable','Smart Watches','New',20,'','',0,100),('Tile Spot','Key Finder',34.99,'keyFinder.jpg','Wearable','Pet Tracker','New',0,'','',0,99),('TouchScreen256Storage','TouchScreen Chromebook 256GB',529.99,'samTouchScreenChromebook.jpg','Laptop','','New',200,'','Samsung',0,100),('TP-Link-Echo-Home','Smart Wi-fi Plug Mini',89.99,'TP-Link-Echo-Home.jpg','Accessory','','New',20,'','TPLink',0,100),('Whistle','Whistle 3 GPS',79.99,'whistle3.jpg','Wearable','Pet Tracker','New',0,'','',0,100);
/*!40000 ALTER TABLE `Products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Users` (
  `name` varchar(30) NOT NULL,
  `password` varchar(45) NOT NULL,
  `userType` varchar(45) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('a0','aa','customer'),('a1','aa','customer'),('a10','aa','customer'),('a11','aa','customer'),('a12','aa','customer'),('a13','aa','customer'),('a14','aa','customer'),('a15','aa','customer'),('a16','aa','customer'),('a17','aa','customer'),('a18','aa','customer'),('a19','aa','customer'),('a2','aa','customer'),('a20','aa','customer'),('a21','aa','customer'),('a22','aa','customer'),('a23','aa','customer'),('a24','aa','customer'),('a25','aa','customer'),('a26','aa','customer'),('a27','aa','customer'),('a28','aa','customer'),('a29','aa','customer'),('a3','aa','customer'),('a30','aa','customer'),('a31','aa','customer'),('a32','aa','customer'),('a33','aa','customer'),('a34','aa','customer'),('a35','aa','customer'),('a36','aa','customer'),('a37','aa','customer'),('a38','aa','customer'),('a39','aa','customer'),('a4','aa','customer'),('a40','aa','customer'),('a41','aa','customer'),('a42','aa','customer'),('a43','aa','customer'),('a44','aa','customer'),('a45','aa','customer'),('a46','aa','customer'),('a47','aa','customer'),('a48','aa','customer'),('a49','aa','customer'),('a5','aa','customer'),('a50','aa','customer'),('a51','aa','customer'),('a52','aa','customer'),('a53','aa','customer'),('a54','aa','customer'),('a55','aa','customer'),('a56','aa','customer'),('a57','aa','customer'),('a58','aa','customer'),('a59','aa','customer'),('a6','aa','customer'),('a60','aa','customer'),('a61','aa','customer'),('a62','aa','customer'),('a63','aa','customer'),('a64','aa','customer'),('a65','aa','customer'),('a66','aa','customer'),('a67','aa','customer'),('a68','aa','customer'),('a69','aa','customer'),('a7','aa','customer'),('a70','aa','customer'),('a71','aa','customer'),('a72','aa','customer'),('a73','aa','customer'),('a74','aa','customer'),('a75','aa','customer'),('a76','aa','customer'),('a77','aa','customer'),('a78','aa','customer'),('a79','aa','customer'),('a8','aa','customer'),('a80','aa','customer'),('a81','aa','customer'),('a82','aa','customer'),('a83','aa','customer'),('a84','aa','customer'),('a85','aa','customer'),('a86','aa','customer'),('a87','aa','customer'),('a88','aa','customer'),('a89','aa','customer'),('a9','aa','customer'),('a90','aa','customer'),('a91','aa','customer'),('a92','aa','customer'),('a93','aa','customer'),('a94','aa','customer'),('a95','aa','customer'),('a96','aa','customer'),('a97','aa','customer'),('a98','aa','customer'),('a99','aa','customer'),('aa','aa','customer'),('jie6','aa','customer'),('jx','aa','customer'),('jx1','aa','customer'),('jx2','aa','customer'),('jx3','aa','customer'),('jx4','aa','customer'),('jx5','aa','customer'),('sale','aa','salesman'),('sm','aa','storeManager');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-25  9:10:06
