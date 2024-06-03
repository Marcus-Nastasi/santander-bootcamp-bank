package com.santander.bank.Models.Users;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String name;
    @Column
    private String cpf;
    @Column
    private String password;
    @Column
    private String account_id;
    @Column
    private List<BigInteger> card_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public List<BigInteger> getCard_id() {
        return card_id;
    }

    public void setCard_id(List<BigInteger> card_id) {
        this.card_id = card_id;
    }
}



