package net.scotshouse.mininote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 *  MainActivity.java
 *
 *  Scot Andre                                              sandre@rollins.edu
 *  Project 4 - MiniNotes
 *  CMS395.H1X - Mobile App Development
 *  Professor Anderson
 *  27 October 2015
 *
 *  Write an app that allows a user to create, save, and retrieve mini notes.
 *
 *  To do this, create a database to hold each note, the date each note was
 *  written, and its subject (the user will give this to you).
 *
 *  Limit each note to 125 characters.
 *
 *  In one activity, allow the user to create and save a note.
 *
 *  In a second activity, allow the user to retrieve their mini notes based on
 *  date or subject. Bind the date and subject data entry fields to the
 *  database using ArrayAdapters.
 *
 *  Implement menus to allow the user to move between activities.
 *
 *  Separate the database functionality into a DatabaseManager class (as we did
 *  with the Tipper).
 *
 *  Deliverables:
 *      Upload your project .zipped.
 *      A printout of your .java files, your .xml files, and your model
 */
public class MainActivity extends AppCompatActivity {
    private DatabaseManager mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create the database manager and the database
        mDb = new DatabaseManager(this);
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
        if (id == R.id.action_add_note) {
            addNotes(item.getActionView());
            return true;
        } else if(id == R.id.action_search_notes){
            searchNotes(item.getActionView());
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNotes(View v){
        startActivity(new Intent(getApplicationContext(), AddNoteActivity.class));
    }

    public void searchNotes(View v){
        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
    }
}
