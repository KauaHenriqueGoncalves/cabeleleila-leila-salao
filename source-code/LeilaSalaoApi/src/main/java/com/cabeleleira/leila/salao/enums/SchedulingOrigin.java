package com.cabeleleira.leila.salao.enums;

public enum SchedulingOrigin {
    APP("app"),
    PHONE_NUMBER("phone_number");

    private final String name;

    SchedulingOrigin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
