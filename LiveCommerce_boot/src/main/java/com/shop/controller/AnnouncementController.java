package com.shop.controller;

import com.shop.entity.Announcement;
import com.shop.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    /**
     * 모든 공지사항을 조회하는 페이지를 보여줍니다.
     * @param model Model 객체
     * @return 공지사항 목록 페이지 경로
     */
    @GetMapping
    public String getAllAnnouncements(Model model) {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        model.addAttribute("announcements", announcements);
        return "announcement/announcementList";
    }

    /**
     * 공지사항 등록 폼을 보여줍니다.
     * @param model Model 객체
     * @return 공지사항 등록 폼 페이지 경로
     */
    @GetMapping("/new")
    public String showAnnouncementForm(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "announcement/announcementForm";
    }

    /**
     * 공지사항을 등록합니다.
     * @param announcement 공지사항 객체
     * @param bindingResult 입력 유효성 검사 결과
     * @param model Model 객체
     * @return 공지사항 목록 페이지로 리다이렉트
     */
    @PostMapping("/new")
    public String createAnnouncement(@Valid Announcement announcement, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "announcement/announcementForm";
        }
        announcementService.createAnnouncement(announcement.getTitle(), announcement.getContent());
        return "redirect:/announcement";
    }

    /**
     * 공지사항 상세 정보를 보여줍니다.
     * @param id 공지사항 ID
     * @param model Model 객체
     * @return 공지사항 상세 정보 페이지 경로
     */
    @GetMapping("/{id}")
    public String getAnnouncementById(@PathVariable Long id, Model model) {
        Announcement announcement = announcementService.getAnnouncementById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid announcement ID: " + id));
        model.addAttribute("announcement", announcement);
        return "announcement/announcementDetail";
    }

    /**
     * 공지사항 수정 폼을 보여줍니다.
     * @param id 공지사항 ID
     * @param model Model 객체
     * @return 공지사항 수정 폼 페이지 경로
     */
    @GetMapping("/{id}/edit")
    public String showEditAnnouncementForm(@PathVariable Long id, Model model) {
        Announcement announcement = announcementService.getAnnouncementById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid announcement ID: " + id));
        model.addAttribute("announcement", announcement);
        return "announcement/edit-form";
    }

    /**
     * 공지사항을 수정합니다.
     * @param id 공지사항 ID
     * @param announcement 공지사항 객체
     * @param bindingResult 입력 유효성 검사 결과
     * @return 공지사항 상세 정보 페이지로 리다이렉트
     */
    @PostMapping("/{id}/edit")
    public String updateAnnouncement(@PathVariable Long id, @Valid Announcement announcement, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "announcement/edit-form";
        }
        announcementService.updateAnnouncement(id, announcement.getTitle(), announcement.getContent());
        return "redirect:/announcement/{id}";
    }

    /**
     * 공지사항을 삭제합니다.
     * @param id 공지사항 ID
     * @return 공지사항 목록 페이지로 리다이렉트
     */
    @PostMapping("/{id}/delete")
    public String deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return "redirect:/announcement";
    }
}
