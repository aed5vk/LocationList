package cs4720.cs.virginia.edu.locationlist;

/**
 * Created by aerikdan on 9/24/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class database extends SQLiteOpenHelper {

    private static database db;

    // Table Name
    public static final String TABLE_NAME = "todos";

    // Table columns
    public static final String _ID = "_id";
    public static final String TODO_SUBJECT = "Subject";
    public static final String TODO_DESC = "Description";
    public static final String TODO_LOCA = "Location";
    public static final String TODO_IMG = "Image Bitmap";
    // Database Information
    static final String DB_NAME = "todoDB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TODO_SUBJECT + " TEXT NOT NULL, " + TODO_DESC + " TEXT, " + TODO_LOCA + " TEXT, "+ TODO_IMG + " TEXT);" ;

    public static synchronized database getInstance(Context context) {
        if(db == null){
            db = new database(context.getApplicationContext());
        }
        return db;
    }

    private database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTask(todoEntry task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TODO_SUBJECT, task.getTitle());
        values.put(TODO_DESC, task.getDescription());
        values.put(TODO_LOCA, task.getLocationString());
        values.put(TODO_IMG, task.getImageString());
// Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<todoEntry> getAllTasks() {
        ArrayList<todoEntry> taskList = new ArrayList<todoEntry>();
// Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                todoEntry task = new todoEntry();
                task.setId(cursor.getInt(0));
                task.setTitle(cursor.getString(1));
                task.setDescription(cursor.getString(2));
// Adding contact to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }
// return task list


        for(int i=0; i < taskList.size()-1; i++){
            taskList.get(i).setLocationString(taskList.get(i).getLocationString());
            taskList.get(i).setImageString(taskList.get(i).getImageString());
        }

        return taskList;
    }

    public void updateTask(todoEntry task) {
// updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TODO_SUBJECT, task.getTitle());
        values.put(TODO_DESC, task.getDescription());
        values.put(TODO_LOCA, task.getLocationString());
        values.put(TODO_IMG, task.getImageString());
        db.update(TABLE_NAME, values, _ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }
}