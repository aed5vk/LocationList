package cs4720.cs.virginia.edu.locationlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class toDO extends AppCompatActivity implements TaskFragment.OnFragmentInteractionListener {


    private Button cameraButton;
    private Button saveButton;
    private Bitmap bitmap;
    private ImageView image;
    ArrayList<todoEntry> toDoList;
    int positionInList;
    String FILENAME="list_file";
    private BroadcastReceiver broadcastReceiver;

    private todoEntry task;

    private String title;
    private String locationString = "";
    private String imageString;
    private double latitude;
    private double longitude;

    // image creation stuff
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("toDo", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        task = (todoEntry)extras.getSerializable("EXTRA_MESSAGE");
        String message = task.getTitle();
        locationString = task.getLocationString();
        latitude = task.getLatitude();
        longitude = task.getLongitude();

        TextView txtView = (TextView) findViewById(R.id.oneItem);
        txtView.setText(message);
        toDoList = (ArrayList<todoEntry>)extras.getSerializable("EXTRA_MESSAGE2");
        positionInList = extras.getInt("EXTRA_MESSAGE3");
        image = (ImageView) findViewById(R.id.imageV);

        if(locationString.equals("")) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        locationString = bundle.getString("locationAsString");
                        latitude = bundle.getDouble("latitude");
                        longitude = bundle.getDouble("longitude");
                        task.setLocationString(locationString);
                        task.setLatitude(latitude);
                        task.setLongitude(longitude);
                    }
                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter(locationService.BROADCAST_LOCATION));
        }

        if(!task.getImageString().equals("")){
            onReOpen();
        }

        /*
        picture = task.getImage();


        if (picture != null) {
            image.setImageBitmap(picture);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            image = (ImageView) findViewById(R.id.imageV);
        }
        */

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
        cameraButton = (Button) findViewById(R.id.cameraButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                activateCamera();
            }
        });

        Log.i("IO", "Trying to find a photoFile");
        if (bitmap != null) {
            Log.i("IO", "photoFile exists");
            try{
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.e("IO", "photo file exists but no bitmap");
                image = (ImageView) findViewById(R.id.imageV);
            }
        } else {
            image = (ImageView) findViewById(R.id.imageV);
        }

       /* TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Set the text view as the activity layout
        setContentView(textView);
        */
        //adapter = new ArrayAdapter(this, )
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void activateCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error stuff should never get here
                Log.e("IO", "couldn't make a photo file");
            }

            // By now the file should be successfully created
            if (photoFile != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);

                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(photoFile));
        } catch (IOException e) {
            Log.e("IO", "couldn't find uri for photofile");
        }
        if (bitmap != null) {
            image.setImageBitmap(bitmap);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            task.setImageString(photoFile.getAbsolutePath());
        } else {
            photoFile.delete();
        }
        Log.d("pics", image.getScaleType().toString());
    }


    public void onReOpen(){

        bitmap = BitmapFactory.decodeFile(task.getImageString());




    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        Log.i("toDo", "onDestroy Called");
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //savedInstanceState.putString();
    }

    private void save() {

        toDoList.set(positionInList, task);
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(toDoList);
            oos.flush();
            oos.close();
            fos.close();
        }catch(Exception e) {
            Log.e("LocationList", e.getMessage());
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onFragmentInteraction(String id) {

    }

    public void openMaps (View view){
        if(latitude == 0 || longitude == 0){
            return;
        }
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        this.startActivity(intent);
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;

    }

}
