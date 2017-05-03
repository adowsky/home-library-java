package com.adowsky.service;

import com.adowsky.model.Book;
import com.adowsky.model.BorrowedBook;
import com.adowsky.model.Library;
import com.adowsky.model.SimpleUser;
import com.adowsky.service.entities.LibraryEntity;
import com.adowsky.service.exception.LibraryException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final UserService userService;
    private final BorrowService borrowService;

    public Library getLibraryOf(String username) {
        long userId = userService.getUserId(username);
        List<Book> books = libraryRepository.getAllByLibraryOwner(userId).stream()
                .map(book -> new Book(book.getId(), book.getTitle(), book.getAuthor(), (book.isBorrowed())? username : null))
                .collect(Collectors.toList());
        log.info("Fetching library of user={}", userId);

        List<BorrowedBook> borrowedBooks = borrowService.getBooksBorrowedBy(new SimpleUser(userId, username));
        return new Library(books, borrowedBooks);
    }

    public Book addBook(Book book, String username) {
        if (book.getBorrowedBy() != null) {
            throw LibraryException.cannotAddBorrowed();
        }

        long userId = userService.getUserId(username);

        LibraryEntity libraryEntity = LibraryEntity.builder()
                .libraryOwner(userId)
                .author(book.getAuthor())
                .title(book.getTitle())
                .borrowed(false)
                .build();
        libraryEntity = libraryRepository.save(libraryEntity);

        log.info("Added book=({},{}) to user={} library", book.getAuthor(), book.getTitle(), username);
        return new Book(libraryEntity.getId(), libraryEntity.getTitle(), libraryEntity.getAuthor(), null);
    }

    public List<Book> findByTitleAndAuthor(String title, String author) {
        log.info("Looking for book: author={} title={}", author, title);

        List<LibraryEntity> books;
        if (author == null && title == null)
            throw LibraryException.search();
        else if (author == null)
            books = libraryRepository.getAllByTitle(title);
        else if (title == null)
            books = libraryRepository.getAllByAuthor(author);
        else
            books = libraryRepository.getAllByTitleAndAuthor(title, author);

        return books.stream().map(book -> new Book(book.getId(), book.getTitle(), book.getAuthor(), null))
                .collect(Collectors.toList());
    }
}
