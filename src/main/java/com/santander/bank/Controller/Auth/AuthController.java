package com.santander.bank.Controller.Auth;

import com.santander.bank.DTO.User.UserLoginDTO;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.Token.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userLoginDTO.cpf(), userLoginDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        User u = userRepo.findByUserCpf(userLoginDTO.cpf());

        return passwordEncoder.matches(userLoginDTO.password(), u.getPassword()) ?
                ResponseEntity.status(HttpStatus.ACCEPTED).body(tokenService.generate(u)) :
                ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("wrong password");
    }
}



