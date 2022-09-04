package com.example.chatroom.service;

import com.example.chatroom.controller.WSChatController;
import com.example.chatroom.model.ChatRoom;
import com.example.chatroom.model.Message;
import com.example.chatroom.repository.ChatRepository;
import com.example.chatroom.repository.RoomRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
        if (rooms.size() > 0) {
            return rooms;
        } else {
            return new ArrayList<>();
        }
    }

    public ChatRoom addRoom(ChatRoom chatRoom) {
        chatRoom.setMessages(Collections.emptyList());
        return roomRepository.save(chatRoom);
    }

    public List<Message> getMessage() {
        List<Message> message = chatRepository.findAll();
        if (message.size() > 0) {
            return message;
        } else {
            return new ArrayList<>();
        }
    }

    public Message addMessage(Message message) throws Exception {
        message.setId(new ObjectId().toString());
        Optional<ChatRoom> chatRoom = roomRepository.findById(message.getCurrentRoomId());
        if (!chatRoom.isPresent()) {
            throw new Exception("Room not found!");
        }
        ChatRoom chRoom = chatRoom.get();
        List<Message> messages = chRoom.getMessages();
        if (chRoom.getMessages() == null) {
            chRoom.setMessages(Collections.emptyList());
        }
        messages.add(message);
        chRoom.setMessages(messages);
        roomRepository.save(chRoom);

        return message;
    }


    public Optional<ChatRoom> getRoomById(String id) {
        return roomRepository.findById(id);
    }

    public Message editMessage(Message message) throws Exception {
        Optional<ChatRoom> chatRoom = roomRepository.findById(message.getCurrentRoomId());
        if (!chatRoom.isPresent()) {
            throw new Exception("Not found!");
        }
        ChatRoom chRoom = chatRoom.get();
        List<Message> messages = chRoom.getMessages();
        for (Message m : messages) {
            if (m.getId().equals(message.getId())) {
                m.setContent(message.getContent());
            }
        }
        chRoom.setMessages(messages);
        roomRepository.save(chRoom);

        return message;
    }
}





