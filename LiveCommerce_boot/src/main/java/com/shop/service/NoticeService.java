package com.shop.service;

import com.shop.entity.Notice;
import com.shop.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    // 모든 공지사항 조회 (페이징 처리)
    public Page<Notice> getAllNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    // 공지사항 저장
    public Notice saveNotice(Notice notice, String author) {
        notice.setAuthor(author);
        return noticeRepository.save(notice);
    }

    // 공지사항 상세 조회
    public Optional<Notice> getNoticeById(Long id) {
        return noticeRepository.findById(id);
    }

    // 공지사항 삭제
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    // 제목으로 공지사항 검색
    public List<Notice> searchNoticesByTitle(String title) {
        return noticeRepository.findByTitleContainingIgnoreCase(title);
    }
}
