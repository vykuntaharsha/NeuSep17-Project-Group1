# Function Description

This function is to get the vehicle picture from a URL string of the vehicle object, which could be either cached in the local file system or fetched from the remote if it is valid and not existed locally.

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

1. If the this URL has never been checked (cannot find a entry in the URL:photoName dicitionary), try to load this photo from the internet, save it to local disk, and update the dictionary as well.
   - set the photoName to empty string if this URL does not have valid photo
   - otherwise, set it to the actual photo name in the disk
2. Otherwise, read the file from the local disk, and return an BufferedImage instance.

## Update Photo URL

1. Show tooltip to tell user to click the photo to update
2. Popup dialog for the user to enter new URL
3. Validate and the URL, and update the vehicle's URL after validation passing. Otherwise, show "No Photo"

# Optimization

* Later photo loading. Use `SwingUtilities.invokeLater()` method to display a picture at a later time to avoid blocking UI loading.

* **Use Java 8's paralle stream feature to initialize the photo libaray (15x speed boost: from 10min to about 40 seconds. )** 
  Note: 

  - Code `vehicles.parallelStream().forEach(v-> loadImageFromURL(v.getPhotoURL(), false));`


  - This test based on data file `gmps-covert-country`, 304  of 1224 URLs has photo (~2-4K).

* Read the property `INIT_PICTURE_LIBARAY` from the configuration file.

# Source Code

src/com/neuSep17/dao/PictureManagement.java

src/com/neuSep17/ui/InventoryEditUI.java

# Change History

2017-11-30 Draft.

2017-12-05 Fixed some bugs and now it is release.

2017-12-12. The loading time for 1368 records took about 50 seconds, more than 95% are empty pictures. So try to use a hash map to make it quick.