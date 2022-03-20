-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data

DELETE FROM horse where id < 0;

INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId)
VALUES (-1, 'Wendy','Wendy is a great horse', '2005-04-03', 'female', '1')
;
