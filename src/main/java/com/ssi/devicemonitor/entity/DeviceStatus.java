package com.ssi.devicemonitor.entity;

public enum DeviceStatus {
    ONLINE("Online"),
    OFFLINE("Offline");

    private final String name;

    DeviceStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
