package com.example.yuhanmarket.PostPack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuhanmarket.PostListPack.ListVO;
import com.example.yuhanmarket.R;
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
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UpdatePostViewActivity extends AppCompatActivity {
    private static final String TAG = "WritePostActivity";
    int REQUEST_IMAGE_CODE=1001;
    EditText Title,Price,Content;
    ImageView PostImg;
    Button regitBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");
    ListAdapter listAdapter;
    String UserId,key;
    File localFile;
    long ProductListNum;
    Uri image;
    private StorageReference mStorageRef;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM 월dd일 hh:mm:ss");
    String uploadDate = dateFormat.format(calendar.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        setTitle("글 수정");
        Intent intent = getIntent();
        key= intent.getStringExtra("key");
        Title =(EditText)findViewById(R.id.Title);
        Price =(EditText)findViewById(R.id.Price);
        Content =(EditText)findViewById(R.id.Content);
        PostImg = (ImageView)findViewById(R.id.ivProduct);

        SharedPreferences sharedPreferences = getSharedPreferences("shard", Context.MODE_PRIVATE);
        UserId= sharedPreferences.getString("UserId","");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        myRef.child("ProductList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String stTitle=snapshot.child(key).child("title").getValue().toString();
                String stPrice=snapshot.child(key).child("price").getValue().toString();
                String stText=snapshot.child(key).child("content").getValue().toString();

                Title.setText(stTitle);
                Price.setText(stPrice);
                Content.setText(stText);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        PostImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_IMAGE_CODE);
            }
        });

        localFile = null;
       try {
            localFile = File.createTempFile("images", "jpg");
            StorageReference riversRef =  mStorageRef.child("Post").child(key).child("PostImg.jpg");
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CODE)
        {

            image = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),image);
                PostImg.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
            //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.writemenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        final String StTitle = Title.getText().toString();
        final String StPrice = Price.getText().toString();
        final String StContent = Content.getText().toString();



        if (!(StTitle.isEmpty() && StPrice.isEmpty() && StContent.isEmpty())) {

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // myRef.child("PostNum").setValue(snapshot.child("ProductList").getChildrenCount()+1);


                    ProductListNum = snapshot.child("ProductList").getChildrenCount();


                    PostVO postVO = new PostVO(UserId,StTitle,StPrice,StContent,false,uploadDate);

                    //WriteVO writeVO = new WriteVO(UserId,StTitle,StPrice,StContent);

                    //String key= myRef.child("ProductList").push().getKey();
                    myRef.child("ProductList").child(key).setValue(postVO);




                    StorageReference riversRef = mStorageRef.child("Post").child(key).child("PostImg.jpg");
                    riversRef.putFile(image)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    //   Uri downloadUrl = taskSnapshot.getDownloadUrl();3
                                    Toast.makeText(getApplicationContext(),"글 작성 완료",Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), TapActivity.class);
                                    startActivity(intent);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    // ...
                                }
                            });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }
        else
        {
            Toast.makeText(getApplicationContext(),"모두 작성바람",Toast.LENGTH_SHORT).show();
        }





        return super.onOptionsItemSelected(item);


    }
}