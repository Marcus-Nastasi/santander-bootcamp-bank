package com.santander.bank.Controller.Account;

import com.santander.bank.Repository.Account.AccountRepo;
import com.santander.bank.Repository.User.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping(value = "/transfer")
    public ResponseEntity<String> transfer(@RequestBody String ac1, @RequestBody String ac2, @RequestBody BigDecimal value) {

    }
}




