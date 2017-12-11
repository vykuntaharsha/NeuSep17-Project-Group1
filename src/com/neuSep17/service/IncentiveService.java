package com.neuSep17.service;

import com.neuSep17.dao.IncentiveImple;
import com.neuSep17.dto.Incentive;
import com.neuSep17.io.FileReading;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//author: Zezhu Jin
//Team #: 6
//Team Lead: Nhat

public class IncentiveService {
    //sort the incentives by incentiveID
    public static ArrayList<Incentive> sortIncentivesByIncentID(ArrayList<Incentive> incentives, boolean isAsc){
        if (isAsc == true)
            //sort in ascending order
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    return Integer.parseInt(o1.getID()) < Integer.parseInt(o2.getID()) ? -1 : o1.getID() == o2.getID() ? 0 : 1 ;
                }
            });
        else //sort in descending order
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    return Integer.parseInt(o1.getID()) < Integer.parseInt(o2.getID()) ? 1 : o1.getID() == o2.getID() ? 0 : -1 ;
                }
            });

        return incentives;
    }

    //sort the incentives by dealerID
    public static ArrayList<Incentive> sortIncentivesByDealerID(ArrayList<Incentive> incentives, boolean isAsc){
        if (isAsc == true) //sort in ascending order
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    int compareInt = o1.getDealerID().compareTo(o2.getDealerID());
                    if(compareInt < 0) return -1;
                    else if (compareInt > 0) return 1;
                    else return 0; //if they are equal
                }
            });
        else
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    int compareInt = o1.getDealerID().compareTo(o2.getDealerID());
                    if(compareInt < 0) return 1;
                    else if (compareInt > 0) return -1;
                    else return 0; //if they are equal
                }
            });
        return incentives;
    }

    //sort the incentives by incentiveID
    public static ArrayList<Incentive> sortIncentivesByTitle(ArrayList<Incentive> incentives, boolean isAsc){
        if (isAsc == true) //sort in ascending order
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    int compareInt = o1.getTitle().compareTo(o2.getTitle());
                    if(compareInt < 0) return -1;
                    else if (compareInt > 0) return 1;
                    else return 0; //if they are equal
                }
            });
        else
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    int compareInt = o1.getTitle().compareTo(o2.getTitle());
                    if(compareInt < 0) return 1;
                    else if (compareInt > 0) return -1;
                    else return 0; //if they are equal
                }
            });

        return incentives;
    }

    //sort the incentives by StartDate
    public static ArrayList<Incentive> sortIncentivesByStartDate(ArrayList<Incentive> incentives, boolean isAsc){
        if(isAsc == true) //sort in ascending order
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = sdf.parse(o1.getStartDate());
                        Date date2 = sdf.parse(o2.getStartDate());

                        int compareDate = date1.compareTo(date2);
                        if (compareDate == 0) return 0;
                        else if (compareDate < 0) return -1;
                        else return 1;
                    } catch (ParseException p){
                        System.out.println("Incorrect date-format record found");
                    }
                    return 0;
                }
            });
        else
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = sdf.parse(o1.getStartDate());
                        Date date2 = sdf.parse(o2.getStartDate());

                        int compareDate = date1.compareTo(date2);
                        if (compareDate == 0) return 0;
                        else if (compareDate < 0) return 1;
                        else return -1;
                    } catch (ParseException p){
                        System.out.println("Incorrect date-format record found");
                    }
                    return 0;
                }
            });

        return incentives;
    }

    //sort the incentives by EndDate
    public static ArrayList<Incentive> sortIncentivesByEndDate(ArrayList<Incentive> incentives, boolean isAsc){
        if(isAsc == true) //sort in ascending order
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = sdf.parse(o1.getEndDate());
                        Date date2 = sdf.parse(o2.getEndDate());
                        System.out.println(date1.toString() + date2.toString());
                        int compareDate = date1.compareTo(date2);
                        if (compareDate == 0) return 0;
                        else if (compareDate < 0) return -1;
                        else return 1;
                    } catch (ParseException p){
                        System.out.println("Incorrect date-format record found");
                    }
                    return 0;
                }
            });
        else
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = sdf.parse(o1.getEndDate());
                        Date date2 = sdf.parse(o2.getEndDate());

                        int compareDate = date1.compareTo(date2);
                        if (compareDate == 0) return 0;
                        else if (compareDate < 0) return 1;
                        else return -1;
                    } catch (ParseException p){
                        System.out.println("Incorrect date-format record found");
                    }
                    return 0;
                }
            });

        return incentives;
    }

    //sort the incentives by Description
    public static ArrayList<Incentive> sortIncentivesByDescription(ArrayList<Incentive> incentives, boolean isAsc){
        if(isAsc == true)
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    int compareInt = o1.getDescription().compareTo(o2.getDescription());
                    if(compareInt < 0) return -1;
                    else if (compareInt > 0) return 1;
                    else return 0; //if they are equal
                }
            });
        else //descending sort order
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    int compareInt = o1.getDescription().compareTo(o2.getDescription());
                    if(compareInt < 0) return 1;
                    else if (compareInt > 0) return -1;
                    else return 0; //if they are equal
                }
            });

        return incentives;
    }

    //sort the incentives by cashValue
    public static ArrayList<Incentive> sortIncentivesByCashValue(ArrayList<Incentive> incentives, boolean isAsc){
        if (isAsc == true)
            //sort in ascending order
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    return o1.getCashValue() < o2.getCashValue() ? -1 : o1.getCashValue() == o2.getCashValue() ? 0 : 1 ;
                }
            });
        else //sort in descending order
            Collections.sort(incentives, new Comparator<Incentive>() {
                @Override
                public int compare(Incentive o1, Incentive o2) {
                    return o1.getCashValue() < o2.getCashValue() ? 1 : o1.getCashValue() == o2.getCashValue() ? 0 : -1 ;
                }
            });

        return incentives;

    }


    //search incentives by any criterias (except cashValue) from the input incentives
    public static ArrayList<Incentive> searchIncentives(ArrayList<Incentive> inputIncentives,  String criteriaValue){
        //initialize the filtered arraylist
        ArrayList<Incentive> resultIncents = new ArrayList<>();
        String str = criteriaValue.toUpperCase();

        //for loop to filter incentives
        for (Incentive incent : inputIncentives){
            HashSet<String> dic = new HashSet<>();
            dic.add(incent.getDealerID().toUpperCase());
            dic.add(incent.getID().toUpperCase());
            dic.add(incent.getTitle().toUpperCase());
            dic.add(incent.getStartDate().toUpperCase());
            dic.add(incent.getEndDate().toUpperCase());
            dic.add(incent.getDescription().toUpperCase());
            for(String s : incent.getDiscountCriteria()){
                dic.add(s.toUpperCase());
            }
            boolean[] dp = new boolean[str.length()+1];
            dp[0] = true;
            for(int i = 1; i <= str.length(); i++){
                for (int j = 0; j < i; j++){
                    if(dic.contains(str.substring(j,i))){
                        dp[i] = true;
                    }
                }
            }

            if(dp[str.length()]){
                resultIncents.add(incent);
            }
        }

        return resultIncents;
    }

    //update an incentive
    public static void updateAnIncentive(Incentive incent){
        //call data access object
        IncentiveImple incentiveDAO = new IncentiveImple();
        //call the update method
        incentiveDAO.updateAIncentive(incent);
    }

    //update a batch of incentives
    public static void updateIncentives(ArrayList<Incentive> incentives){
        //call data access object
        IncentiveImple incentiveDAO = new IncentiveImple();
        //call the batch update method
        incentiveDAO.updateIncentives(incentives);
    }

    //delete an incentive
    public static void deleteAnIncentive(String incentID){
        //call data access object
        IncentiveImple incentiveDAO = new IncentiveImple();
        //call the delete method
        incentiveDAO.deleteAIncentive(incentID);
    }

    //delete incentives
    public static void deleteIncentives(ArrayList<Incentive> incentives){
        //call data access object
        IncentiveImple incentiveDAO = new IncentiveImple();
        //call the batch delete method
        incentiveDAO.deleteIncentives(incentives);
    }

    //get incentives from a specific dealer
    public static ArrayList<Incentive> dealerIncentives(String dealerID){
        //call data access object
        IncentiveImple incentiveDAO = new IncentiveImple();
        //return all incentives form all dealer if the dealerID input is null or empty
        if(dealerID.isEmpty() || dealerID == null)
            return FileReading.getAllIncentives();
        //call the get method
        return incentiveDAO.getIncentivesForDealer(dealerID);
    }

    //add an incentive to the data file
    public static void addAnIncentive(Incentive incentive){
        //call data access object
        IncentiveImple incentiveDAO = new IncentiveImple();
        //call the add method
        incentiveDAO.addAIncentive(incentive);
    }

    //add a batch of incentives to the data file
    public static void addIncentives(ArrayList<Incentive> incentives){
        //call data access object
        IncentiveImple incentiveDAO = new IncentiveImple();
        //call the add method
        incentiveDAO.addIncentives(incentives);
    }

    //get an specific incentive by ID
    public static Incentive getAnIncentive(String incentID){
        //call data access object
        IncentiveImple incentiveDAO = new IncentiveImple();
        //call the get method
        return incentiveDAO.getAIncentive(incentID);
    }




    //service unit test function starts here
    public static void main(String[] args){
        IncentiveImple incentiveImple = new IncentiveImple();
        ArrayList<Incentive> incentivesInput = new ArrayList<>();
//        incentivesInput = FileReading.getAllIncentives();
//        getSortedIncentives(incentivesInput, "startdate", true);
//        for (Incentive i : incentivesInput)
//            System.out.print(i.toString());

    }
}
