package com.example.homework2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ButtonsFragment extends Fragment implements View.OnClickListener {
    static Button start;
    static Button lap;
    static Button reset;
    static TextView timer;
    private OnFragmentInteractionListener mListener;

    public ButtonsFragment() {
        // Required empty public constructor
    }

    public static String getTime() {
        return (String) timer.getText();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_buttons, container, false);

        //Initialize buttons and TextView
        start = (Button) view.findViewById(R.id.startButton);
        lap = (Button) view.findViewById(R.id.lapButton);
        reset = (Button) view.findViewById(R.id.resetButton);
        timer = (TextView) view.findViewById(R.id.timer);

        //Set listeners
        start.setOnClickListener(this);
        lap.setOnClickListener(this);
        reset.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnFragmentInteractionListener){
            this.mListener= (OnFragmentInteractionListener) context;
        }else{
            throw new RuntimeException(context.toString()+" must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == start.getId()){

            mListener.onButtonClicked(0);

        }else if (view.getId() == lap.getId()){

            mListener.onButtonClicked(1);

        } else if(view.getId() == reset.getId() ){

            mListener.onButtonClicked(2);

        }
    }

    public static void startStop() {
        if (start.getText().equals("Start")) {
            start.setText("Stop");
        }
        else {
            start.setText("Start");
        }
    }

    public static void resetTimer() { timer.setText("00:00:00"); }

    public static void setStart() { start.setText("Start"); }

    public interface OnFragmentInteractionListener{
        void onButtonClicked(int infoID);
    }
}