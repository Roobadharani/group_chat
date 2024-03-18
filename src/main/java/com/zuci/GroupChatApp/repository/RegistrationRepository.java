package com.zuci.GroupChatApp.repository;

import com.zuci.GroupChatApp.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration,Integer> {
    Registration findByUsername(String username);
}
