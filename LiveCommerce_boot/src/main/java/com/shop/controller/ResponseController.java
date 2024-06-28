package com.shop.controller;

import com.shop.entity.Inquiry;
import com.shop.entity.Member;
import com.shop.entity.Response;
import com.shop.service.InquiryService;
import com.shop.service.ResponseService;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/responses")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;
    private final InquiryService inquiryService;
    private final MemberService memberService;

    // 특정 문의의 모든 응답을 조회하는 엔드포인트
    @GetMapping("/inquiry/{inquiryId}")
    public String getResponsesByInquiry(@PathVariable Long inquiryId, Model model) {
        List<Response> responses = responseService.getResponsesByInquiryId(inquiryId);
        model.addAttribute("responses", responses);
        return "response-list"; // response-list.html 반환
    }

    // 답변 제출하는 POST 매핑
    @PostMapping("")
    public String submitResponse(@RequestParam("inquiryId") Long inquiryId,
                                 @RequestParam("response") String content,
                                 @AuthenticationPrincipal User user,
                                 Model model) {
        // 현재 로그인한 관리자 정보 조회
        Member admin = memberService.getCurrentLoggedInMember();

        // Inquiry 객체 조회
        Inquiry inquiry = inquiryService.getInquiryById(inquiryId);

        // Response 객체 생성 및 설정
        Response response = new Response();
        response.setInquiry(inquiry);
        response.setAdmin(admin);
        response.setContent(content);

        // Response 저장
        responseService.saveResponse(response);


        return "redirect:/inquiries/" + inquiry.getId();
    }
}
