package com.adowsky.service;

import com.adowsky.model.Comment;
import com.adowsky.service.entities.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void commentBook(Comment comment) {
        CommentEntity commentEntity = new CommentEntity(null, comment.getBookId(), comment.getAuthorUsername(), comment.getComment());
        commentRepository.save(commentEntity);

        log.info("Comment added to book={} by {}", comment.getBookId(), comment.getAuthorUsername());
    }

    public List<Comment> getCommentsOfBook(long bookId) {
        return commentRepository.findAllByBookId(bookId).stream()
                .map(entity -> new Comment(entity.getBookId(), entity.getAuthor(), entity.getContent()))
                .collect(Collectors.toList());


    }

}
