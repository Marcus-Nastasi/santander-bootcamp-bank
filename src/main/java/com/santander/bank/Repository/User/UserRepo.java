package com.santander.bank.Repository.User;

import com.santander.bank.Models.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}




