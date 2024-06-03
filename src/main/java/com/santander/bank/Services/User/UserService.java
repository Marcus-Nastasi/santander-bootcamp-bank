package com.santander.bank.Services.User;

import com.santander.bank.DTO.User.UserDTO;
import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Cards.Card;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.User.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void transfer(Account from, Account to, BigDecimal value) {
        from.setBalance(from.getBalance().subtract(value));
        to.setBalance(to.getBalance().add(value));
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return userRepo.findByCpf(cpf);
    }

    @Override
    public User findById(String id) throws NoSuchElementException {
        return userRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }


    @Override
    public User findByCpf(String cpf) {
        return userRepo.findByUserCpf(cpf);
    }

    @Override
    public void create(UserDTO userToCreate) throws IllegalArgumentException {
        if (userRepo.findByUserCpf(userToCreate.cpf()) != null) throw new IllegalArgumentException("user already exists");
        User newUser = new User();
        newUser.setName(userToCreate.name());
        newUser.setCpf(userToCreate.cpf());
        newUser.setPassword(passwordEncoder.encode(userToCreate.password()));
        newUser.setRole(userToCreate.roles());

        Card card = new Card();
        card.setLimits(BigDecimal.valueOf(1000.00));

        newUser.getCard_id().add(card.getId());
        userRepo.save(newUser);
    }
}



