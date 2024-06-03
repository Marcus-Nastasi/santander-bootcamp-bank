package com.santander.bank.Services.Account;

import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Repository.Account.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepo accountRepo;


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

    private String generateCardNumber() {
        return UUID.randomUUID().toString().substring(0, 5);
    }
}




