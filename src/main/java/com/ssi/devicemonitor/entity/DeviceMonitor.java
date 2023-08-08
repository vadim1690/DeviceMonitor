package com.ssi.devicemonitor.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class DeviceMonitor {

    private static DeviceMonitor instance;
    private ObservableList<Device> devices;
    private Timer statusUpdateTimer;
    private GeneralDevice currentlySelectedGeneralDevice;

    public static DeviceMonitor getInstance() {
        if (instance == null)
            instance = new DeviceMonitor();
        return instance;
    }

    private DeviceMonitor() {
        devices = FXCollections.observableArrayList();
        addDevice(new GeneralDevice("Device 1"));
        addDevice(new GeneralDevice("Device 2"));
        addDevice(new GeneralDevice("Device 3"));

        // Start the timer to simulate status updates every few seconds
        statusUpdateTimer = new Timer();
        statusUpdateTimer.schedule(new StatusUpdateTask(), 0, 5000); // Update every 5 seconds
    }

    public ObservableList<Device> getDevices() {
        return devices;
    }

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    public void removeDevice(Device device) {
        devices.remove(device);
    }

    public List<DeviceType> getDeviceTypes() {
        return Arrays.asList(
                DeviceType.HARDWARE_DEVICE,
                DeviceType.SOFTWARE_DEVICE
        );
    }

    public void createAndAddDevice(String deviceName, DeviceType deviceType) {
        if (deviceType == DeviceType.HARDWARE_DEVICE)
            DeviceMonitor.getInstance().addDevice(new HardwareDevice(deviceName));
        else if (deviceType == DeviceType.SOFTWARE_DEVICE)
            DeviceMonitor.getInstance().addDevice(new SoftwareDevice(deviceName));
        else
            DeviceMonitor.getInstance().addDevice(new GeneralDevice(deviceName));
    }


    private class StatusUpdateTask extends TimerTask {
        private Random random = new Random();

        @Override
        public void run() {
            for (Device device : devices) {
                // Simulate random status updates
                boolean isOnline = random.nextBoolean();
                device.setStatus(isOnline ? DeviceStatus.ONLINE : DeviceStatus.OFFLINE);
            }
        }
    }
}
