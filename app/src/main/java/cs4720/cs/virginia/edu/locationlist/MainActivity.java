package cs4720.cs.virginia.edu.locationlist;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private EditText userWords;
    private Button responseButton;
    private Button locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userWords = (EditText) findViewById(R.id.userInput);
        responseButton = (Button) findViewById(R.id.button);
        locationButton = (Button) findViewById(R.id.buttonCoordinates);

        responseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendToast();
            }
        });

    }

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

    public void sendToast(){
        String s = userWords.getText().toString();
        Toast.makeText(getApplicationContext(), s,
                Toast.LENGTH_LONG).show();
    }

    public void startService(View view) {
        Intent intent = new Intent(this, locationService.class);
        startService(intent);
    }

    public void stopService(View view) {
        Intent intent = new Intent(this, locationService.class);
        stopService(intent);
    }



}
