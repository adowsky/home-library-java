package com.adowsky.service;

import com.adowsky.service.entities.BorrowEntity;
import com.adowsky.service.entities.LibraryEntity;
import com.adowsky.service.exception.BorrowException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final LibraryRepository libraryRepository;

    @Transactional
    public void borrow(Long bookId, Long borrowerId) {
        LibraryEntity libraryEntity = libraryRepository.findOne(bookId);
        if(libraryEntity == null) {
            throw BorrowException.noSuchBook();
        }

        if(libraryEntity.isBorrowed()) {
            throw BorrowException.bookBorrowed();
        }

        BorrowEntity borrowEntity = new BorrowEntity(null, bookId, borrowerId, false);
        borrowRepository.save(borrowEntity);

        libraryEntity.setBorrowed(true);

        log.info("Borrowed book {} from user {} by user {}", bookId, libraryEntity.getLibraryOwner(), borrowerId);
        libraryRepository.save(libraryEntity);
    }

}
