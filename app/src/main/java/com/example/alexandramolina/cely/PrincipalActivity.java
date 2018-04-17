package com.example.alexandramolina.cely;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void browserBBC(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bbc.com/news"));
        startActivity(browserIntent);
    }
    public void browserCNN(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://edition.cnn.com"));
        startActivity(browserIntent);
    }
    public void browserLaNacion(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nacion.com"));
        startActivity(browserIntent);
    }
    public void browserBusinessInsider(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.businessinsider.com"));
        startActivity(browserIntent);
    }
    public void browserElMundo(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.elmundo.es"));
        startActivity(browserIntent);
    }
    public void browserEconomist(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.economist.com"));
        startActivity(browserIntent);
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

        if(id == R.id.convertidor){
            Log.d("Entro","convertidor");
            Toast.makeText(this, "Este es el convertidor", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.usuario){
            Toast.makeText(this, "Este es el usuario", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.archivos){
            Toast.makeText(this, "Estas son los archivos", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.paginasSugeridas){
            Toast.makeText(this, "Estas son las paginas sugeridas", Toast.LENGTH_SHORT).show();
        }
        if(id != R.id.noticias){
            Toast.makeText(this, "Estas son las noticias", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.traductor){
            Toast.makeText(this, "Este es el traductor", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.cerrarSesion){
            Toast.makeText(this, "Este es cerrar sesion", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
