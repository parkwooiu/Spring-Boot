package com.shop.service;

import com.shop.entity.ChatMessage;
import com.shop.entity.Member;
import com.shop.entity.Item;
import com.shop.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<ChatMessage> getMessagesByItemId(Long itemId) {
        return chatMessageRepository.findByItem_IdOrderByTimestampAsc(itemId);
    }

    public ChatMessage saveMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }
}


