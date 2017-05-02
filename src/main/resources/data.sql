SET SCHEMA homelibrary;

INSERT INTO users(username, password,email, first_name, surname, registration_hash, confirmed) VALUES
  ('ado', 'password', 'adrian@kosinski.com', 'adrian', 'Kosinski','abcd', TRUE);

INSERT INTO users(username, password,email, first_name, surname, registration_hash, confirmed) VALUES
  ('ado2', 'ado', 'adrian@kosinski.com', 'adrian', 'Kosinski','abcd', TRUE);


INSERT INTO libraries(library_owner, title, author, borrowed) VALUES
  (1, 'Pan Tadeusz', 'Henryk Sienkiewicz', FALSE);

INSERT INTO permissions(owner_id, granted_to) VALUES
  (1,1);

INSERT INTO permissions(owner_id, granted_to) VALUES
  (2,2);
