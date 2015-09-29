package cs4720.cs.virginia.edu.locationlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class toDO extends AppCompatActivity implements TaskFragment.OnFragmentInteractionListener {


    private Button cameraButton;
    private Button saveButton;
    private Bitmap bitmap;
    ImageView image;
    ArrayAdapter adapter;

    private todoEntry task = new todoEntry();
    private database db;

    private String title;
    private String locationString;
    private String imageString;
    private int id;

    // image creation stuff
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("toDo", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        db = database.getInstance(this);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView txtView = (TextView) findViewById(R.id.oneItem);
        txtView.setText(message);


        task.setTitle(message);

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
        } else {
            photoFile.delete();
        }
        Log.d("pics", image.getScaleType().toString());
        //task.setImage(picture);
    }

    @Override
    protected void onDestroy() {
        Log.i("toDo", "onDestroy Called");

        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //savedInstanceState.putString();
    }

    private void save() {
        /*
        TODO save stuff
        This is where I plan to do the database stuff
         */

    }

    public void onFragmentInteraction(String id) {

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
