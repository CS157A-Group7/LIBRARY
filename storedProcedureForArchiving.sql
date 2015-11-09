use library;

alter table item add updatedAt timestamp;

drop table archiveitem;
CREATE TABLE `archiveitem` (
  `title` varchar(150) DEFAULT NULL,
  `author` varchar(120) DEFAULT NULL,
  `ItemId` int(11) NOT NULL AUTO_INCREMENT,
  `standardnumber` VARCHAR(20) DEFAULT NULL,
  `Edition` varchar(50) DEFAULT NULL,
  `LibraryBranchID` int(11) DEFAULT NULL,
  `ItemType` varchar(50) DEFAULT NULL,
  `copies` int(11) DEFAULT NULL,
  `updatedAt` timestamp,
  PRIMARY KEY (`ItemId`)
) ;


DROP PROCEDURE IF EXISTS CopyToArchive;
DELIMITER //
CREATE PROCEDURE CopyToArchive ( IN cutoffDate timestamp)
BEGIN
START TRANSACTION;
insert into archiveitem(title,author,standardNumber,edition,libraryBranchID,itemtype,copies,updatedAt) 
select title,author,standardNumber,edition,libraryBranchID,itemtype,copies,updatedAt from item where date(updatedAt)<=date(cutoffdate) ;
commit;
END //
DELIMITER ;


insert into item(title,updatedAt) values("bambi3",current_timestamp());


call copyToArchive(current_timestamp()+1);

