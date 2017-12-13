package com.neuSep17.dao;

/**
 * Created by team 2 (Bin Shi) to manage the application properties.
 * 
 * Quick Use Guide:
 * - To hide the debug information during demo:
 * if(PropertyManager.getProperty("debug").equalsIgnoreCase("true")) {
                            exception.printStackTrace();
 * }
 */
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class PropertyManager {
    private static final File propertyFile = new File("config", "global.properties");
    private static Properties config = new Properties();
    
    static {
        try {
            config.load(new FileInputStream(propertyFile));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find the perperty file " + propertyFile.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Cannot read the perperty file " + propertyFile.getAbsolutePath());
            e.printStackTrace();
        }
    }
    
    public static String getProperty(String key){
        return config.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue){
        return config.getProperty(key, defaultValue);
    }
    
    public static Object setProperty(String key, String value) {
        Object previous = config.setProperty(key, value);
        store();
        return previous;
    }
    
    public static Set<String> stringPropertyNames(){
        return config.stringPropertyNames();
    }

    public static void store(){
        String comments = "Updated on " + LocalDate.now();
        store(comments);
    }
    
    public static void store(String comments) {
        OutputStream out=null;
        try {
            out = new FileOutputStream(propertyFile);
            config.store(out, comments);
        } catch (IOException e) {
            if(getProperty("debug").equalsIgnoreCase("true"))  e.printStackTrace();
        }
    }
    

    private PropertyManager(){};
    
    public static void main(String[] args) {
        for(String name: stringPropertyNames()){
            System.out.println(name + ":"+getProperty(name));
        }
    }

}
