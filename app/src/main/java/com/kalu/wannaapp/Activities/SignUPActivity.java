package com.kalu.wannaapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.Activities.exchangepackage.ExchangeActivity;
import com.kalu.wannaapp.models.LocalUser;

public class SignUPActivity extends AppCompatActivity {
    EditText kName,kBirthday,kPhoneNumber;
    RadioGroup kGender;
    RadioButton kMale,kFemale;
    CheckBox kPrivacy;
    Button kRegister;
    FirebaseAuth kFirebaseAuth;
    String sex;
    static final int REQUESTCODE =000;
    static int FReqCode=1;
    ImageButton kAddimage;
    Uri pickedimgurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_u_p);

        kFirebaseAuth=FirebaseAuth.getInstance();
        //get all layout elements
        kName=findViewById(R.id.signupname);
        kBirthday=findViewById(R.id.signupbirthday);
        kPhoneNumber=findViewById(R.id.signupphone);
        kGender=findViewById(R.id.signupradiogroup);
        kMale=findViewById(R.id.signupmale);
        kFemale=findViewById(R.id.signupfemale);
        kPrivacy=findViewById(R.id.signupcheckbox);
        kRegister=findViewById(R.id.signupregisterbutton);
        kAddimage=findViewById(R.id.signup_addimage);


        kAddimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>22) {
                    checkAndRequestPermission();
                }
                else {openGallary();}
            }
        });


        kGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button=findViewById(checkedId);
                sex=button.getText().toString();
            }
        });



        // set click listener for registerbutton
        kRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkphonedata(sex);

            }
        });
    }

    private void checkphonedata(String sex) {
        String phonenumber=kPhoneNumber.getText().toString();
        String name=kName.getText().toString();
        String birthday=kBirthday.getText().toString();
        String gender=sex;
        if(phonenumber.isEmpty() || name.isEmpty() || birthday.isEmpty() ){
            shortMessage("please insert phone number");
        } else {
            LocalUser myyser=new LocalUser(name,gender,birthday,phonenumber,pickedimgurl);
            signinwithphonenumber(myyser);
        }
        }


    private void signinwithphonenumber(LocalUser number){
        Intent toverify=new Intent(getApplicationContext(),VerificationActivity.class);
        toverify.putExtra("phonenumber",number);
        startActivity(toverify);
    }

    private void setupuser(String email, String password) {
        kFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), ExchangeActivity.class));
                    }
                }
        });

    }
    private void shortMessage(String please_verify_all_fields) {
        Toast.makeText(getApplicationContext(),please_verify_all_fields,Toast.LENGTH_LONG).show();

    }
    private void openGallary() {
        shortMessage("Opening ur gallary");
        Intent gallaryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(gallaryIntent,"choose ur image"),REQUESTCODE);

    }

    private void checkAndRequestPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
                Toast.makeText(SignUPActivity.this,"Please Accept permission",Toast.LENGTH_LONG).show();
            else ActivityCompat.requestPermissions(SignUPActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},FReqCode);
            openGallary();
        }
        else openGallary();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){
            pickedimgurl=data.getData();
            shortMessage(pickedimgurl.getLastPathSegment()+"is selected");
        }
    }
}
