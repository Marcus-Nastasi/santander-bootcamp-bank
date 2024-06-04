package com.santander.bank.Services.Account;

import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.Account.AccountRepo;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
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
        account1.setNumber(generateAccountNumber());
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
    public String deposit(String acc, BigDecimal value) {
        Account a = accountRepo.findById(acc).orElseThrow(RuntimeException::new);
        a.setBalance(a.getBalance().add(value));
        accountRepo.save(a);
        return "deposit done";
    }

    @Override
    public String withdraw(String acc, BigDecimal value) {
        Account a = accountRepo.findById(acc).orElseThrow(RuntimeException::new);
        a.setBalance(a.getBalance().subtract(value));
        accountRepo.save(a);
        return "withdraw done";
    }

    @Override
    public String transfer(String from, String to, BigDecimal value, String token) {
        User u = userRepo.findByAccountId(from);

        if (u == null) return null;

        String cpf1 = tokenService.validate(token);

        if (!cpf1.equals(u.getCpf())) return "cpf on token invalid, or token invalid";

        Account ac1 = accountRepo.findById(from).orElseThrow(RuntimeException::new);
        Account ac2 = accountRepo.findById(to).orElseThrow(RuntimeException::new);

        if (ac1.getBalance().add(ac1.getLimits()).compareTo(value) < 0) return "do not have balance to do it";

        ac1.setBalance(ac1.getBalance().subtract(value));
        ac2.setBalance(ac2.getBalance().add(value));

        accountRepo.save(ac1);
        accountRepo.save(ac2);

        return "ac1 balance: " + ac1.getBalance() + ", ac2 balance: " + ac2.getBalance();
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString().substring(0, 5);
    }
}




