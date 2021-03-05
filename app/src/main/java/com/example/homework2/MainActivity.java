package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements ButtonsFragment.OnFragmentInteractionListener  {
    ButtonsFragment buttonsFragment;
    ListFragment listFragment;
    Button list;
    MyAsyncTask myAsyncTask;
    public final static String LIST = "list";
    public final static String TIME = "time";
    public final static String COUNT = "count";
    public final static String SWITCH = "switch";
    //Declare the lap list and counter
    static String times = "";
    int count = 1;
    static int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize button
        list = (Button) findViewById(R.id.listButton);
        if (list != null) {
            list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openListActivity();
                }
            });
        }
        //Initialize fragments
        buttonsFragment = (ButtonsFragment) getSupportFragmentManager().findFragmentById(R.id.buttonsFrag);
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);
        //initialize AsyncTask class
        myAsyncTask= new MyAsyncTask();
        if (savedInstanceState != null) {
            times = savedInstanceState.getString(LIST);
            ButtonsFragment.setTimer(savedInstanceState.getString(TIME));
            ListFragment.setList();
            count = savedInstanceState.getInt(COUNT);
            if (savedInstanceState.getString(SWITCH).equals("Stop ")) {
                myAsyncTask.execute(360000);
                ButtonsFragment.setStop();
            }
        }
    }

    private void openListActivity() {
        //change to ListActivity
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(LIST, times);
        startActivity(intent);
    }

    public static String getTimes() { return times; }

    public void addTime(String time, int count) {
        times += count + ". " + time + "\n";
        ListFragment.setList();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TIME, ButtonsFragment.getTime());
        outState.putString(LIST, times);
        outState.putInt(COUNT, count);
        outState.putString(SWITCH, ButtonsFragment.getStart());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        //checking if asynctask is still runnning
        if(myAsyncTask!=null && myAsyncTask.getStatus()== AsyncTask.Status.RUNNING){
            //cancel the task before destroying activity
            myAsyncTask.cancel(true);
            myAsyncTask= null;
        }
        super.onDestroy();
    }

    @Override
    public void onButtonClicked(int id) {
        if (id == 0) { changeState(); }
        else if (id == 1) { addTime(); }
        else if (id == 2) { reset(); }
    }

    private void changeState() {
        ButtonsFragment.startStop();
        if(myAsyncTask.getStatus()!= AsyncTask.Status.RUNNING) {
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute(360000);
        }
        else {
            myAsyncTask.cancel(true);
        }
    }

    private void addTime() {
        addTime(ButtonsFragment.getTime(), count);
        count++;
    }

    private void reset() {
        times = "";
        count = 1;
        seconds = 0;
        ButtonsFragment.setStart();
        ListFragment.setList();
        myAsyncTask.cancel(true);
        ButtonsFragment.resetTimer();
    }

    public static String formatSeconds(int seconds){
        int hours = seconds/3600;
        int minutes = seconds/60%60;
        int remaining = seconds%60;
        String format = "";
        if (hours < 10){ format += "0"; }
        format += hours + ":";
        if (minutes < 10){ format += "0"; }
        format += minutes + ":" ;
        if (remaining < 10){ format += "0"; }
        format += remaining;
        return format;
    }

    private static class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            while(seconds < params[0]){
                try{
                    //checking if the asynctask has been cancelled, end loop if so
                    if(isCancelled()) break;

                    Thread.sleep(1000);

                    seconds++;

                    //send count to onProgressUpdate to update UI
                    publishProgress(seconds);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //setting count to 0 and setting textview to 0 after doInBackground finishes running
            seconds = 0;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //Display timer
            ButtonsFragment.setTimer(formatSeconds(seconds));
        }
    }
}