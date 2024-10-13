package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.ResourceNotFoundException;
import com.revature.model.User;
import com.revature.model.enums.Role;
import com.revature.repository.MedicationRepository;
import com.revature.repository.RequestRepository;
import com.revature.repository.UserRepository;
import com.revature.security.AuthenticationRequest;
import com.revature.security.AuthenticationResponse;
import com.revature.security.JwtUtil;
import com.revature.security.RegistrationRequest;
import com.revature.service.MedicationService;
import com.revature.service.PaymentService;
import com.revature.service.RequestService;
import com.revature.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserServiceUnitTests {

    private UserService userService;
    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        jwtUtil = Mockito.mock(JwtUtil.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        userService= new UserService(userRepository, jwtUtil, passwordEncoder, authenticationManager);
    }

    User user = new User("user","fname","lname","pass", Role.CUSTOMER);
    User user2 = new User("user","fname2","lname2","pass2", Role.CUSTOMER);
    RegistrationRequest registrationRequest = new RegistrationRequest("user","pass","fname","lname");
    AuthenticationRequest authenticationRequest = new AuthenticationRequest("user", "pass");
    AuthenticationResponse authenticationResponse = new AuthenticationResponse(null,"user","fname","lname", Role.CUSTOMER);

    @Test
    public void whenCreateUserIsCalledDoesNotThrow() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Assertions.assertDoesNotThrow(()-> userService.createUser(registrationRequest));
    }

    @Test
    public void whenCreateUserIsCalledReturnsCorrectUser() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        User returnedUser = userService.createUser(registrationRequest);
        Assertions.assertEquals(user,returnedUser);
    }

    @Test
    public void whenUpdateUserIsCalledDoesNotThrowException() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        Assertions.assertDoesNotThrow(()-> userService.updateUser(user, 1));
    }

    @Test
    public void whenUpdateUserIsCalledReturnsCorrectUser() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        Mockito.when(userRepository.save(user)).thenReturn(user2);
        User returnedUser = userService.updateUser(user2, 1);
        Assertions.assertEquals(user2, returnedUser);
    }

    @Test
    public void whenAuthenticateIsCalledDoesNotThrowError() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        Assertions.assertDoesNotThrow(()-> userService.authenticate(authenticationRequest));
    }

    @Test
    public void whenAuthenticateIsCalledReturnsCorrectAuthenticationResponse() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        AuthenticationResponse actualauthRes = userService.authenticate(authenticationRequest);
        Assertions.assertEquals(authenticationResponse, actualauthRes);
    }

    @Test
    public void whenAuthenticateIsCalledWithInvalidUserThrowsError() {
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> userService.authenticate(authenticationRequest));
    }
}
