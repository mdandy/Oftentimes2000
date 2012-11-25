-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: db.cip.gatech.edu
-- Generation Time: Nov 25, 2012 at 03:14 PM
-- Server version: 5.5.15-log
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `CONTRIB_maven`
--

-- --------------------------------------------------------

--
-- Table structure for table `oAdvertisements`
--

CREATE TABLE IF NOT EXISTS `oAdvertisements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `title` varchar(64) NOT NULL,
  `type` int(11) NOT NULL,
  `highlights` varchar(140) NOT NULL,
  `fine_print` varchar(140) DEFAULT NULL,
  `street_address` varchar(64) NOT NULL,
  `city` varchar(64) NOT NULL,
  `state` varchar(64) NOT NULL,
  `zipcode` varchar(64) NOT NULL,
  `radius` int(11) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `regular_price` float DEFAULT NULL,
  `promotional_price` float DEFAULT NULL,
  `from_date` datetime NOT NULL,
  `to_date` datetime NOT NULL,
  `url` varchar(64) DEFAULT NULL,
  `category` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`,`username`),
  KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `oDevices`
--

CREATE TABLE IF NOT EXISTS `oDevices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gcm_id` varchar(512) NOT NULL,
  `latitude` double NULL,
  `longitude` double NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `oUsers`
--

CREATE TABLE IF NOT EXISTS `oUsers` (
  `username` varchar(64) NOT NULL,
  `password` varchar(128) NOT NULL,
  `name` varchar(64) NOT NULL,
  `street_address` varchar(64) NOT NULL,
  `city` varchar(64) NOT NULL,
  `state` varchar(64) NOT NULL,
  `zipcode` varchar(64) NOT NULL,
  `website` varchar(64) NULL,
  `email` varchar(64) NOT NULL,
  `about` varchar(4096) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for table `oAdvertisements`
--
ALTER TABLE `oAdvertisements`
  ADD CONSTRAINT `oAdvertisements_ibfk_1` FOREIGN KEY (`username`) REFERENCES `oUsers` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
