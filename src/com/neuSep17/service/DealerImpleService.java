package com.neuSep17.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


import com.neuSep17.dao.DealerImple;

import com.neuSep17.dto.Dealer;
import com.neuSep17.validation.DealerValidation;

/*********************************************************
 * This class does not access the dealer file directly.
 * Hence, all the methods in this class must call the corresponding
 * methods provided in the DealerImple class (located in the com.neuSep17.dao folder), 
 * which reads data from the dealer file, and returns them to the calling methods
 *
 *********************************************************/


public class DealerImpleService  {
	private DealerImple dealerImple;
	private DealerValidation dealerValidation;

	public DealerImpleService() throws FileNotFoundException, IOException{
		dealerImple = new DealerImple();
		dealerValidation = new DealerValidation(new File("data" + File.separator+"dealers"));
	}

	public ArrayList<Dealer> getDealers(){
		return dealerImple.getDealers();
	}

	public Dealer getADealer(String dealerID) {
		return dealerImple.getADealer(dealerID);
	}

	public boolean updateDealer(Dealer d){
		return dealerImple.updateDealer(d);
	}

	/********************************************************************
	 * Exceptions:
	 * 		IllegalArgumentException: if dealerID is already added
	 * 		IOException 
	 *
	 */

	public boolean addDealer(Dealer dealer) throws IOException{
		dealerValidation.checkDealerID(dealer.getId());
		return dealerImple.addDealer(dealer);
	}

	public boolean deleteDealer(String dealerID) {
		return dealerImple.deleteDealer(dealerID);

	}
}
