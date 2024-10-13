package com.revature.model;

import com.revature.model.enums.Role;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
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
        private String password;

        @Enumerated
        private Role role;

        @ColumnDefault("true")
        private boolean isActive = true;

        public User(String username, String firstName, String lastName, String password){
                this.username = username;
                this.firstName = firstName;
                this.lastName = lastName;
                this.password = password;
        }

        public User(String username, String firstName, String lastName, String password, Role role){
                this.username = username;
                this.firstName = firstName;
                this.lastName = lastName;
                this.password = password;
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


        public String getPassword() {
                return this.password;
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

        public User setPassword(String password) {
                this.password = password;
                return this;
        }

        public Role getRole() {
                return role;
        }

        public User setRole(Role role) {
                this.role = role;
                return this;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                User user = (User) o;
                return isActive == user.isActive && Objects.equals(userId, user.userId) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && role == user.role;
        }

        @Override
        public int hashCode() {
                return Objects.hash(userId, firstName, lastName, username, password, role, isActive);
        }

        @Override
        public String toString() {
                return "User{" +
                        "userId=" + userId +
                        ", firstName='" + firstName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", username='" + username + '\'' +
                        ", password='" + password + '\'' +
                        ", role=" + role +
                        ", isActive=" + isActive +
                        '}';
        }
}
