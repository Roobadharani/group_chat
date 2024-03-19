package com.zuci.GroupChatApp.service;

import com.zuci.GroupChatApp.model.ChatMessagePojo;
import com.zuci.GroupChatApp.model.Login;
import com.zuci.GroupChatApp.model.Registration;
import com.zuci.GroupChatApp.repository.ChatRepository;
import com.zuci.GroupChatApp.repository.RegistrationRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService{
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private ChatRepository chatRepository;


    @Override
    public ResponseEntity<Registration> addDetails(Registration registration) {
            Registration register=registrationRepository.save(registration);
            return new ResponseEntity<>(register, HttpStatus.CREATED);    }
    @Override
    public ResponseEntity<Boolean> findByname(Login login){
        Optional<Registration> optional=registrationRepository.findByUsername(login.getUsername());
        Boolean res=false;
        if(optional.isPresent()){
            res=true;
        }

        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);

    }

    @Override
    public ResponseEntity<List> getAllChats() {
        List<ChatMessagePojo> allChat=chatRepository.findAll();
        if(!allChat.isEmpty()){
            return new ResponseEntity<>(allChat,HttpStatus.ACCEPTED);
        }
        else{
            return null;
        }
    }
}
