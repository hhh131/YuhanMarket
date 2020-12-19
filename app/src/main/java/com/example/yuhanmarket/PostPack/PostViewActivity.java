package com.example.yuhanmarket.PostPack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuhanmarket.ChatPack.ChatActivity;
import com.example.yuhanmarket.PostListPack.ListAdapter;
import com.example.yuhanmarket.PostListPack.ListVO;
import com.example.yuhanmarket.R;
import com.example.yuhanmarket.ReportActivity;
import com.example.yuhanmarket.TapActivity;
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
    TextView Title,Price,Content,uploadDate,tvid;
    Button ChatBtn;
    String stTitle,stPrice,stContent,PostId="",UserId,key;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ProductList");
    File localFile,localFile2;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ViewPager viewPager;
    ImageView PostImg,UserImg;
    PostVO postVO;
    Boolean salesStatus;
    int pos;
    LinearLayout postViewLay;
    MenuItem item;
    ArrayList<ListVO> arrayList;
    ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        key= intent.getStringExtra("key");
        pos= intent.getIntExtra("pos",1);
        //arrayList = intent.getParcelableArrayListExtra("array");
        tvid= (TextView)findViewById(R.id.tv_id);
        uploadDate = (TextView)findViewById(R.id.tv_product_date);
       // postViewLay = (LinearLayout)findViewById(R.id.postViewLay);
        Title = (TextView)findViewById(R.id.Title);
        Price = (TextView)findViewById(R.id.Price);
        Content = (TextView)findViewById(R.id.Content);
        ChatBtn = (Button) findViewById(R.id.ChatBtn);

        //viewPager = (ViewPager)findViewById(R.id.product_viewpager);
        UserImg = (ImageView)findViewById(R.id.user_profile);
        PostImg = (ImageView)findViewById(R.id.ivProduct);

        SharedPreferences sharedPreferences = getSharedPreferences("shard", Context.MODE_PRIVATE);
        UserId= sharedPreferences.getString("UserId","");




        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                  postVO = snapshot.child(key).getValue(PostVO.class);

                tvid.setText(postVO.userId);
                Title.setText(postVO.getTitle());
                Price.setText(postVO.getPrice()+"원");
                Content.setText(postVO.getContent());
                uploadDate.setText(postVO.uploadDate);
                PostId= postVO.getUserId();
                salesStatus=postVO.getSalesStatus();
                setTitle(postVO.getTitle());



                if(salesStatus.equals(true))
                {
                    //postViewLay.setBackgroundColor(Color.GRAY);
                    ChatBtn.setText("판매 완료");
                    //postViewLay.setEnabled(false);
                }
                else
                {
                    if(PostId.equals(UserId))
                    {
                        ChatBtn.setText("거래 완료처리");
                    }

                }


                ChatBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            if(PostId.equals(UserId))
                        {
                          myRef.child(key).child("salesStatus").setValue(true);
                        }
                        else{
                            Intent intent =new Intent(getApplicationContext(), ChatActivity.class);

                            intent.putExtra("OtherId", postVO.getUserId());
                            intent.putExtra("UserId",PostId);
                            startActivity(intent);

                        }



                    }
                });


                localFile2 = null;
                try {
                    localFile2 = File.createTempFile("Images", "jpg");

                    StorageReference riversRef = storageRef.child("Post").child(key).child("PostImg.jpg");
                    riversRef.getFile(localFile2)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    // Successfully downloaded data to local file
                                    // ...
                                    Bitmap bitmap2 = BitmapFactory.decodeFile(localFile2.getAbsolutePath());
                                    PostImg.setImageBitmap(bitmap2);
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








                /*----------------------------------------------------------------------------------------------*/
                localFile = null;
                try {
                    localFile = File.createTempFile("Images", "jpg");
                    StorageReference riversRef = storageRef.child("users").child(UserId).child("profile.jpg");
                    //StorageReference riversRef = storageRef.child("Post").child(key).child("PostImg.jpg");
                    riversRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    // Successfully downloaded data to local file
                                    // ...
                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    UserImg.setImageBitmap(bitmap);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.post_menu,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delItem:
                showDig();
                return true;
            case R.id.updateItem:
                Intent intent = new Intent(getApplicationContext(),UpdatePostViewActivity.class);
                intent.putExtra("key",key);
                startActivity(intent);
                return true;

            case R.id.reportItem:

               Intent intent2 = new Intent(getApplicationContext(), ReportActivity.class);
                 intent2.putExtra("User",PostId);
                startActivity(intent2);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }




    }


    void showDig()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("글 삭제");
        builder.setMessage("글을 삭제 하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        myRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"글을 삭제 하였습니다.",Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(getApplicationContext(), TapActivity.class);
                                startActivity(intent);
                            }
                        });




                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소 하였습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }
}