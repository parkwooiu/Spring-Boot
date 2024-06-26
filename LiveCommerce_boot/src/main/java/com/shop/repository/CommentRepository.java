package com.shop.repository;

import com.shop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByNoticeId(Long noticeId);
    // 추가적으로 필요한 쿼리 메서드는 여기에 선언 가능
}
