CREATE DATABASE  IF NOT EXISTS `friendfinder` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `friendfinder`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: friendfinder
-- ------------------------------------------------------
-- Server version	5.6.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book_genres`
--

DROP TABLE IF EXISTS `book_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_genres` (
  `book_genre_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_genre` text,
  PRIMARY KEY (`book_genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_genres`
--

LOCK TABLES `book_genres` WRITE;
/*!40000 ALTER TABLE `book_genres` DISABLE KEYS */;
INSERT INTO `book_genres` VALUES (1,'Action'),(2,'Adventure'),(3,'Children'),(4,'Crime'),(5,'Drama'),(6,'Fantasy'),(7,'Horror'),(8,'Non-Fiction'),(9,'Romance'),(10,'Sci-Fi'),(11,'Thriller'),(12,'Young Adult');
/*!40000 ALTER TABLE `book_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cities` (
  `city_id` int(11) NOT NULL AUTO_INCREMENT,
  `city_name` text,
  `citys_region` int(11) DEFAULT NULL,
  PRIMARY KEY (`city_id`),
  KEY `citys_region` (`citys_region`),
  CONSTRAINT `cities_ibfk_1` FOREIGN KEY (`citys_region`) REFERENCES `regions` (`region_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` VALUES (1,'Jonsered',21),(2,'Göteborg',21),(3,'Mölndal',21),(4,'Kungälv',21),(5,'Borås',21),(6,'Partille',21),(7,'Torslanda',21),(8,'Västra frölunda',21),(9,'Kungsbacka',21),(10,'Malmö',15);
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interests`
--

DROP TABLE IF EXISTS `interests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interests` (
  `interest_id` int(11) NOT NULL AUTO_INCREMENT,
  `interest_name` text,
  PRIMARY KEY (`interest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interests`
--

LOCK TABLES `interests` WRITE;
/*!40000 ALTER TABLE `interests` DISABLE KEYS */;
INSERT INTO `interests` VALUES (1,'Football'),(2,'Fishing'),(3,'Karate'),(4,'Skydiving'),(5,'Diving'),(6,'Dancing'),(7,'Partying'),(8,'Computers'),(9,'Computer games'),(10,'Video games'),(11,'Poker'),(12,'Music'),(13,'Movies'),(14,'Running'),(15,'Traveling'),(16,'Puzzles'),(17,'Memory'),(18,'Beer'),(19,'Drinking'),(20,'Go-carts'),(21,'Arts'),(22,'Books'),(23,'Tolkien'),(24,'Star Trek'),(25,'Programming'),(26,'Sleeping'),(27,'TV'),(28,'Lifting weights'),(29,'MMA'),(30,'Manga'),(31,'Facebook'),(32,'Fashion'),(33,'Gardening'),(34,'Baking'),(35,'Cooking'),(36,'Fighting crime'),(37,'Chess');
/*!40000 ALTER TABLE `interests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languages`
--

DROP TABLE IF EXISTS `languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `languages` (
  `language_id` int(11) NOT NULL AUTO_INCREMENT,
  `language_name` text,
  PRIMARY KEY (`language_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `languages`
--

LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
INSERT INTO `languages` VALUES (1,'Swedish'),(2,'Danish'),(3,'Norwegian'),(4,'English'),(5,'Hungarian'),(6,'Dutch'),(7,'Arabic'),(8,'Mandarin'),(9,'Japanese'),(10,'Thai'),(11,'Finnish'),(12,'German'),(13,'Russian'),(14,'Spanish'),(15,'Italian'),(16,'Greek'),(17,'French'),(18,'Hebrew'),(19,'Klingon'),(20,'Elvish');
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_genres`
--

DROP TABLE IF EXISTS `movie_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_genres` (
  `movie_genre_id` int(11) NOT NULL AUTO_INCREMENT,
  `movie_genre` text,
  PRIMARY KEY (`movie_genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_genres`
--

LOCK TABLES `movie_genres` WRITE;
/*!40000 ALTER TABLE `movie_genres` DISABLE KEYS */;
INSERT INTO `movie_genres` VALUES (1,'Action'),(2,'Adventure'),(3,'Animation'),(4,'Comedy'),(5,'Crime'),(6,'Documentary'),(7,'Drama'),(8,'Family'),(9,'Fantasy'),(10,'Horror'),(11,'Musical'),(12,'Romance'),(13,'Sci-Fi'),(14,'Thriller'),(15,'War');
/*!40000 ALTER TABLE `movie_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music_genres`
--

DROP TABLE IF EXISTS `music_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `music_genres` (
  `music_genre_id` int(11) NOT NULL AUTO_INCREMENT,
  `music_genre` text,
  PRIMARY KEY (`music_genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music_genres`
--

LOCK TABLES `music_genres` WRITE;
/*!40000 ALTER TABLE `music_genres` DISABLE KEYS */;
INSERT INTO `music_genres` VALUES (1,'Alternative'),(2,'Blues'),(3,'Classical'),(4,'Country'),(5,'Electronic'),(6,'Folk'),(7,'Funk'),(8,'Grunge'),(9,'Hard Rock'),(10,'Indie'),(11,'Jazz'),(12,'Metal'),(13,'New Age'),(14,'Pop'),(15,'R&B'),(16,'Rap'),(17,'Raggae'),(18,'Rock');
/*!40000 ALTER TABLE `music_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nationalities`
--

DROP TABLE IF EXISTS `nationalities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nationalities` (
  `nationality_id` int(11) NOT NULL AUTO_INCREMENT,
  `nationality_name` text,
  PRIMARY KEY (`nationality_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nationalities`
--

LOCK TABLES `nationalities` WRITE;
/*!40000 ALTER TABLE `nationalities` DISABLE KEYS */;
INSERT INTO `nationalities` VALUES (1,'Swedish'),(2,'Brittish'),(3,'Hungarian'),(4,'Danish'),(5,'Norwegian'),(6,'American'),(7,'German'),(8,'Dutch'),(9,'Chinese'),(10,'Japanese'),(11,'Finnish'),(12,'Canadian'),(13,'Iranian'),(14,'French'),(15,'Italian'),(16,'Spanish'),(17,'Greek'),(18,'Brazilian'),(19,'Portuguese'),(20,'Turkish'),(21,'Libanese'),(22,'Polish');
/*!40000 ALTER TABLE `nationalities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` text,
  `last_name` text,
  `gender` text,
  `persons_nationality` int(11) DEFAULT NULL,
  `persons_city` int(11) DEFAULT NULL,
  `persons_program` int(11) DEFAULT NULL,
  `phonenumber` varchar(15) DEFAULT NULL,
  `email` text,
  `persons_religion` int(11) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  KEY `persons_ibfk_1` (`persons_city`),
  KEY `persons_ibfk_2` (`persons_program`),
  KEY `persons_ibfk_3` (`persons_religion`),
  KEY `persons_ibfk_4` (`persons_nationality`),
  CONSTRAINT `persons_ibfk_1` FOREIGN KEY (`persons_city`) REFERENCES `cities` (`city_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `persons_ibfk_2` FOREIGN KEY (`persons_program`) REFERENCES `programs` (`program_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `persons_ibfk_3` FOREIGN KEY (`persons_religion`) REFERENCES `religions` (`religion_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `persons_ibfk_4` FOREIGN KEY (`persons_nationality`) REFERENCES `nationalities` (`nationality_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons`
--

LOCK TABLES `persons` WRITE;
/*!40000 ALTER TABLE `persons` DISABLE KEYS */;
INSERT INTO `persons` VALUES (1,'Henrik','Edholm','M',1,2,1,'012345678','henrik@student.gu.se',1,'1992-05-27'),(2,'Mikaela','Lidström','F',1,2,1,'012345678','mikaela@student.gu.se',1,'1988-08-22'),(3,'David','Szabo','M',3,2,1,'012345678','david@student.gu.se',1,'1989-09-21'),(4,'Felix','Fortoul','M',1,4,1,'012345678','felix@student.gu.se',1,'1990-08-09'),(5,'Johan','Nilsson','M',1,1,1,'0334534553','johan.nilsson@student.gu.se',6,'1989-03-15'),(6,'Pelle','Persson','M',1,4,2,'031444555','pelle.p@student.gu.se',1,'1987-05-21'),(7,'Hans','Svensson','M',1,3,1,'012345678','hasse.s@student.gu.se',5,'1990-02-07'),(8,'Harald','Karlsson','M',1,5,3,'012345678','harald.k@student.gu.se',2,'1991-09-15'),(9,'Johanna','Johansson','F',1,5,4,'012345678','johanna.j@student.gu.se',3,'1987-08-18'),(10,'Stina','Björk','F',1,4,3,'012345678','stina.b@student.gu.se',4,'1985-06-03'),(11,'Fredrik','Bladh','M',1,8,3,'012345678','fredrik.b@student.gu.se',6,'1990-05-20'),(12,'Sture','Ahl','M',1,2,4,'012345678','sture.a@student.gu.se',6,'1992-12-24'),(13,'Frans','Jonsson','M',21,2,5,'031777444','frans.j@student.gu.se',1,'1991-11-30'),(14,'Fredrika','Svensson','F',1,7,3,'031222333','fredrika.s@student.gu.se',5,'1990-10-31'),(15,'Lars','Gustavsson','M',1,5,1,'031555666','lars.g@student.gu.se',5,'1989-03-25'),(16,'Magnus','Lennartsson','M',1,2,3,'031555666','magnus.l@student.gu.se',6,'1981-03-14'),(17,'Marcus','Larsson','M',1,7,2,'031555666','marcus.l@student.gu.se',1,'1980-07-21'),(18,'Paula','Markusson','F',1,8,4,'031555666','paula.m@student.gu.se',2,'1990-06-24'),(21,'Anna','Larsson','F',1,4,3,'09345345345','anna.larsson@hej.se',6,'1992-04-01'),(22,'Helen','Svensson','M',1,7,3,'0934535345','helen@hej.se',1,'1984-10-10'),(23,'Hans','Andersson','M',1,3,7,'04678978923','hans.andersson@hej.se',3,'1986-07-13'),(24,'Tomas','Haraldsson','M',1,2,3,'046475855','tomas.h@hej.se',6,'1987-02-17'),(25,'Frida','Karlsson','F',1,8,6,'03143434343','frida@hej.se',1,'1989-06-22'),(26,'Anders','Andersson','M',1,10,2,'034535345','anders.andersson@hej.se',1,'1989-07-06'),(27,'Henrik','Allansson','M',1,2,1,'0987234245','henrik.allansson@hej.se',1,'1990-03-16'),(28,'Bruce','Wayne','M',6,2,1,'03453453455','batman@dc.com',1,'1973-05-10'),(29,'Clark','Kent','M',6,5,7,'03567345345','superman@dc.com',6,'1981-04-12'),(30,'Jesus','Christ','M',12,4,4,'0234234234','jesus@heaven.se',1,'1980-09-20'),(31,'Mickey','Mouse','M',6,3,3,'02342342','mickey@disney.com',5,'1985-09-10'),(32,'Charles','Xavier','M',6,8,7,'0345345353','xavier@@xmen.com',3,'1955-02-02'),(33,'Donald','Duck','M',6,9,5,'023423424','donald@disney.com',4,'1979-07-05');
/*!40000 ALTER TABLE `persons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons_book_genres`
--

DROP TABLE IF EXISTS `persons_book_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons_book_genres` (
  `person_id` int(11) NOT NULL DEFAULT '0',
  `book_genre_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`person_id`,`book_genre_id`),
  KEY `persons_book_genres_ibfk_2` (`book_genre_id`),
  CONSTRAINT `persons_book_genres_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `persons_book_genres_ibfk_2` FOREIGN KEY (`book_genre_id`) REFERENCES `book_genres` (`book_genre_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons_book_genres`
--

LOCK TABLES `persons_book_genres` WRITE;
/*!40000 ALTER TABLE `persons_book_genres` DISABLE KEYS */;
INSERT INTO `persons_book_genres` VALUES (2,1),(8,1),(11,1),(15,1),(17,1),(18,1),(31,1),(33,1),(1,2),(4,2),(5,2),(11,2),(14,2),(15,2),(26,2),(11,3),(13,3),(24,3),(27,3),(2,4),(9,4),(13,4),(16,4),(17,4),(32,4),(1,5),(2,5),(5,5),(6,5),(7,5),(9,5),(10,5),(14,5),(16,5),(17,5),(18,5),(21,5),(22,5),(24,5),(25,5),(26,5),(27,5),(29,5),(1,6),(2,6),(4,6),(5,6),(6,6),(8,6),(9,6),(10,6),(11,6),(13,6),(16,6),(30,6),(32,6),(33,6),(1,7),(6,7),(10,7),(12,7),(13,7),(21,7),(22,7),(27,7),(31,7),(3,8),(5,8),(6,8),(7,8),(9,8),(15,8),(24,8),(3,9),(4,9),(7,9),(14,9),(16,9),(18,9),(25,9),(29,9),(3,10),(4,10),(8,10),(10,10),(12,10),(26,10),(30,10),(32,10),(3,11),(12,11),(15,11),(17,11),(31,11),(7,12),(8,12),(12,12),(14,12),(18,12),(25,12),(28,12),(30,12);
/*!40000 ALTER TABLE `persons_book_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons_interests`
--

DROP TABLE IF EXISTS `persons_interests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons_interests` (
  `person_id` int(11) NOT NULL DEFAULT '0',
  `interest_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`person_id`,`interest_id`),
  KEY `interest_id` (`interest_id`),
  CONSTRAINT `persons_interests_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `persons_interests_ibfk_2` FOREIGN KEY (`interest_id`) REFERENCES `interests` (`interest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons_interests`
--

LOCK TABLES `persons_interests` WRITE;
/*!40000 ALTER TABLE `persons_interests` DISABLE KEYS */;
INSERT INTO `persons_interests` VALUES (5,1),(6,1),(10,1),(16,1),(24,1),(25,1),(7,2),(10,2),(15,2),(33,2),(2,3),(8,3),(25,3),(31,3),(18,4),(24,4),(28,4),(1,5),(4,5),(15,5),(24,5),(6,6),(14,6),(15,6),(3,7),(14,7),(16,7),(30,7),(6,8),(11,8),(12,8),(11,9),(17,9),(17,10),(32,10),(2,11),(30,11),(3,12),(5,13),(9,13),(15,14),(16,14),(32,14),(17,15),(18,15),(9,16),(12,16),(28,16),(2,17),(8,17),(9,17),(13,17),(33,17),(5,18),(14,18),(30,18),(14,19),(30,19),(7,20),(13,20),(16,20),(17,20),(1,21),(6,21),(11,21),(21,21),(22,21),(1,22),(4,22),(7,22),(21,22),(26,22),(27,22),(28,24),(30,24),(32,24),(26,25),(33,26),(30,27),(33,27),(8,28),(12,28),(13,28),(31,28),(3,29),(8,29),(31,29),(3,30),(9,30),(10,30),(7,31),(18,31),(27,31),(31,31),(11,32),(18,32),(27,32),(29,32),(2,33),(10,33),(12,33),(13,33),(25,33),(29,33),(4,34),(1,35),(4,35),(5,35),(22,35),(26,35),(29,35),(28,36),(29,36),(32,37);
/*!40000 ALTER TABLE `persons_interests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons_languages`
--

DROP TABLE IF EXISTS `persons_languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons_languages` (
  `person_id` int(11) NOT NULL DEFAULT '0',
  `language_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`person_id`,`language_id`),
  KEY `persons_languages_ibfk_2` (`language_id`),
  CONSTRAINT `persons_languages_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `persons_languages_ibfk_2` FOREIGN KEY (`language_id`) REFERENCES `languages` (`language_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons_languages`
--

LOCK TABLES `persons_languages` WRITE;
/*!40000 ALTER TABLE `persons_languages` DISABLE KEYS */;
INSERT INTO `persons_languages` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(21,1),(22,1),(24,1),(25,1),(26,1),(27,1),(7,2),(12,2),(16,2),(26,2),(14,3),(18,3),(21,3),(24,3),(1,4),(2,4),(3,4),(4,4),(5,4),(6,4),(7,4),(9,4),(11,4),(16,4),(17,4),(22,4),(25,4),(26,4),(27,4),(28,4),(29,4),(31,4),(32,4),(33,4),(3,5),(28,5),(15,6),(6,7),(32,7),(10,8),(29,8),(31,8),(10,9),(18,9),(31,9),(9,11),(3,12),(32,12),(30,13),(13,14),(32,14),(8,15),(29,15),(8,16),(30,16),(11,17),(17,17),(28,18),(30,18),(6,19),(28,19),(15,20);
/*!40000 ALTER TABLE `persons_languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons_movie_genres`
--

DROP TABLE IF EXISTS `persons_movie_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons_movie_genres` (
  `person_id` int(11) NOT NULL DEFAULT '0',
  `movie_genre_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`person_id`,`movie_genre_id`),
  KEY `persons_movie_genres_ibfk_2` (`movie_genre_id`),
  CONSTRAINT `persons_movie_genres_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `persons_movie_genres_ibfk_2` FOREIGN KEY (`movie_genre_id`) REFERENCES `movie_genres` (`movie_genre_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons_movie_genres`
--

LOCK TABLES `persons_movie_genres` WRITE;
/*!40000 ALTER TABLE `persons_movie_genres` DISABLE KEYS */;
INSERT INTO `persons_movie_genres` VALUES (8,1),(11,1),(14,1),(15,1),(17,1),(25,1),(28,1),(33,1),(5,2),(7,2),(12,2),(14,2),(16,2),(17,2),(1,3),(2,3),(6,3),(7,3),(13,3),(16,3),(21,3),(26,3),(27,3),(30,3),(31,3),(5,4),(7,4),(8,4),(11,4),(12,4),(14,4),(16,4),(18,4),(24,4),(25,4),(30,4),(31,4),(1,5),(6,5),(9,5),(11,5),(13,5),(15,5),(17,5),(22,5),(27,5),(28,5),(29,5),(30,5),(2,6),(12,6),(13,6),(15,6),(21,6),(22,6),(24,6),(26,6),(29,6),(1,7),(2,7),(3,7),(4,7),(6,7),(12,7),(13,7),(15,7),(18,7),(25,7),(26,7),(30,7),(3,8),(7,8),(12,8),(16,8),(17,8),(18,8),(28,8),(33,8),(1,9),(3,9),(5,9),(10,9),(11,9),(14,9),(18,9),(27,9),(28,9),(30,9),(32,9),(33,9),(3,10),(4,10),(6,10),(10,10),(13,10),(2,11),(9,11),(29,11),(32,11),(9,12),(5,13),(9,13),(10,13),(32,13),(4,14),(8,14),(10,14),(24,14),(28,14),(4,15),(8,15),(28,15);
/*!40000 ALTER TABLE `persons_movie_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons_music_genres`
--

DROP TABLE IF EXISTS `persons_music_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons_music_genres` (
  `person_id` int(11) NOT NULL DEFAULT '0',
  `music_genre_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`person_id`,`music_genre_id`),
  KEY `persons_music_genres_ibfk_2` (`music_genre_id`),
  CONSTRAINT `persons_music_genres_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `persons_music_genres_ibfk_2` FOREIGN KEY (`music_genre_id`) REFERENCES `music_genres` (`music_genre_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons_music_genres`
--

LOCK TABLES `persons_music_genres` WRITE;
/*!40000 ALTER TABLE `persons_music_genres` DISABLE KEYS */;
INSERT INTO `persons_music_genres` VALUES (7,1),(11,1),(12,1),(15,1),(17,1),(32,1),(3,2),(6,2),(11,2),(22,2),(29,2),(2,3),(5,3),(9,3),(13,3),(16,3),(27,3),(30,3),(1,4),(3,4),(6,4),(9,4),(12,4),(15,4),(21,4),(29,4),(31,4),(1,5),(2,5),(5,5),(12,5),(13,5),(16,5),(18,5),(22,5),(27,5),(32,5),(1,6),(3,6),(5,6),(21,6),(29,6),(3,7),(8,7),(9,7),(16,7),(27,7),(31,7),(8,8),(13,8),(31,8),(2,9),(5,9),(7,9),(8,9),(12,9),(13,9),(16,9),(28,9),(33,9),(2,10),(6,10),(8,10),(10,10),(11,10),(12,10),(14,10),(18,10),(30,10),(1,11),(6,11),(10,11),(11,11),(28,11),(7,12),(10,12),(15,12),(25,12),(26,12),(33,12),(4,13),(10,13),(18,13),(25,13),(33,13),(4,14),(14,14),(18,14),(24,14),(25,14),(26,14),(4,15),(14,15),(17,15),(24,15),(26,15),(17,16),(28,16),(9,17),(14,17),(17,17),(24,17),(30,17),(4,18),(7,18),(15,18);
/*!40000 ALTER TABLE `persons_music_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programs`
--

DROP TABLE IF EXISTS `programs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `programs` (
  `program_id` int(11) NOT NULL AUTO_INCREMENT,
  `program_name` text,
  `programs_university` int(11) DEFAULT NULL,
  PRIMARY KEY (`program_id`),
  KEY `programs_university` (`programs_university`),
  CONSTRAINT `programs_ibfk_1` FOREIGN KEY (`programs_university`) REFERENCES `universities` (`university_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programs`
--

LOCK TABLES `programs` WRITE;
/*!40000 ALTER TABLE `programs` DISABLE KEYS */;
INSERT INTO `programs` VALUES (1,'Software Engineering & Management',1),(2,'Systemvetenskap',1),(3,'Lärarprogrammet',1),(4,'Civilingenjör Data',2),(5,'Civilingenjör Electro',2),(6,'Sjuksjöteskeprogrammet',3),(7,'Läkarprogrammet',3),(8,'Juristprogrammet',1);
/*!40000 ALTER TABLE `programs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regions`
--

DROP TABLE IF EXISTS `regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regions` (
  `region_id` int(11) NOT NULL AUTO_INCREMENT,
  `region_name` text,
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regions`
--

LOCK TABLES `regions` WRITE;
/*!40000 ALTER TABLE `regions` DISABLE KEYS */;
INSERT INTO `regions` VALUES (1,'Blekinge'),(2,'Bohuslän'),(3,'Dalarna'),(4,'Dalsland'),(5,'Gotland'),(6,'Gästrikland'),(7,'Halland'),(8,'Helsingland'),(9,'Härjedalen'),(10,'Jämtland'),(11,'Lappland'),(12,'Medelpad'),(13,'Norrbotten'),(14,'Närke'),(15,'Skåne'),(16,'Småland'),(17,'Södermanland'),(18,'Uppland'),(19,'Värmland'),(20,'Västerbotten'),(21,'Västergötland'),(22,'Västmanland'),(23,'Ångermanland'),(24,'Öland'),(25,'Östergötland');
/*!40000 ALTER TABLE `regions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `religions`
--

DROP TABLE IF EXISTS `religions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `religions` (
  `religion_id` int(11) NOT NULL AUTO_INCREMENT,
  `religion_name` text,
  PRIMARY KEY (`religion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `religions`
--

LOCK TABLES `religions` WRITE;
/*!40000 ALTER TABLE `religions` DISABLE KEYS */;
INSERT INTO `religions` VALUES (1,'Christianity'),(2,'Islam'),(3,'Judaism'),(4,'Hinduism'),(5,'Buddhism'),(6,'Atheism');
/*!40000 ALTER TABLE `religions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `universities`
--

DROP TABLE IF EXISTS `universities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `universities` (
  `university_id` int(11) NOT NULL AUTO_INCREMENT,
  `university_name` text,
  PRIMARY KEY (`university_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `universities`
--

LOCK TABLES `universities` WRITE;
/*!40000 ALTER TABLE `universities` DISABLE KEYS */;
INSERT INTO `universities` VALUES (1,'Gothenburg University'),(2,'Chalmers'),(3,'Sahlgrenska Universitetssjukhus');
/*!40000 ALTER TABLE `universities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-01-15 10:31:27
