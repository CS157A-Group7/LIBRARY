DROP DATABASE IF EXISTS LIBRARY;
CREATE DATABASE LIBRARY;
USE LIBRARY; 

CREATE TABLE LibraryBranch
( LibraryBranchID INT auto_increment,
 BranchName varchar(120),
 Address VARCHAR(120),
 PRIMARY KEY (LibraryBranchID)
) ;

CREATE TABLE ITEM
(
 title VARCHAR(50) NOT NULL,
 author VARCHAR(30),
 ItemId INT auto_increment,
 ISBN INT,
 Edition VARCHAR(50),
 LibraryBranchID INT ,
 ItemType CHAR(2),
 copies int,
 PRIMARY KEY(ItemId),
 FOREIGN KEY ( LibraryBranchID) references LibraryBranch(LibraryBranchID)
 );

CREATE TABLE Person
(PersonId INT AUTO_INCREMENT,
 uNAME VARCHAR(30) NOT NULL,
 UserType VARCHAR (30),
 PreferredBranch INT,
 TotalLoansMade INT,
 PRIMARY KEY (PersonId),
 FOREIGN KEY ( PreferredBranch) references LibraryBranch(LibraryBranchID)
) ;
ALTER table Person AUTO_INCREMENT = 1001;

CREATE TABLE LOAN
(LoanId INT auto_increment,
 Pid INT,
 Itemid INT,
 loanDate DATE DEFAULT '0000-00-00',
 overdue BOOLEAN DEFAULT FALSE,
 PRIMARY KEY(LoanId),
 FOREIGN KEY (Pid) references Person (PersonId),
 FOREIGN KEY (Itemid) references Item(ItemId) 
) ;

CREATE TABLE Rating
(RatingId INT ,
 RatingDate INT,
 ItemId INT,
 PersonId INT,
 Stars BOOLEAN DEFAULT FALSE,
 PRIMARY KEY(RatingId),
 FOREIGN KEY (PersonId) references Person (PersonId),
 FOREIGN KEY (ItemId) references Item(ItemId) 
) ;
