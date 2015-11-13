-- Admin shall be able to retrieve users who have several [two or more] overdue books.* _ _ 11/14

 select PersonId,uname,count(*) 
 from Person join Loan on Person.PersonId=Loan.Pid 
 where overdue = true
 group by personid
 having count(*)>=2;