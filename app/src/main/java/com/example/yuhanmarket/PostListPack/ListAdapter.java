package com.example.yuhanmarket.PostListPack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yuhanmarket.PostViewActivity;
import com.example.yuhanmarket.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    ArrayList<ListVO> mDataset;
    String MyId,Title;
    private StorageReference mStorageRef;
    File localFile;
    String UserId;
    ImageView PostImg;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout PostLay;
        public TextView tvUser,tvTitle,tvPrice;
        public ImageView ivUser;
        String UserId;


        public MyViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvUser = v.findViewById(R.id.tvUser);
            ivUser = v.findViewById(R.id.ivUser);
            tvPrice =v.findViewById(R.id.tvPrice);
            PostLay=v.findViewById(R.id.postLayout);
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("shard", Context.MODE_PRIVATE);
            UserId= sharedPreferences.getString("UserId","");

            v.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), PostViewActivity.class);
                    //intent.putExtra("pos",pos);
                    //intent.putExtra("key",mDataset.get(pos).getKey());

                    v.getContext().startActivity(intent);
                }
            });


        }


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    public ListAdapter(ArrayList<ListVO> mDataset, String MyId) {
        this.mDataset = mDataset;
        this.MyId=MyId;

    }


    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_view, parent, false);



        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tvUser.setText(mDataset.get(position).getUserId());
        holder.tvTitle.setText(mDataset.get(position).getTitle());
        holder.tvPrice.setText(mDataset.get(position).getKey());


        localFile = null;
      /*  try {
            localFile = File.createTempFile("images", "jpg");
            StorageReference riversRef = mStorageRef.child("users").child("1").child("profile.jpg");
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.ivUser.setImageBitmap(bitmap);
                            //PostImg.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }*/




    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
