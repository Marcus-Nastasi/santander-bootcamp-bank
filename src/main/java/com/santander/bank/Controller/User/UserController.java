package com.santander.bank.Controller.User;

import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepo.findAll());
    }
}



