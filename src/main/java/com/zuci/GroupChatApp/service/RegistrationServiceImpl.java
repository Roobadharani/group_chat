package com.zuci.GroupChatApp.service;

import com.zuci.GroupChatApp.config.ChatSecurityConfig;
import com.zuci.GroupChatApp.model.Registration;
import com.zuci.GroupChatApp.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService{
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ChatSecurityConfig chatSecurityConfig;
    @Override
    public Registration createUser(Registration registration) {
        registration.setUserPassword(passwordEncoder.encode(registration.getUserPassword()));
        return registrationRepository.save(registration);
    }

    @Override
    public Registration addDetails(Registration registration) {

        registration.setUserPassword(chatSecurityConfig.passwordEncoder().encode(registration.getUserPassword()));
        return registrationRepository.save(registration);
    }
    @Override
    public List<Registration> findBookingByUserId(int userId) {
        Optional<Registration> optionalHotel = registrationRepository.findById(userId);
        if(optionalHotel.isPresent()){
            List<Registration> hotelList = optionalHotel
                    .map(Collections::singletonList) // Converts to List
                    .orElse(Collections.emptyList());
            return hotelList;}
        else{
            throw new RuntimeException();
        }
    }
}
