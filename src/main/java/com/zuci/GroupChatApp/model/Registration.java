package com.zuci.GroupChatApp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "bookings")
@Entity
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(unique = true)
    @NotBlank(message = "mail id should not be blank")
    private String userMailId;
    @Column(unique = true)
    @NotBlank(message = "user name  should not be blank")
    private String username;
    @NotBlank(message = "Password  should not be blank")
    private String userPassword;
    private long mobileNumber;

}
