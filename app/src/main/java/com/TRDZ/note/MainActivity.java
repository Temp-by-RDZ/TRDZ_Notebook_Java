package com.TRDZ.note;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "LIST";
    protected static Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (data==null) data = new Data(); //Это временное обьявление данных
        load(MainActivity.this);
        if (savedInstanceState == null) getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new WindowList()).commit();
        setSupportActionBar(findViewById(R.id.toolbar));
        WindowList winList = (WindowList) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (winList == null) winList = new WindowList();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, winList, FRAGMENT_TAG).commit();
        DrawerLayout drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,findViewById(R.id.toolbar),R.string.ND_OPEN,R.string.ND_CLOSE);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigation = findViewById(R.id.V_navigation);
        navigation.setNavigationItemSelectedListener(item -> { drawer_work(item,drawer); return false; });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basic,menu);
        return super.onCreateOptionsMenu(menu);
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_help) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WindowHelp()).addToBackStack("").commit();
            }
        return super.onOptionsItemSelected(item);
        }

    public void drawer_work(MenuItem item, DrawerLayout drawer) {
        switch (item.getItemId()) {
        case (R.id.menu_help):
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WindowHelp()).addToBackStack("").commit();
            drawer.close();
            break;
        case (R.id.menu_sort):
            data.sort();
            MainActivity.save(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WindowList(), FRAGMENT_TAG).commit();
            drawer.close();
            break;
        case (R.id.menu_exit):
            finish();
            }
        }

    /**
     * Загрузка данных
     */
    protected static void load(Context My_con) {
        SharedPreferences myPreferences = My_con.getApplicationContext().getSharedPreferences("BIG",Context.MODE_PRIVATE);
        try { data.load(
            (ArrayList<Date>) ObjectSerializer.deserialize(myPreferences.getString("T1", ObjectSerializer.serialize(data.saveT1()))),
            (ArrayList<Integer>) ObjectSerializer.deserialize(myPreferences.getString("T2", ObjectSerializer.serialize(data.saveT2()))),
            (ArrayList<Integer>) ObjectSerializer.deserialize(myPreferences.getString("T3", ObjectSerializer.serialize(data.saveT3()))),
            (ArrayList<Integer>) ObjectSerializer.deserialize(myPreferences.getString("T4", ObjectSerializer.serialize(data.saveT4()))),
            (ArrayList<Integer>) ObjectSerializer.deserialize(myPreferences.getString("T5", ObjectSerializer.serialize(data.saveT5()))),
            (ArrayList<String>) ObjectSerializer.deserialize(myPreferences.getString("T6", ObjectSerializer.serialize(data.saveT6()))),
            (ArrayList<String>) ObjectSerializer.deserialize(myPreferences.getString("T7", ObjectSerializer.serialize(data.saveT7())))
                );
            }
        catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
        }

    /**
     * Сохранение данных
     */
    public static void save(Context My_con) {
        SharedPreferences myPreferences = My_con.getApplicationContext().getSharedPreferences("BIG",Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        try {
            myEditor.putString("T1", ObjectSerializer.serialize(data.saveT1()));
            myEditor.putString("T2", ObjectSerializer.serialize(data.saveT2()));
            myEditor.putString("T3", ObjectSerializer.serialize(data.saveT3()));
            myEditor.putString("T4", ObjectSerializer.serialize(data.saveT4()));
            myEditor.putString("T5", ObjectSerializer.serialize(data.saveT5()));
            myEditor.putString("T6", ObjectSerializer.serialize(data.saveT6()));
            myEditor.putString("T7", ObjectSerializer.serialize(data.saveT7()));
            }
        catch (IOException e) { e.printStackTrace(); }
        myEditor.apply();}
    }
