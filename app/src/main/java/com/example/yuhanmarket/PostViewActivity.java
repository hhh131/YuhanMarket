package com.example.yuhanmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuhanmarket.ChatPack.ChatActivity;
import com.example.yuhanmarket.PostListPack.ListAdapter;
import com.example.yuhanmarket.PostListPack.ListVO;
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

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class  PostViewActivity extends AppCompatActivity {
    TextView Title,Price,Content;
    Button ChatBtn;
    String key;
    String stTitle,stPrice,stContent,PostId,UserId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ProductList");
    File localFile;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ViewPager viewPager;
    ImageView PostImg;
    PostViewVO postViewVO;
    int pos;
    ArrayList<ListVO> arrayList;
    ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        Intent intent = getIntent();
        key= intent.getStringExtra("key");
        pos= intent.getIntExtra("pos",1);
        //arrayList = intent.getParcelableArrayListExtra("array");
        Title = (TextView)findViewById(R.id.Title);
        Price = (TextView)findViewById(R.id.Price);
        Content = (TextView)findViewById(R.id.Content);
        ChatBtn = (Button) findViewById(R.id.ChatBtn);
        viewPager = (ViewPager)findViewById(R.id.product_viewpager);
        PostImg = (ImageView)findViewById(R.id.ivProduct);
        SharedPreferences sharedPreferences = getSharedPreferences("shard", Context.MODE_PRIVATE);
        UserId= sharedPreferences.getString("UserId","");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                  postViewVO = snapshot.child(key).getValue(PostViewVO.class);


                Title.setText(postViewVO.getTitle());
                Price.setText(postViewVO.getPrice());
                Content.setText(postViewVO.getContent());
                PostId=postViewVO.getUserId();



                if(PostId.equals(UserId))
                {
                    ChatBtn.setText("거래 완료처리");




                }

                ChatBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(PostId.equals(UserId))
                        {


                        }
                        else{
                            Intent intent =new Intent(getApplicationContext(), ChatActivity.class);

                            intent.putExtra("OtherId",postViewVO.getUserId());
                            intent.putExtra("UserId",PostId);
                            startActivity(intent);

                        }



                    }
                });

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
                                    PostImg.setImageBitmap(bitmap);
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}