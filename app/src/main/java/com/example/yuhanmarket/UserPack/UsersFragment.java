package com.example.yuhanmarket.UserPack;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yuhanmarket.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersFragment extends Fragment {
    private static final String TAG = "UsersFragment";
    private RecyclerView recyclerView;
    UserAdapter userAdapter;
    private RecyclerView.LayoutManager layoutManager;
    String UserId;
    ArrayList<User> UserArray;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");
    private UserViewModel userViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_users, container, false);

     /*   final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shard", Context.MODE_PRIVATE);
        UserId= sharedPreferences.getString("UserId","");

        UserArray = new ArrayList<>();
        recyclerView = (RecyclerView)root.findViewById(R.id.ryView);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        userAdapter = new UserAdapter(UserArray,UserId);
        recyclerView.setAdapter(userAdapter);




         myRef = database.getReference("users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e(TAG,snapshot.getValue().toString());
                for(DataSnapshot dataSnapshot1: snapshot.getChildren())
                {
                    User user = dataSnapshot1.getValue(User.class);
                    Log.e(TAG,user.getUserId());
                    UserArray.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}