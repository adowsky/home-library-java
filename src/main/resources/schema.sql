CREATE SCHEMA homelibrary
  AUTHORIZATION sa;

SET SCHEMA homelibrary;

CREATE TABLE users (
  id                INT AUTO_INCREMENT,
  username          VARCHAR(255) NOT NULL,
  password          VARCHAR(255) NOT NULL,
  email             VARCHAR(255) NOT NULL,
  first_name        VARCHAR(255) NOT NULL,
  surname           VARCHAR(255) NOT NULL,
  registration_hash VARCHAR(255) NOT NULL,
  confirmed         BOOLEAN
);

CREATE UNIQUE INDEX users_username__idx
  ON users (username ASC);

ALTER TABLE users
  ADD CONSTRAINT users_pk PRIMARY KEY (id);


CREATE TABLE authorizations (
  id              INT NOT NULL,
  token           VARCHAR(255),
  expiration_Date DATE
);

ALTER TABLE authorizations
  ADD CONSTRAINT authorizations_pk PRIMARY KEY (id);

ALTER TABLE authorizations
  ADD CONSTRAINT authorizations_users_fk FOREIGN KEY (id)
REFERENCES users (id);
