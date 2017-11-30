package com.neuSep17.dto;

import java.util.ArrayList;
import java.util.Collection;

public class Inventory {
    private String dealerId;
    private Collection<Vehicle> vehicles = new ArrayList<>();

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
