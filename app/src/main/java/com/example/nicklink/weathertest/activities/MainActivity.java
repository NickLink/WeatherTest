package com.example.nicklink.weathertest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nicklink.weathertest.R;
import com.example.nicklink.weathertest.adapters.RecyclerClick;
import com.example.nicklink.weathertest.adapters.WeatherAdapter;
import com.example.nicklink.weathertest.application.AppClass;
import com.example.nicklink.weathertest.presenters.MainPresenter;
import com.example.nicklink.weathertest.presenters.MainPresenterImpl;
import com.example.nicklink.weathertest.presenters.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView, RecyclerClick { //  implements NavigationView.OnNavigationItemSelectedListener

    @BindView(R.id.city_list)
    ListView cityList;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.daysGroup)
    RadioGroup daysGroup;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new MainPresenterImpl(this);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                presenter.onCitySelected(position);
            }
        });

        daysGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                presenter.onDaysSelected(checkedId);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshData();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            presenter.showSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void toggleDrawer(int toggleStatus) {
        if (toggleStatus == Gravity.LEFT)
            drawer.closeDrawer(toggleStatus);
    }

    @Override
    public void setCityAdapter(ArrayAdapter<String> adapter) {
        cityList.setAdapter(adapter);
    }

    @Override
    public void setWeatherAdapter(WeatherAdapter adapter) {
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
    }

    @Override
    public void setToolBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void showLoadingIndicator() {
        recyclerView.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingIndicator() {
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String city = data.getStringExtra("name");
        presenter.addCity(city);
    }

    @Override
    public void onClick(int position) {
        presenter.showDetailInfo(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setCitySelected(int position) {
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityList.setItemChecked(position, true);
    }

    @Override
    public void setDaysSelected(int id) {
        switch (AppClass.getDB().getDefaultDays()){
            case 7:
                daysGroup.check(R.id.radio_7);
                break;
            default:
                daysGroup.check(R.id.radio_3);
                break;
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
