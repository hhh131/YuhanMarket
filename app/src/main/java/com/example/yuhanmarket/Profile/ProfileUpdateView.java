package com.example.yuhanmarket.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yuhanmarket.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileUpdateView extends AppCompatActivity {
    EditText pwd,pwdck;
    Button btn;
    TextView id;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    String UserId,bePwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update_view);
        setTitle("비밀번호 수정");
        id=(TextView)findViewById(R.id.tvId);
        pwd=(EditText)findViewById(R.id.pwd);
        pwdck=(EditText)findViewById(R.id.pwdck);
        btn= (Button)findViewById(R.id.updateBtn);
        SharedPreferences sharedPreferences = getSharedPreferences("shard", Context.MODE_PRIVATE);
        UserId= sharedPreferences.getString("UserId","");


            id.setText(UserId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bePwd=snapshot.child(UserId).child("pwd").getValue().toString();
                //id.setText(snapshot.child(UserId).getKey());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stPwd= pwd.getText().toString();
                String stPwdck = pwdck.getText().toString();

                if(stPwd.equals(stPwdck))
                {
                    myRef.child(UserId).child("pwd").setValue(stPwd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"수정 완료",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                }







            }
        });



    }
}