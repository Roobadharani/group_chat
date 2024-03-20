package com.zuci.GroupChatApp.repository;

import com.zuci.GroupChatApp.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration,Integer> {
    Optional<Registration> findByUsername(String username);
    //Registration deleteByUsername(String username);
}
