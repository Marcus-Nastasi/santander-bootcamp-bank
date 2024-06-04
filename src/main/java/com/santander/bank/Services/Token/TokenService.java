package com.santander.bank.Services.Token;

import com.auth0.jwt.algorithms.Algorithm;
import com.santander.bank.Models.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${api.spring.security.token.secret}")
    private String secret;

    public String generate(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);


        }
    }
}




