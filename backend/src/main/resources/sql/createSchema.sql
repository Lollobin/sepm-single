CREATE TABLE IF NOT EXISTS horse_sex
(
    id  INTEGER PRIMARY KEY,
    sex VARCHAR(6) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS horse
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    dateOfBirth DATE NOT NULL,
    sex         ENUM ('male', 'female'),
    ownerId     BIGINT
);