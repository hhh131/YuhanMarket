package com.example.yuhanmarket.PostListPack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yuhanmarket.PostPack.PostViewActivity;
import com.example.yuhanmarket.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    ArrayList<ListVO> mDataset;
    String MyId,Title;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ProductList");
    File localFile;
    String UserId;

    String key;
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout PostLay;
        public TextView tvUser,tvTitle,tvPrice,tvSales,tvDate;

        String UserId;
        ImageView PostImg;

        public MyViewHolder(View v) {
            super(v);
            tvDate= v.findViewById(R.id.tv_product_date);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvUser = v.findViewById(R.id.tvUser);
            PostImg = v.findViewById(R.id.ivUser);
            tvPrice =v.findViewById(R.id.tvPrice);
            PostLay=v.findViewById(R.id.item_lay);
           // tvSales=v.findViewById(R.id.tvSales);
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("shard", Context.MODE_PRIVATE);
            UserId= sharedPreferences.getString("UserId","");

            v.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();

                    Intent intent = new Intent(v.getContext(), PostViewActivity.class);
                    intent.putExtra("pos",pos);
                    intent.putExtra("key",mDataset.get(pos).getKey());
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

    public ListAdapter(ArrayList<ListVO> mDataset, String MyId) {
        this.mDataset = mDataset;
        this.MyId=MyId;

    }


    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_recycler_view_item, parent, false);



        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        key=mDataset.get(position).getKey();
       // holder.tvUser.setText(mDataset.get(position).getUserId());
        holder.tvTitle.setText(mDataset.get(position).getTitle());
       holder.tvPrice.setText("20,000원");
       holder.tvDate.setText(mDataset.get(position).getUploadDate());

/*

       myRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               if(snapshot.child(key).child("salesStatus").getValue().equals(true)){
                   holder.tvSales.setVisibility(View.VISIBLE);
                   Log.e("===================","true");
                   Log.e("===================",key);
               }else {
                   Log.e("===================","flase");
                   Log.e("===================",key);
                   holder.tvSales.setVisibility(View.GONE);
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

*/




        localFile = null;
        try {
            localFile = File.createTempFile("Images", "jpg");
            StorageReference riversRef = storageRef.child("Post").child(key).child("PostImg.jpg");
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.PostImg.setImageBitmap(bitmap);

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
        }




    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
