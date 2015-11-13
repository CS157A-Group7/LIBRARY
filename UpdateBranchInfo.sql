-- Users shall be able to update information. [preferred branch] - 11/14

update person set PreferredBranch= (select libraryBranchID from librarybranch where BranchName=?) where person.PersonId = ?";
      
