package com.shop.controller;

import com.shop.entity.Comment;
import com.shop.entity.Member;
import com.shop.entity.Notice;
import com.shop.service.CommentService;
import com.shop.service.MemberService;
import com.shop.service.NoticeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@AllArgsConstructor

public class CommentController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private final CommentService commentService;

    // 댓글 보기 페이지
    @GetMapping("/notices/{id}/comments")
    public String showCommentsForNotice(@PathVariable Long id, Model model) {
        Optional<Notice> optionalNotice = noticeService.getNoticeById(id);
        if (optionalNotice.isPresent()) {
            Notice notice = optionalNotice.get();
            List<Comment> comments = commentService.getCommentsByNoticeId(id);
            model.addAttribute("notice", notice);
            model.addAttribute("comments", comments);

            log.info("notice" + notice);
            log.info("comments" + comments);

            return "notice/commentList";
        } else {
            // 공지사항을 찾지 못한 경우에 대한 처리
            // 예: 에러 페이지로 리다이렉트 혹은 에러 메시지 출력

            return "error/notFound";
        }

    }

    //댓글 등록
    @PostMapping("/comments")
    public String addComment(@RequestParam("noticeId") Long noticeId,
                             @RequestParam("content") String content,
                             Model model) {
        log.info("댓글 추가 요청 수신: noticeId={}, content={}", noticeId, content);
        Optional<Notice> optionalNotice = noticeService.getNoticeById(noticeId);
        if (optionalNotice.isPresent()) {
            Notice notice = optionalNotice.get();
            log.info("공지사항 찾음: {}", notice.getTitle());

            Member currentMember = memberService.getCurrentLoggedInMember();
            if (currentMember == null) {
                log.error("로그인이 필요합니다.");
                model.addAttribute("errorMessage", "로그인이 필요합니다.");
                return "error";
            }

            Comment comment = new Comment();
            comment.setNotice(notice);
            comment.setContent(content);
            comment.setMember(currentMember);

            commentService.saveComment(comment);
            log.info("댓글 저장 성공");

            return "redirect:/notices/" + noticeId + "/comments";
        } else {
            log.error("공지사항을 찾을 수 없습니다. noticeId={}", noticeId);
            model.addAttribute("errorMessage", "공지사항을 찾을 수 없습니다.");
            return "error";
        }
    }
    // 댓글 수정 폼 표시
    @GetMapping("/comments/{commentId}/edit")
    public String showEditCommentForm(@PathVariable Long commentId, Model model, Principal principal) {
        Optional<Comment> optionalComment = commentService.getCommentById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            // 현재 로그인된 사용자 정보 가져오기
            Member loggedInMember = memberService.getCurrentLoggedInMember();

            // 댓글 작성자와 현재 로그인된 사용자 비교
            if (loggedInMember != null && loggedInMember.equals(comment.getMember())) {
                model.addAttribute("comment", comment);
                return "comment/editComment";
            } else {
                model.addAttribute("errorMessage", "댓글 수정 권한이 없습니다.");
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "댓글을 찾을 수 없습니다.");
            return "error";
        }
    }

    // 댓글 수정 처리
    @PostMapping("/comments/{commentId}/edit")
    public String editComment(@PathVariable Long commentId, @RequestParam("content") String content, Model model, Principal principal) {
        Optional<Comment> optionalComment = commentService.getCommentById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            // 현재 로그인된 사용자 정보 가져오기
            Member loggedInMember = memberService.getCurrentLoggedInMember();

            // 댓글 작성자와 현재 로그인된 사용자 비교
            if (loggedInMember != null && loggedInMember.equals(comment.getMember())) {
                comment.setContent(content);
                commentService.saveComment(comment);
                return "redirect:/notices/" + comment.getNotice().getId() + "/comments";
            } else {
                model.addAttribute("errorMessage", "댓글 수정 권한이 없습니다.");
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "댓글을 찾을 수 없습니다.");
            return "error";
        }
    }

    // 댓글 삭제 처리
    @PostMapping("/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, Model model, Principal principal) {
        Optional<Comment> optionalComment = commentService.getCommentById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            // 현재 로그인된 사용자 정보 가져오기
            Member loggedInMember = memberService.getCurrentLoggedInMember();

            // 댓글 작성자와 현재 로그인된 사용자 비교
            if (loggedInMember != null && loggedInMember.equals(comment.getMember())) {
                commentService.deleteComment(commentId);
                return "redirect:/notices/" + comment.getNotice().getId() + "/comments";
            } else {
                model.addAttribute("errorMessage", "댓글 삭제 권한이 없습니다.");
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "댓글을 찾을 수 없습니다.");
            return "error";
        }
    }
}
