package com.revature.controller;

import com.revature.model.User;
import com.revature.security.RegistrationRequest;
import com.revature.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping
    public User createUser(@RequestBody RegistrationRequest registrationRequest){
        return userService.createUser(registrationRequest);
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    @CrossOrigin
    public User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @PatchMapping("/{userId}")
    @CrossOrigin
    public User updateUser(@PathVariable Integer userId, @RequestBody User user) {
        return userService.updateUser(user, userId);
    }

}
