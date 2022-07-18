package com.revature.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.model.enums.Role;
import com.revature.repository.UserRepository;
import com.revature.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.revature.security.RegistrationRequest;

import javax.sql.RowSet;
import java.util.List;

@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;


    public UserService(UserRepository userRepository, JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                       ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    public User createUser(User user){
        //business checks

        User newUser = new User()
                .setUsername(registrationRequest.getUsername())
                .setPassWord(passwordEncoder.encode(registrationRequest.getPassword()))
                .setRole(Role.CUSTOMER)
                .build();

        return userRepository.save(newUser);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){

        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
