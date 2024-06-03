package com.santander.bank.Repository.Account;

import com.santander.bank.Models.Accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, String> {
}



