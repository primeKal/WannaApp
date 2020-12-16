package com.kalu.wannaapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.Activities.exchangepackage.ExchangeActivity;
import com.kalu.wannaapp.models.LocalUser;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {
    EditText kCode;
    Button kVerify;
    ProgressBar kProgressBar;
    String kSystemcode;
    FirebaseDatabase kFirebaseDatabase;
    Boolean flag=true;
    LocalUser tobesaved;
    FirebaseUser kFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        //get all 3 elements
        kCode=(EditText)findViewById(R.id.veriedittext);
        kProgressBar=findViewById(R.id.progressBar);
        kProgressBar.setVisibility(View.GONE);
        kVerify=findViewById(R.id.veriregister);

        Bundle bundle=getIntent().getExtras();
        tobesaved=(LocalUser) bundle.getParcelable("phonenumber");
        setupphoneathentication(tobesaved);

        kVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coeintereduser=kCode.getText().toString();
                LocalUser storage=new LocalUser();
                storage.setPhonenumber(coeintereduser);
                if(coeintereduser.isEmpty() || coeintereduser.length()<6){
                    kCode.setError("please insert corrctly");
                    kCode.requestFocus();
                    return;
                }
                else setupphoneathentication(storage);

            }
        });
    }

    private void setupphoneathentication(LocalUser myuser) {
        String phonenumber=myuser.getPhonenumber();
        if(myuser.getName()!=null) {flag=false;}
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phonenumber,
                         60,TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,mCallBacks
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code= phoneAuthCredential.getSmsCode();
            if(code!=null){
                kProgressBar.setVisibility(View.VISIBLE);
                checkcode(code);
            }


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            shortMessage("failed to send message");

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            kSystemcode=s;
        }
    };

    private void checkcode(String code) {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(kSystemcode,code);
        signInUserCredential(credential);
    }

    private void signInUserCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if(flag==false)
                    saveuserondatabase();
                    else {Intent tomain=new Intent(getApplicationContext(), ExchangeActivity.class);
                    tomain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(tomain);}
                }
            }
        });
    }

    private void saveuserondatabase() {
        kFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        updateUserInfo(tobesaved.getName(),tobesaved.getProfileimg(),kFirebaseUser);


    }



    private void shortMessage(String please_verify_all_fields) {
        Toast.makeText(getApplicationContext(),please_verify_all_fields,Toast.LENGTH_LONG).show();
    }


    private void updateUserInfo(final String name, Uri pickedImgUrl, final FirebaseUser currentuser){
        shortMessage("starting img upload");
        StorageReference mstorage= FirebaseStorage.getInstance().getReference().child("user_photo");
        final StorageReference imgFilePath=mstorage.child(pickedImgUrl.getLastPathSegment().toString());
        imgFilePath.putFile(pickedImgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                shortMessage("img succesfully uploaded");
                imgFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        shortMessage("uri downloaded");
                        UserProfileChangeRequest profilechange=new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentuser.updateProfile(profilechange).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    shortMessage("Profile Created");
                                    savetodatabase2();


                                    }
                            }
                        });


                    }
                });

            }
        });

    }

    private void savetodatabase2() {
        //tobesaved.setProfileimg(kFirebaseUser.getPhotoUrl());
        kFirebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference myref= kFirebaseDatabase.getReference("LocalUser").child( kFirebaseUser.getUid());
        String ph=kFirebaseUser.getPhotoUrl().toString();
        LocalUser savewithouturi=new LocalUser(tobesaved.getName(),tobesaved.getGender(),tobesaved.getBirthday(),tobesaved.getPhonenumber(),ph);
        myref.setValue(savewithouturi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent tomain=new Intent(getApplicationContext(), ExchangeActivity.class);
                    tomain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(tomain);
                }
            }
        });
    }
}
