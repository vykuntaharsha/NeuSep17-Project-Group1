package com.neuSep17.dto;

import java.net.URL;

public class Vehicle {

        public String id;
        public String webId;
        public Integer year;
        public String make;
        public String model;
        public String trim;
        public Category category;
        public String type;
        public Float price;
        public URL photoUrl;
        
        public URL getPhotoUrl() {
            return photoUrl;
        }
        public void setPhotoUrl(URL photoUrl) {
            this.photoUrl = photoUrl;
        }
}
