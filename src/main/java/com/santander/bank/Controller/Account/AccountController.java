package com.santander.bank.Controller.Account;

import com.google.gson.Gson;
import com.santander.bank.DTO.User.FindCpfDTO;
import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Services.Token.TokenService;
import jakarta.validation.Valid;
import com.santander.bank.DTO.Account.DepositDTO;
import com.santander.bank.DTO.Account.TransferDTO;
import com.santander.bank.DTO.Account.WithdrawDTO;
import com.santander.bank.Repository.Account.AccountRepo;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private TokenService tokenService;
    @Autowired
    private Gson gson;

    @GetMapping(value = "/get")
    public ResponseEntity<Account> findByUserCpf(@RequestBody @Valid FindCpfDTO data, @RequestHeader Map<String, String> headers) {
        User u = userRepo.findByUserCpf(data.cpf());
        String token = headers.get("authorization").replace("Bearer ", "");
        String cpf = tokenService.validate(token);

        if (u == null) return ResponseEntity.badRequest().build();
        if (!cpf.equals(u.getCpf())) return ResponseEntity.badRequest().build();

        Account a = accountRepo.findById(u.getAccount_id()).orElseThrow(RuntimeException::new);

        return (a != null) ? ResponseEntity.ok(a) : ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/deposit")
    public ResponseEntity<String> deposit(@RequestBody @Valid DepositDTO data) {
        String d = accountService.deposit(data.account(), data.value());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(d);
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody @Valid WithdrawDTO data, @RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization").replace("Bearer ", "");
        String d = accountService.withdraw(data.account(), data.value(), token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(d);
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<String> transfer(@RequestBody @Valid TransferDTO data, @RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization").replace("Bearer ", "");
        String acs = accountService.transfer(data.fromAccount(), data.toAccount(), data.value(), token);

        if (acs == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not valid user");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(acs);
    }
}




