package com.example.scu_mp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.scu_mp.models.ChatMessages;

import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<ChatMessages> mMessageChat;
    private String currentUser;

    public MessageChatAdapter(Context context, List<ChatMessages> messageChat, String currentUser)
    {
        this.mContext = context;
        this.mMessageChat = messageChat;
        this.currentUser = currentUser;
    }

    @Override
    public int getItemCount()
    {
        return mMessageChat.size();
    }

    //Determines appropriate viewtype according to sender of the message
    @Override
    public int getItemViewType(int position)
    {
        ChatMessages msg = (ChatMessages) mMessageChat.get(position);
        if(msg.getUser().equals(currentUser)) //message sent
        {
            return VIEW_TYPE_MESSAGE_SENT;
        }
        else //message received
        {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    //Inflates the appropriate layout according to ViewType
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;

        if(viewType == VIEW_TYPE_MESSAGE_SENT)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_chat_sent, parent, false);
            return new SentMessageHolder(view);
        }
        else if(viewType == VIEW_TYPE_MESSAGE_RECEIVED)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_chat_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessages message = (ChatMessages) mMessageChat.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_chat_message_me);
            timeText = (TextView) itemView.findViewById(R.id.text_chat_timestamp_me);
        }

        void bind(ChatMessages message) {
            messageText.setText(message.getMessage());
            timeText.setText(message.getTimestamp());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_chat_message_other);
            timeText = (TextView) itemView.findViewById(R.id.text_chat_timestamp_other);
        }

        void bind(ChatMessages message) {
            messageText.setText(message.getMessage());
            timeText.setText(message.getTimestamp());
        }
    }
}
