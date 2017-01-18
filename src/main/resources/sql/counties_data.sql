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
(10, 'Buzău', '2017-01-16 21:52:07', NULL, NULL),
(11, 'Caraș-Severin', '2017-01-16 21:52:07', NULL, NULL),
(12, 'Călărași', '2017-01-16 21:52:07', NULL, NULL),
(13, 'Cluj', '2017-01-16 21:52:07', NULL, NULL),
(14, 'Constanța', '2017-01-16 21:52:07', NULL, NULL),
(15, 'Covasna', '2017-01-16 21:52:07', NULL, NULL),
(16, 'Dâmbovița', '2017-01-16 21:52:07', NULL, NULL),
(17, 'Dolj', '2017-01-16 21:52:07', NULL, NULL),
(18, 'Galați', '2017-01-16 21:52:07', NULL, NULL),
(19, 'Giurgiu', '2017-01-16 21:52:07', NULL, NULL),
(20, 'Gorj', '2017-01-16 21:52:07', NULL, NULL),
(21, 'Harghita', '2017-01-16 21:52:07', NULL, NULL),
(22, 'Hunedoara', '2017-01-16 21:52:07', NULL, NULL),
(23, 'Ialomița', '2017-01-16 21:52:07', NULL, NULL),
(24, 'Iași', '2017-01-16 21:52:07', NULL, NULL),
(25, 'Ilfov', '2017-01-16 21:52:07', NULL, NULL),
(26, 'Maramureș', '2017-01-16 21:52:07', NULL, NULL),
(27, 'Mehedinți', '2017-01-16 21:52:07', NULL, NULL),
(28, 'Mureș', '2017-01-16 21:52:07', NULL, NULL),
(29, 'Neamț', '2017-01-16 21:52:07', NULL, NULL),
(30, 'Olt', '2017-01-16 21:52:07', NULL, NULL),
(31, 'Prahova', '2017-01-16 21:52:07', NULL, NULL),
(32, 'Satu', '2017-01-16 21:52:07', NULL, NULL),
(33, 'Sălaj', '2017-01-16 21:52:07', NULL, NULL),
(34, 'Sibiu', '2017-01-16 21:52:07', NULL, NULL),
(35, 'Suceava', '2017-01-16 21:52:07', NULL, NULL),
(36, 'Teleorman', '2017-01-16 21:52:07', NULL, NULL),
(37, 'Timiș', '2017-01-16 21:52:07', NULL, NULL),
(38, 'Tulcea', '2017-01-16 21:52:07', NULL, NULL),
(39, 'Vaslui', '2017-01-16 21:52:07', NULL, NULL),
(40, 'Vâlcea', '2017-01-16 21:52:07', NULL, NULL),
(41, 'Vrancea', '2017-01-16 21:52:07', NULL, NULL),
(42, 'București', '2017-01-16 21:52:07', NULL, NULL),
(43, 'Diaspora', '2017-01-16 21:52:07', NULL, NULL);

SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
