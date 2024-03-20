package com.zuci.GroupChatApp.service;

import com.zuci.GroupChatApp.exception.LogOutException;
import com.zuci.GroupChatApp.exception.UserNotFoundException;
import com.zuci.GroupChatApp.exception.WrongPasswordException;
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
        if(optional.isPresent()){
            Registration registration=optional.get();
            if(registration.getUserPassword().equals(login.getPassword())) {
                return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
            }
            else{
                throw new WrongPasswordException();
            }
        }
        else {
             throw new UserNotFoundException();
        }
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

    @Override
    public ResponseEntity<Registration> deleteUser(Login login) {
        Optional<Registration> optional=registrationRepository.findByUsername(login.getUsername());
        System.out.println(optional);
        if(optional.isPresent()){
           Registration value=optional.get();
           System.out.println(value);
           registrationRepository.deleteById(value.getUserId());
           return new ResponseEntity<>(value,HttpStatus.ACCEPTED);
        }
        else throw new LogOutException();
    }
}
