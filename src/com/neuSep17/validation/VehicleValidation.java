package com.neuSep17.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;

import com.neuSep17.dto.Vehicle;
import com.neuSep17.io.FileReading;

public class VehicleValidation {
	private FileReading fileReading;

	public VehicleValidation(File file) throws FileNotFoundException {
		fileReading = new FileReading(file);
	}
	//for add
	public void checkVehicleID(String vehicleID) throws IOException {
		fileReading.checkID(vehicleID);
	}
	//check sortingField
	public static void checkSortingField(Vehicle v, String sortingField){
		int count=0;
		Field[] fields = v.getClass().getDeclaredFields();
		for(Field field :fields) {
			if(field.getName().equalsIgnoreCase(sortingField)) {
				count++;
			}
		}
		if(count ==0) {
			throw new IllegalArgumentException("Invalid sorting field");
		}

	}
}