package com.kalu.wannaapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.Activities.exchangepackage.ExchangeActivity;

public class SplashScreen extends AppCompatActivity {
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //here we create the thread to intiate main activity

        Thread background=new Thread(){
            public void run(){
                try {
                    sleep(3*1000);
                    startActivity(i);
                    finish();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        // Now start the thread background

        //check for authentication
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        if(currentuser == null){
            i=new Intent(getBaseContext(),LoginActivity.class);
            background.start();
        }
        else {
            i=new Intent(getBaseContext(), ExchangeActivity.class);
            background.start();
        }


    }
}
