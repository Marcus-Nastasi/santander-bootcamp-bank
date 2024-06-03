package com.santander.bank.DTO.User;

import com.santander.bank.Enums.Roles;

public record UserRequestDTO(String id, String name, String cpf, String password, String account_id, String card_id, String roles) {
}



