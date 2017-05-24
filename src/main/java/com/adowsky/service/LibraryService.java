package com.adowsky.service;

import com.adowsky.model.*;
import com.adowsky.service.entities.LibraryEntity;
import com.adowsky.service.exception.LibraryException;
import com.adowsky.service.external.OpenLibraryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final UserService userService;
    private final BorrowService borrowService;
    private final ReadingService readingService;
    private final OpenLibraryService openLibraryService;

    public Library getLibraryOf(String username) {
        long userId = userService.getUserId(username);
        List<LibraryEntity> libraryEntities = libraryRepository.getAllByLibraryOwner(userId);
        Map<Long, String> borrows = borrowService.getBorrowerUsernameByBookIdBorrowedFrom(userId);

        List<LibraryBook> libraryBooks = libraryEntities.stream()
                .map(book -> new LibraryBook(book.getId(), book.getTitle(), book.getAuthor(), borrows.get(book.getId())))
                .collect(Collectors.toList());

        log.info("Fetching library of user={}", userId);

        List<BorrowedBook> borrowedBooks = borrowService.getBooksBorrowedBy(new SimpleUser(userId, username));
        return new Library(libraryBooks, borrowedBooks);
    }

    public LibraryBook addBook(LibraryBook libraryBook, String username) {
        if (libraryBook.getBorrowedBy() != null) {
            throw LibraryException.cannotAddBorrowed();
        }

        long userId = userService.getUserId(username);

        long booksCount = libraryRepository
                .countAllByLibraryOwnerAndTitleAndAuthor(userId, libraryBook.getTitle(), libraryBook.getAuthor());

        if(booksCount > 0) {
            throw LibraryException.bookExists();
        }

        LibraryEntity libraryEntity = LibraryEntity.builder()
                .libraryOwner(userId)
                .author(libraryBook.getAuthor())
                .title(libraryBook.getTitle())
                .borrowed(false)
                .build();
        libraryEntity = libraryRepository.save(libraryEntity);

        log.info("Added book=({},{}) to user={} library", libraryBook.getAuthor(), libraryBook.getTitle(), username);
        return new LibraryBook(libraryEntity.getId(), libraryEntity.getTitle(), libraryEntity.getAuthor(), null);
    }

    public List<BookMetadata> findByTitleAndAuthor(String title, String author) {
        log.info("Looking for book: author={} title={}", author, title);

        List<LibraryEntity> books;
        if (StringUtils.isBlank(author) && StringUtils.isBlank(title))
            throw LibraryException.search();
        else if (StringUtils.isBlank(author))
            books = libraryRepository.getAllByTitleContains(title);
        else if (StringUtils.isBlank(title))
            books = libraryRepository.getAllByAuthorContains(author);
        else
            books = libraryRepository.getAllByTitleContainsAndAuthorContains(title, author);

        List<BookMetadata> localBooks =  books.stream()
                .map(book -> new BookMetadata(book.getTitle(), book.getAuthor(), "this"))
                .collect(Collectors.toList());
        List<BookMetadata> fromOpenLib = openLibraryService.findByAuthorAndTitle(author, title);
        List<BookMetadata> result = new ArrayList<>();
        result.addAll(localBooks);
        result.addAll(fromOpenLib);
        return result;
    }

    public Statistics getStatisticsForUser(long userId) {
        Book lastBook = readingService.getLastBookForUser(userId);
        double averageBooks = readingService.getAverageBooksReadByUser(userId);
        List<BorrowedBook> borrowedBooks = borrowService.getBooksBorrowedBy(new SimpleUser(userId, null));
        List<Book> booksOutsideLibrary = borrowService.getBooksBorrowedFrom(userId);
        return new Statistics(averageBooks, lastBook, booksOutsideLibrary, borrowedBooks);
    }

    public Book getBookDetails(long bookId) {
        LibraryEntity libraryEntity = libraryRepository.findOne(bookId);
        if (libraryEntity == null) {
            throw LibraryException.noSuchBook();
        }
        return new Book(libraryEntity.getId().toString(), libraryEntity.getTitle(), libraryEntity.getAuthor());

    }
}
