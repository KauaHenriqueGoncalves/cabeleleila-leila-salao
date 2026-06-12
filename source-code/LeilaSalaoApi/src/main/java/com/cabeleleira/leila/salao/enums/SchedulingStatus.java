package com.cabeleleira.leila.salao.enums;

public enum SchedulingStatus {
    NO_SCHEDULED("no_scheduled"),
    SCHEDULED("scheduled"),
    CONFIRMED("confirmed"),
    DONE("done"),
    CANCELED("canceled"),
    NO_SHOW("no_show");

    private final String name;

    SchedulingStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
