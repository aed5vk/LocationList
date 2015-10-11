package cs4720.cs.virginia.edu.locationlist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    ArrayList<todoEntry> toDoList;
    ArrayAdapter<todoEntry> adapter;
    private EditText userWords;
    private Button responseButton;
    String FILENAME="list_file";

    //public final static String EXTRA_MESSAGE = "cs4720.cs.virginia.edu.toDo";
    //public final static String EXTRA_MESSAGE2 = "cs4720.cs.virginia.edu.toDo";
    //public final static String EXTRA_MESSAGE3 = "cs4720.cs.virginia.edu.toDo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           toDoList = new ArrayList<todoEntry>();

            try {
                FileInputStream fis = openFileInput(FILENAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
                toDoList = (ArrayList<todoEntry>) ois.readObject();
                ois.close();
                fis.close();
            } catch (Exception e) {
                Log.e("LocationList", e.getMessage());
            }


        startLocationService();

        ListView listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, toDoList);
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


    @Override
    protected void onStop() {
        super.onStop();

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


    private AdapterView.OnItemClickListener listClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            editTODO(toDoList.get(position), position);
            //Toast.makeText(getApplicationContext(), toDoList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
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
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        todoEntry a = new todoEntry(timeStamp);
        a.setTitle(s);
        toDoList.add(a);
        int pos = toDoList.indexOf(a);
        adapter.notifyDataSetChanged();

        Intent intent = new Intent(this, toDO.class);
        Bundle extras = new Bundle();
        extras.putSerializable("EXTRA_MESSAGE", a);
        extras.putSerializable("EXTRA_MESSAGE2", toDoList);
        extras.putInt("EXTRA_MESSAGE3", pos);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void editTODO (todoEntry specificEntry, int pos){
        Intent intent = new Intent(this, toDO.class);
        Bundle extras = new Bundle();
        extras.putSerializable("EXTRA_MESSAGE", specificEntry);
        extras.putSerializable("EXTRA_MESSAGE2", toDoList);
        extras.putInt("EXTRA_MESSAGE3", pos);
        intent.putExtras(extras);

        todoEntry task = (todoEntry)specificEntry;


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

