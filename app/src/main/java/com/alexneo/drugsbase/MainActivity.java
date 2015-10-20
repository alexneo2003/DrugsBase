package com.alexneo.drugsbase;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alexneo.drugsbase.Adapter.TabsPagerFragmentAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity{

    private static final int LAYOUT =  R.layout.activity_main;

    boolean doubleBackToExitPressedOnce = false;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private MaterialSearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppDefault);
        setContentView(LAYOUT);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.placeholder)
//                .displayer(new RoundedBitmapDisplayer(4))
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);

        toolbar = (Toolbar) findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setCursorDrawable(R.drawable.color_cursor_primary);


//        initToolbar();
        initNavigationView();
        initTabs();
    }

/*    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_view);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onOptionsItemSelected(item);
                return true;
            }
        });
        toolbar.inflateMenu(R.menu.menu_search);
    }*/

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_nawigation_open, R.string.view_nawigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.actionDrugItem:
                        showDrugTab();
                        break;

                    case R.id.actionWeaponItem:
                        showWeaponTab();
                        break;

                    case R.id.actionAlcoholItem:
                        showAlcoholTab();
                        break;

                    case R.id.actionSearchItem:
                        showSearch();
                        break;
                }
                return true;
            }
        });

    }

    private void showDrugTab(){
        viewPager.setCurrentItem(0);
    }

    private void showWeaponTab(){
        viewPager.setCurrentItem(1);
    }
    private void showAlcoholTab(){
        viewPager.setCurrentItem(2);
    }
    private void showSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu);

//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);

//        if (!query.isEmpty()) searchView.setQuery(query, true);
//
//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override public boolean onQueryTextSubmit(String query) {
//                SearchActivity.this.query = query;
//                sendEvent("search", "click", "query");
//                if (searchFragment != null) {
//                    searchFragment.search(query);
//                } else {
//                    searchFragment = SearchFragment.getSearch(query);
//                    getSupportFragmentManager() .beginTransaction() .add(R.id.container, searchFragment) .commit();
//                } return true;
//            }
//
//
//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //Do some magic
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //Do some magic
//
//                return false;
//            }
//        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }
        if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.back_pressed, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
    }

}
