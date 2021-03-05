package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListActivity extends AppCompatActivity {
    Button ret;
    ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //initialize button
        ret = (Button) findViewById(R.id.returnButton);
        //Create fragment references
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);
        Bundle b1= getIntent().getExtras();

        String info = b1.getString("list");
        listFragment.setText(info);
        //set listener and onClick method
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    private void openMainActivity() {
        //Change to MainActivity
        finish();
    }
}