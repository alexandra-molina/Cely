package com.example.alexandramolina.cely;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.plus.PlusOneButton;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A fragment with a Google +1 button.
 */
public class NotasFragment extends Fragment{

    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    private PlusOneButton mPlusOneButton;
    static ArrayList<String> notas = new ArrayList<>();
    static ArrayAdapter arrayAdapter;


    public NotasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notas, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), NoteEdit.class);
                startActivity(intent);
            }
        });

        ListView listView = view.findViewById(R.id.notesListView);

        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("notes", null);

        if (set == null){
            notas.add("nota 1");
        }
        else {
            notas = new ArrayList(set);
        }


        arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, notas);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), NoteEdit.class);
                intent.putExtra("note_id", i);
                startActivity(intent);
            }
        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int note_id_por_eliminar = i;

                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Está seguro?")
                        .setMessage("Desea eliminar la nota?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notas.remove(note_id_por_eliminar);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(notas);
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


                return true;
            }
        });

        return view;
    }



}
