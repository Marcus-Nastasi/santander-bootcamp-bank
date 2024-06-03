package com.santander.bank.Services.User;

import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Users.User;

import java.math.BigDecimal;

public interface IUserService {

    User findById(String id);
    User findByCpf(String cpf);
    User create(User userToCreate);
    void transfer(Account from, Account to, BigDecimal value);
}


