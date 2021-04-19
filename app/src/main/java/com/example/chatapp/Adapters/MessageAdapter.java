package com.example.chatapp.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Models.MessagesModel;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter{

    ArrayList<MessagesModel> messagesModels;
    Context context;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public MessageAdapter(ArrayList<MessagesModel> messagesModels, Context context) {
        this.messagesModels = messagesModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == SENDER_VIEW_TYPE){

            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new senderViewHolder(view);

        }else{

            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever, parent, false);
            return new ReceiverViewHolder(view);

        }
    }

    @Override
    public int getItemViewType(int position) {

        if(messagesModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;

        }else{

            return RECEIVER_VIEW_TYPE;

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {



        MessagesModel messagesModel = messagesModels.get(position);

        if(holder.getClass() == senderViewHolder.class)
        {
            ((senderViewHolder)holder).senderMsgs.setText(messagesModel.getMsg());
            ((senderViewHolder)holder).senderTimestamp.setText(messagesModel.getTimestamp().toString());

        }
        else
            {
            ((ReceiverViewHolder)holder).receiverMsgs.setText(messagesModel.getMsg());
            ((ReceiverViewHolder)holder).receiverTimestamp.setText(messagesModel.getTimestamp().toString());
        }

    }

    @Override
    public int getItemCount() {
        return messagesModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{

        TextView receiverMsgs, receiverTimestamp;

        public ReceiverViewHolder(@NonNull View itemView){
            super(itemView);

            receiverMsgs = itemView.findViewById(R.id.txtReceiver);
            receiverTimestamp = itemView.findViewById(R.id.txtTime);

        }
    }


    public class senderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMsgs, senderTimestamp;
        public senderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsgs = itemView.findViewById(R.id.txtSender);
            senderTimestamp = itemView.findViewById(R.id.txtSenderTime);
        }
    }

}
