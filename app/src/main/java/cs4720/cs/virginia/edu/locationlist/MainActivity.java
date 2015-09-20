package cs4720.cs.virginia.edu.locationlist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> toDoList = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private EditText userWords;
    private Button responseButton;
    //private Button locationButton;

    public final static String EXTRA_MESSAGE = "cs4720.cs.virginia.edu.toDo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLocationService();

        ListView listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoList);
        listView.setOnItemClickListener(listClickedHandler);
        listView.setAdapter(adapter);

        userWords = (EditText) findViewById(R.id.userInput);
        //responseButton = (Button) findViewById(R.id.button);
       // locationButton = (Button) findViewById(R.id.buttonCoordinates);

       /* responseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addItem();
            }
        });
        */

    }

    private AdapterView.OnItemClickListener listClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Toast.makeText(getApplicationContext(), toDoList.get(position), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

   /* @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationService();
                } else {

                }
                return;
            }

        }
    }

    public void checkMyPermission (){
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }else{
            startLocationService();
        }
    }
    */

    public void startLocationService(){
        Intent intent = new Intent(this, locationService.class);
        startService(intent);

    }

    public void sendToast(){
        String s = userWords.getText().toString();
        Toast.makeText(getApplicationContext(), s,
                Toast.LENGTH_LONG).show();
    }


    public void addItem(){
        String s = userWords.getText().toString();
        Intent intent = new Intent(this, toDO.class);
        intent.putExtra(EXTRA_MESSAGE, s);
        startActivity(intent);
    }

    public void addItem(View view){
        String s = userWords.getText().toString();
        toDoList.add(s);
        adapter.notifyDataSetChanged();
        Intent intent = new Intent(this, toDO.class);
        intent.putExtra(EXTRA_MESSAGE, s);
        startActivity(intent);
    }

    public void startService(View view) {
        //checkMyPermission();
        Intent intent = new Intent(this, locationService.class);
        startService(intent);

    }

    public void stopService(View view) {
        Intent intent = new Intent(this, locationService.class);
        stopService(intent);
    }



}
