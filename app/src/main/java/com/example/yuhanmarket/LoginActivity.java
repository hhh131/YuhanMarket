package com.example.yuhanmarket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.yuhanmarket.ChatPack.ChatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {
    Button joinBtn,loginBtn;
    EditText etid,etpwd;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       etid= (EditText)findViewById(R.id.id);
        etpwd = (EditText)findViewById(R.id.pwd);
        loginBtn = (Button)findViewById(R.id.loginbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etid.getText().toString().isEmpty()&&etpwd.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력 해 주세요", Toast.LENGTH_SHORT).show();
                }else {
                    if (etid.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "아이디를 입력 해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(etpwd.getText().toString().isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력 해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String id = etid.getText().toString();
                                String pwd = etpwd.getText().toString();
                                if(snapshot.hasChild(id))
                                {

                                    if(snapshot.child(id).child("pwd").getValue().equals(pwd))
                                    {

                                        if(snapshot.child(id).child("mailAuthStatus").getValue().equals(true))
                                        {
                                            Intent intent = new Intent(getApplicationContext(), TapActivity.class);


                                            SharedPreferences sharedPreferences = getSharedPreferences("shard",Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("UserId",etid.getText().toString());
                                            editor.commit();


                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            SharedPreferences sharedPreferences = getSharedPreferences("shard",Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("UserId",etid.getText().toString());
                                            editor.commit();

                                            Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                                            intent.putExtra("UserId",etid.getText().toString());
                                            startActivity(intent);

                                        }


                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"비밀번호가 일치 하지 않습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"아이디가 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                                }




                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                }



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