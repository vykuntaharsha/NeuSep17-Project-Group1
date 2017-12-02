package com.neuSep17.dao;
import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Inventory;
import com.neuSep17.dto.Vehicle;
import java.util.ArrayList;



public interface IVehicleManager {
	
	ArrayList<Inventory> getInventoryForDealers(ArrayList<Dealer> dealers); //new method per feedback
	Inventory getInventory(String dealerID);	
	Vehicle getAVehicle(String dealerID, String vehicleID);	
	
	Inventory searchInventory(String dealerID, Vehicle vehicle); // searched by year, color, model, etc.., not by vehicleID 
	
	boolean addVehicle(String dealerID, Vehicle v);
	
	boolean updateVehicle(String dealerID, Vehicle v);
	
	boolean deleteVehicle(String dealerID, String vehicleID);
	
}
