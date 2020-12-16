package com.kalu.wannaapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.Activities.exchangepackage.ExchangeActivity;
import com.kalu.wannaapp.models.LocalUser;

public class LoginActivity extends AppCompatActivity {
    EditText kEmail,kPassword,kPhoneNumber;
    Button kEmailLogin,kPhoneLogin,kSignUp;
    FirebaseAuth kFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //instantiate firebase auth
        kFirebaseAuth=FirebaseAuth.getInstance();

        //find all elements in layout

        kPhoneNumber=findViewById(R.id.loginphonenumber);
        kPhoneLogin=findViewById(R.id.loginwithphonebutton);
        kSignUp=findViewById(R.id.loginsignupbutton);

        //go to registeration page
        kSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUPActivity.class));
            }
        });

        //set click listner for email ligin
        //set click listener for phone number button
        kPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkphonedata();
            }
        });
    }

    private void checkphonedata() {
        String phonenumber=kPhoneNumber.getText().toString();
        if(phonenumber.isEmpty()){
            shortMessage("please insert phone number");
        } else signinwithphonenumber(phonenumber);
    }

    private void signinwithphonenumber(String number){
        Intent toverify=new Intent(getApplicationContext(),VerificationActivity.class);
        LocalUser storage= new LocalUser();
        storage.setPhonenumber(number);
        toverify.putExtra("phonenumber",storage);
//        toverify.putExtra("phonenumber",number);
        startActivity(toverify);
    }

    private void checkdataisinserted() {
        String email=kEmail.getText().toString();
        String password=kPassword.getText().toString();
        if(email.isEmpty() || password.isEmpty()){
            shortMessage("Please verify all fields");
        } else signinwithemail(email,password);


    }

    private void signinwithemail(String email, String password) {
        kFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i=new Intent(getApplicationContext(), ExchangeActivity.class);
                    startActivity(i);
                } else {
                    shortMessage(task.getException().getMessage());
                }
            }
        });

    }

    private void shortMessage(String please_verify_all_fields) {
        Toast.makeText(getApplicationContext(),please_verify_all_fields,Toast.LENGTH_LONG).show();

    }
}
