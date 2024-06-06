package com.santander.bank.DTO.Account;

import java.math.BigDecimal;

public record TransferDTO(String fromAccount, String toAccount, BigDecimal value) {
}



