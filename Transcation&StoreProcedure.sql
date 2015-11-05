
use library;
select * from person;
DROP PROCEDURE IF EXISTS getPersonByName;
DELIMITER //
CREATE PROCEDURE getPersonByName(IN uName VARCHAR(50))
BEGIN
SELECT *
FROM person
WHERE name = uName;
END//
DELIMITER ;

CALL getPersonByName('Brandon'); 

select * from loan;
Drop table if exists countByLoansMade;
CREATE TABLE countByLoansMade(A INT PRIMARY KEY, B INT) ;
INSERT INTO countByLoansMade VALUES (TotalLoansMade);
Drop procedure if exists CommitTest;
delimiter //
create procedure CommitTest()
begin
delete from countByLoansMade where TotalLoansMade = 1;end; //
delimiter ;

START TRANSACTION;
delete from countByLoansMade where TotalLoansMade = 2;
CALL CommitTest;
rollback;

select * from countByLoansMade;

