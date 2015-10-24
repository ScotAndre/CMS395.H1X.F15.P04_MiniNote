package net.scotshouse.mininote;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {
    private DatabaseManager mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mDb = new DatabaseManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search_notes) {
            searchNotes(item.getActionView());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveNoteToDb(View v){
        TextView noteTitle = (TextView) findViewById(R.id.add_note_title);
        TextView noteContents = (TextView) findViewById(R.id.add_note_note);
        String noteTitleText = noteTitle.getText().toString();
        String noteText = noteContents.getText().toString();

        if(noteTitleText.length() <= 1 || noteText.length() <= 1){
            Toast.makeText(this, R.string.title_or_note_empty, Toast.LENGTH_LONG).show();
        }

        try{
            Calendar now = Calendar.getInstance();
            String today = (now.get(Calendar.MONTH) + 1) + "/" +
                    now.get(Calendar.DATE) + "/" +
                    now.get(Calendar.YEAR);
            mDb.insert(today, noteTitleText, noteText);
            Toast.makeText(this, R.string.db_insert_ok, Toast.LENGTH_LONG).show();
        }catch (SQLiteException sle){
            Toast.makeText(this, R.string.db_insert_error, Toast.LENGTH_LONG).show();
        }
    }



    public void searchNotes(View v){
        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
    }
}
