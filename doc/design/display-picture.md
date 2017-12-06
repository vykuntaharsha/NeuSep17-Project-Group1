# Display Vehicle's Picture 

## Function Description

This function is to get the vehicle picture from a URL string of the vehicle object, which could be either cached in the local file system or fetched from the remote if it is not existed locally.

 The methods signatures are as below.

`public static BufferedImage getVehiclePhoto(URL photoURL)`

## Process Flow

1. If the picture does not exists in the local disk, read it from the internet, and cache it to the disk.

2. Otherwise, read the file from the local disk, and return an BufferedImage instance.

# Optimization

* Late picture loading. Use `SwingUtilities.invokeLater()` method to display a picture at a later time because loading a picture form either URL or disk is time consuming.
* Cache the picture to the disk for further usage.

# Furture features

- initilize whole picture libarary at the first time when the program launch
- read the property `INIT_PICTURE_LIBARAY` from the configuration file

# Source Code

src/com/neuSep17/dao/PictureManagement.java