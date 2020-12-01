package com.example.yuhanmarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Hashtable;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class JoinActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");
    Button SignBtn;
    EditText etid,etpwd,etnick;
    String id,pwd,nick,code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        etid=(EditText)findViewById(R.id.id);
        etpwd = (EditText)findViewById(R.id.pwd);
        SignBtn = (Button)findViewById(R.id.Signbtn);
        etnick=(EditText)findViewById(R.id.nick);



        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());



        SignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                code=Integer.toString(random.nextInt(9999 - 1000 + 1) + 1000);
                id=etid.getText().toString();
                pwd=etpwd.getText().toString();
                nick=etnick.getText().toString();
            if(id.isEmpty()&&pwd.isEmpty()&&nick.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"내용을 모두 입력해주세요",Toast.LENGTH_SHORT).show();
            }
            else
                {
                    DatabaseReference myRef = database.getReference("users").child(id);

                    UserVO userVO = new UserVO(id,pwd,nick,false,code);

                    myRef.setValue(userVO);


                    try {
                        GMailSender gMailSender = new GMailSender("gmlrnjs131@gmail.com", "akxlcl157");
                        //GMailSender.sendMail(제목, 본문내용, 받는사람);
                        gMailSender.sendMail("유한마켓 인증메일 입니다.","인증 번호입니다. 번호를 입력하여 회원가입을 완료하여 주세요 \n인증번호 : "+code,"heekwon131@yuhan.ac.kr");
                        Toast.makeText(getApplicationContext(), "송신 완료", Toast.LENGTH_SHORT).show();
                        //sendResultOk = true;
                        //button.setEnabled(false);
                    } catch (SendFailedException e) {
                        Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    } catch (MessagingException e) {
                        Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finish();
                    Intent intent = new Intent(getApplicationContext(),AuthActivity.class);
                    intent.putExtra("JoinId",id);
                    startActivity(intent);

            }







            }
        });



    }
}