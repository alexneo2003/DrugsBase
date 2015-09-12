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
public class DrugsItemsAdapter extends BaseAdapter{

    List<DrugsItems> list;
    LayoutInflater layoutInflater;

    public DrugsItemsAdapter(Context context, List<DrugsItems> list) {
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

        DrugsItems drugsItems = getDrugs(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(drugsItems.getNameDrugs());

        return view;
    }

    private DrugsItems getDrugs(int position) {
        return ((DrugsItems) getItem(position));
    }

}