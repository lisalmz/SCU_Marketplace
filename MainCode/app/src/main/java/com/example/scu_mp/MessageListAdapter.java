package com.example.scu_mp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scu_mp.models.ChatData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.Myholder> {
    Context context;
    List<ChatData> chatList;
    List<String> relevantChatsID;
    String current_user, state_temp;
    int state; //0 - buyer, 1 - seller

    public MessageListAdapter(Context context, List<ChatData> chats, List<String> relevantChatsID, String current_user, int state) {
        this.context = context;
        this.chatList = chats;
        this.relevantChatsID = relevantChatsID;
        this.current_user = current_user;
        this.state = state;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.i("MessageListAdapter", "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_message_list_row, parent, false);
        return new Myholder(view);
    }

    //TODO:figure out why names do not display correctly
    @Override
    public void onBindViewHolder(@NonNull Myholder holder, @SuppressLint("RecyclerView") int position) {
        //Log.i("MessageListAdapter", "onBindViewHolder");
        //String user1 = chatList.get(position).getUser1();
        String user1 = chatList.get(holder.getBindingAdapterPosition()).getUser1();
        //String user2 = chatList.get(position).getUser2();
        String user2 = chatList.get(holder.getBindingAdapterPosition()).getUser2();
        //String lastmess = chatList.get(position).getLastMessage();
        String lastmess = chatList.get(holder.getBindingAdapterPosition()).getLastMessage();
        //String interested_item = chatList.get(position).getProduct_item();
        String interested_item = chatList.get(holder.getBindingAdapterPosition()).getProduct_item();
        //String latest_time = chatList.get(position).getTimestamp();
        String latest_time = chatList.get(holder.getBindingAdapterPosition()).getTimestamp();

        //If message is from today, display those respectively, else display 'Month Day, Year'
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July",
                                            "August", "September", "October", "November", "December");
        Calendar currentTime = Calendar.getInstance();
        int year = currentTime.get(Calendar.YEAR);
        int month = currentTime.get(Calendar.MONTH); //0 = January -> 11 = December
        int day_of_month = currentTime.get(Calendar.DAY_OF_MONTH); //between 1 and 31
        String current_date = months.get(month) + " " + day_of_month + ", " + year;

        if(latest_time.equals(current_date)) latest_time = "Today";

        // if no last message then Hide the layout
        if (lastmess == null || lastmess.equals("default")) {
            holder.lastmessage.setVisibility(View.GONE);
        } else {
            holder.lastmessage.setVisibility(View.VISIBLE);
            holder.lastmessage.setText(lastmess);
            holder.timestamp.setText(latest_time);
        }

        if(state == 0) //current user is buyer (seller page)
        {
            holder.name.setText(user2);
            state_temp = "0";
        }
        else //current user is seller (buyer page)
        {
            holder.name.setText(user1);
            state_temp = "1";
        }
        /*
        if (Objects.equals(current_user, user1))
            holder.name.setText(user2); //display other user
        else
            holder.name.setText(user1);

         */
        holder.interested_item.setText(interested_item);

        // redirecting to chat activity on item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("MessageListAdapter", "onClick Message from MessageList");
                Intent intent = new Intent(context, MessageChatActivity.class);

                //Pass the id for the relevant chat
                intent.putExtra("State", state_temp);
                intent.putExtra("chatID", relevantChatsID.get(holder.getBindingAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class Myholder extends RecyclerView.ViewHolder {
        //ImageView status, block, seen;
        TextView name, lastmessage, interested_item, timestamp;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            //status = itemView.findViewById(R.id.onlinestatus);
            name = itemView.findViewById(R.id.from);
            lastmessage = itemView.findViewById(R.id.txt_content);
            interested_item = itemView.findViewById(R.id.interested_item);
            timestamp = itemView.findViewById(R.id.timestamp);
            //block = itemView.findViewById(R.id.blocking);
            //seen = itemView.findViewById(R.id.seen);
        }
    }
}
