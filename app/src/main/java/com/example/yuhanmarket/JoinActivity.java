package com.example.yuhanmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Hashtable;

public class JoinActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");
    Button SignBtn;
    EditText etid,etpwd;
    String id,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        etid=(EditText)findViewById(R.id.id);
        etpwd = (EditText)findViewById(R.id.pwd);
        SignBtn = (Button)findViewById(R.id.Signbtn);


        SignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=etid.getText().toString();
                pwd=etpwd.getText().toString();
                DatabaseReference myRef = database.getReference("users").child(id);
                Hashtable<String,String> users
                        =new Hashtable<String, String>();
                users.put("UserId",id);

                myRef.setValue(users);
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });



    }
}