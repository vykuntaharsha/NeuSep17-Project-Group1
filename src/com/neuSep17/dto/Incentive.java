package com.neuSep17.dto;

import java.lang.reflect.Field;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;


public class Incentive implements Comparable<Incentive> {
	private String ID;
	private String dealerID;
	private String title;
	private String startDate; //format should be "YYYY-MM-DD"
	private String endDate;//format should be "YYYY-MM-DD"
	private String description;
	private double cashValue;
	
	//NOTE: convert this to String using + sign as delimiter between criteria before save to the file
	private  ArrayList<String> discountCriteria = new ArrayList<>();
	
	//Sorting field
	private String sortingField;
	
	public Incentive() {}
	public Incentive(String ID, String dealerID, String title, String startDate, String endDate, String description,
			double cashValue, ArrayList<String> discountCriteria) {
		this.ID =ID; this.dealerID = dealerID; this.title =title; this.startDate=startDate;
		this.endDate=endDate; this.description=description; this.cashValue=cashValue; 
		this.discountCriteria=discountCriteria;
	}

	public void updateIncentive(Incentive incentive) {
	    this.ID = incentive.getID();
        this.dealerID = incentive.getDealerID();
        this.title = incentive.getTitle();
        this.startDate = incentive.getStartDate();
        this.endDate = incentive.getEndDate();
        this.description = incentive.getDescription();
        this.cashValue = incentive.getCashValue();
        this.discountCriteria = incentive.getDiscountCriteria();
    }
	
	public void setID(String id) {
		ID=id;
	}
    public void setDealerID(String dealerID) { this.dealerID = dealerID; }
    public void setTitle(String t) {
		title=t;
	}
	public void setStartDate(String sD) {
		startDate =sD;
	}
	public void setEndDate(String eD) {
		endDate =eD;
	}
	public void setDescription(String d) {
		description=d;
	}
	public void setCashValue(float cV) {
		cashValue=cV;
	}
	public void setDiscountCriteria(ArrayList<String> dC) {
		discountCriteria=dC;
	}
	
	public void setSortingField(String sortField) {
		sortingField = sortField;
	}

	public String getID() {
		return ID;
	}
    public String getDealerID() { return dealerID; }
    public String getTitle() {
		return title;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getDescription() {
		return description;
	}
	public double getCashValue() {
		return cashValue;
	}
	public ArrayList<String> getDiscountCriteria() {
		return discountCriteria;
	}
	
	
	
	//Call this  method to sort --Nhat T.
	public void sortBy(ArrayList<Incentive> incentives, String sortingField) {
     	
     	for(Incentive incentive : incentives) {
     		incentive.setSortingField(sortingField);
     	}
     	Collections.sort(incentives); 
     }
	
	//override this method from the Comparable interface --Nhat T.
	 @Override
     public int compareTo(Incentive v) { 
     	String s1 = getValueToSort(this);
     	String s2 = getValueToSort(v);
     	String decimalPattern = "([0-9]*)\\.([0-9]*)";
     	String datePattern ="([0-9]{2})-([0-9]{2})-([0-9]{4})";
     	
     	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
     	     	
     	if(Pattern.matches(decimalPattern, s1)) {     	
     		return (int) (Double.parseDouble(s1) - Double.parseDouble(s2));          		
     	}
     	else if(s1.matches(datePattern)) {//this is either start or end date
     		Date firstDate = null;
     		Date secondDate = null;
			try {
				firstDate = sdf.parse(s1);
				secondDate = sdf.parse(s2);
			} catch (ParseException e) {				
				e.printStackTrace();
			} 
     		return firstDate.compareTo(secondDate);
     	}
     	
     	else {     		      	
     		return s1.compareToIgnoreCase(s2);     	
     	}     	       	    	
     }
     
	 //Used by the CompareTo method --Nhat T.
     private String getValueToSort(Incentive c) {
     	String valueToCompare="";
     	Field[] fields = c.getClass().getDeclaredFields();
     	
     	for(Field f :fields) {// check fields in the class against the sorting field     		
     		
     		if(f.getName().equalsIgnoreCase(sortingField)) {
     			f.setAccessible(true); //enable field accessible
 	
     			try {//get field value to compare
     				
     				if(f.getType() ==double.class) {
     					valueToCompare = new Double((Double)f.get(c)).toString();
     				}
     				
     				else {
     					valueToCompare= (String)f.get(c);
     				}						
     			} 
     			catch (IllegalArgumentException | IllegalAccessException e) {						
						e.printStackTrace();
				}
     		}
     	}
     	return valueToCompare;
     }  
     
	
	@Override
    public String toString(){
	    //create a string array to store criteria
	    StringBuilder sb = new StringBuilder();
        //for loop to form the stringBuilder
        for(String s : discountCriteria){
            sb.append(s);
            sb.append("+");
        }
        sb.deleteCharAt(sb.length()-1); //delete the last + sign

	    return String.format("%s~%s~%s~%s~%s~%s~%.2f~%s\n",this.ID, this.dealerID, this.title, this.startDate, this.endDate, this.description, this.getCashValue(),sb.toString());
    }	
}
