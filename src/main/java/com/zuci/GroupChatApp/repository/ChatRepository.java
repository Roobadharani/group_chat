package com.zuci.GroupChatApp.repository;

import com.zuci.GroupChatApp.model.ChatMessagePojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository  extends JpaRepository<ChatMessagePojo, Integer> {
}
