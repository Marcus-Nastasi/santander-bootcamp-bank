package com.santander.bank.Services.User;

import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.User.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void transfer(Account from, Account to, BigDecimal value) {
        from.setBalance(from.getBalance().subtract(value));
        to.setBalance(to.getBalance().add(value));
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return userRepo.findByUserCpf(cpf);
    }

    @Override
    public User findById(String id) throws NoSuchElementException {
        return userRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }


    @Override
    public User findByCpf(String cpf) {
        return userRepo.findByCpf(cpf);
    }

    @Override
    public User create(User userToCreate) throws IllegalArgumentException {
        if (userRepo.findByCpf(userToCreate.getCpf()) != null) throw new IllegalArgumentException("user already exists");
        return userRepo.save(userToCreate);
    }
}



