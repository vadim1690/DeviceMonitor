package com.ssi.devicemonitor.entity;

public enum DeviceType {

    HARDWARE_DEVICE("Hardware Device"),
    SOFTWARE_DEVICE("Software Device");

    private final String name;

    DeviceType(String name) {
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
