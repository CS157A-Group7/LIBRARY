DROP DATABASE IF EXISTS LIBRARY;
CREATE DATABASE LIBRARY;
USE LIBRARY; 

CREATE TABLE `librarybranch` 
(
  `LibraryBranchID` int(11) NOT NULL AUTO_INCREMENT,
  `BranchName` varchar(120) DEFAULT NULL,
  `Address` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`LibraryBranchID`)
) ;

CREATE TABLE `item` (
  `title` varchar(150) DEFAULT NULL,
  `author` varchar(120) DEFAULT NULL,
  `ItemId` int(11) NOT NULL AUTO_INCREMENT,
  `standardnumber` VARCHAR(20) DEFAULT NULL,
  `Edition` varchar(50) DEFAULT NULL,
  `LibraryBranchID` int(11) DEFAULT NULL,
  `ItemType` varchar(50) DEFAULT NULL,
  `copies` int(11) DEFAULT NULL,
  PRIMARY KEY (`ItemId`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`LibraryBranchID`) REFERENCES `librarybranch` (`LibraryBranchID`)
) ;

CREATE TABLE `person` (
  `PersonId` int(11) NOT NULL AUTO_INCREMENT,
  `uNAME` varchar(30) NOT NULL,
  `UserType` varchar(30) DEFAULT NULL,
  `PreferredBranch` int(11) DEFAULT NULL,
  `TotalLoansMade` int(11) DEFAULT NULL,
  PRIMARY KEY (`PersonId`),
  CONSTRAINT `person_ibfk_1` FOREIGN KEY (`PreferredBranch`) REFERENCES `librarybranch` (`LibraryBranchID`)
) ;

CREATE TABLE `loan` (
  `LoanId` int(11) NOT NULL AUTO_INCREMENT,
  `Pid` int(11) DEFAULT NULL,
  `Itemid` int(11) DEFAULT NULL,
  `loanDate` date DEFAULT '0000-00-00',
  `overdue` BOOLEAN DEFAULT '0',
  PRIMARY KEY (`LoanId`),
  CONSTRAINT `loan_ibfk_1` FOREIGN KEY (`Pid`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `loan_ibfk_2` FOREIGN KEY (`Itemid`) REFERENCES `item` (`ItemId`)
) ;

CREATE TABLE `rating` (
  `RatingId` int(11) NOT NULL auto_increment,
  `RatingDate` date DEFAULT NULL,
  `ItemId` int(11) DEFAULT NULL,
  `PersonId` int(11) DEFAULT NULL,
  `Stars` BOOLEAN DEFAULT '0',
  PRIMARY KEY (`RatingId`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`PersonId`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`ItemId`) REFERENCES `item` (`ItemId`)
); 