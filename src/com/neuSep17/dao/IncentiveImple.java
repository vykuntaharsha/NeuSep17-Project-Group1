package com.neuSep17.dao;

import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Incentive;
import com.neuSep17.io.FileReading;
import com.neuSep17.io.FileWriting;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class IncentiveImple implements IIncentiveManager {

    //An arrayList to store all the incentive object from data file 'data/incentives'
    ArrayList<Incentive>  allIncentives = new ArrayList<>();

    public IncentiveImple(){
        //retrieving all the incentives despite of dealerID
        allIncentives = FileReading.getAllIncentives();
    }

    @Override
    public ArrayList<Incentive> getIncentivesForDealer(String dealerID) {
        //create an arrayList of Incentive for a specific dealer
        ArrayList<Incentive> dealerIncentives = new ArrayList<>();
        //for loop to filter out the incentive which is not belongs to this dealer
        for(Incentive i : allIncentives){
            if(i.getDealerID().equals(dealerID))
                dealerIncentives.add(i);
        }
        return dealerIncentives;
    }

    @Override
    public boolean updateAIncentive(Incentive incentive) {
        //initialize the update status
        boolean isSuccess = false;

        //for loop to get that specific incentive to update
        for(Incentive i : allIncentives){
            if(i.getID().equals(incentive.getID())) {
//                System.out.println("Found one matched!!!");
                i.updateIncentive(incentive);
                isSuccess = true;
            }
        }
        //overwrite the list of incentives
        for(Incentive i : allIncentives)
            System.out.println(i.toString());
        FileWriting.writeIncentiveToFile(allIncentives);
        //return status to see if updating succeed
        return isSuccess;
    }
    @Override
    public boolean updateIncentives(ArrayList<Incentive> incentives){
        //initialize the update status
        boolean isSuccess = false;

        //for loop to get that an incentive to update
        for(Incentive ni : incentives ) {
            for (Incentive i : allIncentives) {
                if (i.getID().equals(ni.getID())) {
                    //System.out.println("Found one matched!!!");
                    i.updateIncentive(ni);
                    isSuccess = true;
                }
            }
        }
        //overwrite the list of incentives
//        for(Incentive i : allIncentives)
//            System.out.println(i.toString());
        FileWriting.writeIncentiveToFile(allIncentives);
        //return status to see if updating succeed
        return isSuccess;
    }

    @Override
    public boolean addAIncentive(Incentive incentive) {
        //check if incentive is null
        if(incentive == null)
            return false;
        //add new incentive to data file
        FileWriting.writeAIncentiveToFile(incentive);
        //return true for showing adding process succeed
        return true;
    }

    @Override
    public boolean addIncentives(ArrayList<Incentive> incentives){
        //check if the incentives list is null
        if(incentives == null){
            return false;
        }
        //add incentives to data file
        for(Incentive i : incentives){
            FileWriting.writeAIncentiveToFile(i);
        }
        return true;
    }

    @Override
    public boolean deleteAIncentive(String incentiveID) {
        //initialize the delete status
        boolean isSuccess = false;
        //initialize an array list to store the filtered incentives
        ArrayList<Incentive> newIncentives = new ArrayList<>();
        //for loop to get that specific incentive to delete
        for (int i = 0; i < allIncentives.size(); ++i){
            if(allIncentives.get(i).getID().equals(incentiveID))
                allIncentives.remove(i);
        }
        isSuccess = true;
        FileWriting.writeIncentiveToFile(allIncentives);

        //return status to see if delete succeed
        return isSuccess;
    }

    @Override
    public boolean deleteIncentives(ArrayList<Incentive> incentives){
        //initialize the delete status
        boolean isSuccess = false;
        if(incentives == null){
            return isSuccess;
        }

        for(Incentive ni : incentives){
            for(int i = 0; i < allIncentives.size(); ++i){
                if(allIncentives.get(i).getID().equals(ni.getID()))
                    allIncentives.remove(i);
            }
        }
        isSuccess = true;
        //write the new list of incentives to the data file
        FileWriting.writeIncentiveToFile(allIncentives);

        return isSuccess;
    }

    @Override
    public Incentive getAIncentive(String incentiveID) {
        //for loop to get that unique incentive
        for(Incentive i : allIncentives){
            if(i.getID().equals(incentiveID))
                return i;
        }
        //else return null
        return null;
    }

    //testing main function starts here
    public static void main(String[] args){
        IncentiveImple test = new IncentiveImple();

//        testing for get incentives for a dealer
        ArrayList<Incentive> incents;
        incents = test.getIncentivesForDealer("gmps-brown-wood");
        for (Incentive i : incents)
            System.out.println(String.format("ID - %s, DealerID - %s, Description - %s, CashValue - %.2f", i.getID(), i.getDealerID(), i.getDescription(), i.getCashValue()));

        //first testing ends here

        //testing update fuction
        ArrayList<String> dCriteria = new ArrayList<>(Arrays.asList("Toyota", "Corolla", "2015"));
        Incentive incentive1 = new Incentive("002","gmps-bresee","Thanksgiving","2016-07-01","2019-08-02","big deal for toyota", 4000, dCriteria);
        test.updateAIncentive(incentive1);
        //update testing ends here

        //testing delete function
        //test.deleteAIncentive("002");
        //delete update ends here

        //testing add function
        Incentive incentive2 = new Incentive("006","gmps-bresee","Labor Day","2016-03-01","2019-02-31","big deal for Audi", 1100, dCriteria);
        test.addAIncentive(incentive2);
        //add testing ends here
    } //testing main function ends here

}
