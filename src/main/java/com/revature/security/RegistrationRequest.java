package com.revature.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}