package com.example.tacocloud.Controllers;

import com.example.tacocloud.Repositories.UserRepository;
import com.example.tacocloud.Security.RegistrationForm;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(){
        return "registration";
    }

    @Transactional
    @PostMapping
    public String processRegistrationUser(RegistrationForm form){
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
