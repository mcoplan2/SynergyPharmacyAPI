package com.example.demo.model.User;

import java.io.Serializable;
import javax.persistence.*;

@Entity(name="users")
public class User implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer UserId;

        @Column(unique = true, nullable = false)
        private String firstName;

        @Column(nullable = false)
        private String lastName;

        @Column
        private String username;

        @Column(unique = true)
        private String passWord;

        @Column
        private Boolean Employee;
    }
