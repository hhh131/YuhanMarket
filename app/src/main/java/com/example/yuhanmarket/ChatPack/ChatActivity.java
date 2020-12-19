package com.example.yuhanmarket.ChatPack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yuhanmarket.PostListPack.ListVO;
import com.example.yuhanmarket.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private RecyclerView recyclerView;
    ChatAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText editText;
    ImageButton bntSend;
    private String UserId, OtherId, RoomId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    ArrayList<Chat> ChatArray;
    DatabaseReference myRef = database.getReference("");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        SharedPreferences sharedPreferences = getSharedPreferences("shard", Context.MODE_PRIVATE);
        UserId = sharedPreferences.getString("UserId", "");

        database = FirebaseDatabase.getInstance();
        // UserId=getIntent().getStringExtra("UserId");
        OtherId = getIntent().getStringExtra("OtherId");
        setTitle(OtherId);
        recyclerView = (RecyclerView) findViewById(R.id.ryView);
        bntSend = (ImageButton) findViewById(R.id.btnSend);
        editText = (EditText) findViewById(R.id.etSend);
        ChatArray = new ArrayList<>();

        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new ChatAdapter(ChatArray, UserId);
        recyclerView.setAdapter(mAdapter);


     /*   final Hashtable<String, String> ChatRoom
                = new Hashtable<String, String>();
        ChatRoom.put("OtherId", OtherId);
        ChatRoom.put("UserId", UserId);*/


        //RoomName = ChatRoom.get("OtherId") + ChatRoom.get("UserId");

  /*      ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Chat chat = dataSnapshot.getValue(Chat.class);
               // String commentKey = dataSnapshot.getKey();
                String stUserId = chat.getUserId();
               // Log.d(TAG,chat.getText().toString());
                ChatArray.add(chat);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Chat chat = dataSnapshot.getValue(Chat.class);

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(getApplicationContext(), "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        DatabaseReference ref = database.getReference("Chat").child(RoomId);
        ref.addChildEventListener(childEventListener);
*/
        bntSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stText = editText.getText().toString();
             //   Toast.makeText(getApplicationContext(), stText, Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String datetime = dateFormat.format(calendar.getTime());

                ChatModel chatModel = new ChatModel();
                chatModel.users.put(UserId,true);
                chatModel.users.put(OtherId,true);




                myRef = database.getReference("Chat");
                if(RoomId==null) {
                    bntSend.setEnabled(false);
                    myRef.push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkChatRoom();
                        }
                    });

                }
                else{
                    Map<String, String> comment = new HashMap<>();//채팅방 대화내용
                    comment.put("UserId",UserId);
                    comment.put("Message",stText);
              /*      ChatModel.Comment comment = new ChatModel.Comment();
                    comment.uid=UserId;
                    comment.message=stText;*/

                    //Log.e("ASdasd",comment.message);

                    Toast.makeText(getApplicationContext(),RoomId,Toast.LENGTH_SHORT).show();
                   myRef.child(RoomId).child("comments").push().setValue(comment);
                }


                /* Hashtable<String, String> comment
                        = new Hashtable<String, String>();
                comment.put("UserId", UserId);
                comment.put("text", stText);
                comment.put("time", datetime);



             // String Roomkey= myRef.child(RoomName).push().getKey();

               // myRef.child(RoomName).setValue(ChatRoom);
                myRef.child(RoomName).push().setValue(comment);*/

            }
        });
        checkChatRoom();
    }


    void checkChatRoom()
    {
        database.getReference("Chat").orderByChild("users/"+UserId).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){

                  /*  ChatModel chatModel=item.getValue(ChatModel.class);
                    if(chatModel.users.containsKey(OtherId)){
                            RoomId=item.getKey();
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}



