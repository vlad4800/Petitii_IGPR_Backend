-- phpMyAdmin SQL Dump
-- version 4.2.13.3
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Jan 16, 2017 at 11:31 PM
-- Server version: 10.1.20-MariaDB
-- PHP Version: 7.0.14

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `petitions`
--
CREATE DATABASE IF NOT EXISTS `petitions` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `petitions`;

-- --------------------------------------------------------

--
-- Table structure for table `counties`
--

DROP TABLE IF EXISTS `counties`;
CREATE TABLE IF NOT EXISTS `counties` (
  `id` smallint(6) NOT NULL,
  `name` varchar(50) NOT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `counties`
--

INSERT INTO `counties` (`id`, `name`, `create_date`, `update_date`, `delete_date`) VALUES
(1, 'Alba', '2017-01-16 21:52:07', NULL, NULL),
(2, 'Arad', '2017-01-16 21:52:07', NULL, NULL),
(3, 'Argeș', '2017-01-16 21:52:07', NULL, NULL),
(4, 'Bacău', '2017-01-16 21:52:07', NULL, NULL),
(5, 'Bihor', '2017-01-16 21:52:07', NULL, NULL),
(6, 'Bistrița-Năsăud', '2017-01-16 21:52:07', NULL, NULL),
(7, 'Botoșani', '2017-01-16 21:52:07', NULL, NULL),
(8, 'Brașov', '2017-01-16 21:52:07', NULL, NULL),
(9, 'Brăila', '2017-01-16 21:52:07', NULL, NULL),
(10, 'București', '2017-01-16 21:52:07', NULL, NULL),
(11, 'Buzău', '2017-01-16 21:52:07', NULL, NULL),
(12, 'Caraș-Severin', '2017-01-16 21:52:07', NULL, NULL),
(13, 'Călărași', '2017-01-16 21:52:07', NULL, NULL),
(14, 'Cluj', '2017-01-16 21:52:07', NULL, NULL),
(15, 'Constanța', '2017-01-16 21:52:07', NULL, NULL),
(16, 'Covasna', '2017-01-16 21:52:07', NULL, NULL),
(17, 'Dâmbovița', '2017-01-16 21:52:07', NULL, NULL),
(18, 'Dolj', '2017-01-16 21:52:07', NULL, NULL),
(19, 'Galați', '2017-01-16 21:52:07', NULL, NULL),
(20, 'Giurgiu', '2017-01-16 21:52:07', NULL, NULL),
(21, 'Gorj', '2017-01-16 21:52:07', NULL, NULL),
(22, 'Harghita', '2017-01-16 21:52:07', NULL, NULL),
(23, 'Hunedoara', '2017-01-16 21:52:07', NULL, NULL),
(24, 'Ialomița', '2017-01-16 21:52:07', NULL, NULL),
(25, 'Iași', '2017-01-16 21:52:07', NULL, NULL),
(26, 'Ilfov', '2017-01-16 21:52:07', NULL, NULL),
(27, 'Maramureș', '2017-01-16 21:52:07', NULL, NULL),
(28, 'Mehedinți', '2017-01-16 21:52:07', NULL, NULL),
(29, 'Mureș', '2017-01-16 21:52:07', NULL, NULL),
(30, 'Neamț', '2017-01-16 21:52:07', NULL, NULL),
(31, 'Olt', '2017-01-16 21:52:07', NULL, NULL),
(32, 'Prahova', '2017-01-16 21:52:07', NULL, NULL),
(33, 'Satu', '2017-01-16 21:52:07', NULL, NULL),
(34, 'Sălaj', '2017-01-16 21:52:07', NULL, NULL),
(35, 'Sibiu', '2017-01-16 21:52:07', NULL, NULL),
(36, 'Suceava', '2017-01-16 21:52:07', NULL, NULL),
(37, 'Teleorman', '2017-01-16 21:52:07', NULL, NULL),
(38, 'Timiș', '2017-01-16 21:52:07', NULL, NULL),
(39, 'Tulcea', '2017-01-16 21:52:07', NULL, NULL),
(40, 'Vaslui', '2017-01-16 21:52:07', NULL, NULL),
(41, 'Vâlcea', '2017-01-16 21:52:07', NULL, NULL),
(42, 'Vrancea', '2017-01-16 21:52:07', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `petitions`
--

DROP TABLE IF EXISTS `petitions`;
CREATE TABLE IF NOT EXISTS `petitions` (
`id` int(10) unsigned NOT NULL,
  `petition_county_id` smallint(6) NOT NULL,
  `ip` varchar(50) NOT NULL,
  `name` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `address` varchar(200) NOT NULL,
  `county_id` smallint(6) NOT NULL,
  `cnp` int(11) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `petition_attachments`
--

DROP TABLE IF EXISTS `petition_attachments`;
CREATE TABLE IF NOT EXISTS `petition_attachments` (
`id` int(10) unsigned NOT NULL,
  `petition_id` int(10) unsigned NOT NULL,
  `path` varchar(250) NOT NULL,
  `content_type` varchar(50) NOT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `petition_messages`
--

DROP TABLE IF EXISTS `petition_messages`;
CREATE TABLE IF NOT EXISTS `petition_messages` (
`id` int(10) unsigned NOT NULL,
  `petition_id` int(10) unsigned NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `subject` varchar(255) NOT NULL,
  `message` text NOT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `counties`
--
ALTER TABLE `counties`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `petitions`
--
ALTER TABLE `petitions`
 ADD PRIMARY KEY (`id`), ADD KEY `petition_county_id` (`petition_county_id`), ADD KEY `county_id` (`county_id`);

--
-- Indexes for table `petition_attachments`
--
ALTER TABLE `petition_attachments`
 ADD PRIMARY KEY (`id`), ADD KEY `petition_id` (`petition_id`);

--
-- Indexes for table `petition_messages`
--
ALTER TABLE `petition_messages`
 ADD PRIMARY KEY (`id`), ADD KEY `petition_id` (`petition_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `petitions`
--
ALTER TABLE `petitions`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `petition_attachments`
--
ALTER TABLE `petition_attachments`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `petition_messages`
--
ALTER TABLE `petition_messages`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `petitions`
--
ALTER TABLE `petitions`
ADD CONSTRAINT `petitions_ibfk_1` FOREIGN KEY (`county_id`) REFERENCES `counties` (`id`) ON UPDATE CASCADE,
ADD CONSTRAINT `petitions_ibfk_2` FOREIGN KEY (`petition_county_id`) REFERENCES `counties` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `petition_attachments`
--
ALTER TABLE `petition_attachments`
ADD CONSTRAINT `petition_attachments_ibfk_1` FOREIGN KEY (`petition_id`) REFERENCES `petitions` (`id`);

--
-- Constraints for table `petition_messages`
--
ALTER TABLE `petition_messages`
ADD CONSTRAINT `FKtk46ar33apaeld0d46qpa70vo` FOREIGN KEY (`petition_id`) REFERENCES `petitions` (`id`),
ADD CONSTRAINT `petition_messages_ibfk_1` FOREIGN KEY (`petition_id`) REFERENCES `petitions` (`id`) ON UPDATE CASCADE;
SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
