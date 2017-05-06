SET SCHEMA homelibrary;

INSERT INTO users(username, password,email, first_name, surname, registration_hash, confirmed, creation_date) VALUES
  ('ado', 'password', 'adrian@kosinski.com', 'adrian', 'Kosinski','abcd', TRUE, '2107-03-03 00:00:00');

INSERT INTO users(username, password,email, first_name, surname, registration_hash, confirmed, creation_date) VALUES
  ('ado2', '256b93a925d9f71f4db05e2ce8a0a8c33cd6f13e907e62e0a8ac0dfbb29657dc', 'adrian2@kosinski.com', 'adrian', 'Kosinski','abcd', TRUE, '2107-03-03 00:00:00');


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
