package com.TRDZ.note;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import static com.TRDZ.note.MainActivity.data;
import static com.TRDZ.note.Win_Text.ARG_INDEX;
import static com.TRDZ.note.Win_Text.ARG_TEXT;
import static com.TRDZ.note.Win_New.NEW_INDEX;
import static com.TRDZ.note.Win_New.NEW_TEXT;
import static com.TRDZ.note.Win_New.NEW_ID;

public class Win_List extends Fragment {
    private static final String CURRENT_NOTE = "CURRENT_NOTE";
    private int Current_note = 0;
    private Button B_new;

    /**
     * Создание фрамента с указанием макета
     * @param inflater Инфлатер
     * @param container Визуальный контейнер
     * @param savedInstanceState Бекап при прирывании
     * @return Экран
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.win_list, container, false);
        }

    /**
     * Инициализация после создания и подготовки макета
     * @param view Экран
     * @param savedInstanceState Бекап при прирывании
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) Current_note = savedInstanceState.getInt(CURRENT_NOTE, 0);
        create_list(view);
        if (isLandscape()) { create_info_land(Current_note); }
        create_button(view);
        }

    /**
     * Заполнение списка заметок
     * @param view Экран
     */
    protected void create_list(View view) {
        LinearLayout layoutView = (LinearLayout) view.findViewById(R.id.list_block);
        for (int i = 0; i < data.Size(); i++) {
            String name = data.get_line(i);
            TextView Line = new TextView(getContext(),null,0,R.style.Lines);
            Line.setText(name);
            layoutView.addView(Line);
            final int line_number = i;
            Line.setOnClickListener(v -> { create_info(line_number);});
            }
        }

    /**
     * Подготовка к созданию окна с информацией
     * @param index Номер выбранной записи
     */
    private void create_info(int index) {
        Current_note = index;
        if (isLandscape()) create_info_land(index);
        else create_info_port(index);
        }

    /**
     * Вывод содержимого земетки в порт ориентации
     * @param index номер заметки
     */
    private void create_info_port(int index) {
        Win_Text detail = Win_Text.newInstance(data.get_type(index),data.get_cont(index));
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack("").commit();
        }

    /**
     * Вывод содержимого земетки в ланд ориентации
     * @param index номер заметки
     */
    private void create_info_land(int index) {
        Win_Text detail = Win_Text.newInstance(data.get_type(index),data.get_cont(index));
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_second, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
        }

    private void create_button(View view) {
        B_new=view.findViewById(R.id.B_new);
        B_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLandscape()) {
                create_new_land();}
                else create_new_port();
                }
            });
        }

    /**
     * Вывод содержимого земетки в порт ориентации
     */
    private void create_new_port() {
        Win_New detail = Win_New.newInstance(-1,"",-1);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack("").commit();
        }

    /**
     * Вывод содержимого земетки в ланд ориентации
     */
    private void create_new_land() {
        Win_New detail = Win_New.newInstance(-1,"",-1);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_second, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, Current_note);
        super.onSaveInstanceState(outState);
        }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        }


    /**
     * Фабричный метод создания фрагмента
     */
    public static Win_List newInstance() {
        return new Win_List();
        }
    }