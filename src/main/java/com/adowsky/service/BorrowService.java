package com.adowsky.service;

import com.adowsky.model.*;
import com.adowsky.service.entities.BorrowEntity;
import com.adowsky.service.entities.LibraryEntity;
import com.adowsky.service.entities.UserEntity;
import com.adowsky.service.exception.BorrowException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
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

        BorrowEntity borrowEntity = new BorrowEntity(null, libraryEntity, new UserEntity(borrowerId),
                libraryEntity.getLibraryOwner(), new Timestamp(new Date().getTime()), null);
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
            throw BorrowException.bookNotBorrowed();
        }

        List<BorrowEntity> borrows = borrowRepository.findAllByBookIdAndReturnDateNull(bookId);
        if (borrows.size() != 1) {
            throw BorrowException.multipleBorrow();
        }
        BorrowEntity borrow = borrows.get(0);
        borrow.setReturnDate(new Timestamp(new Date().getTime()));
        borrowRepository.save(borrow);

        libraryEntity.setBorrowed(false);

        log.info("Returned book {} from user {} by user {}", bookId, libraryEntity.getLibraryOwner(), borrowerId);
        libraryRepository.save(libraryEntity);
    }

    List<BorrowedBook> getBooksBorrowedBy(SimpleUser user) {
        List<BorrowEntity> borrows = borrowRepository.findAllByBorrower(new UserEntity(user.getId()));
        Map<Long, List<BorrowEntity>> borrowsByBookOwner = borrows.stream().filter(borrow -> borrow.getReturnDate() == null)
                .collect(Collectors.groupingBy(BorrowEntity::getOwner));
        Map<User, List<BorrowEntity>> borrowsByOwnerUser = borrowsByBookOwner.entrySet().stream()
                .collect(Collectors.toMap(e -> userService.getRichUserById(e.getKey()), Map.Entry::getValue));

        return borrowsByOwnerUser.entrySet().stream()
                .flatMap(entity -> entity.getValue().stream()
                        .map(b -> new BorrowedBook(new LibraryBook(b.getBook().getId(), b.getBook().getTitle(), b.getBook().getAuthor(), user.getUsername()),
                                new UserDetails(entity.getKey().getUsername(), entity.getKey().getFirstName(), entity.getKey().getSurname()))))
                .collect(Collectors.toList());
    }

    Map<Long, String> getBorrowerUsernameByBookIdBorrowedFrom(long userId) {
        return borrowRepository.findAllByOwnerAndReturnDateNull(userId).stream()
                .collect(Collectors.toMap(entity -> entity.getBook().getId(),
                        entity -> entity.getBorrower().getUsername()));
    }

    List<Book> getBooksBorrowedFrom(long userId) {
        return borrowRepository.findAllByOwnerAndReturnDateNull(userId).stream()
                .map(entity -> new Book(entity.getId().toString(), entity.getBook().getTitle(),entity.getBook().getAuthor()))
                .collect(Collectors.toList());
    }


    public List<BorrowHistoryEntry> getBookBorrowHistory(long bookId) {
        return borrowRepository.findAllByBookId(bookId).stream()
                .map(entity -> new BorrowHistoryEntry(bookId,entity.getBorrowDate(), entity.getReturnDate(),
                        entity.getBorrower().getUsername()))
                .sorted(Comparator.comparing(BorrowHistoryEntry::getBorrowDate))
                .collect(Collectors.toList());

    }
}
