package com.adowsky.service;

import com.adowsky.service.entities.ReadingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReadingRepository extends CrudRepository<ReadingEntity, Long> {
    Optional<ReadingEntity> getByBookIdAndAndReaderId(long bookId, long readerId);
}
