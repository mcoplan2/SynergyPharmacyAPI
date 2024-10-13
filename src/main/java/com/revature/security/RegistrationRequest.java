package com.revature.security;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RegistrationRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

}