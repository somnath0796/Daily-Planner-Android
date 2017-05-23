package com.awesome.d_planner;

import android.content.Intent;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.awesome.d_planner.NoteFragment;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddNote extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);




         Bundle NoteMainData = this.getIntent().getExtras();
        final DBManager dbManager = new DBManager(this, null, null, 1);

        if(NoteMainData==null){
            return;
        }
        //check whether new note or old note
        Boolean newNote  = NoteMainData.getBoolean("new note");
        String noteDataTitle = NoteMainData.getString("note title");
        String noteDataVal = NoteMainData.getString("note_val");


        Button saveButton = (Button)findViewById(R.id.noteSaveButton);
        Button deleteButton = (Button)findViewById(R.id.noteDeleteButton);
        Button updateButton = (Button) findViewById(R.id.noteUpdateButton);
         EditText noteVal = (EditText) findViewById(R.id.noteVal);
         EditText noteTitle = (EditText) findViewById(R.id.noteTitle);
         TextView noteEditTitle = (TextView) findViewById(R.id.NoteEditorTitle);



        if(newNote){
            noteEditTitle.setText("Add a new Note");
            saveButton.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.INVISIBLE);
        }
        else{
            noteEditTitle.setText("Update Note");
            updateButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
            noteTitle.setText(noteDataTitle);
            noteVal.setText(noteDataVal);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText noteVal = (EditText) findViewById(R.id.noteVal);
                EditText noteTitle = (EditText) findViewById(R.id.noteTitle);

                Notes note = new Notes(noteTitle.getText().toString(), noteVal.getText().toString());


                dbManager.addNewNote(note);


                Toast.makeText(getApplicationContext(),"Note Added",Toast.LENGTH_SHORT).show();
                Intent out = new Intent(getApplicationContext(),MainScreen.class);
                out.putExtra("page position",3);
                out.putExtra("other", true);

                startActivity(out);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle NoteMainData = getIntent().getExtras();
                int id = NoteMainData.getInt("note id");
                EditText noteVal = (EditText) findViewById(R.id.noteVal);
                EditText noteTitle = (EditText) findViewById(R.id.noteTitle);

                Notes note = new Notes(noteTitle.getText().toString(), noteVal.getText().toString());
                note.set_id(id);

                dbManager.updateNote(note);



                Toast.makeText(getApplicationContext(),"Note Updated",Toast.LENGTH_SHORT).show();
                Intent out = new Intent(getApplicationContext(),MainScreen.class);
                out.putExtra("page position",3);
                out.putExtra("other", true);

                startActivity(out);
            }
        });


        if (deleteButton != null) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle NoteMainData = getIntent().getExtras();
                    int id = NoteMainData.getInt("note id");
                    Boolean newNote  = NoteMainData.getBoolean("new note");
                    if(!newNote){
                        //Toast.makeText(getApplicationContext(),"You selected id: "+Integer.toString(id), Toast.LENGTH_SHORT).show();
                        dbManager.deleteNote(id,false);
                    }


                    Toast.makeText(getApplicationContext(),"Note scrapped",Toast.LENGTH_SHORT).show();
                    Intent out = new Intent(getApplicationContext(),MainScreen.class);
                    out.putExtra("page position",3);
                    out.putExtra("other", true);

                    startActivity(out);
                }
            });
        }
    }


}
