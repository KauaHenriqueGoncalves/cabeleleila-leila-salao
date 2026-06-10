package com.cabeleleira.leila.salao.enums;

public enum UsersRole {
    ADMIN("admin"),
    CLIENT("client");

    private final String name;

    UsersRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
