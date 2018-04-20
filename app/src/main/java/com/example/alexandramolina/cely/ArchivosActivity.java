package com.example.alexandramolina.cely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

public class ArchivosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos);

        setNavigationViewListner();
        nv=findViewById(R.id.navigation_view);
        gridView3= findViewById(R.id.gridView4);
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
                        sharedPreferences = getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
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

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#233a62")));

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("id", "");
        authentication_token = sharedPreferences.getString("authentication_token", "");

        obtenerNoticias();
        setHeader();


    }

    private void setNavigationViewListner(){
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setHeader(){
        sharedPreferences = getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String name = sharedPreferences.getString("name", "");
        View header = nv.getHeaderView(0);
        TextView headerEmail =  header.findViewById(R.id.headerEmail);
        TextView headerName =  header.findViewById(R.id.headerName);
        headerEmail.setText(email);
        headerName.setText(name);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.convertidor:{
                abrirActivityConvertidor();
                Toast.makeText(this, "Convertidor", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.usuario:{
                abrirActivityUsuario();
                Toast.makeText(this, "Usuario", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.archivos:{
                abrirActivityArchivos();
                Toast.makeText(this, "Archivos", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.paginasSugeridas:{
                abrirActivityPrincipal();
                Toast.makeText(this, "Paginas sugeridas", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.noticias:{
                abrirActivityNoticias();
                Toast.makeText(this, "Noticias", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.traductor:{
                abrirActivityTraductor();
                Toast.makeText(this, "Traductor", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cerrarSesion:{
                abrirMainActivity();
                Toast.makeText(this, "Cerrar sesion", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.noticiaBuscar:{
                abrirActivityBuscarNoticia();
                Toast.makeText(this,"Buscar Noticia", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.GPS:{
                abrirActivityGPS();
                Toast.makeText(this,"GPS", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
    public void abrirActivityConvertidor(){
        Intent intent = new Intent(this, Translation.class);
        startActivity(intent);
    }

    public void abrirActivityPrincipal(){
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

    public void abrirActivityUsuario(){
        Intent intent = new Intent(this, UsuarioActivity.class);
        startActivity(intent);
    }
    public void abrirActivityArchivos(){
        Intent intent = new Intent(this, ArchivosActivity.class);
        startActivity(intent);
    }
    public void abrirActivityNoticias(){
        Intent intent = new Intent(this, NoticiasActivity.class);
        startActivity(intent);
    }
    public void abrirActivityTraductor(){
        Intent intent = new Intent(this, TraductorActivity.class);
        startActivity(intent);
    }
    public void abrirActivityBuscarNoticia(){
        Intent intent = new Intent(this, BuscarNoticiaActivity.class);
        startActivity(intent);
    }
    public void abrirActivityGPS(){
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }
    public void abrirMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
                sharedPreferences = getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("authentication_token", authentication_token).apply();

                adapter = new NewsAdapter(getApplicationContext(), R.layout.newslistview, news);
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
            Intent intent = new Intent(ArchivosActivity.this, NoticiaTraducidaActivity.class);
            intent.putExtra("texto",textos);
            intent.putExtra("tipo",tipos);
            intent.putExtra("imagen",imagenes);
            startActivity(intent);
        }


}
