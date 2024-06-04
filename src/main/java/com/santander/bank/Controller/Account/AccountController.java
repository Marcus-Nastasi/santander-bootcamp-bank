package com.santander.bank.Controller.Account;

import com.google.gson.Gson;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.Account.AccountRepo;
import com.santander.bank.Repository.User.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private Gson gson;

    @PostMapping(value = "/transfer")
    public ResponseEntity<Object[]> transfer(@RequestBody String ac1/*, @RequestBody String ac2, @RequestBody BigDecimal value*/) {
        var json = gson.fromJson(ac1, Object.class);
        String acIdBase = json.toString().split("=")[1];
        String acId = acIdBase.substring(0, acIdBase.length() - 1);

        Object[] u = userRepo.findByAccountId(acId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(u);
    }
}




