package com.awesome.d_planner;

/**
 * Created by Somnath on 23-06-2016.
 * Contains all database addition modules
 * for all activities
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class DBManager extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "planner.db";

    //Table Notes Data
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_NOTEVAL = "NoteVal ";

    //Table Tasks Data
    public static final String TABLE_TASKS = "TASKS";
    public static final String TASKS_ID = "T_ID";
    public static final String TASKS_NAME = "T_NAME";
    public static final String TASKS_DATE = "T_DATE";
    public static final String TASKS_TIME = "T_TIME";
    public static final String TASKS_TYPE = "T_TYPE";
    public static final String TASKS_COMPLETION = "T_COMP";

    //Table Reminders Data
    public static final String TABLE_REMINDERS = "REMINDERS";
    public static final String REMIND_ID = "R_ID";
    public static final String REMIND_VAL = "R_VAL";
    public static final String REMIND_DATE = "R_DATE";
    public static final String REMIND_TIME = "R_TIME";





    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table creation for Notes
        String query = "CREATE TABLE " + TABLE_NOTES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_NOTEVAL + " TEXT " +
                ");";
        db.execSQL(query);

        //table creation for Tasks
        query = "CREATE TABLE " + TABLE_TASKS + "(" +
                TASKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASKS_NAME+ " TEXT, " +
                TASKS_DATE + " TEXT, " +
                TASKS_TIME + " TEXT, " +
                TASKS_TYPE + " TEXT, " +
                TASKS_COMPLETION + " INTEGER " +
                ");";
        db.execSQL(query);

        //table creation for Reminders
        query = "CREATE TABLE " + TABLE_REMINDERS + "(" +
                REMIND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                REMIND_VAL + " TEXT, " +
                REMIND_DATE + " TEXT, " +
                REMIND_TIME + " TEXT " +
                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_REMINDERS);
        onCreate(db);

    }

    //gets the current Date
    public String getCurrentDate(){
        String date = "";
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT DATE('NOW')";
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();

        date  = c.getString(c.getColumnIndex("DATE('NOW')"));
        return date;
    }

    //gets Yesterday's Date
    public String getYesterday(){
        String date = "";
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT DATE('NOW','-1')";
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();

        date  = c.getString(c.getColumnIndex("DATE('NOW','-1')"));
        return date;
    }

    //gets Tomorrow's Date
    public String getTomorrow(){
        String date = "";
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT DATE('NOW','+1')";
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();

        date  = c.getString(c.getColumnIndex("DATE('NOW','+1')"));
        return date;
    }

    //NOTES

    //Add a new row to the table note
    public void addNewNote(Notes note){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.get_title());
        values.put(COLUMN_NOTEVAL,note.get_noteVal());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    //Update any row data to Notes
    public void updateNote(Notes note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.get_title());
        values.put(COLUMN_NOTEVAL,note.get_noteVal());

        db.update(TABLE_NOTES,values,"id = ? ", new String[] { Integer.toString(note.get_id()) });
        db.close();
    }

    //Delete a row to the table note
    public void deleteNote(int id, boolean all){
        SQLiteDatabase db = getWritableDatabase();
        if(!all){
            db.execSQL("DELETE FROM " + TABLE_NOTES + " WHERE "+ COLUMN_ID + "=" + id + ";");
        }
        else{
            db.execSQL("DELETE FROM " + TABLE_NOTES +";" );
        }

        db.close();
    }

    //Retrieves the rows of Notes
    public ArrayList<Notes> getNotes(){
        String dbString = "";
        ArrayList<Notes> n = new ArrayList<Notes>();
        Notes note;


        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE 1;";

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);


        //Move to the first row of results
        c.moveToFirst();

        while(!c.isAfterLast()){

            note = new Notes();
            if(c.getString(c.getColumnIndex("Id"))!= null){
               note.set_id(Integer.parseInt(c.getString(c.getColumnIndex("Id"))));
            }

            if(c.getString(c.getColumnIndex("Title"))!= null){
                note.set_title(c.getString(c.getColumnIndex("Title")));
            }

            if(c.getString(c.getColumnIndex("NoteVal"))!= null){
                note.set_noteVal(c.getString(c.getColumnIndex("NoteVal")));
            }
            n.add(note);

            //go to next result row
            c.moveToNext();

        }

        db.close();
        return n;
    }

    //TASKS

    //adds a row to Tasks Table
    public void addTask(Tasks task){
        ContentValues values = new ContentValues();
        values.put(TASKS_NAME, task.get_name());
        values.put(TASKS_DATE, task.get_date());
        values.put(TASKS_TIME, task.get_time());
        values.put(TASKS_TYPE, task.get_type());
        values.put(TASKS_COMPLETION, task.get_completion());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    //updates a row of Tasks Table
    public void updateTask(Tasks task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASKS_NAME, task.get_name());
        values.put(TASKS_DATE, task.get_date());
        values.put(TASKS_TIME, task.get_time());
        values.put(TASKS_TYPE, task.get_type());
        values.put(TASKS_COMPLETION, Integer.toString(task.get_completion()));

        db.update(TABLE_TASKS,values,"T_id = ? ", new String[] { Integer.toString(task.get_id()) });
        db.close();

    }

    //deletes a row from Tasks Table
    public void deleteTask(int id, boolean all){
        SQLiteDatabase db = getWritableDatabase();
        if(!all){
            db.execSQL("DELETE FROM " + TABLE_TASKS + " WHERE "+ TASKS_ID + "=" + id + ";");
        }
        else{
            db.execSQL("DELETE FROM "+ TABLE_TASKS );
        }

        db.close();
    }

    //retrieves count of all the Tasks
    public int getTaskRows(){
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TASKS + " WHERE "+TASKS_DATE+" = "+getCurrentDate();

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);


        //Move to the first row of results
        c.moveToFirst();

        while(!c.isAfterLast()){
            count++;
            c.moveToNext();
        }

        return count;
    }

    //retrives the count of completed tasks
    public int getTaskCompleted(){
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String dateNull = "Regular";
        String query = "SELECT * FROM " + TABLE_TASKS + " WHERE "+TASKS_COMPLETION+" = 1;";// AND ("+TASKS_DATE+" = \""+getCurrentDate()+"\" OR "+TASKS_DATE+" = "+"\""+dateNull+"\");";

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);


        //Move to the first row of results
        c.moveToFirst();

        while(!c.isAfterLast()){
            count++;
            c.moveToNext();
        }

        return count;
    }

    //gets all tasks for the Current Date
    public ArrayList<Tasks> getCurrentTasks(){
        String dbString = "";
        ArrayList<Tasks> t = new ArrayList<Tasks>();
        Tasks task;


        SQLiteDatabase db = getReadableDatabase();
        String dateNull = "Regular";
        String query = "SELECT * FROM " + TABLE_TASKS + " WHERE "+TASKS_DATE+" = \""+getCurrentDate()+"\" OR "+TASKS_DATE+" = "+"\""+dateNull+"\";";

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);


        //Move to the first row of results
        c.moveToFirst();

        while(!c.isAfterLast()){

            task = new Tasks();
            if(c.getString(c.getColumnIndex("T_ID"))!= null){
                task.set_id(Integer.parseInt(c.getString(c.getColumnIndex("T_ID"))));
            }

            if(c.getString(c.getColumnIndex("T_NAME"))!= null){
                task.set_name(c.getString(c.getColumnIndex("T_NAME")));

            }

            if(c.getString(c.getColumnIndex("T_TYPE"))!= null){
                task.set_type(c.getString(c.getColumnIndex("T_TYPE")));

            }
            if(c.getString(c.getColumnIndex("T_DATE"))!= null){
                task.set_date(c.getString(c.getColumnIndex("T_DATE")));

            }
            if(c.getString(c.getColumnIndex("T_TIME"))!= null){
                task.set_time(c.getString(c.getColumnIndex("T_TIME")));

            }
            if(c.getString(c.getColumnIndex("T_COMP"))!= null){
                if(Integer.parseInt(c.getString(c.getColumnIndex("T_COMP")))==1)
                    task.set_completion(true);
                else if(Integer.parseInt(c.getString(c.getColumnIndex("T_COMP")))==0)
                    task.set_completion(false);

            }
            t.add(task);

            //go to next result row
            c.moveToNext();

        }

        db.close();
        return t;
    }

    public Calendar taskTime(Tasks t){
        String Time = t.get_time();
        int p = Time.indexOf(":");
        int hr = Integer.parseInt(Time.substring(0,p));
        int min = Integer.parseInt(Time.substring(p+1, Time.length()));
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, hr);
        now.set(Calendar.MINUTE, min);
        return now;
    }

    //REMINDERS

    //adds a row to Reminders Table
    public void addReminder(Reminders remind){

        ContentValues values = new ContentValues();
        values.put(REMIND_VAL, remind.get_val());
        values.put(REMIND_DATE, remind.get_date());
        values.put(REMIND_TIME, remind.get_time());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_REMINDERS, null, values);
        db.close();
    }

    //updates a row of Reminders Table
    public void updateReminder(Reminders remind){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REMIND_VAL, remind.get_val());
        values.put(REMIND_DATE, remind.get_date());
        values.put(REMIND_TIME, remind.get_time());

        db.update(TABLE_TASKS,values,"R_ID = ? ", new String[] { Integer.toString(remind.get_id()) });


        db.close();
    }

    //deletes a row from Reminders Table
    public void deleteReminders(int id, boolean all){

        SQLiteDatabase db = getWritableDatabase();
        if(!all){
            db.execSQL("DELETE FROM " + TABLE_REMINDERS + " WHERE "+ REMIND_ID + "=" + id + ";");
        }
        else{
            db.execSQL("DELETE FROM "+ TABLE_REMINDERS );
        }

        db.close();
    }

    //retrieves all rows of reminders
    public ArrayList<Reminders> getReminders(){
        ArrayList<Reminders> r = new ArrayList<Reminders>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_REMINDERS + " WHERE 1;";

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query,null);


        //Move to the first row of results
        c.moveToFirst();

        while(!c.isAfterLast()){

            Reminders remind = new Reminders();
            if(c.getString(c.getColumnIndex("R_ID"))!= null){
                remind.set_id(Integer.parseInt(c.getString(c.getColumnIndex("R_ID"))));
            }

            if(c.getString(c.getColumnIndex("R_VAL"))!= null){
                remind.set_val(c.getString(c.getColumnIndex("R_VAL")));

            }

            if(c.getString(c.getColumnIndex("R_DATE"))!= null){
                remind.set_date(c.getString(c.getColumnIndex("R_DATE")));

            }
            if(c.getString(c.getColumnIndex("R_TIME"))!= null){
                remind.set_time(c.getString(c.getColumnIndex("R_TIME")));

            }

            r.add(remind);

            //go to next result row
            c.moveToNext();

        }

        db.close();

        return r;
    }

    //retrives all rows of Current day
    public ArrayList<Reminders> getCurrentReminders(){

            ArrayList<Reminders> r = new ArrayList<Reminders>();

            SQLiteDatabase db = getReadableDatabase();

            String query = "SELECT * FROM " + TABLE_REMINDERS + " WHERE "+REMIND_DATE+" = \""+getCurrentDate()+"\" ORDER BY "+REMIND_TIME+";";

            //Cursor point to a location in your results
            Cursor c = db.rawQuery(query,null);


            //Move to the first row of results
            c.moveToFirst();

            while(!c.isAfterLast()){

                Reminders remind = new Reminders();
                if(c.getString(c.getColumnIndex("R_ID"))!= null){
                    remind.set_id(Integer.parseInt(c.getString(c.getColumnIndex("R_ID"))));
                }

                if(c.getString(c.getColumnIndex("R_VAL"))!= null){
                    remind.set_val(c.getString(c.getColumnIndex("R_VAL")));

                }

                if(c.getString(c.getColumnIndex("R_DATE"))!= null){
                    remind.set_date(c.getString(c.getColumnIndex("R_DATE")));

                }
                if(c.getString(c.getColumnIndex("R_TIME"))!= null){
                    remind.set_time(c.getString(c.getColumnIndex("R_TIME")));

                }

                r.add(remind);

                //go to next result row
                c.moveToNext();

            }

            db.close();

            return r;
    }

  /*  public Calendar remTime(Reminders r){

        String Time = r.get_time();
        int p = Time.indexOf(":");
        int hr = Integer.parseInt(Time.substring(0,p));
        int min = Integer.parseInt(Time.substring(p+1, Time.length()));
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, hr);
        now.set(Calendar.MINUTE, min);
        Log.i("in remTime now:",now.toString());

        return now;
    }*/

}
