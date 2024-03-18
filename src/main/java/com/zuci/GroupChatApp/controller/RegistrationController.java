package com.zuci.GroupChatApp.controller;

import com.zuci.GroupChatApp.model.Login;
import com.zuci.GroupChatApp.model.Registration;
import com.zuci.GroupChatApp.service.CustomUserDetailsService;
import com.zuci.GroupChatApp.service.JwtUtilityService;
import com.zuci.GroupChatApp.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtilityService jwtUtilityService;
    @PostMapping("/signup")
    public Registration addDetails(@Valid @RequestBody Registration registration){
        return registrationService.addDetails(registration);
    }
    @GetMapping("/registration/{userId}")
    public List<Registration> findBookingByUserId(@PathVariable("userId") int userId){
        return registrationService.findBookingByUserId(userId);
    }

    @PostMapping("/login")
    public String createUser(@RequestBody Login login) {
        if (customUserDetailsService.loadUserByUsername(login.getUsername()) == null) {
            return "Invalid credentials";
        }
        return jwtUtilityService.generateToken(login.getUsername());
    }

}

