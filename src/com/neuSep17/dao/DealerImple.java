package com.neuSep17.dao;


import java.io.File;
import java.io.IOException;
import java.util.*;

import com.neuSep17.dto.Dealer;
import com.neuSep17.io.DealerFileReading;
import com.neuSep17.io.DealerFileWriting;
import com.neuSep17.io.FileWriting;

//provide implementations for IDealer interface
//and access dealer files

public class DealerImple implements IDealerManager {
	Map<String, Dealer> map;


	public DealerImple() throws IOException {
		DealerFileReading reading = new DealerFileReading(new File("data" + File.separator +"dealers"));
		map = reading.loading();
	}
	@Override
	public ArrayList<Dealer> getDealers(){
		ArrayList<Dealer> arrDealer = null;
		arrDealer = new ArrayList<>(map.values());

		return arrDealer;
	}
	@Override
	public Dealer getADealer(String dealerID) {
		Dealer dealer = null;
		dealer = map.get(dealerID);
		return dealer;
	}
	
	@Override
	public boolean updateDealer(Dealer dealer) {
		boolean isSuccess =false;
		if (!map.containsKey(dealer.getId())) {
			return isSuccess;
		}

		map.put(dealer.getId(), dealer);
		saveFile();
		return isSuccess;
	}
	@Override
	public boolean addDealer(Dealer dealer){
		boolean isSuccess =false;

		if (map.containsKey(dealer.getId())) {
			return isSuccess;
		}

		String result = "\n" + dealer.getId() + "\ten_US\t" + dealer.getUrl()
						+ "\t" + dealer.getEmailId() + "\t" + dealer.getContactNumber();
		DealerFileWriting writing = null;
		try {
			writing = new DealerFileWriting(new File("data" + File.separator + "dealers"), true);
			writing.getBufferedWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writing.getBufferedWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isSuccess;
	}
	@Override
	public boolean deleteDealer(String dealerID) {
		boolean isSuccess = false;
		String str = "";
		for (String m : map.keySet()) {
			if (m.equals(dealerID)) {
				str = m;
				isSuccess = true;
			}
		}

		map.remove(str);
		saveFile();
		return isSuccess;
	}

	private void saveFile() {
		try {
			DealerFileWriting writing = new DealerFileWriting(new File("data" + File.separator + "dealers"), false);
			writing.saveToFile(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
