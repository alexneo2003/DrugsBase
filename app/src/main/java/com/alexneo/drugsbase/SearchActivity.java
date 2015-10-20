package com.alexneo.drugsbase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class SearchActivity extends AppCompatActivity {

    private MaterialSearchView searchView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar_view);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white_transparent));
        toolbar.setTitle("");

        onSearchViewStyling();
        setSupportActionBar(toolbar);

    }

    public void onSearchViewStyling(){
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        searchView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        searchView.setTextColor(getResources().getColor(R.color.white));
        searchView.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setBackIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        searchView.setCloseIcon(getResources().getDrawable(R.mipmap.ic_close));
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.showSearch();

        return true;
    }

    public void onBackPressed() {
        if (searchView.isSearchOpen()){
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
