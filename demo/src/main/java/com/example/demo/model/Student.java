package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Users {
    @Id
    private Long id;

    @Column
    private String firstName;

    @Column
    private String email;

    @Column
    private String lastName;



}
