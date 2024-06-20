package com.shop.controller;

import com.shop.dto.ReviewFormDto;
import com.shop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/review/new")
    public String showReviewForm(@RequestParam("orderId") Long orderId,
                                 @RequestParam("itemId") Long itemId,
                                 Model model) {
        ReviewFormDto reviewForm = new ReviewFormDto();
        reviewForm.setOrderId(orderId);
        reviewForm.setItemId(itemId); // itemId 설정

        model.addAttribute("reviewForm", reviewForm);
        return "review/reviewForm";
    }


    @PostMapping("/review")
    public String submitReview(@ModelAttribute @Valid ReviewFormDto reviewForm,
                               BindingResult bindingResult,
                               Principal principal,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "review/reviewForm";
        }

        try {
            reviewService.saveReview(reviewForm, principal.getName());
            return "redirect:/orders"; // 후기 작성 완료 후 리다이렉트할 경로 설정
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "review/reviewForm";
        }
    }
}
