package com.santander.bank.Services.Card;

import com.santander.bank.DTO.Card.CardDTO;
import com.santander.bank.Models.Cards.Card;

public interface ICardService {

    Card create();
    Card get(String number);
}



