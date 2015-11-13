use library;
-- Admins can find the total number of loans made at a library. * 11/21
select count(*) 
from loan join item on item.itemid=loan.itemid 
where libraryBranchID=(select librarybranchid from libraryBranch where branchname=?)