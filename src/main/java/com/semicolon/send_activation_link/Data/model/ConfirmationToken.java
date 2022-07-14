package com.semicolon.send_activation_link.Data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
@Table(name = "confirmationToken")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private  long tokenId;
    @Column(name="confirmation_token")
    private String confirmationToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToOne(targetEntity = UserEntity.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private  UserEntity userEntity;

    public ConfirmationToken( UserEntity userEntity) {
        this.userEntity = userEntity;
        confirmationToken = UUID.randomUUID().toString();
        createdDate = new Date();

    }
}
