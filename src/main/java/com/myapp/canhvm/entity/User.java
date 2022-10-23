package com.myapp.canhvm.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "account")
    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String username;

    @Column(name = "cash")
    private Long cash;

    @Column(name = "role")
    private String role;

    @Column(name = "created_date")
    private LocalDateTime createdDated;

    @Column(name = "lasted_date_login")
    private LocalDateTime lastedDateLogin;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<UserItem> items;

}


