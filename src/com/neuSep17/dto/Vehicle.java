package com.neuSep17.dto;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.neuSep17.dao.PictureManager;
import com.neuSep17.dao.PropertyManager;

import java.awt.Image;
import java.lang.reflect.Field;

public class Vehicle implements Comparable<Vehicle> {
  
    private String id;
    private String webId;
    private Category category;
    private int year;
    private String make;
    private String model;
    private String trim;       
    private String bodyType;
    private double price;
    private URL photoUrl;

    private String vin;
    private String entertainment;
    private String interiorColor;
    private String exteriorColor;
    private String fuelType;
    private String engine;
    private String transmission;
    private String battery;
    private String optionalFeatures;

    //add discount filed for conveniencly used by InventoryList;
    private double discount;
    
    private String sortingField;
       
        
        //Use this constructor when wants to search all search vehicle by category, year..., no id needed
        public Vehicle() {
		}
        public Vehicle(String[] arr){
        	this.id = arr[0];
            this.webId = arr[1];
            this.category = Category.getCategory(arr[2].toLowerCase());
            this.year = Integer.parseInt(arr[3]);
            this.make = arr[4];
            this.model = arr[5];
            this.trim = arr[6];
            this.bodyType = arr[7];
            this.price = Double.parseDouble(arr[8]);
            try {
                this.photoUrl = new URL(arr[9]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            this.vin = arr[10];
            this.entertainment = arr[11];
            this.interiorColor = arr[12];
            this.exteriorColor = arr[13];
            this.fuelType = arr[14];
            this.engine = arr[15];
            this.transmission = arr[16];
            this.battery = arr[17];
            this.setOptionalFeatures(arr[18]);
        }
        public void setID(String id) {
        	this.id=id;
        }
        public void setWebID(String webId) {
        	this.webId=webId;
        }
        public void setYear(int year) {
        	this.year=year;
        }
        public void setMake(String make) {
        	this.make=make;
        }
        public void setModel(String model) {
        	this.model=model;
        }
        public void setTrim(String trim) {
        	this.trim=trim;
        }
        public void setCategory(Category c) {
        	category =c;
        }
        public void setBodyType(String type) {
        	this.bodyType=type;
        }
        public void setPrice(double price) {
        	this.price=price;
        }
        public void setPhotoURL(URL url) {
        	this.photoUrl=url;
        }
        
        public void setSortingField(String sF) {
        	sortingField=sF;
        }
        public String getID() {
        	return id;
        }
        public String getWebID() {
        	return webId;
        }
        public int getYear() {
        	return year;
        }
        public String getMake() {
        	return make;
        }
        public String getModel() {
        	return model;
        }
        public String getTrim() {
        	return trim;
        }
        public Category getCategory() {
        	return category;
        }
        public String getBodyType() {
        	return bodyType;
        }
        public double getPrice() {
        	return price;
        }
        public URL getPhotoURL() {
        	return photoUrl;
        }
        
        //get the actual image from the photo library or the Internet (team 2: Bin Shi)
        //Note: if this image is not valid, return null instead.
        public Image getPhoto(){
            return PictureManager.getVehiclePhoto(photoUrl);
        }

    public String getVin() {
        return vin;
    }
    public String getEntertainment() {
        return entertainment;
    }
    public String getInteriorColor() {
        return interiorColor;
    }
    public String getExteriorColor() {
        return exteriorColor;
    }
    public String getFuelType() {
        return fuelType;
    }
    public String getEngine() {
        return engine;
    }
    public String getTransmission() {
        return transmission;
    }
    public String getBattery() {
        return battery;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }
    public void setEntertainment(String entertainment) {
        this.entertainment = entertainment;
    }
    public void setInteriorColor(String interiorColor) {
        this.interiorColor = interiorColor;
    }
    public void setExteriorColor(String exteriorColor) {
        this.exteriorColor = exteriorColor;
    }
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
    public void setBattery(String battery) {
        this.battery = battery;
    }
        
        public String getOptionalFeatures() {
        return optionalFeatures;
    }
    public void setOptionalFeatures(String optionalFeatures) {
        this.optionalFeatures = optionalFeatures;
    }
        ////override this method from the Comparable interface --Nhat T.
        @Override
        public int compareTo(Vehicle v) { 
        	
        	String s1 = getValueToSort(this);
        	String s2 = getValueToSort(v);
        	String decimalPattern = "([0-9]*)\\.([0-9]*)";
        	String integerPattern = ("^\\d+$");
        	if(Pattern.matches(decimalPattern, s1)) {        	
        		return (int) (Double.parseDouble(s1) - Double.parseDouble(s2)); 
        	}
        	else if(Pattern.matches(integerPattern, s1)) {        		
        		return (int) (Integer.parseInt(s1) - Integer.parseInt(s2));
        	}
        	else {
        		return s1.compareToIgnoreCase(s2);        	
        	}        	       	    	
        }
        
      //Used by the CompareTo method --Nhat T.
        private String getValueToSort(Vehicle v) {
        	String valueToCompare="";
        	Field[] fields = v.getClass().getDeclaredFields();
        	        	
        	for(Field f :fields) {// check fields in the class against the sorting field
        	
        		if(f.getName().equalsIgnoreCase(sortingField)) {
        			f.setAccessible(true); //enable field accessible
        			
        			try {//get field value to compare
        				if(f.getType() ==int.class) {
        					valueToCompare = new Integer((Integer)f.get(v)).toString();
        				}
        				else if(f.getType() ==double.class) {
        					valueToCompare = new Double((Double)f.get(v)).toString();
        				}
        				else if(f.getType()==Category.class) { 
        					valueToCompare = ((Category)f.get(v)).toString();
        				} 
        				else {
        					valueToCompare= (String)f.get(v);
        				}
						
					} catch (IllegalArgumentException | IllegalAccessException e) {						
						e.printStackTrace();
					}
        		}
        	}
        	return valueToCompare;
        } 
        
    //Add Discount Info for InventoryList
    public double getDiscount() {
        return discount;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
