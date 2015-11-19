-- All queries --

-- Users shall be able to see the highest rated books rented for a category (top 3)* - 11/14
select title,avg(stars) 
from rating join item on rating.itemid=Item.itemid 
where ItemType="Book" 
group by title,standardnumber 
order by avg(stars) desc
limit 3;


-- Users shall be able to see the most popular books rented for a category (top 3)* - 11/14
select title,count(*) total 
from loan join item on Loan.itemid=Item.itemid 
where ItemType='BOOK' 
group by title,standardNumber 
order by total desc 
Limit 3 ;


use library;
-- Admin can find users who have given items a higher rating the second time he/she rated it. * 11/21
select * 
from rating r1 join rating r2 on   r1.personid=R2.PERSONID join Item i1 on i1.itemid=r1.itemid join item i2 on i2.itemid=r2.itemid
where r1.ratingDate<r2.ratingDate and r1.stars<=r2.stars and i1.title=i2.title ;


-- find books written by same author as a given book.

select distinct title 
from item old
where author = (select author 
				from item 
                where title=?);
                
                
-- Admin shall be able to retrieve users who have several [two or more] overdue books.* _ _ 11/14

 select PersonId,uname,count(*) 
 from Person join Loan on Person.PersonId=Loan.Pid 
 where overdue = true
 group by personid
 having count(*)>=2;
 
 
 use library;
-- Admins can find the total number of loans made at a library. * 11/21
select count(*) 
from loan join item on item.itemid=loan.itemid 
where libraryBranchID=(select librarybranchid from libraryBranch where branchname=?);

-- Users shall be able to update information. [preferred branch] - 11/14
update person set PreferredBranch= (select libraryBranchID from librarybranch where BranchName=?) where person.PersonId = "?";