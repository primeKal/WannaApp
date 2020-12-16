package com.kalu.wannaapp.Activities.plugpackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.kalu.wannaapp.Activities.LoginActivity;
import com.kalu.wannaapp.Activities.exchangepackage.ExchangeActivity;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.Activities.lookaround.LookAroundActivity;

public class PluginActivtiy extends AppCompatActivity {

    ImageButton kLookaround,kExhcange,kQRScanner,kQRGenerator,kSignout;
    ImageView kBarcodeview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_activtiy);

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
        kExhcange=findViewById(R.id.exchange);
        kExhcange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), ExchangeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        //find all 3 remaaining image buttons
        kQRGenerator=findViewById(R.id.pluginqrcode);
        kQRScanner=findViewById(R.id.pluginscann);
        kSignout=findViewById(R.id.pluginlogout);
        kBarcodeview=findViewById(R.id.pluginbarcodeview);


        kSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        kQRGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setqrcode(kBarcodeview);
            }
        });

    }

    public boolean setqrcode(ImageView v){
        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        try {
            BitMatrix bitMatrix=multiFormatWriter.encode("cents"+"name", BarcodeFormat.QR_CODE, 500,600);
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
            v.setImageBitmap(bitmap);
            return true;
        } catch (WriterException e) {
            e.printStackTrace();
            return  false;
        }


    }
}
