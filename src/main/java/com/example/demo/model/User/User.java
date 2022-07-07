package com.example.demo.model.User;

import java.io.Serializable;
import java.util.Objects;
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

        @Enumerated
        private Role role;

        public User() {
        }

        public User(Integer userId, String firstName, String lastName, String username, String passWord, Role role) {
                UserId = userId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.username = username;
                this.passWord = passWord;
                this.role = role;
        }

        public Integer getUserId() {
                return UserId;
        }

        public User setUserId(Integer userId) {
                UserId = userId;
                return this;
        }

        public String getFirstName() {
                return firstName;
        }

        public User setFirstName(String firstName) {
                this.firstName = firstName;
                return this;
        }

        public String getLastName() {
                return lastName;
        }

        public User setLastName(String lastName) {
                this.lastName = lastName;
                return this;
        }

        public String getUsername() {
                return username;
        }

        public User setUsername(String username) {
                this.username = username;
                return this;
        }

        public String getPassWord() {
                return passWord;
        }

        public User setPassWord(String passWord) {
                this.passWord = passWord;
                return this;
        }

        public Role getRole() {
                return role;
        }

        public User setRole(Role role) {
                this.role = role;
                return this;
        }

}
