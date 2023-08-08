package com.ssi.devicemonitor.controller;

import com.ssi.devicemonitor.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DeviceMonitorController {
    @FXML
    private ListView<Device> deviceListView;

    @FXML
    private TextField deviceNameTextField;

    @FXML
    private Button addDeviceButton;
    @FXML
    private Button hideDetailsButton;
    @FXML
    private Button editDetailsButton;
    @FXML
    private ComboBox<DeviceType> deviceTypeComboBox;
    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField statusTextField;
    @FXML
    private ComboBox<DeviceType> selectedDeviceTypeComboBox;
    @FXML
    private TextField manufacturerTextField;
    @FXML
    private TextField versionTextField;
    @FXML
    private TextField locationTextField = new TextField();
    @FXML
    private TextField macAddressTextField = new TextField();
    @FXML
    private VBox extendedDetailsVbox;
    @FXML
    private VBox detailsVBox;
    @FXML
    private VBox generalDetailsVBox;
    @FXML
    private TextField installationDateTextField = new TextField();
    @FXML
    private TextField installationTimeTextField = new TextField();
    private Label installationTime = new Label("Installation Time:");
    private Label installationDate = new Label("Installation Date:");
    private Label macAddress = new Label("Mac Address:");
    private Label location = new Label("Location:");


    public void initialize() {
        initializeDeviceListView();
        initializeContextMenu();
        initializeDeviceTypeComboBox();
    }

    private void initializeDeviceTypeComboBox() {
        deviceTypeComboBox.setItems(FXCollections.observableList(DeviceMonitor.getInstance().getDeviceTypes()));
        selectedDeviceTypeComboBox.setItems(FXCollections.observableList(DeviceMonitor.getInstance().getDeviceTypes()));
        deviceTypeComboBox.getSelectionModel().selectFirst();
    }

    private void initializeContextMenu() {
        // Add context menu to ListView
        ContextMenu contextMenu = new ContextMenu();
        MenuItem removeItem = new MenuItem("Remove");
        MenuItem showItem = new MenuItem("Show");
        MenuItem editItem = new MenuItem("Edit");
        removeItem.setOnAction(this::removeDevice);
        showItem.setOnAction(this::showItem);
        editItem.setOnAction(this::editItem);
        contextMenu.getItems().addAll(removeItem, showItem, editItem);
        deviceListView.setContextMenu(contextMenu);
    }

    private void editItem(ActionEvent actionEvent) {
        Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
        setSelectedDeviceDetails(selectedDevice, true);
    }

    private void showItem(ActionEvent actionEvent) {
        Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
        setSelectedDeviceDetails(selectedDevice, false);
    }

    private void setSelectedDeviceDetails(Device selectedDevice, boolean isEdit) {
        setDetailsViewsFunctionality(isEdit);
        detailsVBox.setVisible(true);
        editDetailsButton.setVisible(isEdit);
        editDetailsButton.setOnAction(this::editDevice);
        hideDetailsButton.setOnAction(action -> {
            detailsVBox.setVisible(false);
        });
        nameTextField.setText(selectedDevice.getName());
        statusTextField.setText(selectedDevice.getStatus().toString());
        if (selectedDevice instanceof GeneralDevice) {
            GeneralDevice generalDevice = (GeneralDevice) selectedDevice;
            initializeGeneralDetails(generalDevice);
        }
    }

    private void editDevice(ActionEvent actionEvent) {
        // no time to implement need to take the values from the views.
    }

    private void setDetailsViewsFunctionality(boolean isEdit) {
        nameTextField.setDisable(!isEdit);
        statusTextField.setDisable(!isEdit);
        selectedDeviceTypeComboBox.setDisable(!isEdit);
        manufacturerTextField.setDisable(!isEdit);
        versionTextField.setDisable(!isEdit);
        locationTextField.setDisable(!isEdit);
        macAddressTextField.setDisable(!isEdit);
        installationDateTextField.setDisable(!isEdit);
        installationTimeTextField.setDisable(!isEdit);
    }

    private void initializeGeneralDetails(GeneralDevice selectedDevice) {
        generalDetailsVBox.setVisible(true);
        versionTextField.setText(selectedDevice.getVersion());
        manufacturerTextField.setText(selectedDevice.getManufacturer());
        selectedDeviceTypeComboBox.getSelectionModel().select(selectedDevice.getDeviceType());
        if (selectedDevice.getDeviceType() != null)
            initializeExtendedDetailsByDeviceType(selectedDevice);
    }

    private void initializeExtendedDetailsByDeviceType(GeneralDevice selectedDevice) {
        extendedDetailsVbox.setVisible(true);
        extendedDetailsVbox.getChildren().clear();
        switch (selectedDevice.getDeviceType()) {
            case SOFTWARE_DEVICE:
                SoftwareDevice softwareDevice = (SoftwareDevice) selectedDevice;
                extendedDetailsVbox.getChildren().add(installationDate);
                extendedDetailsVbox.getChildren().add(installationDateTextField);
                extendedDetailsVbox.getChildren().add(installationTime);
                extendedDetailsVbox.getChildren().add(installationTimeTextField);
                installationDateTextField.setText(softwareDevice.getInstallationDate());
                installationTimeTextField.setText(softwareDevice.getInstallationTime());
                break;
            case HARDWARE_DEVICE:
                HardwareDevice hardwareDevice = (HardwareDevice) selectedDevice;
                extendedDetailsVbox.getChildren().add(macAddress);
                extendedDetailsVbox.getChildren().add(macAddressTextField);
                extendedDetailsVbox.getChildren().add(location);
                extendedDetailsVbox.getChildren().add(locationTextField);
                macAddressTextField.setText(hardwareDevice.getMacAddress());
                installationTimeTextField.setText(hardwareDevice.getLocation());
                break;
        }
    }

    private void removeDevice(ActionEvent actionEvent) {
        Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
        if (selectedDevice != null) {
            DeviceMonitor.getInstance().removeDevice(selectedDevice);
        }
    }

    private void initializeDeviceListView() {
        deviceListView.setItems(DeviceMonitor.getInstance().getDevices());
        deviceListView.setCellFactory(deviceListView -> new DeviceListCell());
    }

    private class DataUpdateTask extends TimerTask {
        private Random random = new Random();

        @Override
        public void run() {
            refreshListView();
        }
    }

    @FXML
    private void addDevice() {
        String deviceName = deviceNameTextField.getText();
        DeviceType deviceType = deviceTypeComboBox.getSelectionModel().getSelectedItem();
        DeviceMonitor.getInstance().createAndAddDevice(deviceName, deviceType);
        deviceNameTextField.clear();
    }

    public void refreshListView() {
        deviceListView.refresh();
    }

    private class DeviceListCell extends ListCell<Device> {
        @Override
        protected void updateItem(Device device, boolean empty) {
            super.updateItem(device, empty);

            if (device == null || empty) {
                setText(null);
                setGraphic(null);
                setStyle(""); // Reset the cell style
            } else {
                setText(device.getName() + " - " + device.getStatus());

                // Set the cell style based on the device status
                if (device.getStatus() == DeviceStatus.ONLINE) {
                    setStyle("-fx-text-fill: green;");
                } else if (device.getStatus() == DeviceStatus.OFFLINE) {
                    setStyle("-fx-text-fill: red;");
                } else {
                    setStyle(""); // Reset the cell style
                }
            }
        }
    }
}
