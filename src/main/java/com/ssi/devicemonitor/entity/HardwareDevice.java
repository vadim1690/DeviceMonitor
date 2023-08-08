package com.ssi.devicemonitor.entity;

public class HardwareDevice extends GeneralDevice {

    private String location;
    private String macAddress;

    public HardwareDevice(String name, String location, String macAddress) {
        super(name, DeviceType.HARDWARE_DEVICE);
        this.location = location;
        this.macAddress = macAddress;
    }

    public HardwareDevice(String name) {
        super(name, DeviceType.HARDWARE_DEVICE);
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
