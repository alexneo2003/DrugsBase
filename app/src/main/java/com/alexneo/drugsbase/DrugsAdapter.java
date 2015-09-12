package com.alexneo.drugsbase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alex Neo on 09.09.2015.
 */
public class DrugsAdapter extends BaseAdapter{

    List<Drug> list;
    LayoutInflater layoutInflater;

    public DrugsAdapter(Context context, List<Drug> list) {
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
        textView.setText(drug.name);

        return view;
    }

    private Drug getDrugs(int position) {
        return ((Drug) getItem(position));
    }

}