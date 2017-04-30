CREATE SCHEMA homelibrary
  AUTHORIZATION sa;

SET SCHEMA homelibrary;

-- USERS

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

-- AUTHORIZATIONS

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

-- LIBRARIES

CREATE TABLE libraries (
  id            INT AUTO_INCREMENT,
  library_owner INT          NOT NULL,
  title         VARCHAR(255) NOT NULL,
  author        VARCHAR(255) NOT NULL,
  borrowed      BOOLEAN
);

ALTER TABLE libraries
  ADD CONSTRAINT libraries_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX library_entry__idx
  ON libraries (library_owner, title, author ASC);


ALTER TABLE libraries
  ADD CONSTRAINT libraries_users_fk FOREIGN KEY (library_owner)
REFERENCES users (id);

-- BORROWS

CREATE TABLE borrows (
  id       INT AUTO_INCREMENT,
  book_id  INT NOT NULL,
  borrower INT,
  returned BOOLEAN
);

ALTER TABLE borrows
  ADD CONSTRAINT borrows_pk PRIMARY KEY (id);

ALTER TABLE borrows
  ADD CONSTRAINT borrows_library_fk FOREIGN KEY (book_id)
REFERENCES libraries (id);

ALTER TABLE borrows
  ADD CONSTRAINT borrows_users_fk FOREIGN KEY (borrower)
REFERENCES users (id);

-- COMMENTS

CREATE TABLE comments (
  id      INT AUTO_INCREMENT,
  book_id INT              NOT NULL,
  author  INT              NOT NULL,
  content VARCHAR(1048576) NOT NULL
);

CREATE INDEX library_entry__idx
  ON comments (book_id ASC);

ALTER TABLE comments
  ADD CONSTRAINT comments_pk PRIMARY KEY (id);

ALTER TABLE comments
  ADD CONSTRAINT comments_library_fk FOREIGN KEY (book_id)
REFERENCES libraries (id);

ALTER TABLE comments
  ADD CONSTRAINT comments_users_fk FOREIGN KEY (author)
REFERENCES users (id);

-- READING

CREATE TABLE reading (
  id         INT AUTO_INCREMENT,
  book_id    INT  NOT NULL,
  reader_id  INT  NOT NULL,
  start_date DATE NOT NULL,
  end_date   DATE
);

CREATE UNIQUE INDEX reading_entry__idx
  ON reading (book_id, reader_id ASC);

ALTER TABLE reading
  ADD CONSTRAINT reading_pk PRIMARY KEY (id);

ALTER TABLE reading
  ADD CONSTRAINT reading_library_fk FOREIGN KEY (book_id)
REFERENCES libraries (id);

ALTER TABLE reading
  ADD CONSTRAINT reading_users_fk FOREIGN KEY (reader_id)
REFERENCES users (id);
