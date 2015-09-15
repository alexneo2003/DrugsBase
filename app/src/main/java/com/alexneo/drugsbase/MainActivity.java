package com.alexneo.drugsbase;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.tabcontent;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT =  R.layout.activity_main;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    List<Drug> drugsList = new ArrayList<Drug>(){{
        /*add(new Drug("LSD"){{
            description = "Выглядит как порошок или таблетки разных форм и цветов";
            usage = "Таблетка ложится под язык, рассасывается 2-3 минуты.";
            affect = "Сильные галюцинации, начинаешь видеть провалы в стенах.";
            cautions = "Помутнение рассудка, слабость, длится двое суток";
            addiction = AddictionLevel.High;
            price = 100;
        }});*/
        add(new Drug("Марихуана",
                "Галюциноген",
                "Курение",
                "Помутнение разума",
                "Нет",
                AddictionLevel.Medium,
                20,
                "lsd"));
        add(new Drug("Марихуана",
                "Галюциноген",
                "Курение",
                "Помутнение разума",
                "Нет",
                AddictionLevel.Medium,
                20,
                "lsd"));
        add(new Drug("Марихуана",
                "Галюциноген",
                "Курение",
                "Помутнение разума",
                "Нет",
                AddictionLevel.Medium,
                20,
                "lsd"));
        add(new Drug("Марихуана",
                "Галюциноген",
                "Курение",
                "Помутнение разума",
                "Нет",
                AddictionLevel.Medium,
                20,
                "lsd"));
        add(new Drug("Марихуана",
                "Галюциноген",
                "Курение",
                "Помутнение разума",
                "Нет",
                AddictionLevel.Medium,
                20,
                "lsd"));
        add(new Drug("Марихуана",
                "Галюциноген",
                "Курение",
                "Помутнение разума",
                "Нет",
                AddictionLevel.Medium,
                20,
                "lsd"));
        add(new Drug("Марихуана",
                "Галюциноген",
                "Курение",
                "Помутнение разума",
                "Нет",
                AddictionLevel.Medium,
                20,
                "lsd"));
        add(new Drug("LSD",
                "Выглядит как порошок или таблетки разных форм и цветов",
                "Таблетка ложится под язык, рассасывается 2-3 минуты.",
                "Помутнение рассудка, слабость, длится двое суток",
                "Передозировка",
                AddictionLevel.High,
                100,
                "212"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppDefault);
        setContentView(LAYOUT);

        initToolbar();
        initNavigationView();

/*        ImageView imageView = (ImageView) findViewById(R.id.item_layout_image);
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lsd);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //Default
            imageView.setBackgroundResource(R.drawable.lsd);
        } else {
            //RoundCorners
            RoundCornersDrawable round = new RoundCornersDrawable(mBitmap,
                    getResources().getDimension(R.dimen.cardview_radius), 0); //or your custom radius

            CardView cardView = (CardView) findViewById(R.id.card_view);
            cardView.setPreventCornerOverlap(false); //it is very important

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                imageView.setBackground(round);
            else
                imageView.setBackgroundDrawable(round);
        }*/

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

                Log.d("click", "main item click");
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

/*    @Override
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
    }*/
}
