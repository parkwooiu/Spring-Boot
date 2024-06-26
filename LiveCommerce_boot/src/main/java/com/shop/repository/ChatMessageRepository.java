package com.shop.repository;

import com.shop.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // 특정 상품에 대한 채팅 메시지를 시간 순서대로 가져오는 메소드
    List<ChatMessage> findByItem_IdOrderByTimestampAsc(Long itemId);
}
