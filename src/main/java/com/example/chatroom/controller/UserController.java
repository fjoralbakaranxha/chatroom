package com.example.chatroom.controller;

import com.example.chatroom.model.User;
import com.example.chatroom.payload.LoginPayload;
import com.example.chatroom.payload.RegisterPayload;
import com.example.chatroom.repository.UserRepository;
import com.example.chatroom.response.JwtResponse;
import com.example.chatroom.security.JwtUtils;
import com.example.chatroom.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterPayload registerPayload) {
        if (userRepository.existsByUsername(registerPayload.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseEntity("Username is already taken", HttpStatus.NOT_FOUND));
        }

             User user = new User(registerPayload.getUsername(),
                encoder.encode(registerPayload.getPassword()));
                userRepository.save(user);


        return new ResponseEntity("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginPayload loginPayload) {
    try{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginPayload.getUsername(), loginPayload.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername()
        ));
    } catch(Exception e) {
        String a = "1";
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }
    }
}


