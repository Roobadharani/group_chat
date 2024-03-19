package com.zuci.GroupChatApp.controller;

import com.zuci.GroupChatApp.model.ChatMessagePojo;
import com.zuci.GroupChatApp.model.Login;
import com.zuci.GroupChatApp.model.Registration;
import com.zuci.GroupChatApp.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;


    @PostMapping("/signup")
    public ResponseEntity<Registration> addDetails(@Valid @RequestBody Registration registration){
        return registrationService.addDetails(registration);
        //already registered
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> allowUser(@RequestBody Login login) {

        return registrationService.findByname(login);
        //username doesn't exist
    }
    @GetMapping("/msg")
    public ResponseEntity<List> getAllChats(){
        return registrationService.getAllChats();
    }
}

