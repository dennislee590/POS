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
-- Table structure for table `pdstock`
--

DROP TABLE IF EXISTS `pdstock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pdstock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `PDID` varchar(45) DEFAULT NULL,
  `PDname` varchar(45) DEFAULT NULL,
  `PDdesc` varchar(45) DEFAULT NULL,
  `PDbarcode` varchar(45) DEFAULT NULL,
  `PDprice` double DEFAULT NULL,
  `PDqty` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pdstock`
--

LOCK TABLES `pdstock` WRITE;
/*!40000 ALTER TABLE `pdstock` DISABLE KEYS */;
INSERT INTO `pdstock` VALUES (1,'PD1','Product 1','Desc 1','11111111',100,87),(2,'PD2','Product 2','Desc 2','22222222',120,83),(3,'PD3','Product 3','Desc 3','33333333',80,86),(4,'PD4','Product 4','Desc 4','44444444',150,87),(5,'PD5','Product 5','Desc 5','55555555',90,93),(6,'PD6','Product 6','Desc 6','66666666',130,102),(7,'PD7','Product 7','Desc 7','77777777',110,94),(8,'PD8','Product 8','Desc 8','88888888',95,93),(9,'PD9','Product 9','Desc 9','99999999',85,97),(10,'PD10','Product 10','Desc 10','10101010',140,100),(11,'PD11','Product 11','Desc 11','11111112',75,100),(12,'PD12','Product 12','Desc 12','12121212',160,100),(13,'PD13','Product 13','Desc 13','13131313',100,100),(14,'PD14','Product 14','Desc 14','14141414',120,100),(15,'PD15','Product 15','Desc 15','15151515',110,100),(16,'PD16','Product 16','Desc 16','16161616',130,100),(17,'PD17','Product 17','Desc 17','17171717',85,100),(18,'PD18','Product 18','Desc 18','18181818',95,100),(19,'PD19','Product 19','Desc 19','19191919',140,100),(56,'PD400','','','',0,1);
/*!40000 ALTER TABLE `pdstock` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-01 20:40:25
