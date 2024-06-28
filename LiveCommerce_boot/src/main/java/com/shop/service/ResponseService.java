package com.shop.service;

import com.shop.entity.Response;
import com.shop.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    // 응답 저장 메서드
    public Response saveResponse(Response response) {
        return responseRepository.save(response);
    }

    // 문의 ID를 이용해 해당 문의의 모든 응답을 조회하는 메서드
    public List<Response> getResponsesByInquiryId(Long inquiryId) {
        return responseRepository.findByInquiryId(inquiryId);
    }
}
