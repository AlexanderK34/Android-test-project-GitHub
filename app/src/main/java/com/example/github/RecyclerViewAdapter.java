package com.example.github;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.model.FollowersOrFollowingData;
import com.example.github.model.FollowersOrFollowingDataCollection;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerView_ViewHolder> {

    Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cell_list_followers_or_following, parent,false);
        return new RecyclerView_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView_ViewHolder holder, int position) {

        FollowersOrFollowingData followersOrFollowingData =
                FollowersOrFollowingDataCollection.followersOrFollowingDataArrayList.get(position);

        holder.textView.setText(followersOrFollowingData.getLogin());
        holder.textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProfileView.class);
                intent.putExtra("userProfile", followersOrFollowingData.getLogin());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FollowersOrFollowingDataCollection.followersOrFollowingDataArrayList.size();
    }

    public static class RecyclerView_ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public RecyclerView_ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textCellView);
        }
    }
}
