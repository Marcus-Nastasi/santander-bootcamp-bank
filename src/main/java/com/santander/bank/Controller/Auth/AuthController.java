package com.santander.bank.Controller.Auth;

import com.santander.bank.DTO.User.UserLoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @GetMapping(value = "/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}



