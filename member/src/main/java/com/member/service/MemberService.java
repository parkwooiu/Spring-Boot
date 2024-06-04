package com.member.service;

import com.member.repository.MemberRepository;
import com.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;
    //CRUD 작업
    //C,U
    public void registerMember(Member member) {
        memberRepository.save(member);
        log.info("Member registered: " + member);
    }
    //D
    public void delete(long memberid) {
        log.info("Member deleted: " + memberid);
        memberRepository.deleteById(memberid);
    }
    // Read one
    public Member readOne(long memberid) {
        return memberRepository.findById(memberid).get();
    }
    //ReadList
    public List<Member> readAll() {
        return memberRepository.findAll();
    }
    //Paging
    public Page<Member> readPaging(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }
}

