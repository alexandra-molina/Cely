package com.example.alexandramolina.cely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;


import org.json.JSONException;
import org.json.JSONObject;
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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Translation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ArrayList<String> textos=new ArrayList<>();
    private DownloadTask dt;
    private TranslationTask tt;
    private Button btn_traducir;
    private EditText txt_link;
    private Spinner spinner;
    private ProgressBar progressBar;
    private String idioma ="spa";
    private EditText link;
    private ArrayList<String> tipos = new ArrayList<>();
    private ArrayList<String> imagenes = new ArrayList<>();
    ActionBar actionBar;
    SharedPreferences sharedPreferences;
    NavigationView nv;
    String authentication_token;
    String id_usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transalation);

        setNavigationViewListner();
        nv=findViewById(R.id.navigation_view);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#233a62")));

        btn_traducir = findViewById(R.id.btnTraducir);
        txt_link = findViewById(R.id.link);
        spinner = findViewById(R.id.spinner);


        link = findViewById(R.id.link);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Idiomas, android.R.layout.simple_spinner_item);
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


        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHeader();

    }

    public void abrirActivityTraductor(){
        Intent intent = new Intent(Translation.this, NoticiaTraducidaActivity.class);
        intent.putExtra("texto",textos);
        intent.putExtra("tipo",tipos);
        intent.putExtra("imagen",imagenes);
        startActivity(intent);
    }


    public void translate(View view){

        traducir();

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
                abrirActivityTraductor2();
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
    public void abrirActivityTraductor2(){
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

    public void traducir(){

        dt= new DownloadTask();
        tt=new TranslationTask();
        Document document=null;
        Elements nombres=null;
        try {


            String html = dt.execute(link.getText().toString()).get();
            if(html == ""){
                Log.d("HTML:", "empty");
            }else {
                document = Jsoup.parse(html);
            }

            String l = link.getText().toString();

            if(l.contains("bbc.com")){
                Log.d("ENTRO:", "BBC");
                nombres = document.select("div.story-body h1, div.story-body__inner p, div.story-body__inner ul li, div.story-body__inner h2, div.story-body__inner figure span img");
            }
            else if(l.contains("businessinsider.com")){
                Log.d("ENTRO:", "Business Insider");
                nombres = document.select("section.container section h1, section.container section.post-content div figure img, section.container section.post-content div li, section.container section.post-content div p");
            }
            else if(l.contains("economist.com")){
                Log.d("ENTRO:", "The Economist");
                nombres = document.select("div.main-content__clearfix h1, div.main-content__clearfix img, div.main-content__clearfix p, div.main-content__clearfix h2");
            }
            else if(l.contains("elmundo.es")){
                Log.d("ENTRO:", "El Mundo");
                nombres = document.select("article div.titles h1, article figure img, article p");
            }
            else if(l.contains("cnn.com")){
                Log.d("ENTRO:", "CNN");
                nombres = document.select("div.l-container h1, div.l-container div.l-container div p, div.l-container div.l-container div.zn-body__paragraph, div.l-container div.l-container div.zn-body__read-all div.zn-body__paragraph");
            }
            else if(l.contains("nacion.com")){
                Log.d("ENTRO:", "La Nacion");
                nombres = document.select("div.headline-hed-last, header.article-header p, div.article-body div div figure img, div.article-body-elements div div.row p.element.element-paragraph");
            }
            else{
                Log.d("ADVERTENCIA:", "Paginas compatibles: Business Insider, BBC, El Mundo, The Economist, La Nacion, CNN");
            }

            //BBC: nombres = document.select("div.story-body h1, div.story-body__inner p, div.story-body__inner ul li, div.story-body__inner h2, div.story-body__inner figure span img");
            //The Economist: nombres = document.select("div.main-content__clearfix h1, div.main-content__clearfix img, div.main-content__clearfix p, div.main-content__clearfix h2");
            //El Mundo: nombres = document.select("article div.titles h1, article figure img, article p");
            //CNN: nombres = document.select("div.l-container h1, div.l-container div.l-container div p, div.l-container div.l-container div.zn-body__paragraph, div.l-container div.l-container div.zn-body__read-all div.zn-body__paragraph");
            //La Nacion: nombres = document.select("div.headline-hed-last, header.article-header p, div.article-body div div figure img, div.article-body-elements div div.row p.element.element-paragraph");
            //Business Insider: nombres = document.select("section.container section h1, section.container section.post-content div figure img, section.container section.post-content div li, section.container section.post-content div p");

            for (int i = 0; i < nombres.size(); i++) {
                 if(nombres.get(i).tagName().equals("img")){
                     textos.add("");
                     imagenes.add(nombres.get(i).attr("src"));
                 }

                else {
                    textos.add(nombres.get(i).text());
                    imagenes.add("");
                }
                tipos.add(nombres.get(i).tagName());
            }

            textos= tt.execute(textos).get();

            sharedPreferences = getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
            id_usuario = sharedPreferences.getString("i", "");
            authentication_token = sharedPreferences.getString("authentication_token", "");

            abrirActivityTraductor();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class TranslationTask extends AsyncTask<ArrayList<String>, Integer, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... urls) {

            ArrayList<String> a=urls[0];
            ArrayList<String> salida=new ArrayList<>();
            for(String s:a){
                if(s != "") {
                    TranslateOptions options = TranslateOptions.newBuilder()
                            .setApiKey("AIzaSyBArteM1ejf2C6fCBZojg0JZkD7ZZ9IiQk")
                            .build();
                    Translate translate = options.getService();
                    final com.google.cloud.translate.Translation translation =
                            translate.translate(s,
                                    Translate.TranslateOption.targetLanguage(idioma));
                    salida.add(translation.getTranslatedText());
                }
            }
            //for(String s:salida){
            //    Log.d("PRUEBA",s);
            //}
            return salida;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            Toast.makeText(getApplicationContext(),"Ha terminado",Toast.LENGTH_LONG);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            String input;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();


                while ((input = reader.readLine()) != null) {
                    stringBuilder.append("\n");
                    stringBuilder.append(input);
                }

                reader.close();
                inputStreamReader.close();

                result = stringBuilder.toString();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
    public void crearNoticia(){

        StringRequest noticiaRequest = new StringRequest(Request.Method.POST, "https://celytranslate.herokuapp.com/v1/noticias", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;

                String id="";
                String status="";
                String message="";
                String authentication_token="";
                try {
                    json = new JSONObject(response);
                    status=json.getString("status");
                    message=json.getString("message");
                    if(status.equals("Success")){
                        JSONObject main = new JSONObject(json.getString("data"));
                        id = main.getString("id");
                        authentication_token = main.getString("authentication_token");
                        sharedPreferences = getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("authentication_token", authentication_token).apply();
                        sharedPreferences.edit().putString("id", id).apply();

                        abrirActivityPrincipal();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Log.d("ERROR",message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",id_usuario);
                params.put("authentication_token",authentication_token);
                params.put("title","");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(noticiaRequest);

    }
}
