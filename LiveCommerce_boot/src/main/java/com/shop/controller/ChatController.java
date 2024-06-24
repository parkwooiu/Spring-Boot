package com.shop.controller;

import com.shop.dto.ChatMessageDTO;
import com.shop.entity.ChatMessage;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.service.ChatMessageService;
import com.shop.service.MemberService;
import com.shop.service.ItemService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final MemberService memberService;
    private final ItemService itemService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService,
                          MemberService memberService, ItemService itemService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
        this.memberService = memberService;
        this.itemService = itemService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        Member sender = memberService.getCurrentLoggedInMember(); // 로그인된 멤버 정보 가져오기
        Item item = itemService.getItemById(chatMessageDTO.getItemId()); // 상품 정보 가져오기

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setItem(item);
        chatMessage.setContent(chatMessageDTO.getContent());
        chatMessage.setTimestamp(LocalDateTime.now());

        chatMessageService.saveMessage(chatMessage); // 채팅 메시지 저장

        messagingTemplate.convertAndSend("/sub/public", chatMessage); // 클라이언트에게 메시지 전송
    }

    @MessageMapping("/chat.getMessages")
    @SendTo("/sub/public")
    public List<ChatMessage> getMessages(@Payload Long itemId) {
        return chatMessageService.getMessagesByItemId(itemId);
    }
}
