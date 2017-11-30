package com.neuSep17.dao;

import static org.junit.Assert.*;

import java.awt.*;
import java.net.URL;

import org.junit.*;

public class PictureManagementTest {
    URL url = null;
    String imageURL="http://inventory-dmg.assets-cdk.com/chrome_jpgs/2016/24174x90.jpg";
    @Before
    public void setUp() throws Exception {
        url = new URL("http://inventory-dmg.assets-cdk.com/chrome_jpgs/2016/24174x90.jpg");
    }

    @Test
    public void unitTest(){
        Image image = PictureManagement.getVehicleImage(url);
//        System.out.println(image.toString());
    }
    
    @Test
    public void loadFromDisk(){
        System.out.println(PictureManagement.loadImageFromDisk(imageURL));
    }
    
}
