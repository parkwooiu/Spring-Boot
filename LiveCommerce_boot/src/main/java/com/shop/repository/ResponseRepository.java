package com.shop.repository;

import com.shop.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByInquiryId(Long inquiryId);
}
