package com.santander.bank.Services.Account;

import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.Account.AccountRepo;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    public String withdraw(String acc, BigDecimal value, String token) {
        User u = userRepo.findByAccountId(acc);
        String cpf = tokenService.validate(token);
        Account a = accountRepo.findById(acc).orElseThrow(RuntimeException::new);

        if (!cpf.equals(u.getCpf())) return "cpf on token invalid, or token invalid";

        a.setBalance(a.getBalance().subtract(value));
        accountRepo.save(a);

        return "withdraw done";
    }

    @Override
    public String transfer(String from, String to, BigDecimal value, String token) {
        User u = userRepo.findByAccountId(from);
        String cpf = tokenService.validate(token);
        Account fromAc = accountRepo.findById(from).orElseThrow(RuntimeException::new);
        Account toAc = accountRepo.findById(to).orElseThrow(RuntimeException::new);

        if (u == null) return null;
        if (!cpf.equals(u.getCpf())) return "cpf on token invalid, or token invalid";
        if (fromAc.getBalance().add(fromAc.getLimits()).compareTo(value) < 0) return "do not have balance";

        fromAc.setBalance(fromAc.getBalance().subtract(value));
        toAc.setBalance(toAc.getBalance().add(value));

        accountRepo.save(fromAc);
        accountRepo.save(toAc);

        return "{\"account from\": " + fromAc.getBalance() + ", \"account to\":" + toAc.getBalance() + '}';
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    // limit grow evaluation
    public BigDecimal limitEvaluation(BigInteger cardId, BigDecimal limitNow, String token) throws RuntimeException {
        User u = userRepo.findByCardId(String.valueOf(cardId));
        String cpf = tokenService.validate(token);

        if (u == null) throw new RuntimeException("null user");
        if (!cpf.equals(u.getCpf())) throw new RuntimeException("invalid token");

        Account a = accountRepo.findById(u.getAccount_id()).orElseThrow(RuntimeException::new);
        BigDecimal balance = a.getBalance();
        BigDecimal newLimit = null;

        if (balance.compareTo(BigDecimal.valueOf(1000)) < 0) newLimit = BigDecimal.valueOf(0);
        if (balance.compareTo(BigDecimal.valueOf(10000)) >= 0) newLimit = BigDecimal.valueOf(4000);
        if (balance.compareTo(BigDecimal.valueOf(30000)) >= 0) newLimit = BigDecimal.valueOf(10000);
        if (balance.compareTo(BigDecimal.valueOf(50000)) >= 0) newLimit = BigDecimal.valueOf(15000);
        if (balance.compareTo(BigDecimal.valueOf(70000)) >= 0) newLimit = BigDecimal.valueOf(20000);
        if (balance.compareTo(BigDecimal.valueOf(80000)) >= 0) newLimit = BigDecimal.valueOf(25000);
        if (balance.compareTo(BigDecimal.valueOf(90000)) >= 0) newLimit = BigDecimal.valueOf(30000);
        if (balance.compareTo(BigDecimal.valueOf(100000)) >= 0) newLimit = BigDecimal.valueOf(100000000);

        return newLimit;
    }
}




