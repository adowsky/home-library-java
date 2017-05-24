package com.adowsky.service;

import com.adowsky.service.entities.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBookId(long bookId);
}
