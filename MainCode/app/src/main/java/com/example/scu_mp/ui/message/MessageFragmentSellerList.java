package com.example.scu_mp.ui.message;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scu_mp.MessageListAdapter;
import com.example.scu_mp.R;
import com.example.scu_mp.databinding.FragmentMessageSellerListBinding;
import com.example.scu_mp.models.ChatData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragmentSellerList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragmentSellerList extends Fragment {
    private FragmentMessageSellerListBinding binding;
    private String current_user;
    RecyclerView recyclerView;
    List<ChatData> sellerChatData;
    List<String> relevantChats;
    private DatabaseReference ChatDataDB, ChatMembersDB, UsersDB;
    MessageListAdapter msgListAdapter;
    private FirebaseAuth firebaseAuth;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MessageFragmentSellerList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragmentSellerList.
     */
    public static MessageFragmentSellerList newInstance(String param1, String param2) {
        MessageFragmentSellerList fragment = new MessageFragmentSellerList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.i("MessageFragmentSellerList", "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ChatDataDB = FirebaseDatabase.getInstance().getReference().child("ChatData");
        ChatMembersDB = FirebaseDatabase.getInstance().getReference().child("ChatMembers");
        UsersDB = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.i("MessageFragmentSellerList", "onCreateView");
        // Inflate the layout for this fragment
        binding = FragmentMessageSellerListBinding.inflate(inflater, container,false);
        View view = binding.getRoot();
        //Get current user
        recyclerView = view.findViewById(R.id.message_list_recycler_view);
        sellerChatData = new ArrayList<>();
        relevantChats = new ArrayList<>();
        loadChats();
        return view;
    }

    // load the user chat layout using chat node
    private void loadChats()
    {
        String uid = firebaseAuth.getCurrentUser().getUid();
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

        //Get the chats associated with you (the current user) as buyer
        ChatMembersDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                relevantChats.clear(); //clear list
                for(DataSnapshot snap : snapshot.getChildren()) {
                    if(snap.child("buyer_user").getValue(String.class).equals(uid))
                    {
                        relevantChats.add(snap.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //do nothing for now
            }
        });

        //Get general chat data to be displayed
        ChatDataDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sellerChatData.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    if(relevantChats.contains(snap.getKey()))
                    {
                        sellerChatData.add(snap.getValue(ChatData.class));
                    }
                }
                //createNotification();
                msgListAdapter = new MessageListAdapter(getActivity(), sellerChatData, relevantChats, current_user, 1);
                recyclerView.setAdapter(msgListAdapter);
                recyclerView.scrollToPosition(sellerChatData.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //do nothing for now
            }
        });
    }

    private void createNotification()
    {
        NotificationManager notMan = getSystemService(getActivity(), NotificationManager.class);

        CharSequence name = "channel_name";
        String desc = "For incoming messages";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("Channel_ID", name, importance);
        channel.setDescription(desc);

        notMan.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "Channel_ID")
                .setSmallIcon(R.drawable.ic_message_black)
                .setContentTitle("Message Notification")
                .setContentText("Messages Data Synced")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Actually display notification
        notMan.notify(1234, builder.build());
    }
}