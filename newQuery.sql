-- 15. Find all books that are in a audio/video media format. 
select distinct title,author,edition,itemtype from item where itemtype like "%ebook%" 
 union   
 select distinct title,author,edition,itemtype from item where itemtype like "%audio%" 
 union
 select  distinct title,author,edition,itemtype from item where itemtype like"%cd%"
 union  
 distinct select title,author,edition,itemtype from item where itemtype like "%dvd%" ; 
