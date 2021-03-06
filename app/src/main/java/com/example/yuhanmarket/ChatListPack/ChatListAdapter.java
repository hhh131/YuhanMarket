package com.example.yuhanmarket.ChatListPack;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yuhanmarket.ChatPack.ChatActivity;
import com.example.yuhanmarket.R;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {
    private ArrayList<ChatListVO> mDataset;
    String MyId;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView ivUser;
        public MyViewHolder(View v) {
            super(v);

            textView = v.findViewById(R.id.tvUser);
            ivUser = v.findViewById(R.id.ivUser);
            v.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();

                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    intent.putExtra("pos",pos);
                    intent.putExtra("OtherId","heekwon131");
                    //intent.putExtra("Array",mDataset);

                    v.getContext().startActivity(intent);
                }
            });

        }


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    public ChatListAdapter(ArrayList<ChatListVO> myDataset, String MyId) {
        this.mDataset = myDataset;
        this.MyId=MyId;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_view, parent, false);


        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView.setText(mDataset.get(position).getRoomNum());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
