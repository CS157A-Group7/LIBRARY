-- when the item id of a book is changed, make similar changes in related tables.
DROP TRIGGER IF EXISTS UpdateItemidTrigger;
delimiter //
CREATE TRIGGER UpdateItemidTrigger
AFTER UPDATE ON item
FOR EACH ROW
BEGIN
UPDATE LOAN SET Itemid = NEW.ItemId WHERE ItemId = OLD.ItemId;
UPDATE RATING SET Itemid = NEW.ItemId WHERE ItemId = OLD.ItemId;
END//
delimiter ;

DROP TRIGGER IF EXISTS InsertTrigger;

-- when a loan is made, update the number of copies as well as the total loans made by user.
DROP TRIGGER IF EXISTS UpdateUserTotalLoans;
delimiter //
CREATE TRIGGER UpdateUserTotalLoans
AFTER INSERT ON loan
FOR EACH ROW
BEGIN
UPDATE person SET TotalLoansMade = TotalLoansMade+1 WHERE Personid = new.pid;
update item set copies = copies -1 where item.ItemId = new.itemid;

END//
delimiter ;