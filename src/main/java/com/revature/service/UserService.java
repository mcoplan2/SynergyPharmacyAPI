package com.revature.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.InvalidCredentialsException;
import com.revature.exception.ResourceNotFoundException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.model.enums.Role;
import com.revature.repository.UserRepository;
import com.revature.security.AuthenticationRequest;
import com.revature.security.AuthenticationResponse;
import com.revature.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public User createUser(RegistrationRequest registrationRequest){
        //business checks

        User newUser = new User()
                .setFirstName(registrationRequest.getFirstName())
                .setLastName(registrationRequest.getLastName())
                .setUsername(registrationRequest.getUsername())
                .setPassWord(passwordEncoder.encode(registrationRequest.getPassword()))
                .setRole(Role.CUSTOMER);

        return userRepository.save(newUser);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){

        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

    }

    private User findUser(User user){
        Integer userId = user.getUserId();
        String username = user.getUsername();

        User foundUser = null;

        if(userId != null)
            foundUser = userRepository.findById(userId).orElse(null);

        if(foundUser == null && username != null)
            foundUser = userRepository.findByUsername(username).orElse(null);

        if(foundUser == null){
            if(user.getUserId() != null)
                throw new ResourceNotFoundException(User.class, "userId", user.getUserId());
            else
                throw new ResourceNotFoundException(User.class, "username", user.getUsername());
        }

        return foundUser;

    }

    public User updateUser(User user, Integer userId){
        // check if the user exists
        User dbUser = getUserById(userId);
        Boolean fieldUpdated = false;
        if(user.getUsername() != null){
            dbUser.setUsername(user.getUsername());
            fieldUpdated = true;
        }
        if(user.getPassWord() != null){
            dbUser.setPassWord(passwordEncoder.encode(user.getPassWord()));
            fieldUpdated = true;
        }
        if(user.getFirstName() != null){
            dbUser.setFirstName(user.getFirstName());
            fieldUpdated = true;
        }
        if(user.getLastName() != null){
            dbUser.setLastName(user.getLastName());
            fieldUpdated = true;
        }
        if(user.getRole() != null){
            dbUser.setRole(user.getRole());
            fieldUpdated = true;
        }

        return userRepository.save(dbUser);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch(BadCredentialsException e){
            throw new InvalidCredentialsException();
        }

        User newuser = getUserByUsername(request.getUsername());
        return new AuthenticationResponse(jwtUtil.generateToken((User) loadUserByUsername(request.getUsername())), request.getUsername(), newuser.getFirstName(), newuser.getLastName(), newuser.getRole());
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(User.class, "username", username));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
}
