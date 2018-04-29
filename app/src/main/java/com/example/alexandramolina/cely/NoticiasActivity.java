package com.example.alexandramolina.cely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NoticiasActivity  extends android.support.v4.app.Fragment {

    String linkP1 = "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=6ea91d289e6e4e53adb8eec7b039bc97";
    String linkP = "";
    String l = "";
    ArrayList<News> news = new ArrayList<>();
    NewsAdapter adapter;
    GridView gridView3;
    ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    SharedPreferences sharedPreferences;
    NavigationView nv;
    String page = "";
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_usuario,container,false);

        gridView3 = view.findViewById(R.id.gridView3);
        buscarNoticia();

        return view;
    }


    public void buscarNoticia(){
        l = linkP1;
        DownloadTask downloadTask = new DownloadTask();
        try {

            JSONObject jsonObject = new JSONObject(downloadTask.execute(l).get());

            JSONArray jsonArray = new JSONArray(jsonObject.getString("articles"));

            for(int i = 0; i < 20;i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String titulo = jsonObject1.getString("title");
                String url = jsonObject1.getString("url");

                news.add(new News(titulo, url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        adapter = new NewsAdapter(getActivity().getApplicationContext(), R.layout.newslistview,news);
        gridView3.setAdapter(adapter);
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                page = news.get(i).getLink();
                browser();
            }
        });


    }

    public void browser(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(page));
        startActivity(browserIntent);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                int n=0;
                while (data != -1) {
                    if (n > 6) {
                        char current = (char) data;
                        result += current;
                        data = inputStreamReader.read();
                    } else {
                        n++;
                    }
                }
                Log.i("info",result);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);

                Log.i("jsonObject", result);



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
