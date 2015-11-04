use library;
select * from person;
DROP TRIGGER IF EXISTS UpdateItemidTrigger;
delimiter //
CREATE TRIGGER UpdateItemidTrigger
AFTER UPDATE ON item
FOR EACH ROW
BEGIN
UPDATE LOAN SET Itemid = NEW.ItemId
WHERE ItemId = OLD.ItemId;
UPDATE RATING SET Itemid = NEW.ItemId
WHERE ItemId = OLD.ItemId;
END//
delimiter ;

DROP TRIGGER IF EXISTS InsertTrigger;


select * from loan;
DROP TRIGGER IF EXISTS UpdateUserTotalLoans;
delimiter //
CREATE TRIGGER UpdateUserTotalLoans
AFTER INSERT ON loan
FOR EACH ROW
BEGIN
UPDATE person SET TotalLoansMade = TotalLoansMade+1
WHERE Personid = new.pid;

END//
delimiter ;
