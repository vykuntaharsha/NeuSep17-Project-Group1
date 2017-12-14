package com.neuSep17.utility;

import com.neuSep17.dao.DealerImple;
import com.neuSep17.dao.VehicleImple;
import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Inventory;
import com.neuSep17.dto.Vehicle;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class InventoryBrowseUtility {
    public static final String[] SORT_TYPE = {"NONE", "PRICE HIGH TO LOW", "PRICE LOW TO HIGH", "YEAR LOW TO HIGH", "YEAR HIGH TO LOW"};
    Collection<Vehicle> vehicles = new ArrayList<>();


    public Collection<Vehicle> setObjectsforUtility(String dealer) throws IOException {
        DealerImple dealerImpleObject = new DealerImple();
        VehicleImple vehicleImpleObject = new VehicleImple(new File("data"));
        Dealer dealerObject = dealerImpleObject.getADealer(dealer);
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

    public String[] findUniqueVehiclePropertyValues(Collection<Vehicle> vehicles, String property) {
        Set<String> vehiclePropertySet = new HashSet<>();
        vehiclePropertySet.add("NONE");
        for (Vehicle v : vehicles) {
            if (property.equals("make") && v.getMake() != "") {
                vehiclePropertySet.add(v.getMake().toUpperCase());
            } else if (property.equals("type") && v.getBodyType().length() != 0) {
                vehiclePropertySet.add(v.getBodyType().toUpperCase());
            } else if (property.equals("year") && v.getBodyType() != "") {
                vehiclePropertySet.add(Integer.toString(v.getYear()));
            } else if (property.equals("category") && v.getCategory().toString() != "") {
                vehiclePropertySet.add(v.getCategory().toString());
            }
            else if (property.equals(("price"))) {
                if(v.getPrice() >= 0 && v.getPrice() < 10000 ) {
                    vehiclePropertySet.add("0-10000");
                }else if (v.getPrice() > 10000 && v.getPrice() < 20000) {
                    vehiclePropertySet.add("10000-20000");
                }
                else if (v.getPrice() > 20000 && v.getPrice() < 30000) {
                    vehiclePropertySet.add("20000-30000");
                }
                else{
                    vehiclePropertySet.add("above 40000");
                }
            }
        }
        return vehiclePropertySet.toArray(new String[vehiclePropertySet.size()]);

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
//                System.out.println(vehicles.get(1).getCategory().toString());
                filterVehicles = vehicles.stream()
                        .filter(v -> v.getCategory().toString() == value).collect(Collectors.toList());
                break;
            case "Price":
                String[] price = value.split("-", 2);
                if(price[0].contains("above")){
                    price[0]=price[0].split(" ")[1];
                }
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

    public void sortByYear(ArrayList<Vehicle> list, boolean isAscend) {
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

    public void sortByPrice(ArrayList<Vehicle> list, boolean isAscend) {
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
