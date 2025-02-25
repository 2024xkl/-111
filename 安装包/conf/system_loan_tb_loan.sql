-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: system_loan
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_loan`
--

DROP TABLE IF EXISTS `tb_loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_loan` (
  `loan_id` int NOT NULL AUTO_INCREMENT,
  `advance_repay` bit(1) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `money` decimal(38,2) DEFAULT NULL,
  `overtime_rate` decimal(38,2) DEFAULT NULL,
  `rate` decimal(38,2) DEFAULT NULL,
  `time` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`loan_id`),
  KEY `FKjkaw66f1kb1ryrm6uppjjuwo9` (`user_id`),
  CONSTRAINT `FKjkaw66f1kb1ryrm6uppjjuwo9` FOREIGN KEY (`user_id`) REFERENCES `tb_creditreport` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_loan`
--

LOCK TABLES `tb_loan` WRITE;
/*!40000 ALTER TABLE `tb_loan` DISABLE KEYS */;
INSERT INTO `tb_loan` VALUES (1,_binary '','2024-12-11',12.00,0.20,0.10,2,4),(2,_binary '','2024-12-11',20.00,0.20,0.10,2,4);
/*!40000 ALTER TABLE `tb_loan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-25 17:30:55
