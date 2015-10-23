DROP DATABASE IF EXISTS LIBRARYS;
CREATE DATABASE LIBRARYS;
USE LIBRARYS; 

DROP TABLE IF EXISTS BOOK;

CREATE TABLE BOOK
(
 title VARCHAR(50),
 author VARCHAR(30),
 BookId INT auto_increment,
 ISBN INT,
 Edition VARCHAR(50),
 YearBought INT,
 Category VARCHAR (30),
 LibraryBranchID INT,
 updatedOn timestamp not null on update current_timestamp,
 PRIMARY KEY(BookId),
 FOREIGN KEY ( LibraryBranchID) references LibraryBranch(BranchName)
 );

DROP TABLE IF EXISTS Person;
CREATE TABLE Person
(PersonId INT AUTO_INCREMENT,
 uNAME VARCHAR(30),
 age INT,
 UserType VARCHAR (30),
 PreferredBranch INT,
 updatedOn timestamp not null on update current_timestamp,
 PRIMARY KEY (PersonId),
 FOREIGN KEY ( PreferredBranch) references LibraryBranch(LibraryBranchID)
) ;
ALTER table Person AUTO_INCREMENT = 1001;


DROP TABLE IF EXISTS LOAN;
CREATE TABLE LOAN
(LoanId INT auto_increment,
 Pid INT,
 Bid INT,
 loanDate DATE DEFAULT '0000-00-00',
 overdue BOOLEAN DEFAULT FALSE,
 PRIMARY KEY(LoanId),
 FOREIGN KEY (Pid) references Person (PersonId),
 FOREIGN KEY (Bid) references Book(BookId) 
) ;

DROP TABLE IF EXISTS LibraryBranch ;
CREATE TABLE LibraryBranch
(uID INT AUTO_INCREMENT,
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
 PRIMARY KEY(RatingId),
 FOREIGN KEY (PersonId) references Person (PersonId),
 FOREIGN KEY (BookId) references BOOK(BookId) 
) ;


LOAD DATA LOCAL INFILE '' INTO TABLE BOOK;
LOAD DATA LOCAL INFILE '' INTO TABLE Person;
LOAD DATA LOCAL INFILE '' INTO TABLE LOAN;
