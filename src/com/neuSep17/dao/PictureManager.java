package com.neuSep17.dao;

import com.neuSep17.dto.Vehicle;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.util.*;
import javax.imageio.*;

/**
 * Design document: /doc/design/display-picture.md
 * 
 * @author Team 2 - Bin Shi
 * 
 * Updates:
 * 0.1: 2017-11-30 Initialize.
 * 0.2: 2017-12-01 Use the relative path as the root of pictures
 * 1.0: 2017-12-05 Fixed some bugs and now it is release.
 * 1.1: 2017-12-12 Put empty file names to a property file to avoid file IO
 */
public class PictureManager {
//    root direction of the picture files
    private static final String PICTURE_DIR = "picture";
    private static Properties photoNames = new Properties();//URL.file():photo full name.ext
    private static File photoNamesFile = null;
    
    private static URL defaultPhotoURL;
    private static BufferedImage defaultPhoto;

    static {
        try {
//            to use a different property file name, change it here
            photoNamesFile = new File(PICTURE_DIR, "photo-name.properties");
            if(!photoNamesFile.exists()) photoNamesFile.createNewFile(); 
            photoNames.load(new FileInputStream(photoNamesFile));
        } catch (IOException e) {
            System.out.println("Cannot load the perperty file " + "photo-name.properties");
            e.printStackTrace();
        }
    }

    /**
     * Get the image of a vehicle using its photoURL.
     * 
     * @param photoURL
     * @return The image of the vehicle or null if there is no valid image. If you need some default photo, you can use getVehiclePhotoWithDefault()
     */
    public static BufferedImage getVehiclePhoto(URL photoURL) {
        if (photoURL == null) return null;
        
        String fileName = photoNames.getProperty(photoURL.getFile().toString());
        if (fileName == null) {
            return loadImageFromURL(photoURL);
        } else {
            return loadImageFromDisk(fileName);
        }
    }
    
    public static BufferedImage getVehiclePhotoWithDefault(URL photoURL) {
        BufferedImage image = getVehiclePhoto(photoURL);
        return image==null?getDefaultPhoto():image;
    }

    /**
     * Get the full file name (hashed) from the URL's file like /5/6/7/13918653765x90.jpg
     * @param url
     * @return the full file name in the format of hashcode.ext
     */
    private static String getFullName(URL url) {
        return getFileName(url) + "." + getFileExt(url.toString());
    }
    
    private static String getFileName(URL url) {
        return String.valueOf(url.hashCode());//use url's hash code as unique file name
    }

    //use String because we need to get the ext later
    private static String getFileExt(String urlFile) {
        int dot = urlFile.lastIndexOf('.');
        String ext = urlFile.substring(dot+1);
        return ext;
    }

    private static BufferedImage loadImageFromDisk(String imageFileName) {
        if(imageFileName==null || imageFileName.isEmpty() || imageFileName.equalsIgnoreCase("null")) return null;
        BufferedImage image = null;
        try {
            File imageFile = new File(PICTURE_DIR, imageFileName);
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.println("Cannot read the image from disk.");
            debug(e);
        }
        return image;
    }

    private static BufferedImage loadImageFromURL(URL photoURL) {
        return loadImageFromURL(photoURL, true);
    }
    
    private static BufferedImage loadImageFromURL(URL photoURL, boolean storeProperty){
        BufferedImage image = null;
        try {
            URLConnection connection = photoURL.openConnection();
            connection.connect();
            if(connection.getContentLength()==0 || !connection.getContentType().contains("image")){
                photoNames.setProperty(photoURL.getFile().toString(), "null");
            }else{
                image = ImageIO.read(photoURL);
                photoNames.setProperty(photoURL.getFile().toString(), image==null?"null":getFullName(photoURL));
                cacheImage(image, getFullName(photoURL));
            }
        } catch (IOException e) {
            System.out.println("Cannot load the photo from " + photoURL.toString());
            debug(e);
        }
        
        if(storeProperty) storePhotoNames();
        return image;
    }
    
    private static void storePhotoNames(){
        try {
            photoNames.store(new FileOutputStream(photoNamesFile), LocalTime.now().toString());
        } catch (IOException e) {
            System.out.println("Failed to save the photo name property file.");
            debug(e);
        }
    }
    
    private static void debug(Exception e){
        if(PropertyManager.getProperty("debug").equalsIgnoreCase("true"))  e.printStackTrace();
    }
    
    /**
     * @param image
     * @param fullName
     */
    private static void cacheImage(BufferedImage image, String fullName) {
        File file = new File(PICTURE_DIR, fullName);
        try {
            file.createNewFile();
            ImageIO.write(image, getFileExt(fullName), file);
        } catch (IOException e) {
            System.out.println("Cannot save photo "+ fullName + " to disk.");
            debug(e);
        }
    }
    
    /**
     * Initialize the photo library for a deal, and with it's vehicle list.
     * @param dealer: the deal name, could be null (just to initialize a bulk of photos using the vehicle list)
     * @param vehicles
     */
    public static void initDealerPhotoLibrary(String dealer, ArrayList<Vehicle> vehicles){
        if(dealer==null || PropertyManager.getProperty(dealer)==null || PropertyManager.getProperty(dealer).equalsIgnoreCase("true")){
            vehicles.parallelStream().forEach(v-> loadImageFromURL(v.getPhotoURL(), false));
        }
        if(dealer!=null) PropertyManager.setProperty(dealer, "false");
        storePhotoNames();
    }
    
    /**
     * Get a URL instance from the property file. To set the default value, just
     * update the default-photo value in the config/global.properties
     * 
     * @return
     */
    public static URL getDefaultPhotoURL() {
        if(defaultPhotoURL==null){
            String photoString = PropertyManager.getProperty("default-photo");
            try {
                defaultPhotoURL = new URL(photoString);
            } catch (MalformedURLException e) {
                System.out.println("Please set property 'default-photo' to a valid value in the config/global.properties file");
                debug(e);
            }
        }
        return defaultPhotoURL;
    }

    private static BufferedImage getDefaultPhoto() {
        if(defaultPhoto==null){
            defaultPhoto = getVehiclePhoto(getDefaultPhotoURL());
        }
        return defaultPhoto;
    }
    
}