package com.example.scu_mp;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scu_mp.models.ChatData;
import com.example.scu_mp.models.ChatMembers;
import com.example.scu_mp.models.ChatMessages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class SendMessageActivity extends AppCompatActivity {

    private RecyclerView sendMessageRecycler;
    private int chat_counter = 0;
    private SearchView searchForUser;
    private EditText messageContent;
    private String user_name; //contains username of remote user
    private String recipient; //contains remote user id
    private String product_item; //holds result of calling SendMessageAdapter getSelectedProduct()
    private String uid;
    private List<ProductHelperClass> users_products;
    private List<String> months;
    private Button sendBtn;
    private SendMessageAdapter sendMessageAdapter; //handles the user product listing to select
    private DatabaseReference ChatDataDB, ChatMembersDB, ChatMessagesDB, UsersDB, ProductsDB;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        sendMessageRecycler = (RecyclerView) findViewById(R.id.recycler_user_item);
        searchForUser = findViewById(R.id.search_for_user);
        searchForUser.setQueryHint("Type username to search for user"); //Set search bar hint
        messageContent = findViewById(R.id.edit_chat_sendmsg);
        sendBtn = findViewById(R.id.button_chat_sendmsg);
        user_name = "";
        recipient = "";
        product_item = "";
        users_products = new ArrayList<>();

        months = Arrays.asList("January", "February", "March", "April", "May", "June", "July",
                                "August", "September", "October", "November", "December");

        ChatDataDB = FirebaseDatabase.getInstance().getReference().child("ChatData");
        ChatMembersDB = FirebaseDatabase.getInstance().getReference().child("ChatMembers");
        ChatMessagesDB = FirebaseDatabase.getInstance().getReference().child("ChatMessages");

        UsersDB = FirebaseDatabase.getInstance().getReference().child("Users");
        ProductsDB = FirebaseDatabase.getInstance().getReference().child("Product");

        firebaseAuth = FirebaseAuth.getInstance();

        //Get current user of app (you)
        uid = firebaseAuth.getCurrentUser().getUid();

        //Initialize chat counter to last num + 1 found in database
        //If none found, then initialize to zero
        ChatDataDB.addListenerForSingleValueEvent(new ValueEventListener() {
            String temp;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    temp = snap.getKey();
                    if(temp != null)
                        chat_counter = parseInt(temp) + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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

        //set on query text listener for search view
        searchForUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                String query = s.trim();
                if(!TextUtils.isEmpty(query)) //if not empty
                {
                    searchForUser(query);
                }
                else
                {
                    Toast.makeText(SendMessageActivity.this, "Please enter user to search for", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(!TextUtils.isEmpty(newText)) //if not empty
                {
                    searchForUser(newText);
                }
                else
                {
                    //Do something or nothing
                }
                return false;
            }
        });
    }

    private void sendMessageData()
    {
        //TODO: check to make sure user can't send message to themself
        //^user can send multiple messages to same user as long as they are about different items (creating different chats)
        //Check for empty values
        if(messageContent.getText().toString().equals(""))
        {
            Toast.makeText(SendMessageActivity.this, "Please enter message to send", Toast.LENGTH_SHORT).show();
            return;
        }
        if(searchForUser.getQuery().toString().equals(""))
        {
            Toast.makeText(SendMessageActivity.this, "Please enter user to search for", Toast.LENGTH_SHORT).show();
            return;
        }
        product_item = sendMessageAdapter.getSelected_product();
        if(product_item.equals(""))
        {
            Toast.makeText(SendMessageActivity.this, "Please select product", Toast.LENGTH_SHORT).show();
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

        String timesent_chat = months.get(month) + " " + day_of_month + ", " + year;
        String timesent_msg = zero_to_month + (month + 1) + "/" + zero_to_day + day_of_month + "/" + year + " - " + zero_to_hour + hour_of_day + ":" + zero_to_min + minute_of_hour;;
        String lastmessage = messageContent.getText().toString();

        //Get current user info
        UsersDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String current_user = snapshot.child(uid).child("user_name").getValue(String.class);
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    String remote_user = snap.child("user_name").getValue(String.class);
                    if(remote_user.equals(searchForUser.getQuery().toString()))
                    {
                        ChatData chatData = new ChatData(product_item, timesent_chat, lastmessage, current_user, remote_user);
                        //Push chat data to its table in database
                        ChatDataDB.child(valueOf(chat_counter)).setValue(chatData);
                        chat_counter++;
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //do nothing for now
            }
        });

        //Set the ChatMembers table
        String seller_user = recipient;
        ChatMembers chatMembers = new ChatMembers(uid, seller_user);

        //Set the ChatMessages table
        ChatMessages chatMessages = new ChatMessages(uid, lastmessage, timesent_msg);

        //Push chat member data to its table in database
        ChatMembersDB.child(valueOf(chat_counter)).setValue(chatMembers);

        //Push chat message data to its table in database
        DatabaseReference chatNode = ChatMessagesDB.child(valueOf(chat_counter));
        int msg_counter = 0;
        //msg_counter is incremented in MessageChatActivity - it is zero here because this is first message
        DatabaseReference msgNode = chatNode.child("m" + msg_counter);
        msgNode.setValue(chatMessages);

        Toast.makeText(SendMessageActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
        messageContent.setText(""); //make empty after sending
    }

    private void searchForUser(String s)
    {
        //Find user based on username
        UsersDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    if(Objects.equals(snap.child("user_name").getValue(String.class), s))
                    {
                        //save searched username to look up products
                        user_name = snap.child("user_name").getValue(String.class);
                        //get userId to set to recipient
                        recipient = snap.getKey();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //do nothing for now
            }
        });
        //Find products associated with searched user
        ProductsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users_products.clear();
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    if(Objects.equals(snap.child("username").getValue(String.class), user_name))
                    {
                        users_products.add(snap.getValue(ProductHelperClass.class));
                    }
                }
                sendMessageAdapter = new SendMessageAdapter(SendMessageActivity.this, users_products);
                sendMessageRecycler.setAdapter(sendMessageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //do nothing for now
            }
        });
    }

    public void goBack(View view)
    {
        //go back to message list
        finish();
    }
}