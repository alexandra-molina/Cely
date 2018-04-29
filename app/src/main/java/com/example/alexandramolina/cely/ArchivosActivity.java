package com.example.alexandramolina.cely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ArchivosActivity  extends android.support.v4.app.Fragment{
    ArrayList<News> news = new ArrayList<>();
    NoticiasActivity.DownloadTask downloadTask;
    NewsAdapter adapter;
    GridView gridView3;
    ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    SharedPreferences sharedPreferences;
    NavigationView nv;
    String authentication_token;
    String user_id="";
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_archivos,container,false);

        gridView3= view.findViewById(R.id.gridView4);
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DownloadTask downloadTask = new DownloadTask();
                String status = "";
                String message = "";
                try {
                    JSONObject json = new JSONObject(downloadTask.execute("https://celytranslate.herokuapp.com/v1/noticias/getLines/?id_user=" + user_id + "&authentication_token=" + authentication_token).get());
                    status = json.getString("status");
                    Log.d("MEsssage", status);
                    if (status.equals("Success")) {
                        authentication_token = json.getString("authentication_token");
                        JSONArray jsonArray = new JSONArray(json.getString("data"));


                        for (int x = 0; x < jsonArray.length(); x++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(x);
                            Log.d("JSON",jsonObject1.toString());
                            String texto = jsonObject1.getString("texto");
                            String tipo = jsonObject1.getString("tipo");
                            String imagen = jsonObject1.getString("tipo");

                            news.get(i).getImagenes().add(imagen);
                            news.get(i).getTipos().add(tipo);
                            news.get(i).getTextos().add(texto);

                        }
                        sharedPreferences = getActivity().getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("authentication_token", authentication_token).apply();
                        abrirActivityTraductor(news.get(i).getTextos(),news.get(i).getImagenes(),news.get(i).getTipos());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }});

        sharedPreferences = getActivity().getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("id", "");
        authentication_token = sharedPreferences.getString("authentication_token", "");

        obtenerNoticias();


        return view;
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

        }
    }

    private void obtenerNoticias() {

        DownloadTask downloadTask = new DownloadTask();
        String status = "";
        String message = "";
        try {
            JSONObject json = new JSONObject(downloadTask.execute("https://celytranslate.herokuapp.com/v1/noticias/?id_user=" + user_id + "&authentication_token=" + authentication_token).get());
            status = json.getString("status");
            Log.d("MEsssage", status);
            if (status.equals("Success")) {
                authentication_token = json.getString("authentication_token");
                JSONArray jsonArray = new JSONArray(json.getString("data"));


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String titulo = jsonObject1.getString("title");
                    String ide = jsonObject1.getString("id");
                    Log.d("Titulo", titulo);
                    news.add(new News(titulo, ""));
                    news.get(i).setId(ide);
                }
                sharedPreferences = getActivity().getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("authentication_token", authentication_token).apply();

                adapter = new NewsAdapter(getActivity().getApplicationContext(), R.layout.newslistview, news);
                gridView3.setAdapter(adapter);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

        public void abrirActivityTraductor(ArrayList<String> textos,ArrayList<String> imagenes, ArrayList<String> tipos){
            Intent intent = new Intent(getActivity().getApplicationContext(), NoticiaTraducidaActivity.class);
            intent.putExtra("texto",textos);
            intent.putExtra("tipo",tipos);
            intent.putExtra("imagen",imagenes);
            startActivity(intent);
        }


}
