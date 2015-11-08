
/*Create a stored procedure that returns the total number of copies of a book our library owns, given a title 
A. First,find the standard number(S) of A. 
Then, go through the ITEM table to find tuples where standard Number= S. 
Then, add the number of copies together to find the total number of copies the whole library system owns.*/

/*There is no point in your transaction. You did a rollback of a transaction that did not even occur.*/


DROP PROCEDURE IF EXISTS FindNumberOfCopies;
DELIMITER $$
CREATE PROCEDURE FindNumberOfCopies
( 
 IN itemTitle varchar(150)
)
BEGIN
    SELECT copies FROM item WHERE title = itemTitle;

    UPDATE item SET copies = newCopies where title = itemTitle;
END
$$
DELIMITER ;





use library;
select * from person;
DROP PROCEDURE IF EXISTS getPersonByName;
DELIMITER //
CREATE PROCEDURE getPersonByName(IN uName VARCHAR(50))
BEGIN
SELECT *
FROM person
WHERE uName;
END//
DELIMITER ;

CALL getPersonByName('Brandon'); 

select * from person;
Drop table if exists countByLoansMade;
CREATE TABLE countByLoansMade(personId INT PRIMARY KEY, TotalLoansMade INT) ;
INSERT INTO countByLoansMade VALUES (PersonId, TotalLoansMade);
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
