package com.neuSep17.io;

import com.neuSep17.dto.Incentive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileWriting {
	private BufferedWriter bufferedWriter;
	public FileWriting(File file, boolean append) throws IOException {
		bufferedWriter = new BufferedWriter(new FileWriter(file, append));
	}
	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}

    // This function is used for write data to incentive data file -- Zezhu
    public static void writeIncentiveToFile(ArrayList<Incentive> incentives){
        try{
            File dfile = new File("data");
            System.out.println(dfile.exists()? "yes" : "no");
            FileWriter fw = new FileWriter(new File("data/incentives"));
            //write the first line
            fw.append("id~dealerId~title~startDate~endDate~description~cashValue~discountCriteria\n");
            for(Incentive i : incentives){
                fw.append(i.toString());
            }
            //close the fileWriter
            fw.close();
        } catch (IOException i){
            i.printStackTrace();
        }
    }

    //This function is used for appending an new incentive to the data file -- Zezhu
    public static void writeAIncentiveToFile(Incentive incentive){
	    try{
	        FileWriter fw = new FileWriter(new File("data/incentives"), true);
	        //append the new incentive to the data file
            fw.append(incentive.toString());
            fw.close();
        } catch (IOException i){
	        i.printStackTrace();
        }
    }
}
