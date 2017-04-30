package com.adowsky.service;

import com.adowsky.service.entities.BorrowEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BorrowRepository extends CrudRepository<BorrowEntity, Long> {
}
