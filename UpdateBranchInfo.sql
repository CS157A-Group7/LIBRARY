-- Users shall be able to update information. [preferred branch] - 11/14

Use Library;
DROP PROCEDURE IF EXISTS UpdateBranchInfo;
DELIMITER //
CREATE PROCEDURE UpdateBranchInfo ( IN BranchInfo int, uName varchar(150), PersonId int)
BEGIN
START TRANSACTION;
	UPDATE person SET PreferredBranch =  BranchInfo Where uNAME= person.uNAME or personId = person.personId;
END //
DELIMITER ;

#UPDATE person SET PreferredBranch =  5 Where uNAME= 'Anne';


call UpdateBranchInfo(9, 'Anne',null);

select uNAME, PreferredBranch from person;
