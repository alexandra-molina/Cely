package com.example.alexandramolina.cely;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GPSActivity extends android.support.v4.app.Fragment{


    LocationManager locationManager;
    LocationListener locationListener;
    TextView placeName;
    Location lastLocation;
    String linkP1 = "https://newsapi.org/v2/top-headlines?country=";
    String apiKey = "&apiKey=6ea91d289e6e4e53adb8eec7b039bc97";
    String linkP = "";
    String l = "";
    ArrayList<News> news = new ArrayList<>();
    GPSActivity.DownloadTask downloadTask;
    NewsAdapter adapter;
    GridView gridView2;
    String codigo = "US";
    String page = "";
    View view;

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_gps,container,false);

        placeName = view.findViewById(R.id.placeName);
        gridView2 = view.findViewById(R.id.gridView2);

        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 0);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Country();

        }
        buscarNoticia();
        return view;
    }


    public void Country() {
        String pais = null;
        LocationManager lm = (LocationManager)getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
        for(String provider: lm.getAllProviders()) {
            @SuppressWarnings("ResourceType") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Log.d("ADRESSES:",addresses.toString());
                    if(addresses != null && addresses.size() > 0) {
                        pais = addresses.get(0).getCountryName();
                        codigo = addresses.get(0).getCountryCode();
                        Log.d("CODIGO:", codigo);
                        placeName.setText(pais);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Toast.makeText(getActivity().getApplicationContext(), pais, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }

        }
    }
    public void buscarNoticia(){
        linkP = codigo.toLowerCase();
        l = linkP1+linkP+apiKey;
        downloadTask = new GPSActivity.DownloadTask();
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
        gridView2.setAdapter(adapter);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
