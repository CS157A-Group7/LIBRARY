DROP DATABASE IF EXISTS LIBRARYS;
CREATE DATABASE LIBRARYS;
USE LIBRARYS; 

CREATE TABLE LibraryBranch
(uID INT,
 LibraryBranchID INT auto_increment,
 BranchName INT,
 updatedOn timestamp not null on update current_timestamp,
 PRIMARY KEY (LibraryBranchID)
) ;

CREATE TABLE BOOK
(
 title VARCHAR(50) NOT NULL,
 author VARCHAR(30),
 BookId INT auto_increment,
 ISBN INT,
 Edition VARCHAR(50),
 YearBought INT,
 Category VARCHAR (30),
 LibraryBranchID INT ,
 PRIMARY KEY(BookId),
 FOREIGN KEY ( LibraryBranchID) references LibraryBranch(LibraryBranchID)
 );

CREATE TABLE Person
(PersonId INT AUTO_INCREMENT,
 uNAME VARCHAR(30) NOT NULL,
 age INT,
 UserType VARCHAR (30),
 PreferredBranch INT,
 updatedOn timestamp not null on update current_timestamp,
 PRIMARY KEY (PersonId),
 FOREIGN KEY ( PreferredBranch) references LibraryBranch(LibraryBranchID)
) ;
ALTER table Person AUTO_INCREMENT = 1001;

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
