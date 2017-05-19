package com.elise.filmotec;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    private DatabaseHandler dbHandler;
    Adapter adapter;
    ArrayList<Item> arrayList = new ArrayList<Item>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHandler = new DatabaseHandler(this);
        try{
            dbHandler.checkAndCopyDatabase();
            dbHandler.openDatabase();
        } catch (SQLException e) {

        }
        try{
                Cursor cursor = dbHandler.QueryData("select * from films");
            if ( cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        Item item=new Item();
                        item.setTitre(cursor.getString(2));
                        item.setAnnee(Integer.parseInt(cursor.getString(3)));
                        arrayList.add(item);
                    }while (cursor.moveToNext());
                }

            }
        } catch (SQLException e) {
            adapter=new Adapter(this, R.layout.custom_list_item, arrayList);
            listView= (ListView) findViewById(R.id.list_item);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_first_layout) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();
        } else if (id == R.id.nav_second_layout) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new SecondFragment()).commit();

        } else if (id == R.id.nav_third_layout) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new ThirdFragment()).commit();

        } else if (id == R.id.nav_fourth_layout) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new FourthFragment()).commit();

        } else if (id == R.id.nav_fifth_layout) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new FifthFragment()).commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
