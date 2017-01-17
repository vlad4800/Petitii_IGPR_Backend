-- phpMyAdmin SQL Dump
-- version 4.2.13.3
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Jan 17, 2017 at 11:17 AM
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
-- Database: `tickets`
--

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
SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
