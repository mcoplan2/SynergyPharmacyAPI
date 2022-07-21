package com.revature.controller;

import com.revature.security.AuthenticationRequest;
import com.revature.security.AuthenticationResponse;
import com.revature.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public AuthenticationResponse authentication(@RequestBody AuthenticationRequest request){
        return userService.authenticate(request);
    }
}