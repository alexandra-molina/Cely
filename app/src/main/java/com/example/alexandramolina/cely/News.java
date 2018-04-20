package com.example.alexandramolina.cely;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by alexandramolina on 18/4/18.
 */

public class News {
    private String title;
    private String link;
    private String id;
    private ArrayList<String > textos= new ArrayList<String>();
    private ArrayList<String > imagenes= new ArrayList<String>();
    private ArrayList<String > tipos= new ArrayList<String>();


    public News(String title, String link) {
        this.title = title;
        this.link = link;

    }

    public ArrayList<String> getTextos() {
        return textos;
    }

    public void setTextos(ArrayList<String> textos) {
        this.textos = textos;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    public ArrayList<String> getTipos() {
        return tipos;
    }

    public void setTipos(ArrayList<String> tipos) {
        this.tipos = tipos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
