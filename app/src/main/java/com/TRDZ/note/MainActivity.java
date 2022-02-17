package com.TRDZ.note;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

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
        if (data==null) data = new Data(); //Это временное обьявление данных до выноса их в сохраненые и
        load(MainActivity.this);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new Win_List()).commit();

        Win_List winList = (Win_List) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

        if (winList == null) winList = new Win_List();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, winList, FRAGMENT_TAG).commit();
        }

    protected static void load(Context My_con) {
        SharedPreferences myPreferences = My_con.getApplicationContext().getSharedPreferences("BIG",Context.MODE_PRIVATE);

        try {
            data.load(
                (ArrayList<Date>) ObjectSerializer.deserialize(myPreferences.getString("T1", ObjectSerializer.serialize(data.saveT1()))),
                (ArrayList<Integer>) ObjectSerializer.deserialize(myPreferences.getString("T2", ObjectSerializer.serialize(data.saveT2()))),
                (ArrayList<Integer>) ObjectSerializer.deserialize(myPreferences.getString("T3", ObjectSerializer.serialize(data.saveT3()))),
                (ArrayList<Integer>) ObjectSerializer.deserialize(myPreferences.getString("T4", ObjectSerializer.serialize(data.saveT4()))),
                (ArrayList<Integer>) ObjectSerializer.deserialize(myPreferences.getString("T5", ObjectSerializer.serialize(data.saveT5()))),
                (ArrayList<String>) ObjectSerializer.deserialize(myPreferences.getString("T6", ObjectSerializer.serialize(data.saveT6()))),
                (ArrayList<String>) ObjectSerializer.deserialize(myPreferences.getString("T7", ObjectSerializer.serialize(data.saveT7())))
                );
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (ClassNotFoundException e) { e.printStackTrace();}
        }

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
