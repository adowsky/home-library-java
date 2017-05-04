SET SCHEMA homelibrary;

INSERT INTO users(username, password,email, first_name, surname, registration_hash, confirmed) VALUES
  ('ado', 'password', 'adrian@kosinski.com', 'adrian', 'Kosinski','abcd', TRUE);

INSERT INTO users(username, password,email, first_name, surname, registration_hash, confirmed) VALUES
  ('ado2', 'ado', 'adrian@kosinski.com', 'adrian', 'Kosinski','abcd', TRUE);


INSERT INTO libraries(library_owner, title, author, borrowed) VALUES
  (1, 'Pan Tadeusz', 'Jak żyć', FALSE);


INSERT INTO libraries(library_owner, title, author, borrowed) VALUES
  (1, 'DecoMoreno', 'Cacao', FALSE);

INSERT INTO libraries(library_owner, title, author, borrowed) VALUES
  (1, 'Unity. Przewodnik projektanta gier', 'Mike Geig', TRUE);

INSERT INTO borrows( book_id,borrower ,owner,returned) VALUES
  (2, 2, 1, FALSE);

INSERT INTO permissions(owner_id, granted_to) VALUES
  (1,1);

INSERT INTO permissions(owner_id, granted_to) VALUES
  (2,2);

INSERT INTO permissions(owner_id, granted_to) VALUES
  (1,2);
