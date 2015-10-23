DROP DATABASE IF EXISTS LIBRARYS;
CREATE DATABASE LIBRARYS;
USE LIBRARYS; 

DROP TABLE IF EXISTS BOOK;

CREATE TABLE BOOK
(
 title VARCHAR(50) NOT NULL,
 author VARCHAR(30),
 BookId INT ,
 ISBN INT,
 Edition VARCHAR(50),
 YearBought INT,
 Category VARCHAR (30),
 LibraryBranchID INT,
 PRIMARY KEY(BookId)
 );

DROP TABLE IF EXISTS Person;
CREATE TABLE Person
(PersonId INT AUTO_INCREMENT,
 uNAME VARCHAR(30) NOT NULL,
 age INT,
 UserType VARCHAR (30),
 PreferredBranch INT,
 updatedOn timestamp not null on update current_timestamp,
 PRIMARY KEY (PersonId)
 
) ;
ALTER table Person AUTO_INCREMENT = 1001;


DROP TABLE IF EXISTS LOAN;
CREATE TABLE LOAN
(LoanId INT,
 Pid INT,
 Bid INT,
 loanDate DATE DEFAULT '0000-00-00',
 overdue BOOLEAN DEFAULT FALSE,
 PRIMARY KEY(LoanId)
) ;

DROP TABLE IF EXISTS LibraryBranch ;
CREATE TABLE LibraryBranch
(
 LibraryBranchID INT,
 BranchName INT,
 updatedOn timestamp not null on update current_timestamp,
 PRIMARY KEY (LibraryBranchID)
) ;

DROP TABLE IF EXISTS Rating;
CREATE TABLE Rating
(RatingId INT ,
 RatingDate INT,
 BookId INT,
 PersonId INT,
 Stars BOOLEAN DEFAULT FALSE,
 PRIMARY KEY(RatingId)
) ;


