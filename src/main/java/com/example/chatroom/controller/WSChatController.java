package com.example.chatroom.controller;

import com.example.chatroom.model.Message;
import com.example.chatroom.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WSChatController {
    @Autowired
    private ChatRoomService chatroomService;

    // /app/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        try {
            return chatroomService.addMessage(message);
        } catch (Exception e) {
            return message;
        }
    }


    // /app/chat.editMessage
    @MessageMapping("/chat.editMessage")
    @SendTo("/topic/public")
    public Message editMessage(@Payload Message message) {
        try {
            return chatroomService.editMessage(message);
        } catch (Exception e) {
            return message;
        }
    }
}
