package com.example.alexandramolina.cely;

import android.graphics.Bitmap;

/**
 * Created by alexandramolina on 18/4/18.
 */

public class News {
    private String title;
    private String link;


    public News(String title, String link) {
        this.title = title;
        this.link = link;

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
