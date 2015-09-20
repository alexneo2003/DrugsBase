package com.alexneo.drugsbase.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexneo.drugsbase.AddictionLevel;
import com.alexneo.drugsbase.Drug;
import com.alexneo.drugsbase.DrugDetailsActivity;
import com.alexneo.drugsbase.DrugsAdapter;
import com.alexneo.drugsbase.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DrugFragment extends Fragment{

    private static final int LAYOUT =  R.layout.list_item_layout;

    private View view;
    private ListView listView;



    List<Drug> drugsList = new ArrayList<Drug>(){{
        add(new Drug(21,
                "Марихуана",
                "Галюциноген",
                "Курение",
                "Помутнение разума",
                "Нет",
                AddictionLevel.Medium,
                20,
                "lsd"));
        add(new Drug(15,
                "LSD",
                "Выглядит как порошок или таблетки разных форм и цветов",
                "Таблетка ложится под язык, рассасывается 2-3 минуты.",
                "Помутнение рассудка, слабость, длится двое суток",
                "Передозировка",
                AddictionLevel.High,
                100,
                "212"));
        add(new Drug(15,
                "LSD",
                "Выглядит как порошок или таблетки разных форм и цветов",
                "Таблетка ложится под язык, рассасывается 2-3 минуты.",
                "Помутнение рассудка, слабость, длится двое суток",
                "Передозировка",
                AddictionLevel.High,
                100,
                "212"));
        add(new Drug(15,
                "LSD",
                "Выглядит как порошок или таблетки разных форм и цветов",
                "Таблетка ложится под язык, рассасывается 2-3 минуты.",
                "Помутнение рассудка, слабость, длится двое суток",
                "Передозировка",
                AddictionLevel.High,
                100,
                "212"));
        add(new Drug(15,
                "LSD",
                "Выглядит как порошок или таблетки разных форм и цветов",
                "Таблетка ложится под язык, рассасывается 2-3 минуты.",
                "Помутнение рассудка, слабость, длится двое суток",
                "Передозировка",
                AddictionLevel.High,
                100,
                "212"));
        add(new Drug(15,
                "LSD",
                "Выглядит как порошок или таблетки разных форм и цветов",
                "Таблетка ложится под язык, рассасывается 2-3 минуты.",
                "Помутнение рассудка, слабость, длится двое суток",
                "Передозировка",
                AddictionLevel.High,
                100,
                "212"));
        add(new Drug(15,
                "LSD",
                "Выглядит как порошок или таблетки разных форм и цветов",
                "Таблетка ложится под язык, рассасывается 2-3 минуты.",
                "Помутнение рассудка, слабость, длится двое суток",
                "Передозировка",
                AddictionLevel.High,
                100,
                "212"));
    }};

    public static DrugFragment getInstance(){
        Bundle args = new Bundle();
        DrugFragment fragment = new DrugFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

        listView = (ListView) view.findViewById(R.id.listView);



        final String urlString = "http://jesuscodes.me/drugs/list.json";
        // и тут
        new AsyncTask<Void, Void, String>(){

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                final DrugsAdapter drugsAdapter = new DrugsAdapter(getActivity(), drugsList);
                listView.setAdapter(drugsAdapter);

            }

            @Override
            protected String doInBackground(Void[] params) {
                StringBuilder sb = new StringBuilder();
                try {
                    // у тебя тут урл
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    int responseCode;

                    urlConnection.setConnectTimeout(10000);
                    urlConnection.setReadTimeout(10000);

                    responseCode = urlConnection.getResponseCode();

                    if ( responseCode == 200)
                    {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }

                        bufferedInputStream.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                String json = sb.toString();

                drugsList = new Gson().fromJson(json, new TypeToken<ArrayList<Drug>>() {
                }.getType());

                return "";

            }
        }.execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Drug drug = drugsList.get(position);
                startActivity(DrugDetailsActivity.getActivityIntent(getActivity(), drug));
                //getActivity().finish();

                Log.d("click", "main item click");
            }
        });

        return view;

    }

}
