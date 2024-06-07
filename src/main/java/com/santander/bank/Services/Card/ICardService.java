package com.santander.bank.Services.Card;

import com.santander.bank.Models.Accounts.Account;
import com.santander.bank.Models.Cards.Card;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface ICardService {

    Card create();
    Card get(String number);
    String payOnDebit(String acc, BigDecimal value, String token);
    String payOnCredit(BigInteger id, BigDecimal value, String token);
    String payInvoice(BigInteger id, String account, String token);
    String growLimit(BigInteger id, String token);
}



