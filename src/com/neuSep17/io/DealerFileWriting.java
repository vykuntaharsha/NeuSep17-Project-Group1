package com.neuSep17.io;

import com.neuSep17.dto.Dealer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DealerFileWriting extends FileWriting{
    private BufferedWriter bufferedWriter;
    public DealerFileWriting(File file, boolean append) throws IOException{
        super(file, append);
        bufferedWriter = super.getBufferedWriter();
    }

    public void saveToFile(Map<String, Dealer> map)  {
        try {
            for (String m : map.keySet()) {
                bufferedWriter.write(map.get(m).getId() + "\t" +
                                    "en_US" + "\t" + map.get(m).getUrl() + "\t" +
                                    map.get(m).getEmailId() + "\t" + map.get(m).getContactNumber());
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
