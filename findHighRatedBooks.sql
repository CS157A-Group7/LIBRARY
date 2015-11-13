-- Users shall be able to see the highest rated books rented for a category (top 3)* - 11/14
select title,avg(stars) 
from rating join item on rating.itemid=Item.itemid 
where ItemType="Book" 
group by title,standardnumber 
order by avg(stars) desc
limit 3;


