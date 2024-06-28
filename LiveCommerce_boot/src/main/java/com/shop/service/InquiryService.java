package com.shop.service;

import com.shop.entity.Inquiry;
import com.shop.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    // 문의 저장 메서드
    public Inquiry saveInquiry(Inquiry inquiry) {
        inquiry.setInquiryDate(LocalDateTime.now());
        inquiry.setStatus("접수");
        return inquiryRepository.save(inquiry);
    }

    // 회원 ID를 이용해 해당 회원의 모든 문의를 조회하는 메서드
    public List<Inquiry> getInquiriesByMemberId(Long memberId) {
        return inquiryRepository.findByMemberId(memberId);
    }

    // 문의 ID를 이용해 특정 문의를 조회하는 메서드
    public Inquiry getInquiryById(Long id) {
        return inquiryRepository.findById(id).orElse(null);
    }

    // 모든 문의를 조회하는 메서드
    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }
}
