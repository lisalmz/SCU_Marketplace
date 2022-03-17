package com.example.scu_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.scu_mp.models.ChatMessages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MessageChatActivity extends AppCompatActivity {
    private int msg_id = 0; //keeps track of the message id (m0, m1, ...)
    private RecyclerView mMessageRecycler;
    private MessageChatAdapter mMessageAdapter;
    private TextView username, chat_date, product_item;
    private EditText chat_message;
    private Button sendBtn;
    private String user1, user2, chatID, uid, current_user, state;
    private List<ChatMessages> messages;
    private List<String> months;
    private DatabaseReference ChatDataDB, ChatMembersDB, ChatMessagesDB, UsersDB;
    private FirebaseAuth firebaseAuth;

    //Notifications
    private FirebaseMessaging firebaseMessaging;
    private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    private String serverKey = "key=" +
            "AAAAL2yoFqE:APA91bHCUHcXkrTsSmSypKYQbFjt9BKyoEeWk8XKVgluNbl8h6vkIUd6cRoMBtjexz6CDl9ml-kn0O5Np0G-CFptNqbdXvdL5SdL0-F-E0CSvX6QavgPu2IAcn0URwGeTDn69RVkLJt7";
    private String contentType = "application/json";
    private String topic = "";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);

        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_chat);
        username = findViewById(R.id.chat_username);
        chat_message = findViewById(R.id.edit_chat_message);
        sendBtn = findViewById(R.id.button_chat_send);
        chat_date = findViewById(R.id.chat_date);
        product_item = findViewById(R.id.product_item);
        chatID = (String) getIntent().getStringExtra("chatID");
        state = (String) getIntent().getStringExtra("State");

        messages = new ArrayList<>();

        months = Arrays.asList("January", "February", "March", "April", "May", "June", "July",
                                "August", "September", "October", "November", "December");

        ChatDataDB = FirebaseDatabase.getInstance().getReference().child("ChatData");
        ChatMembersDB = FirebaseDatabase.getInstance().getReference().child("ChatMembers");
        ChatMessagesDB = FirebaseDatabase.getInstance().getReference().child("ChatMessages");
        UsersDB = FirebaseDatabase.getInstance().getReference().child("Users");

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseMessaging = FirebaseMessaging.getInstance();

        requestQueue = new Volley().newRequestQueue(this);

        //Get current user of app (you)
        uid = firebaseAuth.getCurrentUser().getUid();
        firebaseMessaging.subscribeToTopic(uid); //associates your userID with notification to be sent

        UsersDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current_user = snapshot.child(uid).child("user_name").getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //do nothing for now
            }
        });

        //get product_item, remote username, and timestamp (most recent) based on currently selected chat
        ChatDataDB.addValueEventListener(new ValueEventListener() {
            String temp, temp2, user1_temp, user2_temp;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap:dataSnapshot.getChildren()) {
                    if(chatID.equals(snap.getKey()))
                    {
                        temp = snap.child("timestamp").getValue(String.class);
                        temp2 = snap.child("product_item").getValue(String.class);
                        user1_temp = snap.child("user1").getValue(String.class);
                        user2_temp = snap.child("user2").getValue(String.class);

                        if(snap.child("timestamp").getValue(String.class) != null)
                            chat_date.setText(temp);

                        if(temp2 != null)
                            product_item.setText(temp2);

                        /*
                        if (state.equals("0"))
                            username.setText(user2_temp); //display other user
                        else
                            username.setText(user1_temp);
                        */
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                //do nothing for now
            }
        });

        //Get current and remote user for chat
        ChatMembersDB.addValueEventListener(new ValueEventListener() {
            String temp, temp2;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap:dataSnapshot.getChildren()) {
                    if(chatID.equals(snap.getKey()))
                    {
                        temp = snap.child("seller_user").getValue(String.class);
                        temp2 = snap.child("buyer_user").getValue(String.class);
                        if(temp.equals(uid)) {
                            user1 = temp;
                            user2 = temp2;
                            topic = user2; //remote user
                        }
                        else
                        {
                            user1 = temp2;
                            user2 = temp;
                            topic = user2;
                        }
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                //do nothing for now
            }
        });

        //Get list of messages for chat
        ChatMessagesDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()) {
                    if(chatID.equals(snap.getKey()))
                    {
                        ChatMessages temp = snap.child("m" + msg_id).getValue(ChatMessages.class);
                        //get all messages in chat
                        while(temp != null)
                        {
                            messages.add(temp);
                            msg_id++;
                            temp = snap.child("m" + msg_id).getValue(ChatMessages.class);
                        }
                        mMessageAdapter = new MessageChatAdapter(MessageChatActivity.this, messages, user1);
                        mMessageRecycler.setLayoutManager(new LinearLayoutManager(MessageChatActivity.this));
                        mMessageRecycler.setAdapter(mMessageAdapter);
                        mMessageRecycler.scrollToPosition(messages.size() - 1); //scroll to latest message
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                //do nothing for now
            }
        });

        //set on click listeners for buttons
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sendMessageData();
            }
        });
    }

    private void sendMessageData()
    {
        Log.i("Topic:", topic);
        String message_content = chat_message.getText().toString();
        if(message_content.equals(""))
        {
            Toast.makeText(MessageChatActivity.this, "Please enter message to send", Toast.LENGTH_SHORT).show();
            return;
        }

        //Get time from system function and use that for time stamp value
        Calendar currentTime = Calendar.getInstance();
        int year = currentTime.get(Calendar.YEAR);
        int month = currentTime.get(Calendar.MONTH); //0 = January -> 11 = December
        int day_of_month = currentTime.get(Calendar.DAY_OF_MONTH); //between 1 and 31
        //int day_of_week = currentTime.get(Calendar.DAY_OF_WEEK); //0 = Sunday -> 6 = Saturday
        int hour_of_day = currentTime.get(Calendar.HOUR_OF_DAY); //0-23
        int minute_of_hour = currentTime.get(Calendar.MINUTE); //0-59
        String zero_to_month = "";
        String zero_to_day = "";
        String zero_to_hour = "";
        String zero_to_min = "";

        //Formatting
        if(month < 10) zero_to_month = "0";
        if(day_of_month < 10) zero_to_day = "0";
        if(hour_of_day < 10) zero_to_hour = "0";
        if(minute_of_hour < 10) zero_to_min = "0";

        String chat_date = months.get(month) + " " + day_of_month + ", " + year;
        String timesent = zero_to_month + (month + 1) + "/" + zero_to_day + day_of_month + "/" + year + " - " + zero_to_hour + hour_of_day + ":" + zero_to_min + minute_of_hour;
        ChatMessages chatMessages = new ChatMessages(user1, message_content, timesent);
        DatabaseReference chatNode = ChatMessagesDB.child(chatID);
        DatabaseReference msgNode = chatNode.child("m" + msg_id);
        msgNode.setValue(chatMessages);

        //update ChatData (metadata), only update lastMessage and timestamp
        ChatDataDB.child(chatID).child("lastMessage").setValue(message_content);
        ChatDataDB.child(chatID).child("timestamp").setValue(chat_date);

        JSONObject notification = new JSONObject();
        JSONObject notificationBody = new JSONObject();

        try {
            notificationBody.put("title", "New Message");
            notificationBody.put("message", message_content);
            notification.put("to", topic);
            notification.put("data", notificationBody);
        }
        catch (JSONException e)
        {

        }

        //Send the notification
        sendNotification(notification);

        //Set editText to empty after clicking send
        chat_message.setText("");
    }

    private void sendNotification(JSONObject notification)
    {
        JsonObjectRequest objectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        //Do nothing
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Do nothing
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        requestQueue.add(objectRequest);
    }

    public void goBack(View view)
    {
        //go back to message list
        finish();
    }
}