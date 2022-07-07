package com.example.demo.service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        //business checks
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){

        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

    }

}
