package com.administrationsystem.dentalClinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/administrator")
public class AdministratorController{

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password)
    {
        String encryptedPassword = "$2a$12$bLl5ZUKQGvRvMPnh278mE.d7F/gfPt2yrU.agSkjFefOkWItsgYxa"; //admin is encrypted
        if(username.equals("admin") && passwordEncoder.matches(password,encryptedPassword))
        {
            return ResponseEntity.ok("Successful Login");
        }else{
            return ResponseEntity.badRequest().body("Unsuccessful Login");
        }
    }


}
