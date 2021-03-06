package cs4720.cs.virginia.edu.locationlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
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

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class toDO extends AppCompatActivity implements TaskFragment.OnFragmentInteractionListener{


    private Button cameraButton;
    private Button saveButton;
    private Bitmap picture;
    ImageView image;
    ArrayList<todoEntry> toDoList;
    int positionInList;
    String FILENAME="list_file";

    private todoEntry task;

    private String title;
    private String locationString;
    private String imageString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("toDo", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        task = (todoEntry)extras.getSerializable("EXTRA_MESSAGE");
        String message = task.getTitle();
        TextView txtView = (TextView) findViewById(R.id.oneItem);
        txtView.setText(message);
        toDoList = (ArrayList<todoEntry>)extras.getSerializable("EXTRA_MESSAGE2");
        positionInList = extras.getInt("EXTRA_MESSAGE3");
        picture = task.getImage();

        if (picture != null) {
            image.setImageBitmap(picture);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            image = (ImageView) findViewById(R.id.imageV);
        }



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
        startActivityForResult(intent, 0);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picture = (Bitmap) data.getExtras().get("data");
        image.setImageBitmap(picture);
        Log.d("pics", image.getScaleType().toString());
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        task.setImage(picture);
    }

    public void onReOpen(){
        picture = task.getImage();
        image.setImageBitmap(picture);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
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
         */

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


    }

    public void onFragmentInteraction(String id) {

    }

}
