package com.kalu.wannaapp.Activities.lookaround;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.Utitlity.PostListAdapter;
import com.kalu.wannaapp.models.UserPost;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class PostFragment extends Fragment {

   PostListAdapter kPostListAdapter;
   FirebaseDatabase kFirebaseDatabase;
   Button addPost;
   DatabaseReference kDatabaseReference;
   List<UserPost> kUserPosts;
   FirebaseUser kFirebaseUser;
   Uri pickedimgurl;
   UserPost postobjectuser;

   //layoutobjects
   RecyclerView kRecyclerView;
   ImageButton tobeposted;
   Button share;
   EditText description;
   String uriafterpostissaved;
   ProgressBar kProgressBar;

   //ImageSelector
    static final int REQUESTCODE =000;
    static int FReqCode=1;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView= inflater.inflate(R.layout.fragment_post, container, false);
        description=fragmentView.findViewById(R.id.postfrag_description);
        tobeposted=fragmentView.findViewById(R.id.postfrag_imagebutton);
        share=fragmentView.findViewById(R.id.postfrag_sharebutton);
        kProgressBar=fragmentView.findViewById(R.id.progressBar2);
        kRecyclerView=fragmentView.findViewById(R.id.recyclerView2);
        kRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        kRecyclerView.setHasFixedSize(true);
        kFirebaseDatabase=FirebaseDatabase.getInstance();
        kDatabaseReference=kFirebaseDatabase.getReference().child("user_post");
        kFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(getContext(),"reference got",Toast.LENGTH_LONG).show();
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        kDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kUserPosts=new ArrayList<>();
                for(DataSnapshot postsnap:dataSnapshot.getChildren()){
                    UserPost post=postsnap.getValue(UserPost.class);
                    kUserPosts.add(post);
                    //    Toast.makeText(getContext(),postList.size(),Toast.LENGTH_LONG).show();
                }
                kPostListAdapter=new PostListAdapter(getActivity(),kUserPosts);
                kRecyclerView.setAdapter(kPostListAdapter);
                kProgressBar.setVisibility(View.INVISIBLE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setimageselectorbutton
        tobeposted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>22) {
                    checkAndRequestPermission();
                }
                else {openGallary();}
            }
        });
        //get all the data
       //setsharebutton
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descri=description.getText().toString();
                String name= kFirebaseUser.getDisplayName();
                String phone=kFirebaseUser.getPhoneNumber();
                Uri profile=kFirebaseUser.getPhotoUrl();
                String uid=kFirebaseUser.getUid();
                postobjectuser=new UserPost(name,descri,uriafterpostissaved,phone,profile.toString());
                insertphotostorage(pickedimgurl,postobjectuser);


            }
        });
    }
    // ImageSelectorsandrequest

    void openGallary() {
        shortMessage("Opening ur gallary");
        Intent gallaryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(gallaryIntent,"choose ur image"),REQUESTCODE);

    }
    void shortMessage(String please_verify_all_fields) {
        Toast.makeText(getContext(),please_verify_all_fields,Toast.LENGTH_LONG).show();

    }
    void checkAndRequestPermission( ) {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE))
                Toast.makeText(getActivity(),"Please Accept permission",Toast.LENGTH_LONG).show();
            else ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},FReqCode);
            openGallary();
        }
        else openGallary();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){
            pickedimgurl=data.getData();
            shortMessage(pickedimgurl.getLastPathSegment()+"is selected");
        }
    }
    private void insertphotostorage(Uri pickedImgUrl, final UserPost mypost){
        shortMessage("starting img upload");
        StorageReference mstorage= FirebaseStorage.getInstance().getReference().child("user_post");
        final StorageReference imgFilePath=mstorage.child(pickedImgUrl.getLastPathSegment().toString());
        imgFilePath.putFile(pickedImgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                shortMessage("img succesfully uploaded");
                imgFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uriafterpostissaved=uri.toString();
                        mypost.setPostimage(uriafterpostissaved);
                        savetodatabase(mypost);
                    }
                });

            }
        });

    }

    private void savetodatabase(UserPost mypost) {
        kFirebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference myref= kFirebaseDatabase.getReference("user_post").push();
        myref.setValue(mypost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                   shortMessage("All is successgull");
                }
            }
        });
    }

}
