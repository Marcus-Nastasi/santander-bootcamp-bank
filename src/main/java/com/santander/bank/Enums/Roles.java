package com.santander.bank.Enums;

public enum Roles {

    ADMIN("admin"), USER("user");

    private final String roles;

    Roles(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }
}



