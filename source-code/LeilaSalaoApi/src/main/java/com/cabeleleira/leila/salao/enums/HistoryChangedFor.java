package com.cabeleleira.leila.salao.enums;

public enum HistoryChangedFor {
    CLIENT("client"),
    LEILA("leila"),
    SYSTEM("system");

    private final String name;

    HistoryChangedFor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
