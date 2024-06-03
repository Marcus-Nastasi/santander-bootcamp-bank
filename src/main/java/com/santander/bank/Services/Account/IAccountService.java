package com.santander.bank.Services.Account;

import com.santander.bank.Models.Accounts.Account;

import java.math.BigDecimal;

public interface IAccountService {

    Account create(String ag, BigDecimal bal, BigDecimal lim);
}



