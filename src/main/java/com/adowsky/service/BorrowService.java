package com.adowsky.service;

import com.adowsky.model.*;
import com.adowsky.service.entities.BorrowEntity;
import com.adowsky.service.entities.LibraryEntity;
import com.adowsky.service.exception.BorrowException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final LibraryRepository libraryRepository;
    private final UserService userService;

    @Transactional
    public void borrow(Long bookId, Long borrowerId) {
        LibraryEntity libraryEntity = libraryRepository.findOne(bookId);
        if (libraryEntity == null) {
            throw BorrowException.noSuchBook();
        }

        if (libraryEntity.isBorrowed()) {
            throw BorrowException.bookBorrowed();
        }

        BorrowEntity borrowEntity = new BorrowEntity(null, libraryEntity, borrowerId, libraryEntity.getLibraryOwner(), false);
        borrowRepository.save(borrowEntity);

        libraryEntity.setBorrowed(true);

        log.info("Borrowed book {} from user {} by user {}", bookId, libraryEntity.getLibraryOwner(), borrowerId);
        libraryRepository.save(libraryEntity);
    }

    @Transactional
    public void returnBook(Long bookId, Long borrowerId) {
        LibraryEntity libraryEntity = libraryRepository.findOne(bookId);
        if (libraryEntity == null) {
            throw BorrowException.noSuchBook();
        }

        if (!libraryEntity.isBorrowed()) {
            throw BorrowException.bookBorrowed();
        }

        List<BorrowEntity> borrows = borrowRepository.findAllByBookIdAndReturned(bookId, false);
        if(borrows.size() != 1) {
            throw BorrowException.multipleBorrow();
        }
        BorrowEntity borrow = borrows.get(0);
        borrow.setReturned(true);
        borrowRepository.save(borrow);

        libraryEntity.setBorrowed(false);

        log.info("Borrowed book {} from user {} by user {}", bookId, libraryEntity.getLibraryOwner(), borrowerId);
        libraryRepository.save(libraryEntity);
    }

    List<BorrowedBook> getBooksBorrowedBy(SimpleUser user) {
        List<BorrowEntity> borrows = borrowRepository.findAllByBorrower(user.getId());
        Map<Long, List<BorrowEntity>> borrowsByBookOwner = borrows.stream()
                .collect(Collectors.groupingBy(BorrowEntity::getOwner));
        Map<User, List<BorrowEntity>> borrowsByOwnerUser = borrowsByBookOwner.entrySet().stream()
                .collect(Collectors.toMap(e -> userService.getRichUserById(e.getKey()), Map.Entry::getValue));

        return borrowsByOwnerUser.entrySet().stream()
                .flatMap(entity -> entity.getValue().stream()
                        .map(b -> new BorrowedBook(new Book(b.getBook().getId(), b.getBook().getTitle(), b.getBook().getAuthor(), user.getUsername()),
                                new UserDetails(entity.getKey().getUsername(), entity.getKey().getFirstName(), entity.getKey().getSurname()))))
                .collect(Collectors.toList());
    }
}
