package com.kalu.wannaapp.Utitlity;

import android.content.Context;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kalu.wannaapp.R;
import com.kalu.wannaapp.models.UserPost;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.MyHolder> {
    Context kContext;
    List<UserPost> kData;


    public PostListAdapter(Context context, List<UserPost> data) {
        kContext = context;
        kData = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(kContext).inflate(R.layout.recycler_post_single,parent,false);
        MyHolder holder= new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.name.setText(kData.get(position).getName().toString());
        holder.number.setText(kData.get(position).getPhone().toString());
        holder.time.setText(timeStamtoString((long)(kData.get(position).getTimestamp())));
        // images of post and user id
        String ui=kData.get(position).getUserimage();
        Glide.with(kContext).load(ui).into(holder.userimg);
        String pi=kData.get(position).getPostimage();
        Glide.with(kContext).load(pi).into(holder.postedimage);
        holder.description.setText(kData.get(position).getDiscription().toString());


    }


    @Override
    public int getItemCount() {
        return kData.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{

        ImageView postedimage,userimg;
        TextView name,time,description;
        TextView number;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            postedimage=itemView.findViewById(R.id.post_postedimg);
            name=itemView.findViewById(R.id.post_name);
            number=itemView.findViewById(R.id.post_number);
            userimg=itemView.findViewById(R.id.post_profile_img);
            description=itemView.findViewById(R.id.post_descri);
            time=itemView.findViewById(R.id.post_time);
        }
    }

    public String timeStamtoString(long t){
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(t);
        String date= DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }
}
