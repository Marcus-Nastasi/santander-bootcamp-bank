package com.santander.bank.Controller.User;

import com.santander.bank.DTO.User.UserDTO;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> get(@PathVariable String id) {
        if (userRepo.findById(id).isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(userRepo.findById(id).get());
    }

    @PostMapping(value = "/new")
    public ResponseEntity<String> insert(@RequestBody UserDTO user) {
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        userRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}



