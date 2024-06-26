package com.shop.service;

import com.shop.entity.Comment;
import com.shop.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getCommentsByNoticeId(Long noticeId) {
        return commentRepository.findByNoticeId(noticeId);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // 추가적인 서비스 메소드는 필요에 따라 추가 가능
}
