use library;


-- Give a rating of 5 to all items after a new user "paid" is added to the user list.
DROP TRIGGER IF EXISTS SuperReviewer;
delimiter //
CREATE TRIGGER SuperReviewer
AFTER INSERT ON person
FOR EACH ROW
BEGIN
IF (new.uname= 'PaidGuy') then
     INSERT Into RATING select current_date(),itemid,new.personid,5  from item;
     END IF;
END//
delimiter ;



-- when a loan is made, update the number of copies as well as the total loans made by user.
DROP TRIGGER IF EXISTS UpdateUserTotalLoans;
delimiter //
CREATE TRIGGER UpdateUserTotalLoans
BEFORE INSERT ON loan
FOR EACH ROW
BEGIN
UPDATE person SET TotalLoansMade = TotalLoansMade+1 WHERE Personid = new.pid;


END//
delimiter ;

-- when a loan is closed, update the number of copies as well as the total loans made by user.
DROP TRIGGER IF EXISTS UpdateUserTotalLoansOnDelete;
delimiter //
CREATE TRIGGER UpdateUserTotalLoansOnDelete
BEFORE DELETE ON loan
FOR EACH ROW
BEGIN
UPDATE person SET TotalLoansMade = TotalLoansMade-1 WHERE Personid = old.pid;

END//
delimiter ;


