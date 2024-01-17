package com.example.eobrazovanje.controller;

import com.example.eobrazovanje.dto.LoginDTO;
import com.example.eobrazovanje.dto.LozinkaDTO;
import com.example.eobrazovanje.security.TokenHelper;
import com.example.eobrazovanje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Value("${jwt.header}")
    private String header;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        authenticationManager.authenticate(token);
        UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.set(header, tokenHelper.generateToken(details));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PutMapping("/promena-lozinke")
    public ResponseEntity promenaLozinke(Principal principal, @RequestBody LozinkaDTO lozinkaDTO) {
        try {
            userService.promenaLozinke(principal.getName(), lozinkaDTO);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
