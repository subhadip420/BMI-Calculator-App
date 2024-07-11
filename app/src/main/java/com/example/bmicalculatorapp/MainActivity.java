package com.example.bmicalculatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set the layout for this activity

        final Handler handler = new Handler();

        // Use postDelayed method to execute the code after a delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the Login_Signin_Page activity after the delay
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                // Finish the current activity to prevent it from being in the back stack
                finish();
            }
        }, 2500); // Delay of 2500 milliseconds (2.5 seconds)
    }
}