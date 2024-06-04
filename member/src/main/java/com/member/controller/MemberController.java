package com.member.controller;

import com.member.entity.Member;
import com.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model){
        List<Member> members = memberService.readAll();
        model.addAttribute("members", members);
        log.info(members);
        return "member/list";

    }
    @GetMapping("/new")
    public String createForm(Model model){
        log.info("create.............");
        model.addAttribute("member", new Member());
        return "member/newForm";
    }

    @PostMapping({"/new","/edit"})
    public String newPost(Member member, Model model){
        memberService.registerMember(member);
        return "redirect:/member/list";
    }
    @GetMapping("/edit/{id}")
    public String updateGet(@PathVariable("id") int id, Model model){
        Member member = memberService.readOne(id);
        model.addAttribute("member", member);
        return "/member/updateForm";
    }
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") int id, Model model){
        memberService.delete(id);
        return "redirect:/member/list";
    }
    @GetMapping("/list2")
    public String list2(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Member> memberPage = memberService.readPaging(pageable);
        model.addAttribute("memberPage", memberPage);
        return "member/listPage";
    }

}
