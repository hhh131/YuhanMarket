package com.example.yuhanmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yuhanmarket.ChatPack.ChatActivity;

public class LoginActivity extends Activity {
    Button joinBtn,loginBtn;
    EditText id,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id= (EditText)findViewById(R.id.id);
        pwd = (EditText)findViewById(R.id.pwd);
        loginBtn = (Button)findViewById(R.id.loginbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TapActivity.class);
                intent.putExtra("UserId",id.getText().toString());
                startActivity(intent);
            }
        });

        joinBtn=(Button)findViewById(R.id.joinbtn);
            joinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
                    startActivity(intent);
                }
            });
    }
}