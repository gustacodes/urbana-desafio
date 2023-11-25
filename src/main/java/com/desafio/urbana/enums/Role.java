package com.desafio.urbana.enums;

public enum Role {

    ADMIN("ADMN"),
    USER("USER");

    private String role;

    Role(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
