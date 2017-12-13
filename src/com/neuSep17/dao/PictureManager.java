package com.neuSep17.dao;

import com.neuSep17.dto.Vehicle;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
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
 * 1.1: 2017-12-12 Put empty file names to a set to avoid file IO
 */
public class PictureManager {
    //root direction of the picture files
    private static final String PICTURE_DIR = "picture";
    private static final boolean DEBUG = false;
    private static URL defaultPhotoURL;
    private static BufferedImage defaultPhoto;
    private static Set<Integer> emptyURLSet;
    
    static {
//        invlidURLSet = 
    }

    //replaced by the getPhoto() in the Vehicle class
    @Deprecated
    public static Image getVehiclePhoto(Vehicle v) {
        if(v==null || v.getPhotoURL() == null || v.getPhotoURL().getFile().isEmpty() ) return null;
        else return getVehiclePhoto(v.getPhotoURL());
    }

    /**
     * Get the image of a vehicle using its photoURL.
     * @param photoURL: URL to a photo file
     * @return The image of the vehicle or null if there is no valid image.
     */
    public static BufferedImage getVehiclePhoto(URL photoURL) {
        if(photoURL==null || photoURL.getFile().length()==0) return null;
        BufferedImage image = null;
        File file = new File(PICTURE_DIR, getFullName(photoURL.getFile()));
        if (file.exists()) {
            if (file.length() > 0)
                image = loadImageFromDisk(file);
        } else {
            image = loadImageFromURL(photoURL);
            if(image!=null) cacheImage(image, getFullName(photoURL.getFile()));
            else
                //cache the empty file to disk even it is empty,
                //because next time the program knows won't try to download it from the Internet.
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("Cannot create this empty file to disk "+ file.toString());
                    if(PropertyManager.getProperty("debug").equalsIgnoreCase("true")) e.printStackTrace();
                }
        }
        return image;
    }

    /**
     * Get the full file name (hashed) from the URL's file like /5/6/7/13918653765x90.jpg
     * @param urlFile
     * @return the full file name in the format of hashcode.ext
     */
    private static String getFullName(String urlFile) {
        return getFileName(urlFile) + "." + getFileExt(urlFile);
    }
    
    private static String getFileName(String urlFile) {
        return String.valueOf(urlFile.hashCode());
    }

    private static String getFileExt(String urlFile) {
        int dot = urlFile.lastIndexOf('.');
        String ext = urlFile.substring(dot+1);
        return ext;
    }

    public static BufferedImage loadImageFromDisk(File imageFile) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.println("Cannot read from disk.");
            if(PropertyManager.getProperty("debug").equalsIgnoreCase("true"))  e.printStackTrace();
        }
//        System.out.println("loaded from disk " + image.toString());
        return image;
    }

    public static BufferedImage loadImageFromURL(URL url) {
        BufferedImage image = null;
        try {
            int length = url.openConnection().getContentLength();
            if (length > 0 && url.openStream() != null)
                image = ImageIO.read(url);
        } catch (IOException e) {
            System.out.println("Cannot load the photo from " + url.toString());
            if(PropertyManager.getProperty("debug").equalsIgnoreCase("true"))  e.printStackTrace();
        }
//        System.out.println("loaded from URL " + image.toString());
        return image;
    }
    
    /**
     * @param image
     * @param fileName
     */
    private static void cacheImage(BufferedImage image, String fileName) {
        File file = new File(PICTURE_DIR, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                ImageIO.write(image, getFileExt(fileName), file);
            } catch (IOException e) {
                System.out.println("Cannot cache the photo.");
                if(PropertyManager.getProperty("debug").equalsIgnoreCase("true"))  e.printStackTrace();
            }
        }
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
                if(PropertyManager.getProperty("debug").equalsIgnoreCase("true"))  e.printStackTrace();
            }
        }
        return defaultPhotoURL;
    }

    public static BufferedImage getDefaultPhoto() {
        if(defaultPhoto==null){
            defaultPhoto = getVehiclePhoto(getDefaultPhotoURL());
        }
        return defaultPhoto;
    }
    
}