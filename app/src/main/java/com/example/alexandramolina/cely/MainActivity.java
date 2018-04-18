package com.example.alexandramolina.cely;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView txt_ayuda;
    private TextView txt_registarse;
    private Button btn_iniciarSesion;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#233a62")));

        txt_ayuda = findViewById(R.id.txt_ayuda);
        txt_registarse = findViewById(R.id.txt_registrarse);
        btn_iniciarSesion = findViewById(R.id.btn_iniciarSesion);

        txt_ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityAyuda();
            }
        });
        txt_registarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityRegistrase();
            }
        });
        btn_iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrirActivityTraductor();
                //abrirActivityPrincipal();
                abrirActivityBienvenido();
            }
        });
    }
    public void abrirActivityAyuda(){
        Intent intent = new Intent(this, AyudaActivity.class);
        startActivity(intent);
    }
    public void abrirActivityRegistrase(){
        registro();
        Intent intent = new Intent(this, RegistrarseActivity.class);
        startActivity(intent);
    }

    public void abrirActivityTraductor(){
        Intent intent = new Intent(this, Translation.class);
        startActivity(intent);
    }
    public void abrirActivityPrincipal(){
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }
    public void abrirActivityBienvenido(){
        iniciarSesion();
        Intent intent = new Intent(this, BienvenidoActivity.class);
        startActivity(intent);
    }

    public void iniciarSesion(){

        StringRequest loginRequest = new StringRequest(Request.Method.POST, "https://celytranslator.herokuapp.com/v1/sessions", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Pruebaaaa",response);
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
                params.put("email","andrem.san12@gmail.com");
                params.put("password","123475");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(loginRequest);

    }
    public void registro(){

        StringRequest registroRequest = new StringRequest(Request.Method.POST, "https://celytranslator.herokuapp.com/v1/registrations", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Pruebaaaa",response);
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
                params.put("email","andrem.san12@gmail.com");
                params.put("password","1234756");
                params.put("name","La trucha");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(registroRequest);

    }


}
