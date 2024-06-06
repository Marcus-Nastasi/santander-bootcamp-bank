package com.santander.bank.DTO.Account;

import java.math.BigDecimal;

public record TransferDTO(String account1, String account2, BigDecimal value) {
}



