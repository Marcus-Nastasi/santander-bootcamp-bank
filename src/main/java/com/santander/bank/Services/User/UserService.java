package com.santander.bank.Services.User;

import com.google.gson.Gson;
import com.santander.bank.DTO.User.UserDTO;
import com.santander.bank.Enums.Roles;
import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Cards.Card;
import com.santander.bank.Models.Users.User;
import com.santander.bank.Repository.User.UserRepo;
import com.santander.bank.Services.Account.AccountService;
import com.santander.bank.Services.Card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CardService cardService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private Gson gson;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return userRepo.findByCpf(cpf);
    }

    @Override
    public User findById(String id) throws NoSuchElementException {
        return userRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User findByAccountId(String accountId) {
        return userRepo.findByAccountId(accountId);
    }

    @Override
    public User findByCpf(String cpf) {
        return userRepo.findByUserCpf(cpf);
    }

    @Override
    public void create(UserDTO userToCreate) throws IllegalArgumentException {
        if (userRepo.findByUserCpf(userToCreate.cpf()) != null) throw new IllegalArgumentException("user already exists");

        Card c = cardService.create();
        Card get = cardService.get(c.getNumber());

        Account account = accountService.create("0000-1", BigDecimal.valueOf(0.0), BigDecimal.valueOf(1000.00));
        Account getAc = accountService.findByNbr(account.getNumber());

        User newUser = new User(userToCreate.name(), userToCreate.cpf(), passwordEncoder.encode(userToCreate.password()), Roles.valueOf(userToCreate.roles()));

        List<BigInteger> cardList = new ArrayList<>();
        cardList.add(get.getId());

        newUser.setAccount_id(getAc.getId());
        newUser.setCard_id(gson.toJson(cardList));

        userRepo.save(newUser);
    }
}



