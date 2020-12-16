package com.kalu.wannaapp.Activities.exchangepackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.Activities.lookaround.LookAroundActivity;
import com.kalu.wannaapp.Activities.plugpackage.PluginActivtiy;

public class ExchangeActivity extends AppCompatActivity {

    private BottomNavigationView kBottomNavigationView;
    private ImageButton kLookaround,kPlugin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find imagebutton of look around and set listner
        kLookaround=findViewById(R.id.lookaround);
        kLookaround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), LookAroundActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        //find imagebutton of look around and set listner
        kPlugin=findViewById(R.id.plugin);
        kPlugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PluginActivtiy.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MessageFragment()).commit();
        kBottomNavigationView=findViewById(R.id.bottom_navigation);
        kBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment= null;
                switch (item.getItemId()){
                    case R.id.nav_icon1:
                        selectedFragment= new MessageFragment();
                        break;

                    case R.id.nav_icon2:
                        selectedFragment= new ContactFragment();
                        break;

                    case R.id.nav_icon3:
                        selectedFragment= new GroupFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });

    }
}
