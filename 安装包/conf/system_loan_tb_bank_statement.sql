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
-- Table structure for table `tb_bank_statement`
--

DROP TABLE IF EXISTS `tb_bank_statement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_bank_statement` (
  `bank_statement_id` int NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL,
  `ammount` decimal(38,2) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `motion_type` varchar(255) NOT NULL,
  PRIMARY KEY (`bank_statement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_bank_statement`
--

LOCK TABLES `tb_bank_statement` WRITE;
/*!40000 ALTER TABLE `tb_bank_statement` DISABLE KEYS */;
INSERT INTO `tb_bank_statement` VALUES (1,2,20.00,'2024-12-11T20:18:25.257030200','存款'),(2,2,222.00,'2024-12-11T20:18:38.642477700','存款'),(3,2,1.00,'2024-12-11T20:18:48.511293400','取款'),(4,2,11.00,'2024-12-11T20:21:02.267993500','存款'),(5,2,233.00,'2024-12-11T21:51:48.304990600','存款'),(6,2,2.00,'2024-12-12T07:44:22.298565100','存款'),(7,2,100.00,'2024-12-12T07:44:27.970909800','存款');
/*!40000 ALTER TABLE `tb_bank_statement` ENABLE KEYS */;
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
