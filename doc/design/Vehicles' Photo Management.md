# Function Description

This function is to get the vehicle picture from a URL string of the vehicle object, which could be either cached in the local file system or fetched from the remote if it is not existed locally.

The methods signatures are as below in the `vehicle.java` file.

```java
//get the actual image from the photo library or the Internet (team 2: Bin Shi)
//Note: if this image is not valid, return null instead.
public Image getPhoto(){
  return PictureManager.getVehiclePhoto(photoUrl);
}
```

And the main implementation is in `src/com/neuSep17/dao/PictureManager.java`

# Process Flow

## Display Photo

1. If the picture does not exists in the local disk, read it from the internet, and cache it to the disk.
2. Otherwise, read the file from the local disk, and return an BufferedImage instance.
   In the meanwhile, it will cache the photo to the local disk for the future use.

## Update Photo URL

1. Show tooltip to tell user to click the photo to update
2. Popup dialog for the user to enter new URL
3. Validate and the URL, and update the vehicle's URL after validation passing. Otherwise, show "No Photo"

# Optimization

* Later photo loading. Use `SwingUtilities.invokeLater()` method to display a picture at a later time to avoid blocking UI loading.
* Use Java 8's paralle stream feature to initialize the photo libaray. (Reduce the first loadin time from 10min to xxx. )
* Read the property `INIT_PICTURE_LIBARAY` from the configuration file.

# Source Code

src/com/neuSep17/dao/PictureManagement.java

src/com/neuSep17/ui/InventoryEditUI.java