package cs4720.cs.virginia.edu.locationlist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    ArrayList<todoEntry> toDoList = new ArrayList<todoEntry>();
    customAdapter adapter;
    private EditText userWords;
    private Button responseButton;
    private database db;
    //private Button locationButton;

    public final static String EXTRA_MESSAGE = "cs4720.cs.virginia.edu.toDo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLocationService();
        db = database.getInstance(this);
        toDoList = db.getAllTasks();

        ListView listView = (ListView)findViewById(R.id.listView);
        adapter = new customAdapter(this, android.R.layout.simple_list_item_1, toDoList);
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
            editTODO(toDoList.get(position).getId());
            Toast.makeText(getApplicationContext(), toDoList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private class customAdapter extends ArrayAdapter<todoEntry> {
        Context context;
        ArrayList<todoEntry> taskList = new ArrayList<todoEntry>();
        int layoutResourceId;

        public customAdapter(Context context, int layoutResourceId,
                             ArrayList<todoEntry> objects) {
            super(context, layoutResourceId, objects);
            this.layoutResourceId = layoutResourceId;
            this.taskList = objects;
            this.context = context;
        }
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


    public void startLocationService(){
        Intent intent = new Intent(this, locationService.class);
        startService(intent);

    }


    public void sendToast(){
        String s = userWords.getText().toString();
        Toast.makeText(getApplicationContext(), s,
                Toast.LENGTH_LONG).show();
    }

    public void addItem(View view){
        String s = userWords.getText().toString();
        todoEntry a = new todoEntry();
        a.setTitle(s);
        toDoList.add(a);
        db.addTask(a);
        adapter.add(a);
        adapter.notifyDataSetChanged();
        Intent intent = new Intent(this, toDO.class);
        intent.putExtra(EXTRA_MESSAGE, s);
        startActivity(intent);
    }

    public void editTODO (int id){
        Intent intent = new Intent(this, toDO.class);
        intent.putExtra(EXTRA_MESSAGE, id);
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

