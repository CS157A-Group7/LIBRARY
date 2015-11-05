DROP PROCEDURE IF EXISTS countByLoansMade;
DELIMITER //
CREATE PROCEDURE countByLoansMade
(IN retirementAge INT,OUT total INT)
BEGIN
SELECT count(*) INTO TotalLoansMade FROM person
WHERE TotalLoansMade > 0 ;
END //
DELIMITER ;
CALL countByLoansMade(10, @result);SELECT @result;

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
delete from countByLoansMade where TotalLoansMade = 1;
CALL CommitTest;
rollback;

select * from countByLoansMade;

