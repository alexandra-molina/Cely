package com.example.alexandramolina.cely;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NoticiasLeerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_leer);

        ImageView iv= findViewById(R.id.imageView2);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserABC();
            }
        });
        iv= findViewById(R.id.imageView3);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserBloomberg();
            }
        });
        iv= findViewById(R.id.imageView4);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserBuzzfeed();
            }
        });
        iv= findViewById(R.id.imageView5);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserFox();
            }
        });
        iv= findViewById(R.id.imageView6);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserLeMonde();
            }
        });
        iv= findViewById(R.id.imageView7);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserNational();
            }
        });
    }
    public void browserABC(){
        WebviewActivity.start(this, "http://www.abc.es");
    }
    public void browserBloomberg(){
        WebviewActivity.start(this, "https://www.bloomberg.com");
    }
    public void browserBuzzfeed(){
        WebviewActivity.start(this, "https://www.buzzfeed.com");
    }
    public void browserFox(){
        WebviewActivity.start(this, "https://www.foxsports.com.mx");
    }
    public void browserLeMonde(){
        WebviewActivity.start(this, "https://www.lemonde.fr");
    }
    public void browserNational(){
        WebviewActivity.start(this, "http://www.nationalgeographic.com.es");
    }
}
