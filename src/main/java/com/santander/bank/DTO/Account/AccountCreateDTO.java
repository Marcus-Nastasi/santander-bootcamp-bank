package com.santander.bank.DTO.Account;

import java.math.BigDecimal;

public record AccountCreateDTO(String agency, BigDecimal balance, BigDecimal limits) {
}


