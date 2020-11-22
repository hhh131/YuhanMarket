package com.example.yuhanmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuhanmarket.ChatPack.ChatActivity;
import com.example.yuhanmarket.PostListPack.ListVO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class  PostViewActivity extends AppCompatActivity {
    TextView Title,Price,Content;
    Button ChatBtn;
    String key;
    String stTitle,stPrice,stContent,UserId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ProductList");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        key= intent.getStringExtra("key");
        int pos= intent.getIntExtra("pos",1);
        Title = (TextView)findViewById(R.id.Title);
        Price = (TextView)findViewById(R.id.Price);
        Content = (TextView)findViewById(R.id.Content);
        ChatBtn = (Button) findViewById(R.id.ChatBtn);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostViewVO postViewVO = snapshot.child(key).getValue(PostViewVO.class);


                Title.setText(postViewVO.getTitle());
                Price.setText(postViewVO.getPrice());
                Content.setText(postViewVO.getContent());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent =new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("UserId",UserId);
                    startActivity(intent);
            }
        });



    }
}