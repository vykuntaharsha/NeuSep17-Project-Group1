package com.neuSep17.dao;

import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Incentive;
import com.neuSep17.dto.Vehicle;

import java.util.ArrayList;

public interface IIncentiveManager {

    //get incentives for a dealer
    ArrayList<Incentive> getIncentivesForDealer(String dealerID);
    //get a specific incentive by passing a unique incentive id
    Incentive getAIncentive(String incentiveID);
    //add a specific incentive
    boolean addAIncentive(Incentive incentive);
    //add a batch of incentives
    boolean addIncentives(ArrayList<Incentive> incentives);
    //update a specific incentive
    boolean updateAIncentive(Incentive incentive);
    //update a batch of incentives
    boolean updateIncentives(ArrayList<Incentive> incentive);
    //delete a specific incentive
    boolean deleteAIncentive(String incentiveID);
    //delete a batch of incentives
    boolean deleteIncentives(ArrayList<Incentive> incentive);
}
