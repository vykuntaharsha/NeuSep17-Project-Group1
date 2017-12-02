package com.neuSep17.dto;

import com.neuSep17.io.FileReading;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Inventory {
    private String dealerId;
    private Collection<Vehicle> vehicles = new ArrayList<>();

    public Inventory(File file) {
        this.dealerId = file.getName();
        this.vehicles = FileReading.readAndGetVehicles(file);
    }

    public Inventory(String dealerId, Collection<Vehicle> vehicles) {
        this.dealerId = dealerId;
        this.vehicles = vehicles;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Collection<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
