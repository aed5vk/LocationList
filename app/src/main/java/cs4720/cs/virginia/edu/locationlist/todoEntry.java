package cs4720.cs.virginia.edu.locationlist;

import android.graphics.Bitmap;
import android.location.Location;
import android.widget.ImageView;

/**
 * Created by aerikdan on 9/24/15.
 */
public class todoEntry {

    private String title, description;
    private int id;
    private Location location;
    private Bitmap image;

    public todoEntry(){
        this.title = "no title";
        this.description = "No description entered";
        this.location = null;
        this.image = null;
        this.id = 0;
    }


    public todoEntry(String title, String description, Location location, Bitmap img) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.image = img;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


}
