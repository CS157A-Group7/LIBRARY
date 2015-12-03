-- All queries --

-- 1. Users shall be able to see the highest rated books rented for a category (top 3)* - 11/14
select title,avg(stars) 
from rating join item on rating.itemid=Item.itemid 
where ItemType="Book" 
group by title,standardnumber 
order by avg(stars) desc
limit 3;


-- 2. Users shall be able to see the most popular books rented for a category (top 3)* - 11/14
select title,count(*) total 
from loan join item on Loan.itemid=Item.itemid 
where ItemType='BOOK' 
group by title,standardNumber 
order by total desc 
Limit 3 ;


use library;
-- 3. Admin can find users who have given items a higher rating the second time he/she rated it. * 11/21
select * 
from rating r1 join rating r2 on   r1.personid=R2.PERSONID join Item i1 on i1.itemid=r1.itemid join item i2 on i2.itemid=r2.itemid
where r1.ratingDate<r2.ratingDate and r1.stars<=r2.stars and i1.title=i2.title ;


-- 4. find books written by same author as a given book.

select distinct title 
from item old
where author = (select author 
				from item 
                where title=?);
                
                
-- 5. Admin shall be able to retrieve users who have several [two or more] overdue books.* _ _ 11/14

 select PersonId,uname,count(*) 
 from Person join Loan on Person.PersonId=Loan.Pid 
 where overdue = true
 group by personid
 having count(*)>=2;
 
 
 use library;
-- 6. Admins can find the total number of loans made at a library. * 11/21
select count(*) 
from loan join item on item.itemid=loan.itemid 
where libraryBranchID=(select librarybranchid from libraryBranch where branchname=?);

-- 7. Users shall be able to update information. [preferred branch] - 11/14
update person set PreferredBranch= (select libraryBranchID from librarybranch where BranchName=?) where person.PersonId = "?";

/* 8. User shall be able to rent a new book (Display books on for the library) .- [11/21] BY*/
Use library;
update item set copies = copies -1 where item.ItemId = ?; -- if user rent a new book from library, it should defaut = 0, and it should NOT BE null
update person set TotalLoansMade = TotalLoansMade + 1 where personId = ?;
insert into loan SET pid = ?, ItemId = ?, loandate = '?',
overdue = false ON DUPLICATE KEY UPDATE ItemId = ?, loandate = '?',overdue = false;



/* 9. Users shall be able to request a book 
from another branch if it is not there in there in the preferred branch  [11/21] BY*/
USE LIBRARY;
update item set copies = (select copies from item where item.copies >= 1) - 1 where item.ItemId = ?; -- if user rent a new book from library, it should defaut = 0, and it should be null
update person set TotalLoansMade = TotalLoansMade + 1 where personId = ?;
INSERT INTO LOAN SET PID = ?, ItemId = ( SELECT ITEMID FROM ITEM WHERE item.ItemId= ? and  COPIES >= 1), 
loanDate = '?', OVERDUE = FALSE; 
--It is not necessary to define differnt preferrend branch in the loan table, but all item source should share.

/* 10. Users shall be able to return a book.- [11/21] BY*/
use library;

update item set copies = copies +1 where item.ItemId = ?;
update person set TotalLoansMade = TotalLoansMade - 1 where personId = ?;
delete from loan where loan.Itemid = ?;

-- 11. Admins shall be able to see all distinct users who have rented a book. _ _ 11/28 BY

use library;
select distinct title , uNAME 
from person join loan join item on Loan.itemid=Item.itemid 
where UserType ='U'
order by title;

-- 12. Users can rate a book.11/28 BY
insert into rating set ratingdate = '?',itemid = ?,personid = ?,stars =?; -- Strings, int, int, int

-- 13. Users can update the rating for a book later. 11/28 BY
update rating set Stars= ? where RatingId = ?;

/* 14. Users can see a set of “recommended books” which are books that are 
from the same category or written by the same author as the books that they have loaned. 11/28 BY*/

use library;
select  Stars , author, title, ItemType
from rating join item on rating.itemid=Item.itemid
WHERE author <> 'null'
order by author;
