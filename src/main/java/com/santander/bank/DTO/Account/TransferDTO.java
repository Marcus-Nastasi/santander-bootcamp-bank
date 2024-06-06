package com.santander.bank.DTO.Account;

import java.math.BigDecimal;

public record TransferDTO(String fromAccount1, String toAccount, BigDecimal value) {
}



