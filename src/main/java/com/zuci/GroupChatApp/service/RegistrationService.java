package com.zuci.GroupChatApp.service;


import com.zuci.GroupChatApp.model.Registration;

import java.util.List;

public interface RegistrationService {
    public Registration createUser(Registration registration);
    public Registration addDetails(Registration registration);
    public List<Registration> findBookingByUserId(int userId);
}
