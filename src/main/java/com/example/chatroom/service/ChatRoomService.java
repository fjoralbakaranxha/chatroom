package com.example.chatroom.service;

import com.example.chatroom.model.ChatRoom;
import com.example.chatroom.model.Message;
import com.example.chatroom.repository.ChatRepository;
import com.example.chatroom.repository.RoomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ChatRepository chatRepository;

    public List<ChatRoom> getRooms() {
        List<ChatRoom> rooms = roomRepository.findAll();
        if(rooms.size() > 0) {
            return rooms;
        } else {
            return new ArrayList<>();
        }
    }

    public ChatRoom addRoom(ChatRoom chatRoom) {
        return roomRepository.save(chatRoom);
    }

    public List<Message> getMessage() {
        List<Message> message = chatRepository.findAll();
        if(message.size() > 0) {
            return message;
        } else {
            return new ArrayList<>();
        }
    }

    public Message addMessage(Message message) {
        return chatRepository.save(message);
    }


//    public void updateMessage(Message message, String id) {
//        Optional<Message> messageOptional = chatRepository.findById(id);
//        (messageOptional.isPresent()) {
//
//            Message messageToUpdate = messageOptional.get();
//            BeanUtils.copyProperties(message, messageToUpdate);
//            chatRepository.save(messageToUpdate);
//        }
    }




