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

    // Table Name
    public static final String TABLE_NAME = "todos";

    // Table columns
    public static final String _ID = "_id";
    public static final String TODO_SUBJECT = "Subject";
    public static final String TODO_DESC = "Description";
    // Database Information
    static final String DB_NAME = "todoDB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TODO_SUBJECT + " TEXT NOT NULL, " + TODO_DESC + " TEXT);";

    public database(Context context) {
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
        values.put(TODO_SUBJECT, task.getTitle()); // task name
// status of task- can be 0 for not done and 1 for done
        values.put(TODO_DESC, task.getDescription());
// Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
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
        return taskList;
    }

    public void updateTask(todoEntry task) {
// updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TODO_SUBJECT, task.getTitle());
        values.put(TODO_DESC, task.getDescription());
        db.update(TABLE_NAME, values, _ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }
}