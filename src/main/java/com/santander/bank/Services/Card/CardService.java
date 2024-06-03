package com.santander.bank.Services.Card;

import com.santander.bank.Models.Cards.Card;
import com.santander.bank.Repository.Card.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CardService implements ICardService {

    @Autowired
    private CardRepo cardRepo;

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

    private String generateCardNumber() {
        return UUID.randomUUID().toString().substring(0, 15);
    }
}



