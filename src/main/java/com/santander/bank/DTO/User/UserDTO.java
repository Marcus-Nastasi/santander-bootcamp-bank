package com.santander.bank.DTO.User;

import com.santander.bank.Enums.Roles;

public record UserDTO(String name, String cpf, String password, Roles roles) {
}



