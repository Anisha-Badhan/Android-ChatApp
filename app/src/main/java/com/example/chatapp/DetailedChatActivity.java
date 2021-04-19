package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import com.example.chatapp.Adapters.MessageAdapter;
import com.example.chatapp.Models.MessagesModel;
import com.example.chatapp.databinding.ActivityDetailedChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailedChatActivity extends AppCompatActivity {

    ActivityDetailedChatBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        final String senderId = auth.getUid();
        String profilPic = getIntent().getStringExtra("profilePic");
        String userName = getIntent().getStringExtra("userName");
        String recieverId = getIntent().getStringExtra("userId");

        binding.txtUser.setText(userName);

        Picasso.get().load(profilPic).placeholder(R.drawable.ic_female_avatar_2).into(binding.imgUser);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedChatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<MessagesModel> messagesModels = new ArrayList<>();
        final MessageAdapter messageAdapter = new MessageAdapter(messagesModels, this);
        binding.UserChat.setAdapter(messageAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.UserChat.setLayoutManager(linearLayoutManager);

        final String senderRoom = senderId + recieverId;
        final String recieverRoom = recieverId + senderId;

        database.getReference().child("Chat Detail")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        messagesModels.clear();
                        for(DataSnapshot snapshot1: snapshot.getChildren()){
                            MessagesModel msgModel = snapshot1.getValue(MessagesModel.class);

                            messagesModels.add(msgModel);
                        }
                       messageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = binding.txtMsg.getText().toString();
                final MessagesModel msgModel = new MessagesModel(senderId, msg);
                msgModel.setTimestamp(new Date().getTime());
                binding.txtMsg.setText("");

                database.getReference().child("Chat Detail")
                        .child(senderRoom)
                        .push()
                        .setValue(msgModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("Chat Detail")
                                .child(recieverRoom)
                                .push()
                                .setValue(msgModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });
            }
        });
    }
}