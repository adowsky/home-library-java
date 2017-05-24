package com.adowsky.service;

import com.adowsky.service.entities.BorrowEntity;
import com.adowsky.service.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BorrowRepository extends CrudRepository<BorrowEntity, Long> {
    List<BorrowEntity> findAllByBorrower(UserEntity borrowerId);
    List<BorrowEntity> findAllByBookIdAndReturnDateNull(Long bookId);
    List<BorrowEntity> findAllByOwnerAndReturnDateNull(Long owner);
    List<BorrowEntity> findAllByBookId(Long bookId);
}
