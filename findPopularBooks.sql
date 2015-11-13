-- Users shall be able to see the most popular books rented for a category (top 3)* - 11/14
select title,count(*) total 
from loan join item on Loan.itemid=Item.itemid 
where ItemType='BOOK' 
group by title,standardNumber 
order by total desc 
Limit 3 ;

