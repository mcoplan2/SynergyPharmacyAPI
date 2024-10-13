package com.revature.service;

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

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public UserService(UserRepository userRepository, JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User createUser(RegistrationRequest registrationRequest){

        User newUser = new User()
                .setFirstName(registrationRequest.getFirstName())
                .setLastName(registrationRequest.getLastName())
                .setUsername(registrationRequest.getUsername())
                .setPassword(passwordEncoder.encode(registrationRequest.getPassword()))
                .setRole(Role.CUSTOMER);

        return userRepository.save(newUser);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){

        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

    }

    public User updateUser(User user, Integer userId){
        // check if the user exists
        User dbUser = getUserById(userId);

        if(user.getUsername() != null){
            dbUser.setUsername(user.getUsername());
        }
        if(user.getPassword() != null){
            dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if(user.getFirstName() != null){
            dbUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null){
            dbUser.setLastName(user.getLastName());
        }
        if(user.getRole() != null){
            dbUser.setRole(user.getRole());
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
