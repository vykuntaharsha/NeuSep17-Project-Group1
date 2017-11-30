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
 * @author team 2 - bin
 * @version 0.1: 2017-11-30 Initialize.
 */
public class PictureManagement {

    private static final String PICTURE_DIR = "/Users/bin/Documents/Workspace/GitHub/inventory-management/picture";

    public static Image getVehicleImage(Vehicle v) {
        return getVehicleImage(v.getPhotoUrl());
    }

    public static Image getVehicleImage(URL url) {
        String imageURL = url.toString();
        File file = new File(getFileAbsolutePath(imageURL));
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

    private static String getFileAbsolutePath(String imageURL) {
        return PICTURE_DIR + getFileFullName(imageURL);
    }

    public static Image loadImageFromDisk(String imageURL) {
        BufferedImage image = null;
        try {
            File file = new File(PICTURE_DIR, getFileFullName(imageURL));
            image = ImageIO.read(file);
        } catch (IOException e) {

        }
        return image;
    }

    public static BufferedImage laodImageFromURL(URL url) {
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
        File file = new File(PICTURE_DIR, fileFullName);
        try {
            if(!file.exists()) file.createNewFile();
            ImageIO.write(image, fileExt, file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
