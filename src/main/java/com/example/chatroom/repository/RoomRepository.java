package com.example.chatroom.repository;

import com.example.chatroom.model.ChatRoom;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface RoomRepository extends MongoRepository <ChatRoom, String> {
}