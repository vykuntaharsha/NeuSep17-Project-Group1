package com.neuSep17.dao;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;
import javax.swing.SwingUtilities;

import com.neuSep17.dto.Vehicle;

/**
 * Design document: /doc/design/display-picture.md
 * 
 * @author Team 2 - Bin Shi
 * 
 * Updates:
 * 0.1: 2017-11-30 Initialize.
 * 0.2: 2017-12-01 Use the relative path as the root of pictures
 * 1.0: 2017-12-05 Fixed some bugs and now it is release.
 */
public class PictureManager {
    //root direction of the picture files
    private static final String PICTURE_DIR = "picture";
    private static final boolean DEBUG = false;

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
                    e.printStackTrace();
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
            if(DEBUG) e.printStackTrace();
        }
//        System.out.println("loaded from disk " + image.toString());
        return image;
    }

    public static BufferedImage loadImageFromURL(URL url) {
        BufferedImage image = null;
        try {
            if(url.openStream()!=null) image = ImageIO.read(url);
        } catch (IOException e) {
            System.out.println("Cannot load the photo from " + url.toString());
            if(DEBUG) e.printStackTrace();
        }
//        System.out.println("loaded from URL " + image.toString());
        return image;
    }
    
    private static void cacheImage(BufferedImage image, String fileName) {
        File file = new File(PICTURE_DIR, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                ImageIO.write(image, getFileExt(fileName), file);
            } catch (IOException e) {
                System.out.println("Cannot cache the photo.");
                if(DEBUG) e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args){
        URL url=null;
        try {
            url = new URL("http://inventory-dmg.assets-cdk.com/chrome_jpgs/2016/15879x90.jpg");
//            url = new URL("http://inventory-dmg.assets-cdk.com/5/6/7/13918653765x90.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        PictureManager.getVehiclePhoto(url);
    }
    
}