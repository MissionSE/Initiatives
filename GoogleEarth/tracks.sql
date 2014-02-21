-- phpMyAdmin SQL Dump
-- version 3.5.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 20, 2014 at 05:05 AM
-- Server version: 5.5.29
-- PHP Version: 5.4.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `earth`
--

-- --------------------------------------------------------

--
-- Table structure for table `tracks`
--

CREATE TABLE `tracks` (
  `trackNum` int(11) NOT NULL AUTO_INCREMENT,
  `trackIdentity` varchar(10) NOT NULL,
  `trackPlatform` varchar(10) NOT NULL,
  `trackLat` varchar(20) NOT NULL,
  `trackLon` varchar(20) NOT NULL,
  `trackAlt` varchar(10) NOT NULL,
  `trackCategory` varchar(10) NOT NULL,
  PRIMARY KEY (`trackNum`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Dumping data for table `tracks`
--

INSERT INTO `tracks` (`trackNum`, `trackIdentity`, `trackPlatform`, `trackLat`, `trackLon`, `trackAlt`, `trackCategory`) VALUES
(1, 'friend', 'destroyer', '39.980261', '-74.90117', '0', 'surface'),
(2, 'friend', 'battleship', '41.480261', '-74.90117', '0', 'surface'),
(3, 'friend', 'destroyer', '39.980261', '-73.40117', '0', 'surface'),
(4, 'friend', 'frigate', '38.480261', '-74.90117', '0', 'surface'),
(5, 'friend', 'littoral', '39.980261', '-76.40117', '0', 'surface'),
(6, 'hostile', '', '42.980261', '-74.90117', '0', 'surface'),
(7, 'hostile', 'missile', '39.980261', '-71.90117', '0', 'air'),
(8, 'hostile', '', '36.980261', '-74.90117', '0', 'air'),
(9, 'hostile', 'fighter', '39.980261', '-77.90117', '0', 'air'),
(10, 'unknown', '', '42.230261', '-74.90117', '0', 'surface'),
(11, 'unknown', 'missile', '39.980261', '-72.65117', '0', 'air'),
(12, 'unknown', '', '37.730261', '-74.90117', '0', 'air'),
(13, 'unknown', 'fighter', '39.980261', '-77.15117', '0', 'air'),
(14, 'neutral', '', '43.730261', '-74.90117', '0', 'surface'),
(15, 'neutral', 'missile', '39.980261', '-71.15117', '0', 'air'),
(16, 'neutral', '', '36.230261', '-74.90117', '0', 'surface'),
(17, 'neutral', 'fighter', '39.980261', '-78.65117', '0', 'air');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
