package com.example.chatroom.controller;

import com.example.chatroom.model.ChatRoom;
import com.example.chatroom.model.Message;
import com.example.chatroom.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
public class ChatController {

    @Autowired
    private ChatRoomService chatroomService;

    @RequestMapping(method = RequestMethod.GET, value = "/rooms")
    public ResponseEntity getRooms() {
        List<ChatRoom> rooms = chatroomService.getRooms();
        return new ResponseEntity(rooms, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rooms")
    public ResponseEntity<ChatRoom> addChatRoom(@RequestBody ChatRoom chatRoom) {
        try {
            ChatRoom c = chatroomService.addRoom(chatRoom);
            return new ResponseEntity(c, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/message")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        try {
            Message m = chatroomService.addMessage(message);
            return new ResponseEntity(m, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/message")
    public ResponseEntity getMessage() {
        List<Message> message = chatroomService.getMessage();
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/rooms/{id}")
    public ResponseEntity getRoom(@PathVariable String id) {
        Optional<ChatRoom> chatroomOptional = chatroomService.getRoomById(id);
        if (chatroomOptional.isPresent()) {
            return new ResponseEntity(chatroomOptional, HttpStatus.OK);
        } else {
            return new ResponseEntity("Room not found", HttpStatus.NOT_FOUND);
        }
    }

}