package com.santander.bank.Controller.Account;

import com.google.gson.Gson;
import com.santander.bank.Repository.Account.AccountRepo;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private Gson gson;

    @PostMapping(value = "/deposit")
    public ResponseEntity<String> deposit(@RequestBody String acc, BigDecimal value) {
        var json = gson.fromJson(acc, Map.class);

        String a = (String) json.get("acc");
        BigDecimal v = BigDecimal.valueOf((Double) json.get("value"));

        String d = accountService.deposit(a, v);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(d);
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<String> transfer(@RequestBody String ac1, @RequestHeader Map<String, String> headers) {
        var json = gson.fromJson(ac1, Map.class);

        String ac1Id = (String) json.get("ac1");
        String ac2Id = (String) json.get("ac2");
        BigDecimal vl = BigDecimal.valueOf((Double) json.get("value"));
        String token = headers.get("authorization").replace("Bearer ", "");

        String acs = accountService.transfer(ac1Id, ac2Id, vl, token);

        if (acs == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not valid user");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(acs);
    }
}




