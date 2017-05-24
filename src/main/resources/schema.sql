DROP SCHEMA IF EXISTS homelibrary;

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
  confirmed         BOOLEAN,
  creation_date     TIMESTAMP    NOT NULL
);

CREATE UNIQUE INDEX users_username__idx
  ON users (username ASC);

CREATE UNIQUE INDEX users_email__idx
  ON users (email ASC);

ALTER TABLE users
  ADD CONSTRAINT users_pk PRIMARY KEY (id);

-- auth

CREATE TABLE auth (
  id              INT NOT NULL,
  token           VARCHAR(255),
  expiration_Date DATE
);

ALTER TABLE auth
  ADD CONSTRAINT auth_pk PRIMARY KEY (id);

ALTER TABLE auth
  ADD CONSTRAINT auth_users_fk FOREIGN KEY (id)
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
  id          INT AUTO_INCREMENT,
  book_id     INT       NOT NULL,
  borrower    INT,
  owner       INT,
  borrow_date TIMESTAMP NOT NULL,
  return_date TIMESTAMP
);

ALTER TABLE borrows
  ADD CONSTRAINT borrows_pk PRIMARY KEY (id);

ALTER TABLE borrows
  ADD CONSTRAINT borrows_library_fk FOREIGN KEY (book_id)
REFERENCES libraries (id);

ALTER TABLE borrows
  ADD CONSTRAINT borrows_users_bor_fk FOREIGN KEY (borrower)
REFERENCES users (id);

ALTER TABLE borrows
  ADD CONSTRAINT borrows_users_own_fk FOREIGN KEY (owner)
REFERENCES users (id);

-- COMMENTS

CREATE TABLE comments (
  id      INT AUTO_INCREMENT,
  book_id INT              NOT NULL,
  author  VARCHAR(255)     NOT NULL,
  content VARCHAR(1048576) NOT NULL
);

CREATE INDEX comments_book_id__idx
  ON comments (book_id ASC);

ALTER TABLE comments
  ADD CONSTRAINT comments_pk PRIMARY KEY (id);

ALTER TABLE comments
  ADD CONSTRAINT comments_library_fk FOREIGN KEY (book_id)
REFERENCES libraries (id);

ALTER TABLE comments
  ADD CONSTRAINT comments_users_fk FOREIGN KEY (author)
REFERENCES users (username);

-- READING

CREATE TABLE reading (
  id         INT AUTO_INCREMENT,
  book_id    INT  NOT NULL,
  reader_id  INT  NOT NULL,
  start_date TIMESTAMP NOT NULL,
  end_date   TIMESTAMP
);

ALTER TABLE reading
  ADD CONSTRAINT reading_pk PRIMARY KEY (id);

ALTER TABLE reading
  ADD CONSTRAINT reading_library_fk FOREIGN KEY (book_id)
REFERENCES libraries (id);

ALTER TABLE reading
  ADD CONSTRAINT reading_users_fk FOREIGN KEY (reader_id)
REFERENCES users (id);

CREATE TABLE permissions (
  id         INT AUTO_INCREMENT,
  owner_id   INT NOT NULL,
  granted_to INT NOT NULL
);

ALTER TABLE permissions
  ADD CONSTRAINT permissions_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX permissions_entry__idx
  ON permissions (owner_id, granted_to ASC);

ALTER TABLE permissions
  ADD CONSTRAINT permissions_lib_users_fk FOREIGN KEY (owner_id)
REFERENCES users (id);

ALTER TABLE permissions
  ADD CONSTRAINT permissions_grant_users_fk FOREIGN KEY (granted_to)
REFERENCES users (id);

CREATE TABLE invitations (
  id              INT AUTO_INCREMENT,
  inviter_id      INT          NOT NULL,
  sent_to         VARCHAR(255) NOT NULL,
  INVITATION_HASH VARCHAR(255) NOT NULL,
  creation_date   TIMESTAMP,
  completed       BOOLEAN
);

ALTER TABLE invitations
  ADD CONSTRAINT invitations_pk PRIMARY KEY (id);

ALTER TABLE invitations
  ADD CONSTRAINT permissions_users_fk FOREIGN KEY (inviter_id)
REFERENCES users (id);


CREATE UNIQUE INDEX invitations_email__idx
  ON invitations (sent_to ASC);


