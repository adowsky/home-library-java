SET SCHEMA homelibrary;

INSERT INTO users (username, password, email, first_name, surname, registration_hash, confirmed, creation_date) VALUES
  ('ado', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'xczv@xcv.com', 'asd',
   'asd', 'abcd', TRUE, '2017-03-03 00:00:00');

INSERT INTO users (username, password, email, first_name, surname, registration_hash, confirmed, creation_date) VALUES
  ('ado2', '256b93a925d9f71f4db05e2ce8a0a8c33cd6f13e907e62e0a8ac0dfbb29657dc', 'xcv@xcv.com', 'asd',
   'asd', 'abcd', TRUE, '2017-03-03 00:00:00');


INSERT INTO libraries (library_owner, title, author, borrowed) VALUES
  (1, 'Pan Tadeusz', 'Jak żyć', FALSE);

INSERT INTO libraries (library_owner, title, author, borrowed) VALUES
  (1, 'DecoMoreno', 'Cacao', TRUE);

INSERT INTO libraries (library_owner, title, author, borrowed) VALUES
  (1, 'Unity. Przewodnik projektanta gier', 'Mike Geig', FALSE);

INSERT INTO borrows (book_id, borrower, owner, borrow_date) VALUES
  (2, 2, 1, '2017-01-01');

INSERT INTO permissions (owner_id, granted_to) VALUES
  (1, 1);

INSERT INTO permissions (owner_id, granted_to) VALUES
  (2, 2);

INSERT INTO permissions (owner_id, granted_to) VALUES
  (1, 2);

INSERT INTO reading (book_id, reader_id, start_date, end_date) VALUES
  (2, 2, '2017-03-03 00:00:00', '2017-05-03 00:00:00');

