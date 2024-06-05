package com.santander.bank.Services.Account;

import com.santander.bank.Models.Accounts.Account;

import java.math.BigDecimal;

public interface IAccountService {

    Account create(String ag, BigDecimal bal, BigDecimal lim);
    Account findByNbr(String number);
    String transfer(String ac1, String ac2, BigDecimal value, String token);
    String deposit(String acc, BigDecimal value);
    String withdraw(String acc, BigDecimal value, String token);
}



