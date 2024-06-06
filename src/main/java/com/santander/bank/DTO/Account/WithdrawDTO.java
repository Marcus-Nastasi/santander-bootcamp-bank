package com.santander.bank.DTO.Account;

import java.math.BigDecimal;

public record WithdrawDTO(String account, BigDecimal value) {
}


