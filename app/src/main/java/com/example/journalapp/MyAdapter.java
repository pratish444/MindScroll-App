package com.example.journalapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //variables
    private Context context;
    private List<Journal> journalList;
    public MyAdapter(Context context, List<Journal>journalList){
        this.context=context;
        this.journalList=journalList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //view holder

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, thoughts,dateAdded,name;
        public ImageView image, shareButton;
        public String userId,username;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.journal_title_list);
            thoughts = itemView.findViewById(R.id.journal_thought_list);
            dateAdded = itemView.findViewById(R.id.journal_timestamp_list);

            image = itemView.findViewById(R.id.journal_image_list);
            name = itemView.findViewById(R.id.journal_row_username);

            shareButton = itemView.findViewById(R.id.journal_row_share_button);

            shareButton.setOnClickListener(v -> {

                // onClick()
                // Sharing the Post....
            });

        }
    }
}