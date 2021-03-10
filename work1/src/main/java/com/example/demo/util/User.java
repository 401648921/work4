package com.example.demo.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Table(name="user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="place")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private int role;

    @Column(name="capacity")
    private float capacity;

    public User(String email, String password, int role, float capacity) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.capacity = capacity;
    }
}
