package com.neuSep17.utility;


import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Inventory;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.DealerImpleService;
import com.neuSep17.service.VehicleImpleService.VehicleImpleService;

import java.io.IOException;
import java.util.*;

public class BrowseInventoryUtility {
    Collection<Vehicle> vehicles = new ArrayList<>();

    public Collection<Vehicle> setObjectsforUtility() throws IOException {
        DealerImpleService dealerServiceObject = new DealerImpleService();
        VehicleImpleService vehicleServiceObject = new VehicleImpleService("E:\\IdeaProjects\\JavaFinalProject\\src\\com\\neuSep17\\data\\gmps-camino.txt");
        Dealer dealerObject = dealerServiceObject.getADealer("gmps-camino");
        Inventory inventoryObject = vehicleServiceObject.getAllVehicles(dealerObject.getId());
        vehicles = inventoryObject.getVehicles();
        return vehicles;
    }


    public List<Vehicle> sortByPrice(List<Vehicle> vehicles) {
        Collections.sort(vehicles, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Vehicle v1 = (Vehicle) o1;
                Vehicle v2 = (Vehicle) o2;
                if (v1.getPrice() > v2.getPrice()) {
                    return 1;
                } else if (v1.getPrice() == v1.getPrice()) {
                    return 0;
                } else {
                    return -1;
                }
            }
            /**  createResultsPanel
             display **/
        });
        return vehicles;
    }

    public List<Vehicle> sortByYear(List<Vehicle> vehicles) {
        Collections.sort(vehicles, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Vehicle v1 = (Vehicle) o1;
                Vehicle v2 = (Vehicle) o2;
                if (v1.getYear() > v2.getYear()) {
                    return 1;
                } else if (v1.getYear() == v1.getYear()) {
                    return 0;
                } else {
                    return -1;
                }
            }
          /**  createResultsPanel
                    display **/
        });
        return vehicles;
    }
    /** public ArrayList<Vehicle> filter(ArrayList<Vehicle> vehicles, String condition, String value) {
     int value=Integer.parseInt(value);
     switch (condition) {
     case "year":
     vehicles = vehicles.stream()
     .filter(v -> v.getYear() == value).collect(Collectors.toList());
     case "model":
     vehicles = vehicles.stream()
     .filter(v -> v.getModel() == value).collect(Collectors.toList());
     case "type":
     vehicles = vehicles.stream()
     .filter(v -> v.getBodyType() == value).collect(Collectors.toList());
     case "color":
     vehicles = vehicles.stream()
     .filter(v -> v.getColor() == value).collect(Collectors.toList());
     case "category":
     vehicles = vehicles.stream()
     .filter(v -> v.getCategory() == value).collect(Collectors.toList());
     case "price":
     String [] price = str.split("-", 2);
     int lo = price[0];
     int hi = price[1];

     vehicles = vehicles.stream()
     .filter(v -> v.getPrice() >= lo && v.getPrice() <= hi).collect(Collectors.toList());
     }

     return vehicles;
     createResultsPanel
     display
     }
     **/


}
