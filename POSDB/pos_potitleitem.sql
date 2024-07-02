-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: pos
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `potitleitem`
--

DROP TABLE IF EXISTS `potitleitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `potitleitem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `PONO` varchar(45) DEFAULT NULL,
  `POID` varchar(45) DEFAULT NULL,
  `POqty` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `potitleitem`
--

LOCK TABLES `potitleitem` WRITE;
/*!40000 ALTER TABLE `potitleitem` DISABLE KEYS */;
INSERT INTO `potitleitem` VALUES (55,'PO0022','PD5',1),(56,'PO0022','PD4',1),(57,'PO0022','PD3',1),(58,'PO0022','PD2',1),(59,'PO0023','PD7',1),(60,'PO0023','PD9',1),(61,'PO0023','PD8',1),(62,'PO0023','PD1',1),(63,'PO0024','PD7',1),(64,'PO0024','PD1',1),(65,'PO0024','PD3',1),(66,'PO0024','PD2',1),(67,'PO0025','PD4',1),(68,'PO0025','PD7',1),(69,'PO0025','PD6',1),(70,'PO0025','PD8',1),(71,'PO0026','PD7',1),(72,'PO0026','PD6',1),(73,'PO0026','PD8',1),(74,'PO0026','PD3',1),(75,'PO0027','PD5',1),(76,'PO0027','PD4',1),(77,'PO0027','PD8',1),(78,'PO0028','PD5',2),(79,'PO0028','PD4',1),(80,'PO0028','PD1',1),(81,'PO0028','PD3',2),(82,'PO0029','PD7',1),(83,'PO0029','PD6',1),(84,'PO0029','PD9',1),(85,'PO0029','PD8',1),(86,'PO0030','PD5',1),(87,'PO0030','PD9',1),(88,'PO0030','PD8',1),(89,'PO0030','PD3',1),(90,'PO0031','PD4',1),(91,'PO0031','PD1',1),(92,'PO0031','PD2',1);
/*!40000 ALTER TABLE `potitleitem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-01 20:40:24
