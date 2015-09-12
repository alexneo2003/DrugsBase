package com.alexneo.drugsbase;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<Drug> drugsList = new ArrayList<Drug>(){{
        add(new Drug("LSD"){{
            description = "Выглядит как порошок или таблетки разных форм и цветов";
            usage = "Таблетка ложится под язык, рассасывается 2-3 минуты.";
            affect = "Сильные галюцинации, начинаешь видеть провалы в стенах.";
            causions = "Помутнение рассудка, слабость, длится двое суток";
            addiction = AddictionLevel.High;
            price = 100;
            // наверно побочные эффекты тоже стоит писать
            // и какой эффект даёт
        }});
        add(new Drug("LSD2"));
        add(new Drug("LSD3"));
        add(new Drug("LSD4"));
        add(new Drug("LSD5"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        // допустим у нас есть наркотик
        Drug myDrug = new Drug("Мой новый наркотик");

        String name = myDrug.getName();
        // вот мы сделали новый наркотик и взяли у него имя
        // допустим мы хотим, чтобы наркотики создавались сами без четкого объявления

        // таким образом мы можем просить наркотик создаваться самому.
        // как думаешь, где это можно использовать на деле?
        // хз))
        Drug myAnotherDrug = Drug.createDrug();

        String myAnotherName = myAnotherDrug.getName();*/


        ListView listView = (ListView) findViewById(R.id.listView);
        final DrugsAdapter drugsAdapter = new DrugsAdapter(this, drugsList);
//        nameDrugs.add(new  Drug("LSD"));
        listView.setAdapter(drugsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // у меня просто клавиши от мака и винды путаются =(
                // в общем. по клику передается переменная position
                // т.к. у нас есть список, то мы должны найти эту наркоту в списке по этому позишну
                // и передать в активити
                // почему у тебя вдруг выскочила та ошибка...
                // привожу пример как используется статические переменные и методы
                Drug drug = drugsList.get(position);
                startActivity(DrugDetailsActivity.getActivityIntent(getBaseContext(), drug));

                Log.d("click", "click");
            }
        });
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
