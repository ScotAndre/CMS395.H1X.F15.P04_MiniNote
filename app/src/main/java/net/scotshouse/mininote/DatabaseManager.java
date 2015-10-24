package net.scotshouse.mininote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *  DatabaseManager.java
 *
 *  Scot Andre                                              sandre@rollins.edu
 *  Project 4 - MiniNotes
 *  CMS395.H1X - Mobile App Development
 *  Professor Anderson
 *  27 October 2015
 */
public class DatabaseManager extends SQLiteOpenHelper {
    private Context mContext;
    private final int MAX_NOTE_LENGTH = 125;
    private static final String DATABASE_NAME = "MiniNote";
    private static final int DATABASE_VERSION = 1;
    private final String TABLE_NAME = "notes";
    private final String ID = "_id";
    private final String DATE = "date";
    private final String NOTE_TITLE = "noteTitle";
    private final String NOTE = "note";

    private final String CREATE_MININOTES_TABLE = "CREATE TABLE " +
            TABLE_NAME + " (" + ID + " integer Primary Key AutoIncrement, " +
            DATE + " Text Not Null, " +
            NOTE_TITLE + " Text Not Null, " +
            NOTE + " Text)";

    public DatabaseManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try {
            db.execSQL(CREATE_MININOTES_TABLE);
        } catch (SQLException e) {
            Toast.makeText(mContext, R.string.db_open_error_message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // do something when the database schema is changed.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void insert(String date, String title, String note){
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            if(title.isEmpty() || note.isEmpty()){
                // check to make sure that neither the title nor note is empty
                Toast.makeText(mContext, R.string.title_or_note_empty,
                        Toast.LENGTH_LONG).show();
            } else if(note.length() > MAX_NOTE_LENGTH){
                // ensure that note is not longer than MAX_NOTE_LENGTH
                Toast.makeText(mContext, R.string.max_note_length,
                        Toast.LENGTH_LONG).show();
            } else {
                ContentValues values = new ContentValues();

                values.put(DATE, date);
                values.put(NOTE_TITLE, title);
                values.put(NOTE, note);

                long newId = db.insert(TABLE_NAME, null, values);

                if(newId == -1){
                    Toast.makeText(mContext, R.string.db_insert_error,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, R.string.db_insert_ok,
                            Toast.LENGTH_LONG).show();
                }
                db.close();
            }
        } catch (SQLiteException se){
            Toast.makeText(mContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }
    }// end insert

    /**
     *
     * @return
     */
    public ArrayList<String> selectAll(){
        ArrayList<String> noteList = new ArrayList<String>();

        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sqlQuery = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(sqlQuery, null);

            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                String oneNote = cursor.getString(0) + " " + // id
                    cursor.getString(1) + " " +  // date
                    cursor.getString(2) + " " + // title
                    cursor.getString(3); // note

                noteList.add(oneNote);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }catch (SQLiteException sle){
            Toast.makeText(mContext, sle.toString(), Toast.LENGTH_LONG).show();
        }
        return noteList;
    }
}
