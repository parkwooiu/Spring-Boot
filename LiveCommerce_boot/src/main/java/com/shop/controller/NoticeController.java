package com.shop.controller;

import com.shop.dto.ItemFormDto;
import com.shop.dto.NoticeFormDto;
import com.shop.entity.Comment;
import com.shop.entity.Member;
import com.shop.entity.Notice;
import com.shop.service.CommentService;
import com.shop.service.MemberService;
import com.shop.service.NoticeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@AllArgsConstructor
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private MemberService memberService;

    private final CommentService commentService;


    // 공지사항 추가 폼 페이지
    @GetMapping("/admin/notice/new")
    public String showNoticeForm(Model model) {
        Member currentMember = memberService.getCurrentLoggedInMember();

        NoticeFormDto noticeFormDto = new NoticeFormDto();

        model.addAttribute("noticeFormDto", noticeFormDto);
        model.addAttribute("currentMember", currentMember); // 현재 로그인한 멤버를 모델에 추가
        return "notice/noticeForm"; // noticeForm.html을 렌더링하여 클라이언트에게 반환
    }

    //공지사항 등록
    @PostMapping("/admin/notice/new")
    public String addNotice(@ModelAttribute("noticeFormDto")
                            @Valid NoticeFormDto noticeFormDto,
                            BindingResult bindingResult,
                            Model model) {

        if (bindingResult.hasErrors()) {
            // 에러 처리 로직
            return "notice/noticeForm"; // 에러 발생 시 다시 폼 페이지 보여주기
        }

        try {
            // 유효성 검사를 통과한 경우 공지사항 추가 로직을 수행합니다.
            Notice notice = new Notice();
            notice.setTitle(noticeFormDto.getTitle());
            notice.setContent(noticeFormDto.getContent());

            Member currentMember = memberService.getCurrentLoggedInMember(); // 현재 로그인한 멤버 가져오기
            String author = currentMember.getName(); // 멤버 이름을 작성자로 설정

            noticeService.saveNotice(notice, author);

            return "redirect:/notices";
        } catch(Exception e) {
            log.error("공지사항 등록에 실패했습니다.", e);
            model.addAttribute("errorMessage", "공지사항 등록 중 에러가 발생했습니다.");
            return "error";
        }
    }

    // 모든 공지사항 조회 (페이징 처리)
    @GetMapping("/notices")
    public String getAllNotices(Pageable pageable, Model model) {
        Page<Notice> notices = noticeService.getAllNotices(pageable);
        model.addAttribute("notices", notices);
        return "notice/noticeList";
    }



    // 공지사항 상세 조회
    @GetMapping("/admin/notice/{id}")
    public String getNoticeDetail(@PathVariable("id") Long id, Model model) {
        Optional<Notice> notice = noticeService.getNoticeById(id);
        if (notice.isPresent()) {
            model.addAttribute("notice", notice.get());
            return "notice/noticeDetail"; // noticeDetail.html을 렌더링하여 공지사항 상세 정보를 클라이언트에게 반환
        } else {
            // 공지사항이 존재하지 않는 경우 예외 처리
            model.addAttribute("errorMessage", "해당 ID의 공지사항을 찾을 수 없습니다.");
            return "error"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 보여줄 수 있는 페이지로 이동
        }
    }

    // 공지사항 수정 폼 페이지
    @GetMapping("/admin/notice/edit/{id}")
    public String showEditNoticeForm(@PathVariable("id") Long id, Model model) {
        Optional<Notice> notice = noticeService.getNoticeById(id);
        if (notice.isPresent()) {
            // 현재 로그인한 멤버 정보 가져오기
            Member currentMember = memberService.getCurrentLoggedInMember();

            // 공지사항 작성자 정보 가져오기
            String noticeAuthor = notice.get().getAuthor();

            // 현재 로그인한 멤버와 공지사항 작성자가 같은 경우에만 수정 폼 보여주기
            if (currentMember.getName().equals(noticeAuthor)) {
                NoticeFormDto noticeFormDto = new NoticeFormDto();
                noticeFormDto.setId(notice.get().getId());
                noticeFormDto.setTitle(notice.get().getTitle());
                noticeFormDto.setContent(notice.get().getContent());
                model.addAttribute("noticeFormDto", noticeFormDto);
                return "notice/editnoticeForm"; // noticeForm.html을 렌더링하여 수정 폼 페이지를 클라이언트에게 반환
            } else {
                // 권한이 없는 경우 에러 처리
                model.addAttribute("errorMessage", "해당 공지사항을 수정할 수 있는 권한이 없습니다.");
                return "error"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 보여줄 수 있는 페이지로 이동
            }
        } else {
            // 공지사항이 존재하지 않는 경우 예외 처리
            model.addAttribute("errorMessage", "해당 ID의 공지사항을 찾을 수 없습니다.");
            return "error"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 보여줄 수 있는 페이지로 이동
        }
    }

    // 공지사항 수정
    @PostMapping("/admin/notice/edit/{id}")
    public String updateNotice(@PathVariable("id") Long id,
                               @ModelAttribute("noticeFormDto") @Valid NoticeFormDto noticeFormDto,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            // 에러 처리 로직
            return "notice/editnoticeForm"; // 에러 발생 시 다시 폼 페이지 보여주기
        }

        try {
            Optional<Notice> existingNotice = noticeService.getNoticeById(id);
            if (existingNotice.isPresent()) {
                Notice notice = existingNotice.get();
                notice.setTitle(noticeFormDto.getTitle());
                notice.setContent(noticeFormDto.getContent());

                Member currentMember = memberService.getCurrentLoggedInMember(); // 현재 로그인한 멤버 가져오기
                String author = currentMember.getName(); // 멤버 이름을 작성자로 설정

                noticeService.saveNotice(notice, author);

                return "redirect:/notices";
            } else {
                // 공지사항이 존재하지 않는 경우 예외 처리
                model.addAttribute("errorMessage", "해당 ID의 공지사항을 찾을 수 없습니다.");
                return "error"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 보여줄 수 있는 페이지로 이동
            }
        } catch (Exception e) {
            log.error("공지사항 수정 중 에러가 발생했습니다.", e);
            model.addAttribute("errorMessage", "공지사항 수정 중 에러가 발생했습니다.");
            return "error"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 보여줄 수 있는 페이지로 이동
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/admin/notice/delete/{id}")
    public String deleteNotice(@PathVariable("id") Long id, Model model) {
        try {
            Optional<Notice> existingNotice = noticeService.getNoticeById(id);
            if (existingNotice.isPresent()) {
                // 현재 로그인한 멤버 정보 가져오기
                Member currentMember = memberService.getCurrentLoggedInMember();

                // 공지사항 작성자 정보 가져오기
                String noticeAuthor = existingNotice.get().getAuthor();

                // 현재 로그인한 멤버와 공지사항 작성자가 같은 경우에만 삭제 가능
                if (currentMember.getName().equals(noticeAuthor)) {
                    noticeService.deleteNotice(id);
                    return "redirect:/notices";
                } else {
                    // 권한이 없는 경우 에러 처리
                    model.addAttribute("errorMessage", "해당 공지사항을 삭제할 수 있는 권한이 없습니다.");
                    return "error"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 보여줄 수 있는 페이지로 이동
                }
            } else {
                // 공지사항이 존재하지 않는 경우 예외 처리
                model.addAttribute("errorMessage", "해당 ID의 공지사항을 찾을 수 없습니다.");
                return "error"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 보여줄 수 있는 페이지로 이동
            }
        } catch (Exception e) {
            log.error("공지사항 삭제 중 에러가 발생했습니다.", e);
            model.addAttribute("errorMessage", "공지사항 삭제 중 에러가 발생했습니다.");
            return "error"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 보여줄 수 있는 페이지로 이동
        }
    }

}