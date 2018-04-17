package com.example.alexandramolina.cely;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txt_ayuda;
    private TextView txt_registarse;
    private Button btn_iniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                abrirActivityTraductor();
                //abrirActivityPrincipal();
            }
        });
    }
    public void abrirActivityAyuda(){
        Intent intent = new Intent(this, AyudaActivity.class);
        startActivity(intent);
    }
    public void abrirActivityRegistrase(){
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
}
