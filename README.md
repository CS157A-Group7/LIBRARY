DROP DATABASE IF EXISTS LIBRARY;
CREATE DATABASE LIBRARY;
USE LIBRARY; 
DROP TABLE IF EXISTS BOOK;

CREATE TABLE BOOK
(
 title VARCHAR(50),
 author VARCHAR(30),
 copies INT,
 BookId INT,
 ISBN INT,
 Edition VARCHAR(50),
 YearBought INT,
 Category VARCHAR (50),
 LibraryBranchId INT,
 PRIMARY KEY(BookId)
 );

DROP TABLE IF EXISTS USER;
CREATE TABLE USER
(uID INT AUTO_INCREMENT,
 uNAME VARCHAR(30),
 age INT,
 UserType VARCHAR (30),
 PreferredBranch INT,
 updatedOn timestamp not null on update current_timestamp,
 PRIMARY KEY (uID)
) ;

DROP TABLE IF EXISTS USER ;
CREATE TABLE USER
(uID INT AUTO_INCREMENT,
 uNAME VARCHAR(30),
 age INT,
 loaned INT,
 updatedOn timestamp not null on update current_timestamp,
 PRIMARY KEY (uID)
) ;
ALTER table USER AUTO_INCREMENT = 1001;

DROP TABLE IF EXISTS LOAN;
CREATE TABLE LOAN
(uID INT ,
 title VARCHAR(50),
 loanDate DATE DEFAULT '0000-00-00',
 overdue BOOLEAN DEFAULT FALSE,
 PRIMARY KEY(uID,title,loanDate),
 FOREIGN KEY (uID) references User (uID),
 FOREIGN KEY (title) references Book(title) 
) ;

LOAD DATA LOCAL INFILE '~/Dropbox/CS157A/LectureNotes/books.txt' INTO TABLE BOOK;
LOAD DATA LOCAL INFILE '~/Dropbox/CS157A/LectureNotes/users.txt' INTO TABLE USER;
LOAD DATA LOCAL INFILE '~/Dropbox/CS157A/LectureNotes/loans.txt' INTO TABLE LOAN;
