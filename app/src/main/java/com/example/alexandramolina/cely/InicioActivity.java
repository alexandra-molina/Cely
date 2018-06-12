package com.example.alexandramolina.cely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InicioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    SharedPreferences sharedPreferences;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,new PrincipalActivity()).commit();

        setNavigationViewListner();
        nv=findViewById(R.id.navigation_view);



        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#233a62")));

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                sharedPreferences = getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("authentication_token", "").apply();
                sharedPreferences.edit().putString("id", "").apply();
                sharedPreferences.edit().putString("email", "").apply();
                sharedPreferences.edit().putString("name", "").apply();
                sharedPreferences.edit().putString("imagen", "").apply();
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
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,new  Translation()).commit();
    }

    public void abrirActivityPrincipal(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,new PrincipalActivity()).commit();
    }

    public void abrirActivityUsuario(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,new UsuarioActivity()).commit();

    }
    public void abrirActivityArchivos(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,new ArchivosActivity()).commit();
    }
    public void abrirActivityNoticias(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,new NoticiasActivity()).commit();
    }
    public void abrirActivityTraductor(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,new TraductorActivity()).commit();
    }
    public void abrirActivityBuscarNoticia(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,new BuscarNoticiaActivity()).commit();
    }
    public void abrirActivityGPS(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,new GPSActivity()).commit();
    }
    public void abrirMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
