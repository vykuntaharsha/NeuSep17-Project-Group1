package com.neuSep17.utility;

import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Inventory;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.DealerImpleService;
import com.neuSep17.service.VehicleImpleService.VehicleImpleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BrowseInventoryUtility {
    Collection<Vehicle> vehicles = new ArrayList<>();

    public Collection<Vehicle> setObjectsforUtility() throws IOException {
        DealerImpleService dealerServiceObject = new DealerImpleService();
        VehicleImpleService vehicleServiceObject = new VehicleImpleService("E:\\IdeaProjects\\JavaFinalProject\\src\\com\\neuSep17\\data");
        Dealer dealerObject = dealerServiceObject.getADealer("gmps-camino");
        Inventory inventoryObject = vehicleServiceObject.getAllVehicles(dealerObject.getId());
        vehicles = inventoryObject.getVehicles();
        return vehicles;
    }

    public ArrayList<Vehicle> filterVehicles(ArrayList<Vehicle> vehicles, HashMap<String, String> filter) {
        ArrayList<Vehicle> filterVehicles = new ArrayList<Vehicle>();
        filterVehicles.addAll(vehicles);
        for (String c : filter.keySet()) {
            if (filter.get(c) == "NONE") {
                continue;
            }

            filterVehicles = filterVehicle(filterVehicles, c, filter.get(c));
        }
        return filterVehicles;
    }


    public ArrayList<Vehicle> filterVehicle(ArrayList<Vehicle> vehicles, String condition, String value) {
        List<Vehicle> filterVehicles = new ArrayList<Vehicle>();

        switch (condition) {
            case "Year":
                filterVehicles = vehicles.stream()
                        .filter(v -> v.getYear() == Integer.parseInt(value)).collect(Collectors.toList());
                break;
            case "Make":
                filterVehicles = vehicles.stream()
                        .filter(v -> v.getMake().toUpperCase().equals(value)).collect(Collectors.toList());
                break;
            case "Type":
                filterVehicles = vehicles.stream()
                        .filter(v -> v.getBodyType().toUpperCase().equals(value)).collect(Collectors.toList());
                break;
            case "Category":
                System.out.println(vehicles.get(1).getCategory().toString());
                filterVehicles = vehicles.stream()
                        .filter(v -> v.getCategory().toString() == value).collect(Collectors.toList());
                break;
            case "Price":
                String[] price = value.split("-", 2);
                double lo = Double.parseDouble(price[0]);
                if (price.length > 1) {
                    double hi = Double.parseDouble(price[1]);
                    // Double.POSITIVE_INFINITY;
                    filterVehicles = vehicles.stream()
                            .filter(v -> v.getPrice() >= lo && v.getPrice() <= hi).collect(Collectors.toList());
                } else {
                    filterVehicles = vehicles.stream()
                            .filter(v -> v.getPrice() >= lo).collect(Collectors.toList());
                }
        }

        return (ArrayList<Vehicle>) filterVehicles;
    }

}
