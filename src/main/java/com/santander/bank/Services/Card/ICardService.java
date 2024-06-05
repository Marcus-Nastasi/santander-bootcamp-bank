package com.santander.bank.Services.Card;

import com.santander.bank.Models.Cards.Card;

import java.math.BigDecimal;

public interface ICardService {

    Card create();
    Card get(String number);
    String payOnDebit(String acc, BigDecimal value, String token);
    //String payDebit(BigDecimal value);
}



