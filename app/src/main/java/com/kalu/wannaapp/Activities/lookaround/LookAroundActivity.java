package com.kalu.wannaapp.Activities.lookaround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kalu.wannaapp.Activities.exchangepackage.ContactFragment;
import com.kalu.wannaapp.Activities.exchangepackage.ExchangeActivity;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.Activities.exchangepackage.MessageFragment;
import com.kalu.wannaapp.Activities.plugpackage.PluginActivtiy;

public class LookAroundActivity extends AppCompatActivity {

    ImageButton kExchange,kPlugin;
    private BottomNavigationView kBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_around);

        //find imagebutton of look around and set listner
        kExchange=findViewById(R.id.lookaround);
        kExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), ExchangeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        //find imagebutton of look around and set listner
        kPlugin=findViewById(R.id.plugin);
        kPlugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), PluginActivtiy.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PostFragment()).commit();
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
                        selectedFragment= new PostFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });


    }
}
