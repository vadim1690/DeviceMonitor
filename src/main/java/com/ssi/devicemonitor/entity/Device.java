package com.ssi.devicemonitor.entity;

public abstract class Device {
    private String name;
    private DeviceStatus status;


    public Device(String name) {
        this(name, DeviceStatus.OFFLINE); // Set initial status to Offline
    }

    public Device(String name, DeviceStatus status) {
        this.name = name;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }
}
