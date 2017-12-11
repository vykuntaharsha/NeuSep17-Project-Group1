package com.neuSep17.utility;

import com.neuSep17.dao.VehicleImple;
import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Inventory;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.DealerImpleService;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class InventoryBrowseUtility {
    Collection<Vehicle> vehicles = new ArrayList<>();

    public Collection<Vehicle> setObjectsforUtility() throws IOException {
        DealerImpleService dealerServiceObject = new DealerImpleService();
        VehicleImple vehicleImpleObject = new VehicleImple(new File("E:\\IdeaProjects\\JavaFinalProject\\src\\com\\neuSep17\\data"));
        Dealer dealerObject = dealerServiceObject.getADealer("gmps-camino");
        Inventory inventoryObject = vehicleImpleObject.getInventory(dealerObject.getId().toString());
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

    public static void sortByYear(ArrayList<Vehicle> list, boolean isAscend) {
        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if (isAscend) {
                    return o1.getYear() - o2.getYear();
                } else {
                    return o2.getYear() - o1.getYear();
                }
            }
        });
    }

    public static void sortByPrice(ArrayList<Vehicle> list, boolean isAscend) {
        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if (isAscend) {
                    return (int) (o1.getPrice() - o2.getPrice());
                } else {
                    return (int) (o2.getPrice() - o1.getPrice());
                }
            }
        });
    }


}
