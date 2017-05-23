package com.awesome.d_planner;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.awesome.d_planner.*;
import java.util.ArrayList;


/**
 * Created by Somnath on 23-06-2016.
 * class for operating Note fragment main
 */
public class NoteFragment extends Fragment {

    DBManager dbManager;

    public NoteFragment() {

    }

    public static NoteFragment newInstance(){
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment, container, false);

        FloatingActionButton addNote = (FloatingActionButton)view.findViewById(R.id.addNoteButton);
        final boolean[] addNoteButtonPressed = {false};


        //addNote action Performed to go to Add Note Activity
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                addNoteButtonPressed[0] = true;
                Intent i = new Intent(act, AddNote.class);
                i.putExtra("new note",addNoteButtonPressed[0]);



                startActivity(i);
            }
        });

        ListAdapter notesAdapter;

        //get the list of Notes Titles
        dbManager = new DBManager(getContext(),null,null,1);
        ArrayList<String> noteTitles = getNoteList();
        ArrayList<Notes> myNotes = dbManager.getNotes();

        ArrayList<Notes> dummy = new ArrayList<Notes>();
        dummy.add(new Notes("No Notes", "Dummy")) ;

        if(noteTitles.isEmpty()) {
            notesAdapter = new ListViewAdapter1(getContext(), dummy);
        }
        else{
            notesAdapter = new ListViewAdapter1(getContext(),myNotes);
        }
        ListView notesList = (ListView)view.findViewById(R.id.notes_list);

        notesList.setAdapter(notesAdapter);

        notesList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent in = new Intent(getActivity(),AddNote.class);
                        String note = null;
                        note = String.valueOf(parent.getItemAtPosition(position));
                        Notes n = (Notes)parent.getItemAtPosition(position);
                        int N_id = n.get_id();
                        if(note.equalsIgnoreCase("No Notes")){
                            in.putExtra("new note",true);
                        }
                        in.putExtra("note id", N_id);
                        in.putExtra("note title", n.get_title());
                        in.putExtra("note_val",n.get_noteVal());
                        in.putExtra("item id", id);

                        startActivity(in);

                    }
                }
        );

        return view;
    }

    public ArrayList<String> getNoteList(){
        dbManager = new DBManager(getContext(),null,null,1);
       ArrayList<Notes> note = dbManager.getNotes();
        ArrayList<String> title = new ArrayList<String>();

           for(Notes n : note){
               title.add(n.get_title());
           }

            return title;


    }




}
