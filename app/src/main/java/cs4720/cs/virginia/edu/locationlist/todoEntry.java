package cs4720.cs.virginia.edu.locationlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by aerikdan on 9/24/15.
 */
public class todoEntry {

    private String title;
    private String description;
    private String locationString;
    private String imageString;
    private int id;
    private Location location;
    private Bitmap image;

    public todoEntry(){
        this.title = "No Title Entered";
        this.description = "No Description Entered";
        this.locationString = "";
        this.imageString = "";
        this.location = null;
        this.image = null;
        this.id = 0;
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
        this.image = StringToBitMap(imageString);
    }

    /** Got this method from stack overflow */
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    /** Got this method from stack overflow */
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
