package com.revature.security;

import com.revature.model.enums.Role;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AuthenticationResponse {
    private String token;
    private String username;
    private String firstName;
    private String lastName;
    private Role role;

    public AuthenticationResponse() {}

    public AuthenticationResponse(String token, String username, String firstName, String lastName, Role role) {
        this.username = username;
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}


