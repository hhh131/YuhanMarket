package com.example.yuhanmarket.ui.ChatListPack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yuhanmarket.R;
import com.example.yuhanmarket.PostPack.WritePostActivity;
import com.example.yuhanmarket.TapActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListFragment extends Fragment {
    private static final String TAG = "ChatListFragment";
    private ChatListViewModel chatListViewModel;
    private RecyclerView recyclerView;
    ChatListAdapter listAdapter;
    private RecyclerView.LayoutManager layoutManager;
    String UserId;
    Button PostBtn;
    ArrayList<ChatListVO> listVOArray;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatListViewModel =
                new ViewModelProvider(this).get(ChatListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chatlist, container, false);


        ActionBar actionBar = ((TapActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("채팅");


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shard", Context.MODE_PRIVATE);
        UserId= sharedPreferences.getString("UserId","");

        listVOArray = new ArrayList<>();
        recyclerView = (RecyclerView)root.findViewById(R.id.ryView);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        listAdapter = new ChatListAdapter(listVOArray, UserId);
        recyclerView.setAdapter(listAdapter);




        myRef = database.getReference("Chat");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // Log.e(TAG,snapshot.getValue().toString());
                for(DataSnapshot dataSnapshot1: snapshot.getChildren())
                {
                    ChatListVO listVO = dataSnapshot1.getValue(ChatListVO.class);
                    //Log.e(TAG, listVO.getUserId());
                    listVOArray.add(listVO);
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
}