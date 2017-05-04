package com.adowsky.service;

import com.adowsky.service.entities.BorrowEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BorrowRepository extends CrudRepository<BorrowEntity, Long> {
    List<BorrowEntity> findAllByBorrower(Long borrowerId);
    List<BorrowEntity> findAllByBookIdAndReturned(Long bookId, boolean returned);
}
