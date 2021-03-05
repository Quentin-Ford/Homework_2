package com.example.homework2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ListFragment extends Fragment {
    static TextView list;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list, container, false);

        //Initialize the list
        list = (TextView) view.findViewById(R.id.list);


        return view;
    }

    public static void setList() {
        if (list != null){
            list.setText(MainActivity.getTimes());
        }
    }

    public void setText(String info) { list.setText(info); }
}