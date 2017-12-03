package com.neuSep17.validation;


import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.HashMap;

import com.neuSep17.io.FileReading;


public class DealerValidation {
	
	private FileReading fileReading;

	public DealerValidation(File file) throws FileNotFoundException {
		fileReading = new FileReading(file);		
	}
	//for add
	public void checkDealerID(String dealerID) throws IOException{
		fileReading.checkID(dealerID);			
	}
	
	
/*	public static void main(String[] args) throws IOException {
		//LyricAnalyzer la = new LyricAnalyzer();
		HashMap<String, String> hashMap=new HashMap<String,String>();
		//hashMap.put("test", "value");
		File f= new File("data\\dealers");
		
		DealerValidation dealerValidation = new DealerValidation(f);
		dealerValidation.checkDealerID("gmps-davis-chevrole");
		DealerValidation.checkHashMap(hashMap);	
	}
	*/
}
