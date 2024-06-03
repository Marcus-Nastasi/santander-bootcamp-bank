package com.santander.bank.Repository.Card;

import com.santander.bank.Models.Cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CardRepo extends JpaRepository<Card, BigInteger> {

    @Query(nativeQuery = true, value = "SELECT * FROM cards WHERE (number=?1)")
    Card findByNumber(String number);
}



