package com.revature.security;


import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;
}