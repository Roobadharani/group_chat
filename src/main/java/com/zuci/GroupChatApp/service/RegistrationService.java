package com.zuci.GroupChatApp.service;


import com.zuci.GroupChatApp.model.ChatMessagePojo;
import com.zuci.GroupChatApp.model.Login;
import com.zuci.GroupChatApp.model.Registration;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RegistrationService {

    public ResponseEntity<Registration> addDetails(Registration registration);
    public ResponseEntity<Boolean> findByname(Login login);
    public ResponseEntity<List> getAllChats();
}
