package com.example.yuhanmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ReportActivity extends AppCompatActivity {
    EditText etContent;
    Button btn;
    TextView taget;
    String User;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setTitle("신고하기");
        Intent intent = getIntent();
        User=intent.getStringExtra("User");
        etContent =(EditText) findViewById(R.id.reportContent);
        taget=(TextView) findViewById(R.id.target);
        btn = (Button) findViewById(R.id.btn);
        taget.setText("\'"+User+"\'님을 신고하는 이유를 간략하게 알려주세요");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String > reportinfo =new HashMap<>();
                    reportinfo.put("UserId",User);
                    reportinfo.put("Content",etContent.getText().toString());

                myRef.child("report").push().setValue(reportinfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"신고 완료.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


            }
        });

















    }
}