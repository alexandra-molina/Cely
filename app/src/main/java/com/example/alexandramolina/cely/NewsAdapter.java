package com.example.alexandramolina.cely;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexandramolina on 18/4/18.
 */

public class NewsAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    private ArrayList<News> arrayList;

    public NewsAdapter(Context context, int layout, ArrayList<News> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView txt_titulo, txt_link;
        ImageView imageView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout,null);
            viewHolder.txt_titulo = view.findViewById(R.id.txt_tituloList);
            viewHolder.txt_link = view.findViewById(R.id.txt_linkList);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        final News news = arrayList.get(i);
        viewHolder.txt_titulo.setText(news.getTitle());
        viewHolder.txt_link.setText(news.getLink());


        return view;
    }
}
