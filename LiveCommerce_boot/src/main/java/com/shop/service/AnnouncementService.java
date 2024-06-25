package com.shop.service;

import com.shop.constant.Role;
import com.shop.entity.Announcement;
import com.shop.entity.Member;
import com.shop.repository.AnnouncementRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final MemberRepository memberRepository;

    /**
     * 공지사항을 생성합니다.
     * @param title 공지사항 제목
     * @param content 공지사항 내용
     * @return 생성된 공지사항
     * @throws SecurityException ADMIN 역할이 아닌 경우 예외 발생
     */
    public Announcement createAnnouncement(String title, String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // 현재 사용자 정보 조회
        Optional<Member> optionalMember = Optional.ofNullable(memberRepository.findByEmail(currentPrincipalName));
        if (!optionalMember.isPresent()) {
            throw new SecurityException("Authenticated member not found.");
        }
        Member createdByMember = optionalMember.get();

        Announcement announcement = Announcement.builder()
                .title(title)
                .content(content)
                .regTime(LocalDateTime.now())
                .createdByMember(createdByMember)
                .build();

        return announcementRepository.save(announcement);
    }

    /**
     * 모든 공지사항을 조회합니다.
     * @return 모든 공지사항 목록
     */
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    /**
     * ID로 공지사항을 조회합니다.
     * @param id 공지사항 ID
     * @return Optional을 통해 공지사항 조회 결과를 반환합니다.
     */
    public Optional<Announcement> getAnnouncementById(Long id) {
        return announcementRepository.findById(id);
    }

    /**
     * 공지사항을 업데이트합니다.
     * @param id 공지사항 ID
     * @param title 공지사항 제목
     * @param content 공지사항 내용
     * @return 업데이트된 공지사항
     * @throws SecurityException ADMIN 역할이 아닌 경우 예외 발생
     * @throws IllegalArgumentException 공지사항 ID가 유효하지 않은 경우 예외 발생
     */
    public Announcement updateAnnouncement(Long id, String title, String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Optional<Member> optionalMember = Optional.ofNullable(memberRepository.findByEmail(currentPrincipalName));
        if (!optionalMember.isPresent()) {
            throw new SecurityException("Authenticated member not found.");
        }
        Member updatedByMember = optionalMember.get();

        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(id);
        if (!optionalAnnouncement.isPresent()) {
            throw new IllegalArgumentException("Invalid announcement ID: " + id);
        }
        Announcement announcement = optionalAnnouncement.get();

        // 관리자만 업데이트 가능
        if (!updatedByMember.getRole().equals(Role.ADMIN)) {
            throw new SecurityException("Only admins can update announcements.");
        }

        announcement.setTitle(title);
        announcement.setContent(content);
        announcement.setUpdateTime(LocalDateTime.now());


        return announcementRepository.save(announcement);
    }

    /**
     * 공지사항을 삭제합니다.
     * @param id 공지사항 ID
     * @throws SecurityException ADMIN 역할이 아닌 경우 예외 발생
     */
    public void deleteAnnouncement(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Optional<Member> optionalMember = Optional.ofNullable(memberRepository.findByEmail(currentPrincipalName));
        if (!optionalMember.isPresent()) {
            throw new SecurityException("Authenticated member not found.");
        }
        Member deletedByMember = optionalMember.get();

        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(id);
        if (!optionalAnnouncement.isPresent()) {
            throw new IllegalArgumentException("Invalid announcement ID: " + id);
        }
        Announcement announcement = optionalAnnouncement.get();

        // 관리자만 삭제 가능
        if (!deletedByMember.getRole().equals(Role.ADMIN)) {
            throw new SecurityException("Only admins can delete announcements.");
        }

        announcementRepository.deleteById(id);
    }
}

