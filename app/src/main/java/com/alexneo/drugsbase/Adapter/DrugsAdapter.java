package com.alexneo.drugsbase.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexneo.drugsbase.Drug;
import com.alexneo.drugsbase.Fragment.DrugFragment;
import com.alexneo.drugsbase.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class DrugsAdapter extends BaseAdapter{

    Context context;
    List<Drug> list;
    LayoutInflater layoutInflater;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.item_layout, container, false);
    }

    public DrugsAdapter (Context context, List<Drug> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_layout, parent, false);

        }

        Drug drug = getDrugs(position);
        TextView textView = (TextView) view.findViewById(R.id.title);
        ImageView coverView = (ImageView) view.findViewById(R.id.cover);

        textView.setText(drug.name);
        String cover = drug.cover;
        ImageLoader.getInstance().displayImage(cover, coverView);


        return view;
    }

    private Drug getDrugs(int position) {
        return ((Drug) getItem(position));
    }

}