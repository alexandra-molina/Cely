package com.example.alexandramolina.cely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TraductorActivity extends android.support.v4.app.Fragment {

    ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView txtPalabra, txtPalabraTraducida;
    private Spinner spinner;
    private String idioma ="spa";
    private TraductorActivity.TranslationTaskWord tt;
    SharedPreferences sharedPreferences;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_traductor,container,false);

        txtPalabra = view.findViewById(R.id.palabra);
        txtPalabraTraducida = view.findViewById(R.id.palabraTraducida);
        spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.Idiomas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int position, long id) {
                String text = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT);
                switch (position) {
                    case 0:
                        idioma = "spa";
                        break;
                    case 1:
                        idioma = "en";
                        break;
                    case 2:
                        idioma = "pt";
                        break;
                    case 3:
                        idioma = "it";
                        break;
                    case 4:
                        idioma = "fr";
                        break;
                    case 5:
                        idioma = "de";
                        break;
                    case 6:
                        idioma = "ru";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Button btn = view.findViewById(R.id.btnTraducir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                traducir();
            }
        });

        return view;
    }

    public void traducir(){

        tt=new TranslationTaskWord();

        String palabra = txtPalabra.getText().toString();

        try {
            palabra= tt.execute(palabra).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        txtPalabraTraducida.setText(palabra);

    }

    public class TranslationTaskWord extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... urls) {

            String a=urls[0];
            String salida="";

            TranslateOptions options = TranslateOptions.newBuilder()
                    .setApiKey("AIzaSyBArteM1ejf2C6fCBZojg0JZkD7ZZ9IiQk")
                    .build();
            Translate translate = options.getService();
            final com.google.cloud.translate.Translation translation =
                    translate.translate(a,
                            Translate.TranslateOption.targetLanguage(idioma));
            salida = translation.getTranslatedText();

            return salida;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

    }

}
