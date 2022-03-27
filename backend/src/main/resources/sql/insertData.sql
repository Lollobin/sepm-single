-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data

DELETE
FROM owner
where id < 0;

INSERT INTO owner(id, firstName, lastName, email)
VALUES (-1, 'Katja', 'Storbeck', 'katja.storbeck@gmail.com');
INSERT INTO owner(id, firstName, lastName, email)
VALUES (-2, 'Christel', 'Naujoks', '');
INSERT INTO owner(id, firstName, lastName, email)
VALUES (-3, 'Björn', 'Müssemeier', '');
INSERT INTO owner(id, firstName, lastName, email)
VALUES (-4, 'Michaela', 'Goldenberg', 'michaela@goldenberg.de');
INSERT INTO owner(id, firstName, lastName, email)
VALUES (-5, 'Simone', 'Maurice', 'smaurice@gmx.com');
INSERT INTO owner(id, firstName, lastName, email)
VALUES (-6, 'Luis', 'Niehaus', '');
INSERT INTO owner(id, firstName, lastName, email)
VALUES (-7, 'Fiona', 'Plate', '');
INSERT INTO owner(id, firstName, lastName, email)
VALUES (-8, 'Kirsten', 'Windelen', 'windelen.kirsten@gmail.com');
INSERT INTO owner(id, firstName, lastName, email)
VALUES (-9, 'Malte', 'Henkel', 'malte@henkel.at');
INSERT INTO owner(id, firstName, lastName, email)
VALUES (-10, 'Ilona', 'Klauke', '');

DELETE
FROM horse
where id < 0;

INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId, fatherId, motherId)
VALUES (-1, 'Wendy', 'Wendy is a great horse', '1980-04-03', 'female', -1, null, null);
INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId, fatherId, motherId)
VALUES (-2, 'Rosie', '', '1992-12-01', 'female', -3, null, null);
INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId, fatherId, motherId)
VALUES (-3, 'Jack', 'Jack has won many prices', '1996-08-02', 'male', -5, null, null);
INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId, fatherId, motherId)
VALUES (-4, 'Charlie', 'Not the smartest', '1999-04-04', 'male', null, -3, -1);
INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId, fatherId, motherId)
VALUES (-5, 'Billy', '', '2001-03-01', 'male', -5, -3, -2);
INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId, fatherId, motherId)
VALUES (-6, 'Ruby', 'Can run faster than any other horse', '2002-02-1', 'female', null, -4, null);
INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId, fatherId, motherId)
VALUES (-7, 'Bella', 'The most beautiful horse', '2010-11-02', 'female', null, -5, -6);
INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId, fatherId, motherId)
VALUES (-8, 'Molly', '', '2015-02-1', 'female', -2, null, null);
INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId, fatherId, motherId)
VALUES (-9, 'Poppy', 'Gone missing', '2019-12-24', 'female', -5, -4, -6);
INSERT INTO horse (id, name, description, dateOfBirth, sex, ownerId, fatherId, motherId)
VALUES (-10, 'Harry', '', '2022-01-03', 'male', null, null, -8);

