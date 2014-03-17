-- phpMyAdmin SQL Dump
-- version 3.5.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 12, 2014 at 06:53 PM
-- Server version: 5.5.29
-- PHP Version: 5.4.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

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
  `trackCourse` varchar(3) NOT NULL,
  `trackSpeed` varchar(5) NOT NULL,
  `trackTag` varchar(32) NOT NULL,
  PRIMARY KEY (`trackNum`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `tracks`
--

INSERT INTO `tracks` (`trackNum`, `trackIdentity`, `trackPlatform`, `trackLat`, `trackLon`, `trackAlt`, `trackCategory`, `trackCourse`, `trackSpeed`, `trackTag`) VALUES
(1, 'friend', 'destroyer', '39.980261', '-74.90117', '0', 'surface', '180', '15', '1'),
(2, 'friend', 'battleship', '41.480261', '-74.90117', '0', 'surface', '45', '15', '2'),
(3, 'friend', 'destroyer', '39.980261', '-73.40117', '0', 'surface', '45', '15', '3'),
(4, 'friend', 'frigate', '38.480261', '-74.90117', '0', 'surface', '225', '15', '4'),
(5, 'friend', 'littoral', '39.980261', '-76.40117', '0', 'surface', '315', '15', '5'),
(6, 'friend', '', '42.980261', '-74.90117', '0', 'surface', '45', '15', '6'),
(7, 'hostile', 'missile', '39.980261', '-71.90117', '0', 'air', '135', '1500', '7'),
(8, 'friend', '', '36.980261', '-74.90117', '0', 'air', '45', '500', '8'),
(9, 'hostile', 'fighter', '39.980261', '-77.90117', '0', 'air', '315', '900', '9'),
(10, 'hostile', '', '42.230261', '-74.90117', '0', 'surface', '315', '15', '10'),
(11, 'unknown', 'missile', '39.980261', '-72.65117', '0', 'air', '45', '1500', '11'),
(12, 'unknown', '', '37.730261', '-74.90117', '0', 'air', '135', '500', '12'),
(13, 'unknown', 'fighter', '39.980261', '-77.15117', '0', 'air', '225', '900', '13'),
(14, 'neutral', 'missile', '43.730261', '-74.90117', '0', 'surface', '10', '1500', '14'),
(15, 'neutral', 'missile', '39.980261', '-71.15117', '0', 'air', '90', '1500', '15'),
(16, 'neutral', '', '36.230261', '-74.90117', '0', 'surface', '180', '15', '16'),
(17, 'neutral', 'fighter', '39.980261', '-78.65117', '0', 'air', '270', '900', '17');