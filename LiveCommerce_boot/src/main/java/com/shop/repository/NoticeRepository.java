package com.shop.repository;

import com.shop.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    // 제목으로 공지사항 검색
    List<Notice> findByTitleContainingIgnoreCase(String title);
}
