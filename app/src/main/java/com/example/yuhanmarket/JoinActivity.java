package com.example.yuhanmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Hashtable;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

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

                try {
                    GMailSender gMailSender = new GMailSender("gmlrnjs131@gmail.com", "akxlcl157");
                    //GMailSender.sendMail(제목, 본문내용, 받는사람);
                    gMailSender.sendMail("유한마켓 인증메일 입니다.","유한마켓 인증 메일입니다.","gmlrnjs131@naver.com");
                    Toast.makeText(getApplicationContext(), "송신 완료", Toast.LENGTH_SHORT).show();
                    //sendResultOk = true;
                  //button.setEnabled(false);
                } catch (SendFailedException e) {
                    Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (MessagingException e) {
                    Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);






            }
        });



    }
}