package com.neuSep17.dao;
        
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;


import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Inventory;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.io.FileReading;
import com.neuSep17.io.FileWriting;


//THIS CLASS IMPLEMENTS LOGIC TO ACCESS gmps-****-*** files

public class VehicleImple implements IVehicleManager {
	FileReading fileReading;
	FileWriting fileWriting;
	String prefix = "data/"; // directory path

	//private Vehicle vehicle;
	private HashMap<String, Inventory> allInventory = new HashMap<String, Inventory>();

	public VehicleImple() {
    }

	public VehicleImple(File filepath) {
		File folder = new File("data");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains("gmps-")) {
				String dealer = listOfFiles[i].getName();
				Inventory inventory = new Inventory(new File(filepath + "/" + dealer));
				allInventory.put(dealer, inventory);
			}
		}
	}
    
  	//new method per feedback
	@Override
	public ArrayList<Inventory> getInventoryForDealers(ArrayList<Dealer> dealers) {
		ArrayList<Inventory> inventories = new ArrayList<Inventory>();
		for (Dealer dealer : dealers) {
			inventories.add(allInventory.get(dealer.getId()));
		}
		return inventories;
	}

	@Override
	public Inventory getInventory(String dealerID) {
		return allInventory.get(dealerID);
	}

	@Override
	public Vehicle getAVehicle(String dealerId, String vehicleID) {
		Collection<Vehicle> vehs = allInventory.get(dealerId).getVehicles();
		Vehicle veh = new Vehicle();
		for (Vehicle v : vehs)
			if (v.getID().equals(vehicleID)) {
				veh = v;
				break;
			}
		return veh;
	}

	@Override
	public Inventory searchInventory(String dealerID, Vehicle vehicle) {
		ArrayList<Vehicle> results = new ArrayList<>();
		Inventory inventory = allInventory.get(dealerID);

		// When ID or WebId is specified in the search criteria, only the matching entry is returned
		if (vehicle.getID() != null) {
			results.add(getAVehicle(dealerID, vehicle.getID()));
			return new Inventory(dealerID, results);
		}

		if (vehicle.getWebID() != null) {
			results.add(getAVehicle(dealerID, vehicle.getWebID()));
			return new Inventory(dealerID, results);
		}
		// Search results will include entries that match all the specified criteria. Omitted fields are considered boolean OR
		// price field is assumed to the max price
		for (Vehicle v : inventory.getVehicles()) {

			if (vehicle.getCategory() != null && !(v.getCategory() == (vehicle.getCategory())))
				continue;

			if (vehicle.getYear() != 0 && v.getYear() != vehicle.getYear())
				continue;

			if (vehicle.getMake() != null && !(v.getMake().equals(vehicle.getMake())))
				continue;

			if (vehicle.getModel() != null && !(v.getModel().equals(vehicle.getModel())))
				continue;

			if (vehicle.getTrim() != null && !(v.getTrim().equals(vehicle.getTrim())))
				continue;
			if (vehicle.getBodyType() != null && !(v.getBodyType().equals(vehicle.getBodyType())))
				continue;

			if (vehicle.getPrice() != 0 && v.getPrice() > vehicle.getPrice())
				continue;

			if (vehicle.getVin() != null && !v.getVin().equals(vehicle.getVin()))
				continue;

			if (vehicle.getEntertainment() != null && !v.getEntertainment().equals(vehicle.getEntertainment()))
				continue;

			if (vehicle.getInteriorColor() != null && !v.getInteriorColor().equals(vehicle.getInteriorColor()))
				continue;

			if (vehicle.getExteriorColor() != null && !v.getExteriorColor().equals(vehicle.getExteriorColor()))
				continue;

			if (vehicle.getFuelType() != null && !v.getFuelType().equals(vehicle.getFuelType()))
				continue;

			if (vehicle.getEngine() != null && !v.getEngine().equals(vehicle.getEngine()))
				continue;

			if (vehicle.getTransmission() != null && !v.getTransmission().equals(vehicle.getTransmission()))
				continue;

			if (vehicle.getBattery() != null && !v.getBattery().equals(vehicle.getBattery()))
				continue;

			results.add(v);
		}
		return new Inventory(dealerID, results);
	}


	@Override
	public boolean addVehicle(String dealerID, Vehicle v) {
		boolean isSuccess = false;

		try {
			File file = new File(prefix); // directory path
			String[] files = file.list();
			for (String f : files) {
			    // Team: Lu Niu fix a bug caused by contains.
				if (f.equalsIgnoreCase(dealerID)) {
					fileReading = new FileReading(new File(prefix + f));
					fileReading.checkID(v.getID());

					fileWriting = new FileWriting(new File(prefix + f), true);
					BufferedWriter bw = fileWriting.getBufferedWriter();

					StringBuilder sb = new StringBuilder();
					sb.append(v.getID()).append("~");
					sb.append(v.getWebID()).append("~");
					sb.append(v.getCategory()).append("~");
					sb.append(v.getYear()).append("~");
					sb.append(v.getMake()).append("~");
					sb.append(v.getModel()).append("~");
					sb.append(v.getTrim()).append("~");
					sb.append(v.getBodyType()).append("~");
					sb.append(v.getPrice()).append("~");
					sb.append(v.getPhotoURL()).append("~");
					sb.append(v.getVin()).append("~");
					sb.append(v.getEntertainment()).append("~");
					sb.append(v.getInteriorColor()).append("~");
					sb.append(v.getExteriorColor()).append("~");
					sb.append(v.getFuelType()).append("~");
					sb.append(v.getEngine()).append("~");
					sb.append(v.getTransmission()).append("~");
					sb.append(v.getBattery()).append("~");
					
					// Fixed a bug caused by parsing vehicle. Team 2: Lu Niu.
					sb.append(v.getOptionalFeatures());
					bw.write("\n" + sb.toString());

					bw.close();
					isSuccess = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return isSuccess;
	}
	
	@Override
	public boolean updateVehicle(String dealerID, Vehicle v) {
		if(deleteVehicle(dealerID, v.getID()) && addVehicle(dealerID, v)) {
			return true;
		}

		return false;
	}
	@Override
	public boolean deleteVehicle(String dealerID, String vehicleID) {
		boolean isSuccess=false;

		try {
			File file = new File(prefix); // directory path
			String[] files = file.list();
			for (String f : files) {
				if (f.equalsIgnoreCase(dealerID)) {
					fileReading = new FileReading(new File(prefix + f));

					// read from vehicle files to HashMap
					HashMap<DealerVehiclePair, String> tmpMap = new HashMap<>();
					BufferedReader br = fileReading.getBufferedReader();
					String line = br.readLine(); // ignore the first line of vehicle txt files
					while ((line = br.readLine()) != null) {
						String[] words = line.split("~");
						if (words[0].equals(vehicleID)) continue;
						tmpMap.put(new DealerVehiclePair(words[1], words[0]), line);
					}
					br.close();

					// write from HashMap to vehicle files
					fileWriting = new FileWriting(new File(prefix + f), false);
					writeToFiles(tmpMap);

					isSuccess = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return isSuccess;
	}

	private void writeToFiles(Map<DealerVehiclePair, String> tmpMap) throws IOException {
		BufferedWriter bw = fileWriting.getBufferedWriter();
		StringBuilder sb = new StringBuilder("id~webId~category~year~make~model~trim~type~price~photo~vin~entertainment~interiorColor~exteriorColor~fuelType~engine~transmission~battery");
		
		// Team 2: Lu Niu, fixed a bug caused by new line. 
		for (String s : tmpMap.values()) {
			sb.append("\n" + s);
		}
		bw.write(sb.toString());
		bw.close();
	}

	private class DealerVehiclePair {
		String dealerID;
		String vehicleID;

		DealerVehiclePair(String dealerID, String vehicleID) {
			this.dealerID = dealerID;
			this.vehicleID = vehicleID;
		}
	}
	
	//call this to sort
    public void sortBy(ArrayList<Vehicle> v, String sortingField) {
    	for(Vehicle vehicle : v) {
    		vehicle.setSortingField(sortingField);
    	}
    	Collections.sort(v);
    }

}
