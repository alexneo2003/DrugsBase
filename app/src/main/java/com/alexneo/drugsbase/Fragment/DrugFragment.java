package com.alexneo.drugsbase.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexneo.drugsbase.AddictionLevel;
import com.alexneo.drugsbase.Drug;
import com.alexneo.drugsbase.DrugDetailsActivity;
import com.alexneo.drugsbase.Adapter.DrugsAdapter;
import com.alexneo.drugsbase.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class DrugFragment extends Fragment {

    private View view;
    private ListView listView;
    private ProgressView mProgress;
    private TextView titleTextError;
    private TextView textError;
    private Button buttonError;
    private ImageView imageView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_item_layout, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        titleTextError = (TextView) view.findViewById(R.id.network_error_titleText);
        textError = (TextView) view.findViewById(R.id.network_error_text);
        buttonError = (Button) view.findViewById(R.id.network_error_button);
        imageView = (ImageView) view.findViewById(R.id.network_error_image);
        mProgress = (ProgressView) view.findViewById(R.id.loading);

        setHasOptionsMenu(true);

        load();

        return view;
    }

    public void load(){
        final String urlString = "http://jesuscodes.me/drugs/list.json";
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void[] params) {
                StringBuilder sb = new StringBuilder();
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    int responseCode;

                    urlConnection.setConnectTimeout(10000);
                    urlConnection.setReadTimeout(10000);

                    responseCode = urlConnection.getResponseCode();

                    if (responseCode == 200){
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }

                        bufferedInputStream.close();
                    }

                }
                catch (UnknownHostException netError){
                    return null;
                }

                catch (Exception e) {
                    System.out.println("Ошибка " + e);
                }

                String json = sb.toString();

                drugsList = new Gson().fromJson(json, new TypeToken<ArrayList<Drug>>() {
                }.getType());

                return "";

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                hideError();
                mProgress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

                if (s == null){
                    showError();
                }

                drugsList.get(0).cover = "http://www.snopes.com/wp-content/uploads/2015/09/ecstasy-1.jpg";
                drugsList.get(0).description = "бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... ";
                drugsList.get(0).cautions = "бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... ";
                drugsList.get(0).affect = "бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... ";
                drugsList.get(0).usage = "бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... ";
                drugsList.get(1).description = "бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... ";
                drugsList.get(1).cautions = "бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... ";
                drugsList.get(1).affect = "бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... ";
                drugsList.get(1).usage = "бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... бла бла бла... ";
                drugsList.get(2).name = "Метамфетамин";
                drugsList.get(2).cover = "http://cs.pikabu.ru/images/big_size_comm/2011-12_5/13247094044991.jpg";
                drugsList.get(3).name = "Эфедрин";
                drugsList.get(3).cover = "http://narcolikvidator.ru/wp-content/uploads/2011/09/Ephedrine-narcolikvidator.jpg";
                drugsList.get(4).name = "Кокаин";
                drugsList.get(4).cover = "http://image.tsn.ua/media/images2/original/Sep2012/383673230.jpg";
                drugsList.get(5).name = "Псилоцибин";
                drugsList.get(5).cover = "http://excitermag.net/wp-content/uploads/2012/12/magic-mushrooms.jpg";

                final DrugsAdapter drugsAdapter = new DrugsAdapter(getActivity(), drugsList);
                listView.setAdapter(drugsAdapter);
            }
        }.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Drug drug = drugsList.get(position);
                startActivity(DrugDetailsActivity.getActivityIntent(getActivity(), drug));

                Log.d("mainList", "item click " + position);
            }
        });

    }

    public void showError() {
        listView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        titleTextError.setVisibility(View.VISIBLE);
        textError.setVisibility(View.VISIBLE);
        buttonError.setVisibility(View.VISIBLE);

        buttonError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
    }

    public void hideError(){
        mProgress.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        titleTextError.setVisibility(View.GONE);
        textError.setVisibility(View.GONE);
        buttonError.setVisibility(View.GONE);
    }


}
