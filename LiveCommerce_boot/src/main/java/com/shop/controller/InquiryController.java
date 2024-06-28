package com.shop.controller;

import com.shop.entity.Inquiry;
import com.shop.entity.Member;
import com.shop.entity.Response;
import com.shop.service.InquiryService;
import com.shop.service.ResponseService;
import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inquiries")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private MemberService memberService;

    // 문의 작성 폼을 보여주는 엔드포인트
    @GetMapping("/new")
    public String showInquiryForm(Model model) {
        model.addAttribute("inquiry", new Inquiry());
        return "inquiries/inquiry-form"; // inquiry-form.html 반환
    }

    // 새로운 문의를 생성하는 엔드포인트
    @PostMapping
    public String createInquiry(@ModelAttribute Inquiry inquiry, @AuthenticationPrincipal User user) {
        Member member = memberService.findByEmail(user.getUsername());
        inquiry.setMember(member);
        inquiryService.saveInquiry(inquiry);
        return "redirect:/inquiries/member"; // 문의 작성 후 회원의 문의 목록 페이지로 리다이렉트
    }

    // 특정 회원의 모든 문의를 조회하는 엔드포인트
    @GetMapping("/member")
    public String getInquiriesByMember(@AuthenticationPrincipal User user, Model model) {
        Member member = memberService.findByEmail(user.getUsername());
        List<Inquiry> inquiries = inquiryService.getInquiriesByMemberId(member.getId());
        model.addAttribute("inquiries", inquiries);
        return "inquiries/inquiry-list"; // inquiry-list.html 반환
    }

    // 모든 문의를 조회하는 엔드포인트 (관리자 전용)
    @GetMapping
    public String getAllInquiries(Model model) {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        model.addAttribute("inquiries", inquiries);
        return "inquiries/inquiry-list"; // inquiry-list.html 반환
    }

    @GetMapping("/{id}")
    public String getInquiry(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        Inquiry inquiry = inquiryService.getInquiryById(id);
        // 문의에 대한 모든 답변을 조회
        List<Response> responses = responseService.getResponsesByInquiryId(id);

        model.addAttribute("inquiry", inquiry);
        model.addAttribute("responses", responses);


        return "inquiries/inquiry-detail";
    }


    // 새로운 답변을 생성하는 엔드포인트
    @PostMapping("/{id}/responses")
    public String createResponse(@PathVariable Long id, @ModelAttribute Response response, @AuthenticationPrincipal User user) {
        Member admin = memberService.findByEmail(user.getUsername());
        Inquiry inquiry = inquiryService.getInquiryById(id);
        response.setAdmin(admin);
        response.setInquiry(inquiry);
        responseService.saveResponse(response);
        return "redirect:/inquiries/" + id; // 응답 작성 후 해당 문의 상세 페이지로 리다이렉트
    }
}
