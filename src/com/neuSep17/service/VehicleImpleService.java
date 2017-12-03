package com.neuSep17.service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.neuSep17.dao.VehicleImple;
import com.neuSep17.dto.Category;
import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Inventory;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.validation.VehicleValidation;


/*********************************************************
 * This class does not access gmps-**** files directly.
 * Hence, all the methods in this class must call the corresponding
 * methods provided in the VehicleImple class (located in the com.neuSep17.dao folder),
 * which reads data from the gmps-***files, and returns them to the calling methods
 *
 *********************************************************/

//provide implementations for IVehicle interface

public class VehicleImpleService {

	private Dealer dealer;
	private VehicleImple vehicleImple;

	public VehicleImpleService() {};
	public VehicleImpleService(Dealer d) {
		this.dealer =d;
		vehicleImple = new VehicleImple();
	}

	public ArrayList<Inventory> getInventoryForDealers(ArrayList<Dealer> dealers){
		return vehicleImple.getInventoryForDealers(dealers);
	}

	public Inventory getInventory(){
		return vehicleImple.getInventory(dealer.getId());
	}

	public Vehicle getAVehicle(String vehicleID) {
		return vehicleImple.getAVehicle(dealer.getId(), vehicleID);
	}

	// searched by year, color, model, etc.., not by vehicleID
	public Inventory searchInventory(Vehicle vehicle){
		return vehicleImple.searchInventory(dealer.getId(), vehicle);
	}

	public boolean addVehicle(Vehicle v) {
		return vehicleImple.addVehicle(dealer.getId(), v);
	}

	public boolean updateVehicle(String dealerID, Vehicle v) {
		return vehicleImple.updateVehicle(dealer.getId(), v);
	}

	public boolean deleteVehicle(String vehicleID) {
		return vehicleImple.deleteVehicle(dealer.getId(), vehicleID);
	}

	/* The sortingField must be one of the instance variables of the Vehicle class
	 * (category, year, make, etc...). Case insensitive
	 *
	 * IllegalArgumentException: if the sortingField does not match the Vehicle instance field
	 *
	 */
	// Nhat T.
	public void sortBy(Collection<Vehicle> v, String sortingField) {
		for(Vehicle vehicle : v) {
			VehicleValidation.checkSortingField(vehicle, sortingField);
			vehicle.setSortingField(sortingField);
		}
		Collections.sort((List<Vehicle>) v);
	}

}