package com.member.service;

import com.member.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@Log4j2
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void testInsert(){
        Member member = new Member();
        member.setAge(20);
        member.setName("펩 콰르디올라");
        member.setAddress("맨시티");

        memberService.register(member);

    }
    //CRUD -> update
    @Test
    public void testupdate(){
        Member member = new Member();
        member.setId(20L);
        member.setAge(28);
        member.setName("클롭");
        member.setAddress("리버풀");
        member.setPhone("010-1111-2222");


        memberService.register(member);
    }

}