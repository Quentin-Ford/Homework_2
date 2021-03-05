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
    public final static String LIST="list";
    //Declare the lap list and counter
    static String times = "";
    int count = 1;
    static int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create fragment references
        buttonsFragment = (ButtonsFragment) getSupportFragmentManager().findFragmentById(R.id.buttonsFrag);
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);
        //initialize button
        list = (Button) findViewById(R.id.listButton);
        //initialize AsyncTask class
        myAsyncTask= new MyAsyncTask();

    }

    private void openListActivity(View view) {
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

        myAsyncTask.execute(360000);
    }

    private void addTime() {
        addTime(ButtonsFragment.getTime(), count);
        count++;
    }

    private void reset() {
        times = "";
        count = 1;
        ButtonsFragment.setStart();
        ListFragment.setList();
        //Stop the timer
        ButtonsFragment.resetTimer();
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

        }
    }
}