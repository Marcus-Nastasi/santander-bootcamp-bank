package com.santander.bank.Repository.Card;

import com.santander.bank.Models.Cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CardRepo extends JpaRepository<Card, BigInteger> {
}



