package com.example.alexandramolina.cely;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Translation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ArrayList<String> textos=new ArrayList<>();
    private String traduccion="";
    private DownloadTask dt;
    private TranslationTask tt;
    private Spinner spinner;
    //private ProgressBar progressBar;
    private String idioma ="spa";
    private ProgressDialog pDialog;
    private EditText link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transalation);

        spinner = findViewById(R.id.spinner);
        //progressBar = findViewById(R.id.progress_bar);
        //progressBar.setVisibility(View.INVISIBLE);
        link = findViewById(R.id.link);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Idiomas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int position, long id) {
                String text = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT);
                switch(position){
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
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





    }

    public void translate(View view){

        traducir();
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
        int id=item.getItemId();
        return false;
    }

    public void traducir(){
        //progressBar.setVisibility(View.VISIBLE);
        dt= new DownloadTask();
        tt=new TranslationTask();
        Document document=null;
        Elements nombres=null;
        try {
            String html= dt.execute(link.getText().toString()).get();
            document = Jsoup.parse(html);
            nombres = document.select("div");

            for (int i = 0; i < nombres.size(); i++) {
                textos.add(nombres.get(i).text());
            }

            Set<String> hs = new HashSet<>();
            hs.addAll(textos);
            textos.clear();
            textos.addAll(hs);
            textos= tt.execute(textos).get();

            for(String s:textos){
                Log.d("PRUEBA",s);
                traduccion =traduccion + s + "\n \n";

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    public class TranslationTask extends AsyncTask<ArrayList<String>, Integer, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... urls) {

            ArrayList<String> a=urls[0];
            ArrayList<String> salida=new ArrayList<>();
            for(String s:a){
                TranslateOptions options = TranslateOptions.newBuilder()
                        .setApiKey("AIzaSyBArteM1ejf2C6fCBZojg0JZkD7ZZ9IiQk")
                        .build();
                Translate translate = options.getService();
                final com.google.cloud.translate.Translation translation =
                        translate.translate(s,
                                Translate.TranslateOption.targetLanguage(idioma));
                salida.add(translation.getTranslatedText());
            }
            for(String s:salida){
                Log.d("PRUEBA",s);
            }


            return salida;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            //progressBar.setVisibility(View.GONE);
            //pDialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

/*            pDialog = new ProgressDialog(Translation.this,
                    ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
            pDialog.setTitle("Please wait");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Loading data...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();*/
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


                while((input = reader.readLine()) != null){
                    stringBuilder.append("\n");
                    stringBuilder.append(input);
                }

                reader.close();
                inputStreamReader.close();

                result = stringBuilder.toString();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }





}
