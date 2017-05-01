package com.adowsky.service;

import com.adowsky.model.Book;
import com.adowsky.service.entities.LibraryEntity;
import com.adowsky.service.exception.LibraryException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public List<Book> getLibraryOf(Long userId) {
        List<LibraryEntity> books = libraryRepository.getAllByLibraryOwner(userId);
        return books.stream()
                .map(book -> new Book(book.getId(), book.getTitle(), book.getAuthor(), book.isBorrowed()))
                .collect(Collectors.toList());
    }

    public void addBook(Book book, Long userId) {
        if(book.isBorrowed()) {
            throw LibraryException.cannotAddBorrowed();
        }

        LibraryEntity libraryEntity = LibraryEntity.builder()
                .libraryOwner(userId)
                .author(book.getAuthor())
                .title(book.getTitle())
                .borrowed(false)
                .build();
        libraryRepository.save(libraryEntity);
    }

    public List<Book> findByTitleAndAuthor(String title, String author) {
        List<LibraryEntity> books;
        if(author == null && title == null)
            throw LibraryException.search();
        else if(author == null)
            books = libraryRepository.getAllByTitle(title);
        else if(title == null)
            books = libraryRepository.getAllByAuthor(author);
        else
            books = libraryRepository.getAllByTitleAndAuthor(title, author);

        return books.stream().map(book -> new Book(book.getId(), book.getTitle(), book.getAuthor(), book.isBorrowed()))
                .collect(Collectors.toList());
    }
}
