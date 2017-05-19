package com.elise.filmotec;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Elise on 18/05/2017.
 */

public class Adapter extends ArrayAdapter<Item> {
    private Activity activity;
    int id;
    ArrayList<Item> items;

    public Adapter(Activity context, int resource, ArrayList<Item> objects) {
        super(context, resource, objects);
        this.activity=context;
        this.id=resource;
        this.items=objects;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater=activity.getLayoutInflater();
            convertView=inflater.inflate(id,null);

        }
        Item item=items.get(position);
        // TextView tv_id = (TextView) convertView.findViewById(R.id.tv_id);
        TextView tv_titre = (TextView) convertView.findViewById(R.id.tv_titre);
        TextView tv_annee = (TextView) convertView.findViewById(R.id.tv_annee);

        // tv_id.setText(item.getId());
        tv_titre.setText(item.getTitre());
        tv_annee.setText(item.getAnnee());
        return convertView;

    }
}

