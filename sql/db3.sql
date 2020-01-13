CREATE DATABASE  IF NOT EXISTS `betstatdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `betstatdb`;
-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: betstatdb
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id_coupon` int(11) NOT NULL AUTO_INCREMENT,
  `data_coupon` datetime NOT NULL,
  `id_tipo` int(11) NOT NULL,
  `id_esito` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `importo` float NOT NULL,
  `vicnita` float DEFAULT NULL,
  PRIMARY KEY (`id_coupon`),
  KEY `id_tipo` (`id_tipo`),
  KEY `id_esito` (`id_esito`),
  KEY `id_utente` (`id_utente`),
  CONSTRAINT `coupon_ibfk_1` FOREIGN KEY (`id_tipo`) REFERENCES `tipo` (`id_tipo`),
  CONSTRAINT `coupon_ibfk_2` FOREIGN KEY (`id_esito`) REFERENCES `esito` (`id_esito`),
  CONSTRAINT `coupon_ibfk_3` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dettaglio_coupon`
--

DROP TABLE IF EXISTS `dettaglio_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dettaglio_coupon` (
  `id_dettaglio_coupon` int(11) NOT NULL AUTO_INCREMENT,
  `data_dettaglio_coupon` datetime NOT NULL,
  `id_squadra_casa` int(11) NOT NULL,
  `id_squadra_ospite` int(11) NOT NULL,
  `id_coupon` int(11) NOT NULL,
  `id_evento` int(11) NOT NULL,
  `id_pronostico` int(11) NOT NULL,
  `quota` float NOT NULL,
  `id_esito` int(11) NOT NULL,
  PRIMARY KEY (`id_dettaglio_coupon`),
  KEY `id_squadra_casa` (`id_squadra_casa`),
  KEY `id_squadra_ospite` (`id_squadra_ospite`),
  KEY `id_coupon` (`id_coupon`),
  KEY `id_pronostico` (`id_pronostico`),
  KEY `id_esito` (`id_esito`),
  CONSTRAINT `dettaglio_coupon_ibfk_1` FOREIGN KEY (`id_squadra_casa`) REFERENCES `squadra` (`id_squadra`),
  CONSTRAINT `dettaglio_coupon_ibfk_2` FOREIGN KEY (`id_squadra_ospite`) REFERENCES `squadra` (`id_squadra`),
  CONSTRAINT `dettaglio_coupon_ibfk_3` FOREIGN KEY (`id_coupon`) REFERENCES `coupon` (`id_coupon`),
  CONSTRAINT `dettaglio_coupon_ibfk_4` FOREIGN KEY (`id_pronostico`) REFERENCES `pronostico` (`id_pronostico`),
  CONSTRAINT `dettaglio_coupon_ibfk_5` FOREIGN KEY (`id_esito`) REFERENCES `esito` (`id_esito`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dettaglio_coupon`
--

LOCK TABLES `dettaglio_coupon` WRITE;
/*!40000 ALTER TABLE `dettaglio_coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `dettaglio_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `esito`
--

DROP TABLE IF EXISTS `esito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `esito` (
  `id_esito` int(11) NOT NULL AUTO_INCREMENT,
  `nome_esito` varchar(20) NOT NULL,
  PRIMARY KEY (`id_esito`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `esito`
--

LOCK TABLES `esito` WRITE;
/*!40000 ALTER TABLE `esito` DISABLE KEYS */;
/*!40000 ALTER TABLE `esito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pronostico`
--

DROP TABLE IF EXISTS `pronostico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pronostico` (
  `id_pronostico` int(11) NOT NULL AUTO_INCREMENT,
  `nome_pronostico` varchar(20) NOT NULL,
  PRIMARY KEY (`id_pronostico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pronostico`
--

LOCK TABLES `pronostico` WRITE;
/*!40000 ALTER TABLE `pronostico` DISABLE KEYS */;
/*!40000 ALTER TABLE `pronostico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `squadra`
--

DROP TABLE IF EXISTS `squadra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `squadra` (
  `id_squadra` int(11) NOT NULL AUTO_INCREMENT,
  `nome_squadra` varchar(20) NOT NULL,
  PRIMARY KEY (`id_squadra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `squadra`
--

LOCK TABLES `squadra` WRITE;
/*!40000 ALTER TABLE `squadra` DISABLE KEYS */;
/*!40000 ALTER TABLE `squadra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo`
--

DROP TABLE IF EXISTS `tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo` (
  `id_tipo` int(11) NOT NULL AUTO_INCREMENT,
  `nome_tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`id_tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo`
--

LOCK TABLES `tipo` WRITE;
/*!40000 ALTER TABLE `tipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id_utente` int(11) NOT NULL AUTO_INCREMENT,
  `nome_utente` varchar(20) NOT NULL,
  `cognome_utente` varchar(20) NOT NULL,
  `cf_utente` varchar(20) NOT NULL,
  `indirizzo_utente` varchar(40) DEFAULT NULL,
  `email_utente` varchar(40) DEFAULT NULL,
  `password_utente` varchar(255) NOT NULL,
  `telefono_utente` varchar(30) DEFAULT NULL,
  `token_utente` varchar(255) DEFAULT NULL,
  `token_validate_utente` tinyint(1) DEFAULT NULL,
  `last_access_utente` datetime DEFAULT NULL,
  PRIMARY KEY (`id_utente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-13 17:39:16
