package com.santander.bank.Services.Card;

import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Cards.Card;
import com.santander.bank.Repository.Account.AccountRepo;
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
    @Autowired
    private AccountRepo accountRepo;

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
        Card c = cardRepo.findById(id).orElseThrow(RuntimeException::new);

        if (c.getLimits().subtract(c.getLimit_spent()).compareTo(value) < 0) return null;

        c.setLimit_spent(c.getLimit_spent().add(value));
        cardRepo.save(c);

        return "payment done on credit. limit: $" + c.getLimits().subtract(c.getLimit_spent());
    }

    @Override
    public String payInvoice(BigInteger id, String account, String token) {
        Card c = cardRepo.findById(id).orElseThrow(RuntimeException::new);
        Account a = accountRepo.findById(account).orElseThrow(RuntimeException::new);

        if (a.getBalance().subtract(c.getLimit_spent()).compareTo(BigDecimal.valueOf(0)) < 0) return null;

        a.setBalance(a.getBalance().subtract(c.getLimit_spent()));
        c.setLimit_spent(BigDecimal.valueOf(0));

        accountRepo.save(a);
        cardRepo.save(c);

        return "thank you for paying your invoice! new limit: R$" + c.getLimits().subtract(c.getLimit_spent());
    }

    @Override
    public String growLimit(BigInteger id, String token) {
        Card c = cardRepo.findById(id).orElseThrow(RuntimeException::new);
        BigDecimal newLimit = accountService.limitEvaluation(id, c.getLimits(), token);

        if (newLimit == null) return "nothing to update";

        c.setLimits(newLimit);
        cardRepo.save(c);

        return "updated limit. New limit: $" + c.getLimits();
    }

    private String generateCardNumber() {
        return UUID.randomUUID().toString().substring(0, 15);
    }
}



