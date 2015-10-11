package cs4720.cs.virginia.edu.locationlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aerikdan on 9/24/15.
 */
public class todoEntry implements Serializable{

    private String title;
    private String description;
    private String locationString;
    private String imageString;
    private Location location;
    private Bitmap image;
    private String taskFileName;

    public todoEntry(String taskFileName){
        this.title = "No Title Entered";
        this.description = "No Description Entered";
        this.locationString = "";
        this.imageString = "";
        this.location = null;
        this.image = null;
        this.taskFileName = taskFileName;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {

        this.location = location;
        this.locationString = location.toString();
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        this.imageString = image.toString();
    }

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
        this.location = new Location (locationString);
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getTaskFileName() {
        return taskFileName;
    }

    public void setTaskFileName(String taskFileName) {
        this.taskFileName = taskFileName;
    }
    @Override
    public String toString() {
        return title;
    }


}
