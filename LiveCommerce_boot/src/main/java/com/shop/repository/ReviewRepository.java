package com.shop.repository;

import com.shop.dto.ReviewFormDto;
import com.shop.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 아이템 ID로 리뷰 조회
    List<ReviewFormDto> findAllByItemId(Long itemId);

    // 기타 필요한 메서드 추가 가능
}
