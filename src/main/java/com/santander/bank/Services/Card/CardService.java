package com.santander.bank.Services.Card;

import com.santander.bank.Handler.GlobalException;
import com.santander.bank.Models.Cards.Card;
import com.santander.bank.Repository.Card.CardRepo;
import com.santander.bank.Services.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Service
public class CardService implements ICardService {

    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private AccountService accountService;

    @Override
    public Card create() {
        Card card = new Card();
        card.setNumber(this.generateCardNumber());
        card.setLimits(BigDecimal.valueOf(1000.00));
        cardRepo.save(card);
        return card;
    }

    @Override
    public Card get(String number) {
        return cardRepo.findByNumber(number);
    }

    @Override
    public String payOnDebit(String acc, BigDecimal value, String token) {
        accountService.withdraw(acc, value, token);
        return "payment done on debit";
    }

    @Override
    public String payOnCredit(BigInteger id, BigDecimal value, String token) {
        Card c = this.cardRepo.findById(id).orElseThrow(RuntimeException::new);
        if (c.getLimits().compareTo(value) < 0) return null;
        c.setLimits(c.getLimits().subtract(value));
        this.cardRepo.save(c);
        return "payment done on credit. limit: $" + c.getLimits();
    }

    private String generateCardNumber() {
        return UUID.randomUUID().toString().substring(0, 15);
    }
}



