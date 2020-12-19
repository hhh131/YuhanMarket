package com.example.yuhanmarket.PostListPack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yuhanmarket.PostPack.WritePostActivity;
import com.example.yuhanmarket.R;
import com.example.yuhanmarket.TapActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListFragment extends Fragment {
    int REQUEST_IMAGE_CODE=1001;
    int REQUEST_EXTERNAL_STORAGE_PERMISSION=1002;
    File localFile;
    private static final String TAG = "UsersFragment";
    private RecyclerView recyclerView;
    private StorageReference mStorageRef;
    ListAdapter listAdapter;
    private RecyclerView.LayoutManager layoutManager;
    String UserId;
    ArrayList<ListVO> listVOArray;
    ImageView PostImg;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");
    private ListViewModel listViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list, container, false);

     /*   final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        PostImg = root.findViewById(R.id.ivUser);
        ActionBar actionBar = ((TapActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("목록");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shard", Context.MODE_PRIVATE);
        UserId= sharedPreferences.getString("UserId","");

        listVOArray = new ArrayList<>();
        recyclerView = (RecyclerView)root.findViewById(R.id.ryView);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getContext());

        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        listAdapter = new ListAdapter(listVOArray,UserId);
        recyclerView.setAdapter(listAdapter);




         myRef = database.getReference("ProductList");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.e(TAG,snapshot.getValue().toString());
                for(DataSnapshot dataSnapshot1: snapshot.getChildren())
                {
                    ListVO listVO = dataSnapshot1.getValue(ListVO.class);
                  //  Log.e(TAG, listVO.getUserId());
                  //log.e(TAG, listVO.getTitle());
                    listVO.setKey(dataSnapshot1.getKey().toString());
                    //Log.e(TAG, dataSnapshot1.getKey().toString());
                    listVOArray.add(listVO);

                }

                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getContext(), WritePostActivity.class);
                startActivity(intent);
            }
        });







        return root;
    }


}