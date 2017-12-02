package com.neuSep17.io;

import com.neuSep17.dto.Dealer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DealerFileReading extends FileReading {
    private BufferedReader bufferedReader;
    public DealerFileReading(File file) throws IOException {
        super(file);
        bufferedReader = super.getBufferedReader();
    }

    public Map<String, Dealer> loading() throws IOException {
        Map<String, Dealer> map = new LinkedHashMap<>();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }

            String[] strs = line.split("\\s+");
            map.put(strs[0], new Dealer(strs[0], strs[0], strs[2], strs[3], strs[4]));
        }

        bufferedReader.close();
        return map;
    }
}
