package com.example.alexandramolina.cely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.HashSet;

/**
 * Created by alexandramolina on 12/6/18.
 */

public class NoteEdit extends AppCompatActivity{

    int note_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        note_id = intent.getIntExtra("note_id", -1);

        // uno:0
        // dos:1
        // tres:2


        if (note_id == -1){
            // nueva nota
            Log.i("note", "new note");
            NotasFragment.notas.add("");
            note_id = NotasFragment.notas.size()-1;
            NotasFragment.arrayAdapter.notifyDataSetChanged();
        }
        else {
            // nota existente
            editText.setText(NotasFragment.notas.get(note_id));
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NotasFragment.notas.set(note_id, String.valueOf(charSequence));
                NotasFragment.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(NotasFragment.notas);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });








    }
}
