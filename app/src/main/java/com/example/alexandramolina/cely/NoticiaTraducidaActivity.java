package com.example.alexandramolina.cely;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class NoticiaTraducidaActivity extends AppCompatActivity {

    ArrayList<String> text = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();

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
            image = extras.getStringArrayList("imagen");
        }

        //et2.setScroller(new Scroller(getApplicationContext()));
        //et2.setVerticalScrollBarEnabled(true);

        //et2.setMaxLines(1000);

        imprimir();

        //for(String v:value){
        //    et.setText(v);
        //}

    }

    public class DownloadTaskImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    public void imprimir(){

        LinearLayout linearLayout = findViewById(R.id.linearLayout);


        for(int i = 0; i < text.size(); i++){

            String a = text.get(i).replaceAll("&quot;","\"").replaceAll("&#39;", "\'");

            DownloadTaskImage downloadTask = new DownloadTaskImage();

            if(type.get(i).equals("h1")){
                TextView tv = new TextView(this);
                tv.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                tv.setTextColor(Color.BLACK);
                tv.setText(a);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,26);
                tv.setGravity(Gravity.CENTER);
                linearLayout.addView(tv);

            }
            else if(type.get(i).equals("img")){
                ImageView im = new ImageView(this);
                try {
                    im.setImageBitmap(downloadTask.execute(image.get(i)).get());
                    linearLayout.addView(im);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            else if(type.get(i).equals("h2")){
                TextView tv = new TextView(this);
                tv.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                tv.setTextColor(Color.BLACK);
                tv.setText(a);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
                linearLayout.addView(tv);
            }
            else{
                TextView tv = new TextView(this);
                tv.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                tv.setTextColor(Color.BLACK);
                tv.setText(a);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                linearLayout.addView(tv);
            }
        }
    }
}
