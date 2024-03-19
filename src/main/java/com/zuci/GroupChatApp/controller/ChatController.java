package com.zuci.GroupChatApp.controller;

import com.zuci.GroupChatApp.model.ChatMessagePojo;
import com.zuci.GroupChatApp.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
        import org.springframework.messaging.handler.annotation.Payload;
        import org.springframework.messaging.handler.annotation.SendTo;
        import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
        import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessagePojo sendMessage(@Payload ChatMessagePojo chatMessagePojo) {
        chatRepository.save(chatMessagePojo);
        return chatMessagePojo;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessagePojo addUser(@Payload ChatMessagePojo chatMessagePojo, SimpMessageHeaderAccessor headerAccessor) {
// Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessagePojo.getSender());
        return chatMessagePojo;
    }


}