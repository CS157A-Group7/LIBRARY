-- Users shall be able to update information. [preferred branch] - 11/14

DROP PROCEDURE IF EXISTS UpdateBranchInfo;
DELIMITER //
CREATE PROCEDURE UpdateBranchInfo ( IN BranchInfo int)
BEGIN
START TRANSACTION;
	UPDATE person SET PreferredBranch =  BranchInfo Where uNAME= person.uNAME or person.PersonId;
END //
DELIMITER ;

UPDATE person SET PreferredBranch =  4 Where uNAME= 'Anne';


call UpdateBranchInfo(4);
