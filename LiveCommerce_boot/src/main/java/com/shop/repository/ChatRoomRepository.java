package com.shop.repository;

import com.shop.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 추가적인 메소드가 필요하다면 여기에 선언할 수 있습니다.
}
