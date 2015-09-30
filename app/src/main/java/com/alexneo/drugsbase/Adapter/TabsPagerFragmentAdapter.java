package com.alexneo.drugsbase.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alexneo.drugsbase.Fragment.DrugFragment;
import com.alexneo.drugsbase.R;
import com.alexneo.drugsbase.views.IconTabProvider;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter implements IconTabProvider{

    private String[] tabs;
    private int[] imageResId = {
        R.drawable.ic_drugs,
        R.drawable.ic_weapon,
        R.drawable.ic_alcohol,
    };

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        tabs = new String[]{
                "Drugs",
                "Weapons",
                "Alcohol",
        };

    }

    @Override
    public int getPageIcon(int position) {
        return imageResId[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DrugFragment.getInstance();
            case 1:
                return DrugFragment.getInstance();
            case 2:
                return DrugFragment.getInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }


}
