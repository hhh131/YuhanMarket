package com.example.yuhanmarket.ChatPack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yuhanmarket.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private ArrayList<Chat> mDataset;
    String MyId;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //List<ChatModel.Comment> comments;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
           // comments=new ArrayList<>();
            //myRef.child("Chat").child()
            textView = v.findViewById(R.id.tvChat);
        }
    }

    @Override
    public int getItemViewType(int position) {
       // return super.getItemViewType(position);
      if(mDataset.get(position).UserId.equals(MyId))
        {
            return 1;
        }
        else
        {
            return 2;
        }
       // return  2;
    }

    public ChatAdapter(ArrayList<Chat> myDataset, String MyId) {
        mDataset = myDataset;
        this.MyId=MyId;
    }


    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        if(viewType==1){
            v= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.right_text_view, parent, false);

        }
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView.setText(mDataset.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
