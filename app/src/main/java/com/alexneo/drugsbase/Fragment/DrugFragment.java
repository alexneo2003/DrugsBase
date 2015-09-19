package com.alexneo.drugsbase.Fragment;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class DrugFragment extends Fragment{

    private static final int LAYOUT =  R.layout.list_item_layout;

    private View view;
    private ListView listView;

    List<Drug> drugsList = new ArrayList<Drug>(){{
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
        final DrugsAdapter drugsAdapter = new DrugsAdapter(getActivity(), drugsList);
        listView.setAdapter(drugsAdapter);

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
