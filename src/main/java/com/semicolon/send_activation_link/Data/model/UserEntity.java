package com.semicolon.send_activation_link.Data.model;

import lombok.Data;


import javax.persistence.*;

@Entity
@Table(name="users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userId;
    private String emailId;
    private String password;
    @Column(name = "first_name")
    private  String firstName;
    @Column(name = "last_name")
    private  String lastName;
    private boolean isEnabled;



}
