package com.shop.controller;

import com.shop.dto.MessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage") // 클라이언트에서 메시지를 보내는 엔드포인트
    @SendTo("/topic/messages") // 메시지를 구독하는 클라이언트에게 전송할 주제 설정
    public MessageDto sendMessage(MessageDto message) {
        return message;
    }
}
