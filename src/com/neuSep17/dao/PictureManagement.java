package com.neuSep17.dao;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;

import com.neuSep17.dto.Vehicle;

/**
 * Design document: /doc/design/display-picture.md
 * 
 * @author Team 2 - Bin Shi
 * 
 * Updates:
 * 0.1: 2017-11-30 Initialize.
 * 0.2: 2017-12-01 Use the relative path as the root of pictures
 * 
 */
public class PictureManagement {

    //root direction of the picture files
    private static final String PICTURE_DIR = "picture";

    public static Image getVehicleImage(Vehicle v) {
        return getVehicleImage(v.getPhotoURL());
    }

    public static Image getVehicleImage(URL url) {
        String imageURL = url.toString();
        File file = new File(PICTURE_DIR, getFileFullName(imageURL));
        if (file.exists()) {
            return loadImageFromDisk(imageURL);
        } else {
            BufferedImage image = laodImageFromURL(url);
            cacheImage(image, imageURL);
            return image;
        }
    }

    public static String getFileHashName(String imageURL) {
        return String.valueOf(imageURL.hashCode());
    }

    private static String getFileExt(String imageURL) {
        int dot = imageURL.lastIndexOf('.');
        String fileExt = imageURL.substring(dot+1);
        return fileExt;
    }

    private static String getFileFullName(String imageURL) {
        return getFileHashName(imageURL) +"."+ getFileExt(imageURL);
    }

    public static Image loadImageFromDisk(String imageURL) {
        System.out.println("loaded from disk");
        BufferedImage image = null;
        try {
            File file = new File(PICTURE_DIR, getFileFullName(imageURL));
            image = ImageIO.read(file);
        } catch (IOException e) {

        }
        return image;
    }

    public static BufferedImage laodImageFromURL(URL url) {
        System.out.println("loaded from internet");
        BufferedImage image = null;

        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    
    protected static void cacheImage(BufferedImage image, String imageURL){
        cacheImage(image, getFileFullName(imageURL), getFileExt(imageURL));
    }

    private static void cacheImage(BufferedImage image, String fileFullName, String fileExt) {
        System.out.println("image cached");
        try { 
            File imageFile = new File(PICTURE_DIR, fileFullName);
            if(!imageFile.exists()) imageFile.createNewFile();
            
            ImageIO.write(image, fileExt, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        URL url=null;
        try {
            url = new URL("http://inventory-dmg.assets-cdk.com/RTT/Chevrolet/2016/2875373/default/ext_GBA_deg01x90.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(PictureManagement.getVehicleImage(url));
    }
    
}