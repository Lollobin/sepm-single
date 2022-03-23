CREATE TABLE IF NOT EXISTS horse
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NULL,
    dateOfBirth DATE NOT NULL,
    sex         ENUM ('male', 'female') NOT NULL,
    ownerId     BIGINT NULL,
    fatherId    BIGINT NULL,
    motherId    BIGINT NULL
);