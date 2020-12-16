package com.kalu.wannaapp.Utitlity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.models.LocalUser;

import java.util.List;

public class AllUsersListAdapter extends RecyclerView.Adapter<AllUsersListAdapter.MyHolder> {

    Context kContext;
    List<LocalUser> kData;

    public AllUsersListAdapter(Context context, List<LocalUser> data) {
        kContext = context;
        kData = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(kContext).inflate(R.layout.recycler_user_single,parent,false);
        MyHolder holder= new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(kData.get(position).getName());
        holder.phone.setText(kData.get(position).getPhonenumber());
        String pi=kData.get(position).getProfileimage();
        Glide.with(kContext).load(pi).into(holder.profile);

    }

    @Override
    public int getItemCount() {
        return kData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name,phone;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.recycleruserimagecomment);
            name=itemView.findViewById(R.id.recyclerusernamecomment);
            phone=itemView.findViewById(R.id.recyclerphonecomment);
        }
    }
}
