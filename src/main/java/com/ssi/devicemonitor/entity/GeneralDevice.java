package com.ssi.devicemonitor.entity;

public class GeneralDevice extends Device {
    private DeviceType deviceType;
    private String manufacturer;
    private String version;
    public GeneralDevice(String name) {
        super(name);
    }

    public GeneralDevice(String name,DeviceType deviceType) {
        super(name);
        this.deviceType = deviceType;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
