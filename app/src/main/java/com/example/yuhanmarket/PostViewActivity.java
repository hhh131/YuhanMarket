package com.example.yuhanmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PostViewActivity extends AppCompatActivity {
    TextView Title,Price,Content;
    Button ChatBtn;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        Intent intent = getIntent();
        key= intent.getStringExtra("key");

        Title = (TextView)findViewById(R.id.Title);
        Price = (TextView)findViewById(R.id.Price);
        Content = (TextView)findViewById(R.id.Content);
        ChatBtn = (Button) findViewById(R.id.ChatBtn);


        Title.setText(key);
        Title.setText("");
        Title.setText("");



    }
}