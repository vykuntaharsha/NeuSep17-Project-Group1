package com.neuSep17.dto;



public enum Category {

    NEW, USED, CERTIFIED;

	 public static Category getCategory(String cat){
	       switch (cat){
	           case "used": return USED;
	           case "new": return NEW;
	           case "certified": return CERTIFIED;
	           default: throw new IllegalArgumentException();
	       }
	    }
}
