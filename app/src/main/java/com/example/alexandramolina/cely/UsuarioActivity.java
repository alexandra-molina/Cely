package com.example.alexandramolina.cely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class UsuarioActivity extends android.support.v4.app.Fragment {


    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_usuario,container,false);
        TextView emailTx = view.findViewById(R.id.Email);
        TextView textView = view.findViewById(R.id.textView11);
        ImageView iv = view.findViewById(R.id.imageView8);
        sharedPreferences = getActivity().getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String name = sharedPreferences.getString("name", "");
        String imagen = sharedPreferences.getString("imagen", "");
        URL profile_picture = null;
        try {
            profile_picture = new URL(imagen);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(!imagen.equals("")) {
            Picasso.with(getActivity().getApplicationContext()).load(profile_picture.toString()).into(iv);
        }
        emailTx.setText(email);
        textView.setText(name);


        return view;
    }


}
