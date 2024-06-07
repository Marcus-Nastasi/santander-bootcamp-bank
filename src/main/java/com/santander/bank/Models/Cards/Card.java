package com.santander.bank.Models.Cards;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Column
    private String number;
    @Column(precision = 13, scale = 2)
    private BigDecimal limits;
    @Column(precision = 13, scale = 2)
    private BigDecimal limit_spent;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getLimits() {
        return limits;
    }

    public void setLimits(BigDecimal limits) {
        this.limits = limits;
    }

    public BigDecimal getLimit_spent() {
        return limit_spent;
    }

    public void setLimit_spent(BigDecimal limit_spent) {
        this.limit_spent = limit_spent;
    }
}



