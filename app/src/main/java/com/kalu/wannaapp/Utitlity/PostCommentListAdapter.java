package com.kalu.wannaapp.Utitlity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalu.wannaapp.R;
import com.kalu.wannaapp.models.PostComment;

import java.util.List;

public class PostCommentListAdapter extends RecyclerView.Adapter<PostCommentListAdapter.MyHolder> {
    Context kContext;
    List<PostComment> kData;

    public PostCommentListAdapter(Context context, List<PostComment> data) {
        kContext = context;
        kData = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(kContext).inflate(R.layout.recycler_user_single,parent,false);
        PostCommentListAdapter.MyHolder holder= new PostCommentListAdapter.MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return kData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name,comment;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.recyclerusernamecomment);
            name=itemView.findViewById(R.id.recyclerusernamecomment);
            comment=itemView.findViewById(R.id.recyclerphonecomment);
        }
    }
}
