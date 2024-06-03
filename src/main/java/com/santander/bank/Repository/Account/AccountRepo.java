package com.santander.bank.Repository.Account;

import com.santander.bank.Models.Accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, String> {
}



