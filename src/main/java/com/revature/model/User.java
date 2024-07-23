package com.revature.model;

import com.revature.model.enums.Role;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.*;

@Entity(name="users")
public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer userId;

        @Column
        private String firstName;

        @Column
        private String lastName;

        @Column(nullable = false, unique= true)
        private String username;

        @Column(nullable = false)
        private String passWord;

        @Enumerated
        private Role role;

        @ColumnDefault("true")
        private boolean isActive = true;

        public User(String username, String firstName, String lastName, String passWord){
                this.username = username;
                this.firstName = firstName;
                this.lastName = lastName;
                this.passWord = passWord;
        }

        public User(String username, String firstName, String lastName, String passWord, Role role){
                this.username = username;
                this.firstName = firstName;
                this.lastName = lastName;
                this.passWord = passWord;
                this.role = role;
        }

        public User(){
        }

        public Integer getUserId() {
                return userId;
        }

        public User setUserId(Integer id) {
                userId = id;
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

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singleton(role);
        }

        @Override
        public String getPassword() {
                return this.passWord;
        }

        public String getUsername() {
                return this.username;
        }

        @Override
        public boolean isAccountNonExpired() {
                return isActive;
        }

        @Override
        public boolean isAccountNonLocked() {
                return isActive;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return isActive;
        }

        @Override
        public boolean isEnabled() {
                return isActive;
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
