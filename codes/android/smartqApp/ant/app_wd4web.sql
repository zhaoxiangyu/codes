-- phpMyAdmin SQL Dump
-- version 3.3.8.1
-- http://www.phpmyadmin.net
--
-- Host: w.rdc.sae.sina.com.cn:3307
-- Generation Time: Dec 07, 2011 at 09:25 PM
-- Server version: 5.1.47
-- PHP Version: 5.2.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `app_wd4web`
--

-- --------------------------------------------------------

--
-- Table structure for table `app_use`
--

CREATE TABLE IF NOT EXISTS `app_use` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appname` varchar(50) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `lastaccess` datetime DEFAULT NULL,
  `visitcount` int(11) DEFAULT NULL,
  `firstaccess` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=169 ;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE IF NOT EXISTS `payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` text NOT NULL,
  `imei` text NOT NULL,
  `amount` int(11) NOT NULL,
  `time` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;
