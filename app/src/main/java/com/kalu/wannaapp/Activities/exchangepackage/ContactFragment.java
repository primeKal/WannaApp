package com.kalu.wannaapp.Activities.exchangepackage;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.Utitlity.AllUsersListAdapter;
import com.kalu.wannaapp.Utitlity.PostListAdapter;
import com.kalu.wannaapp.models.LocalUser;

import java.util.ArrayList;
import java.util.List;


public class ContactFragment extends Fragment {
    DatabaseReference kDatabaseReference;
    RecyclerView kRecyclerView;
    List<LocalUser> kLocalUserList;
    AllUsersListAdapter kAllUsersListAdapter;


    // TODO: Rename and change types of parameters


    public ContactFragment() {
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
        View fragmentview=inflater.inflate(R.layout.fragment_contact, container, false);
        kRecyclerView=fragmentview.findViewById(R.id.recycler_users);
        kRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        kRecyclerView.setHasFixedSize(true);
        kDatabaseReference= FirebaseDatabase.getInstance().getReference().child("LocalUser");
        return fragmentview;
    }

    @Override
    public void onStart() {
        super.onStart();
        kDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kLocalUserList=new ArrayList<>();
                for(DataSnapshot postsnap:dataSnapshot.getChildren()){
                    String name = postsnap.child("name").getValue(String.class);
                    String gender = postsnap.child("gender").getValue(String.class);
                    String birthday = postsnap.child("birthday").getValue(String.class);
                    String phonenumber = postsnap.child("phonenumber").getValue(String.class);
                    String profileimage = postsnap.child("profileimage").getValue(String.class);
                    LocalUser localUser=new LocalUser(name,gender,birthday,phonenumber,profileimage);
                    kLocalUserList.add(localUser);
                    //    Toast.makeText(getContext(),postList.size(),Toast.LENGTH_LONG).show();
                }
                kAllUsersListAdapter=new AllUsersListAdapter(getActivity(),kLocalUserList);
                kRecyclerView.setAdapter(kAllUsersListAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
