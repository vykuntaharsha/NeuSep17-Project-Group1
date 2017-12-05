package com.neuSep17.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.regex.Pattern;
import com.neuSep17.dto.*;


public class FileReading {
	//private FileReading fileReading;
	//private BufferedReader bufferedReader;
	private String currentLine;
	private String[] words;
	
	
	private BufferedReader bufferedReader;
	public FileReading(File file) throws FileNotFoundException {
		bufferedReader= new BufferedReader(new FileReader(file));
	}
	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}
	//validate dealerID and vehicleID
	public void checkID(String ID) throws IOException {
		while ((currentLine = bufferedReader.readLine()) != null)
		{
			currentLine =currentLine.trim();
			//split vehicle info
			if(currentLine.contains("~")) {
				words=currentLine.split("~+");
			}
			//split dealer
			else {
				words=currentLine.split("\\s+"); 
			}
		
			if(words[0].equalsIgnoreCase(ID)) {				
				throw new IllegalArgumentException("The id "+"\""+ID +"\"" +" is already added.");
			}	
		}	
	}
	
	
// This function is used by the Inventory constructor to populate vehicle data --Zhongfei
    public static ArrayList<Vehicle> readAndGetVehicles(File file) {
        ArrayList<Vehicle> vs = new ArrayList<>();
        Pattern pattern = Pattern.compile("~");
        try (BufferedReader in = new BufferedReader(new FileReader(file));) {
            String line = in.readLine(); // skip first line
            while ((line = in.readLine()) != null) {
                String[] x = pattern.split(line);
                Vehicle v = new Vehicle(x);
                vs.add(v);
            }
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
        return vs;
    }


// This function is used for retrieving data from incentive data file -- Zezhu
	public static  ArrayList<Incentive> getAllIncentives(){

		//Initialize a arraylist to store incentive objects
		ArrayList<Incentive> is = new ArrayList<>();
		//set delimiter as '~'
		Pattern pattern = Pattern.compile("~");
		try (BufferedReader in = new BufferedReader(new FileReader("data/incentives"))) {
			String line = in.readLine(); // skip the first line
			while((line = in.readLine()) != null){
				String[] x = pattern.split(line);
				//adding elements to criteria list
                String[] criterias = x[7].split("\\+"); // create a string array to hold the discount criterias
                ArrayList<String> dCriteria = new ArrayList<>(); //discount criteria arrayList
                for(int i = 0; i < criterias.length; i++)
                    dCriteria.add(criterias[i]);
                //sync the record values with object
                Incentive incent = new Incentive(x[0], x[1], x[2], x[3], x[4], x[5], Float.parseFloat(x[6]), dCriteria);
                is.add(incent);
			}
		} catch (FileNotFoundException f){
			f.printStackTrace();
		} catch (IOException i){
			i.printStackTrace();
		}

		//return an array list of incentives here
        return is;
	} //end of getAllIncentives

}




/*
public void read(File file) throws IOException {
 * private BufferedReader buffReader;
		buffReader = new BufferedReader(new FileReader(file));
		while ((currentLine = buffReader.readLine()) != null) {
			if(currentLine.length()==0) continue;
			currentLine =currentLine.trim();
			words=currentLine.split(" +"); 
			
			
			for(int i=0; i<words.length; i++) {
				add(words[i], i);					
			}
			
			//keep only previous line length after the first line
			previousLineLength+= words.length;					
		}			
	}
*/
