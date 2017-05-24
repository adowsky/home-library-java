package com.adowsky.service;

import com.adowsky.service.entities.ReadingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ReadingRepository extends CrudRepository<ReadingEntity, Long> {
    Optional<ReadingEntity> getByBookIdAndAndReaderIdAndEndDateIsNull(long bookId, long readerId);

    List<ReadingEntity> getByReaderId(long readerId);

    Optional<ReadingEntity> getFirstByReaderIdAndEndDateNotNullOrderByEndDateDesc(long readerId);

    List<ReadingEntity> getAllByReaderIdAndEndDateNotNullOrderByEndDate(@Param("userId")long userId);
}
