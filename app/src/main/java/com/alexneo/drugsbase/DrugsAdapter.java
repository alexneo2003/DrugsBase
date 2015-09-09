package com.alexneo.drugsbase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Alex Neo on 09.09.2015.
 */
public class DrugsAdapter extends BaseAdapter{

    Context ctx;
    LayoutInflater Inflater;
    ArrayList<Drugs> objects;

    DrugsAdapter(Context context, ArrayList<Drugs> drugs){
        ctx = context;
        objects = drugs;
        Inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = Inflater.inflate(R.layout.item_layout, parent, false);
        }

        Drugs d = getDrugs (position);

        ((TextView) view.findViewById(R.id.textView)).setText(d.drugs);

        return view;
    }

    Drugs getDrugs(int position) {
        return ((Drugs) getDrugs(position));
    }

}
