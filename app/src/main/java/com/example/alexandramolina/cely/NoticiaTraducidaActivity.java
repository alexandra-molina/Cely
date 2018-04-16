package com.example.alexandramolina.cely;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.ArrayList;

public class NoticiaTraducidaActivity extends AppCompatActivity {

    ArrayList<String> text = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_traducida);

        //final EditText et = findViewById(R.id.et);
        //final EditText et2 = findViewById(R.id.et2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            text = extras.getStringArrayList("texto");
            type = extras.getStringArrayList("tipo");
        }

        //et2.setScroller(new Scroller(getApplicationContext()));
        //et2.setVerticalScrollBarEnabled(true);

        //et2.setMaxLines(1000);

        imprimir();

        //for(String v:value){
        //    et.setText(v);
        //}

    }

    public void imprimir(){

        LinearLayout linearLayout = findViewById(R.id.linearLayout);


        for(int i = 0; i < text.size(); i++){
            for(String s: text) {
                TextView tv = new TextView(this);
                if(type.get(i) == "h1"){
                    tv.setText(s);
                    tv.setTextSize(14);
                    tv.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else{
                    tv.setText(s);
                    linearLayout.addView(tv);
                }
            }
        }

        //for(String s:value){
            //Log.d("PRUEBA:",s);
        //}
    }
}
