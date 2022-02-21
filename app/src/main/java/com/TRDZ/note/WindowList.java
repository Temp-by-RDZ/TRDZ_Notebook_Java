package com.TRDZ.note;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import static com.TRDZ.note.MainActivity.data;

public class WindowList extends Fragment {
    private static final String CURRENT_NOTE = "CURRENT_NOTE";
    private int Current_note = 0;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_win_list,menu);
        super.onCreateOptionsMenu(menu, inflater);
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_sort) {
            data.sort();
            Refresh();
            }
        return super.onOptionsItemSelected(item);
        }

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
     * @param savedInstanceState Бекап при прирывании
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) Current_note = savedInstanceState.getInt(CURRENT_NOTE, 0);
        create_list(view);
        if (isLandscape()) { create_info_land(Current_note); }
        create_button(view);
        }

    /**
     * Заполнение списка заметок
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
            Line.setOnLongClickListener(view1 -> { get_iteration(line_number, view1); return true; });
            }
        }

    public void Refresh() {
        MainActivity.save(requireContext());
        requireActivity().getSupportFragmentManager().beginTransaction().detach(WindowList.this).commit();
        requireActivity().getSupportFragmentManager().beginTransaction().attach(WindowList.this).commit();}

//region Взаимодействие с перечнем
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
     * Взаимодействие через подробное меню
     * @param index Номер выбранной записи
     */
    private void get_iteration(int index, View view) {
        PopupMenu popup = new PopupMenu(requireContext(),view);
        requireActivity().getMenuInflater().inflate(R.menu.popup,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                case (R.id.menu_delete):
                    data.remove(index);
                    Refresh();
                    break;
                case (R.id.menu_change):
                    int segment;
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    segment = R.id.fragment_container_second; }
                    else segment = R.id.fragment_container;
                    WindowNew detail = WindowNew.newInstance(-1, "", index);
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(segment, detail);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                    }
                return false;
                }
            });
        popup.show();
        }

    /**
     * Вывод содержимого земетки в порт ориентации
     * @param index номер заметки
     */
    private void create_info_port(int index) {
        WindowText detail = WindowText.newInstance(data.get_type(index),data.get_cont(index), index);
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
        WindowText detail = WindowText.newInstance(data.get_type(index),data.get_cont(index), index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_second, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
        }
//endregion

//region Взаимодействие с кнопкой добавления
    /**
     * Подготовка к вызову окна создания новой заметки
     */
    private void create_button(View view) {
        Button b_new = view.findViewById(R.id.B_new);
        b_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLandscape()) { create_new_land();}
                else create_new_port();
                }
            });
        }

    /**
     * Вывод окна создяния новой заметки в порт ориентации
     */
    private void create_new_port() {
        WindowNew detail = WindowNew.newInstance(-1,"",-1);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack("").commit();
        }

    /**
     * Вывод окна создяния новой заметки в ланд ориентации
     */
    private void create_new_land() {
        WindowNew detail = WindowNew.newInstance(-1,"",-1);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_second, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        }
//endregion

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
    public static WindowList newInstance() {
        return new WindowList();
        }
    }