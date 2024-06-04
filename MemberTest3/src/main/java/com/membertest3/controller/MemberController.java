package com.membertest3.controller;

import com.membertest3.entity.Member;
import com.membertest3.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Member> members = memberService.readList();
        model.addAttribute("members", members);
        return "/member/list";
    }
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("member", new Member());
        return "/member/newForm";
    }
    @PostMapping({"/new","/update"})
    public String postnew(@ModelAttribute("member") Member member) {
        memberService.register(member);
        return "redirect:/member/list";
    }
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
       Member member = memberService.readOne(id);
       model.addAttribute("member", member);

       return "member/updateForm";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        memberService.delete(id);
        return "redirect:/member/list";
    }


}
