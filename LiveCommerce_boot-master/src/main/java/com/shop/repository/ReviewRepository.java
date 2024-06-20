package com.shop.repository;

import com.shop.dto.ReviewFormDto;
import com.shop.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    ReviewFormDto findByItemId(Long itemId); // 아이템 ID로 리뷰 조회
}
