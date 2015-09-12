package com.alexneo.drugsbase;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        final DrugsItemsAdapter drugsItemsAdapter = new DrugsItemsAdapter(this, initData());
//        nameDrugs.add(new  DrugsItems("LSD"));
        listView.setAdapter(drugsItemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), DrugItemActivity.class);
                intent.putExtra("title", DrugsItems.getNameDrugs());
                startActivity(intent);

                Log.d("click", "click");
            }
        });
    }

    private List<DrugsItems> initData(){
        List<DrugsItems> list = new ArrayList<>();

        list.add(new DrugsItems("LSD"));
        list.add(new DrugsItems("LSD2"));
        list.add(new DrugsItems("LSD3"));
        list.add(new DrugsItems("LSD4"));
        list.add(new DrugsItems("LSD5"));

        return list;

    }


/*        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.drugs, android.R.layout.simple_list_item_1);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });*/




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View view = LayoutInflater.from(this).inflate(R.layout.about, null);
        new AlertDialog.Builder(this).setTitle(R.string.about).setView(view).setPositiveButton(R.string.ok, null).create().show();

        return super.onOptionsItemSelected(item);
    }
}
