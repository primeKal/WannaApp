package com.kalu.wannaapp.Utitlity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kalu.wannaapp.Activities.SignUPActivity;

public class ImageSelector extends SignUPActivity {
    static final int REQUESTCODE =000;
    static int FReqCode=1;


    String addImagefromPhone(ImageButton imageButton){


        return  "";
    }
     void openGallary() {
        shortMessage("Opening ur gallary");
        Intent gallaryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(gallaryIntent,"choose ur image"),REQUESTCODE);

    }
     void shortMessage(String please_verify_all_fields) {
        Toast.makeText(this,please_verify_all_fields,Toast.LENGTH_LONG).show();

    }
     void checkAndRequestPermission( ) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
                Toast.makeText(this,"Please Accept permission",Toast.LENGTH_LONG).show();
            else ActivityCompat.requestPermissions((Activity) this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},FReqCode);
            openGallary();
        }
        else openGallary();
    }
}
