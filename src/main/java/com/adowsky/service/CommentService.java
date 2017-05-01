package com.adowsky.service;

import com.adowsky.model.Comment;
import com.adowsky.service.entities.CommentEntity;
import com.adowsky.service.entities.UserEntity;
import com.adowsky.service.exception.UserException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void commentBook(long bookId, Comment comment) {
        UserEntity userEntity = userRepository.getByUsername(comment.getAuthorUsername()).orElseThrow(UserException::noSuchUser);
        CommentEntity commentEntity = new CommentEntity(null, bookId, userEntity.getId(), comment.getComment());
        commentRepository.save(commentEntity);
    }
}
