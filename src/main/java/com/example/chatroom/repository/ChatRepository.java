package com.example.chatroom.repository;

import com.example.chatroom.model.Message;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ChatRepository extends MongoRepository <Message, Id> {
    @Query("{'id':?0}")
    Optional<Message> findById(Id id);
}