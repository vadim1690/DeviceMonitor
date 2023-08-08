package com.ssi.devicemonitor.entity;

import java.time.LocalDateTime;

import static com.ssi.devicemonitor.entity.DeviceType.SOFTWARE_DEVICE;

public class SoftwareDevice extends GeneralDevice {
    private String installationDate;
    private String installationTime;

    public SoftwareDevice(String name, String installationDate, String installationTime) {
        super(name, SOFTWARE_DEVICE);
        this.installationDate = installationDate;
        this.installationTime = installationTime;
    }

    public SoftwareDevice(String name) {
        super(name, SOFTWARE_DEVICE);
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getInstallationTime() {
        return installationTime;
    }

    public void setInstallationTime(String installationTime) {
        this.installationTime = installationTime;
    }
}
