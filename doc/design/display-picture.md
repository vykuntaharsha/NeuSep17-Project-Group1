# Get the Picture from URL or Local Cache

## Function Description

This function is to get the vehicle picture from a URL string of the vehicle object, which could be either cached in the local file system or fetched from the remote if it is not existed locally.

 The methods signatures are as below (@Overload)

`public Image getVehicleImage(Vehicle v)`

`public Image getVehicleImage(String imageURL)`

## Process Flow

1. If the picture does not exists in the local disk, then read it from the internet and save a copy to the disk for the future use.

2. Otherwise, just read the file from the local disk, and return an Image instance.

   Note: 

   - The image file name will be the hashcode of the URL string, and the extension name is the same as which in the URL (mostly .png). 

   Furture features: 

   - initilize whole picture libarary at the first time when the program launch
   - read the property `INIT_PICTURE_LIBARAY` from the configuration file

## Source Code

src/com/neuSep17/dao/PictureManagement.java