package com.santander.bank.Services.Account;

import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.Account.AccountRepo;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TokenService tokenService;


    @Override
    public Account create(String ag, BigDecimal bal, BigDecimal lim) {
        Account account1 = new Account();
        account1.setNumber(generateCardNumber());
        account1.setAgency(ag);
        account1.setBalance(bal);
        account1.setLimits(lim);
        accountRepo.save(account1);
        return account1;
    }

    @Override
    public Account findByNbr(String number) {
        return accountRepo.findByNumber(number);
    }

    @Override
    public String transfer(String from, String to, BigDecimal value, String token) {
        User u = userRepo.findByAccountId(from);

        if (u == null) return null;

        String cpf1 = tokenService.validate(token);

        if (cpf1.equals(u.getCpf())) System.out.println(true);

        return "Validated";
    }

    private String generateCardNumber() {
        return UUID.randomUUID().toString().substring(0, 5);
    }
}




