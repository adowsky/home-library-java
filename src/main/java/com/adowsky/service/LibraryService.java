package com.adowsky.service;

import com.adowsky.model.Book;
import com.adowsky.service.entities.LibraryEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
