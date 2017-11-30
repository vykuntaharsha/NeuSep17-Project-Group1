package com.neuSep17.dto;

import java.net.URL;

public class Vehicle {

        private String id;
        private Integer year;
        private String make;
        private String model;
        private String trim;
        private Category category;
        private String bodyType;
        private Float price;
        private URL photoUrl;
        
        public URL getPhotoUrl() {
            return photoUrl;
        }
        public void setPhotoUrl(URL photoUrl) {
            this.photoUrl = photoUrl;
        }
}
