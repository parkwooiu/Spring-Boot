package com.shop.controller;

import com.shop.entity.Comment;
import com.shop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/list/{noticeId}")
    public List<Comment> getCommentsByNoticeId(@PathVariable Long noticeId) {
        return commentService.getCommentsByNoticeId(noticeId);
    }

    @PostMapping("/add")
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment);
    }

    // 추가적인 댓글 관련 API는 필요에 따라 추가 가능
}
