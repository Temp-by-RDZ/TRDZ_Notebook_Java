package com.TRDZ.note.contein;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TRDZ.note.MainActivity;
import com.TRDZ.note.R;

public class WindowText extends Fragment implements View.OnClickListener {
    static final String ARG_ID = "ID";
    static final String ARG_INDEX = "INDEX";
    static final String ARG_TEXT = "TEXT";
    int id;

    /**
     * Создание фрамента с указанием макета
     * @param inflater Инфлатер
     * @param container Визуальный контейнер
     * @param savedInstanceState Бекап при прирывании
     * @return Экран
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.win_text, container, false);
        }

    /**
     * Наполнение контентом
     * @param savedInstanceState Бекап при прирывании
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try { Bundle arguments = requireArguments();
            TextView tek = view.findViewById(R.id.list_content);
            tek.setText(arguments.getString(ARG_TEXT));
            id = arguments.getInt(ARG_ID);
            ImageView image = view.findViewById(R.id.I_borderline_type);
            TypedArray images = getResources().obtainTypedArray(R.array.border_type);
            int index = arguments.getInt(ARG_INDEX);
            image.setImageResource(images.getResourceId(index, 0));
            images.recycle();
            image.setOnClickListener(this);
            }
        catch (IllegalStateException ignored) {}
        }

    /**
     * Фабричный метод создания фрагмента
     * @param index ID записи
     * @return Окно содержимого
     */
    public static WindowText newInstance(int index, String text, int id) {
        WindowText fragment = new WindowText();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putInt(ARG_INDEX, index);
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
        }

    /**
     * Выполнение перехода к редактированию заметки
     */
    @Override
    public void onClick(View view) {
        int segment;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            segment = R.id.fragment_container_second; }
        else segment = R.id.fragment_container;
        WindowNew detail = WindowNew.newInstance(-1, "", id);
        ((MainActivity) requireActivity()).get_Navigation().replace(segment,detail,true);
        }
    }
