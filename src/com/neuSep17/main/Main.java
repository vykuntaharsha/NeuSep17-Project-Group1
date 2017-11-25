package com.neuSep17.main;

import com.neuSep17.dao.Incentive;
import com.neuSep17.io.IncentivesDatabaseManager;
import com.neuSep17.ui.AddIncentivesFrame;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        AddIncentivesFrame addIncentives = new AddIncentivesFrame();
        String incentiveDBFilePath = System.getProperty("user.dir")+"/data/incentives.db";

        try {
            IncentivesDatabaseManager idm = new IncentivesDatabaseManager(incentiveDBFilePath);
            idm.add(new Incentive());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}