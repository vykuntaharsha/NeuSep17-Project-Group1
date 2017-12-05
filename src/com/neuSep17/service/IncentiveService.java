package com.neuSep17.service;

import com.neuSep17.dao.IncentiveImple;
import com.neuSep17.dto.Incentive;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class IncentiveService {
    //sort the incentives by criteria input by the user
    public static ArrayList<Incentive> getSortedIncentives(ArrayList<Incentive> incentives, String criteria, boolean isAsc){
        // UpperCase the Criteria
        String sortCriteria = criteria.toUpperCase();
        // sort the incentives by sorting criteria
        switch (sortCriteria){
            case "ID":
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
                break;
            case "DEALERID":
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
                break;
            case "TITLE":
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
                break;
            case "STARTDATE":
                if(isAsc == true) //sort in ascending order
                    Collections.sort(incentives, new Comparator<Incentive>() {
                        @Override
                        public int compare(Incentive o1, Incentive o2) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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
                break;
            case "ENDDATE":
                if(isAsc == true) //sort in ascending order
                    Collections.sort(incentives, new Comparator<Incentive>() {
                        @Override
                        public int compare(Incentive o1, Incentive o2) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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
                break;
            case "DESCRIPTION":
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
                break;
            case "CASHVALUE":
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
                break;
            case "DISCOUNTCRITERIA": //not working now
                break;
            default:
                System.out.println("this sorting criteria does not exist......");
        }

        return  incentives;
    }

    //search incentives by ID, DealerID, Title, Description, discountCriteria
    public static ArrayList<Incentive> searchIncentives(String criteria, String criteriaValue){
        //call data access object
        IncentiveImple incentiveDAO = new IncentiveImple();
        // UpperCase the Criteria
        String searchCriteria = criteria.toUpperCase();
        // initialize an arraylist of incentive objects
        ArrayList<Incentive> resultIncents = new ArrayList<>();
        // retrieve the incentives by search criteria
        switch (searchCriteria){
            case "ID":
                //get incentive by incentiveID
                resultIncents.add(incentiveDAO.getAIncentive(criteriaValue));
                break;
            case "DEALERID":
                //get incentives for a specific Dealer
                resultIncents = incentiveDAO.getIncentivesForDealer(criteriaValue);
                break;
            case "TITLE":

                break;
            case "DESCRIPTION":

                break;
            case "DISCOUNTCRITERIA":
            default:
                System.out.println("this searching criteria does not exist......");
                break;
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
        incentivesInput = searchIncentives("id", "001");
        for (Incentive i : incentivesInput)
            System.out.println(i.toString());
    }
}
