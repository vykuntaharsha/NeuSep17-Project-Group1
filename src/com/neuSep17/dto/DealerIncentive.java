package com.neuSep17.dto;

import java.util.ArrayList;


public class DealerIncentive {
	private String dealerId;
    private ArrayList<Incentive> incentives = new ArrayList<>();

    public DealerIncentive(String dealerID, ArrayList<Incentive> incentives) {
		this.dealerId = dealerID; this.incentives= incentives;
	}
    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public ArrayList<Incentive> getIncentives() {
        return incentives;
    }

    public void setVehicles(ArrayList<Incentive> incentives) {
        this.incentives = incentives;
    }
}
