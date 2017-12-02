package com.neuSep17.dao;


import java.util.ArrayList;


import com.neuSep17.dto.Dealer;

public interface IDealerManager {
	ArrayList<Dealer>getDealers();
	Dealer getADealer(String dealerID); 
	
	
	//Use a Dealer object instead of a Hashmap
	boolean updateDealer(Dealer dealer);
	boolean addDealer(Dealer dealer);
	boolean deleteDealer(String dealerID);
}
