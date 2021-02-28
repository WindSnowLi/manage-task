-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: managework
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clazz`
--

DROP TABLE IF EXISTS `clazz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `clazz` (
  `clazzId` int(11) NOT NULL AUTO_INCREMENT,
  `clazzName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `clazzYear` int(255) DEFAULT NULL,
  PRIMARY KEY (`clazzId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clazz`
--

LOCK TABLES `clazz` WRITE;
/*!40000 ALTER TABLE `clazz` DISABLE KEYS */;
INSERT INTO `clazz` VALUES (1,'一班',2018),(2,'二班',2018),(3,'三班',2018),(4,'四班',2018),(5,'五班',2018);
/*!40000 ALTER TABLE `clazz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student` (
  `userId` int(11) NOT NULL,
  `studentClazzId` int(11) DEFAULT NULL,
  `studentMajorId` varchar(255) DEFAULT NULL,
  `studentAcademyId` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`) USING BTREE,
  KEY `student_FK_1` (`studentClazzId`),
  CONSTRAINT `student_FK` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_FK_1` FOREIGN KEY (`studentClazzId`) REFERENCES `clazz` (`clazzId`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (2,1,'1',1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `task` (
  `taskId` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `taskDateCreateId` int(11) DEFAULT NULL COMMENT '任务创建日期id',
  `taskCreateDate` datetime DEFAULT NULL COMMENT '任务创建日期',
  `taskStartDate` datetime DEFAULT NULL COMMENT '任务开始日期',
  `taskEndDate` datetime DEFAULT NULL COMMENT '任务终止日期',
  `taskTitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务标题',
  `taskContent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务内容描述',
  `taskPowerId` int(11) DEFAULT NULL COMMENT '任务所属级别ID',
  `taskPower` enum('CLAZZ','MAJOR','ACADEMY') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'CLAZZ' COMMENT '任务等级',
  `userId` int(11) DEFAULT NULL COMMENT '发起人ID',
  `lastChangeUserId` int(11) DEFAULT NULL COMMENT '最后修改人ID',
  PRIMARY KEY (`taskId`) USING BTREE,
  KEY `task_FK` (`userId`),
  KEY `task_FK_1` (`lastChangeUserId`),
  CONSTRAINT `task_FK` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `task_FK_1` FOREIGN KEY (`lastChangeUserId`) REFERENCES `user` (`userId`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (43,20210228,'2021-02-28 09:59:37','2021-01-01 12:00:00','2021-03-20 11:00:00','吃','假期吃喝玩乐',1,'ACADEMY',2,NULL),(44,20210228,'2021-02-28 09:59:52','2021-01-01 12:00:00','2021-03-20 11:00:00','作业','不可能的',1,'ACADEMY',2,NULL),(45,20210228,'2021-02-28 10:05:14','2021-01-01 12:00:00','2021-03-20 11:00:00','玩','必须的',1,'ACADEMY',2,NULL),(46,20210228,'2021-02-28 10:23:05','2021-02-28 12:00:00','2021-02-28 11:59:59','睡','每日必须',1,'ACADEMY',2,NULL),(48,20210228,'2021-02-28 10:26:46','2021-02-01 12:00:00','2021-02-06 11:00:00','吃','吃',1,'CLAZZ',2,NULL),(49,20210228,'2021-02-28 10:26:57','2021-02-19 12:00:00','2021-03-26 11:00:00','喝','喝',1,'CLAZZ',2,NULL),(50,20210228,'2021-02-28 10:27:07','2021-02-27 12:00:00','2021-03-25 11:00:00','玩','玩',1,'CLAZZ',2,NULL),(51,20210228,'2021-02-28 10:27:17','2021-02-23 12:00:00','2021-03-26 11:00:00','乐','乐',1,'CLAZZ',2,NULL);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taskrecord`
--

DROP TABLE IF EXISTS `taskrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `taskrecord` (
  `recordId` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录编号',
  `taskId` int(11) NOT NULL COMMENT '任务ID',
  `userId` int(11) NOT NULL COMMENT '用户ID',
  `submissionTime` datetime DEFAULT NULL COMMENT '提交任务时间',
  PRIMARY KEY (`recordId`) USING BTREE,
  KEY `taskrecord_FK` (`taskId`),
  KEY `taskrecord_FK_1` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskrecord`
--

LOCK TABLES `taskrecord` WRITE;
/*!40000 ALTER TABLE `taskrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `taskrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户内部ID',
  `userNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账号',
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `userEmail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `userPasswordSalt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码盐',
  `userPassword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `userPower` enum('0','1','2','3') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '权限，0为最低，普通用户；1班级',
  `userNickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '外显昵称',
  `userHeadPicture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户头像链接',
  `userCreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `userIdentity` enum('STUDENT','STUDENT_CLAZZ') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'STUDENT' COMMENT '用户身份',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'20218888','windSnowLi','123456789@qq.com','20218888','b8d9bcc536b2a769db391ba24fa60111','1','lsp','http://myself.firstmeet.xyz/images/sign/sign.png','2021-01-30 16:49:36','STUDENT_CLAZZ');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'managework'
--

--
-- Dumping routines for database 'managework'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-28 12:44:53
