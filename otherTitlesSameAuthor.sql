-- find books written by same author as a given book.

select distinct title 
from item old
where author = (select author 
				from item 
                where title=?)