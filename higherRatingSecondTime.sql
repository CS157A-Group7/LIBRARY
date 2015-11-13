use library;
-- Admin can find users who have given items a higher rating the second time he/she rated it. * 11/21
select * 
from rating r1 join rating r2 on   r1.personid=R2.PERSONID join Item i1 on i1.itemid=r1.itemid join item i2 on i2.itemid=r2.itemid
where r1.ratingDate<r2.ratingDate and r1.stars<=r2.stars and i1.title=i2.title ;
