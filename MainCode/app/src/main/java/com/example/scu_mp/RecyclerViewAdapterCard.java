package com.example.scu_mp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterCard extends RecyclerView.Adapter<RecyclerViewAdapterCard.ViewHolder1> {
    ArrayList<Card> card;

    public RecyclerViewAdapterCard(ArrayList<Card> card){
        this.card=card;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterCard.ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.card_item,parent,false);
        ViewHolder1 viewHolder1=new ViewHolder1(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterCard.ViewHolder1 holder, int position) {
        holder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
        }

        public void bindData(int position) {
            String cardnumber;
            String cardname;
            TextView textView=itemView.findViewById(R.id.cardnumber);
            textView.setText(card.get(position).cardnumber);
            TextView textView1=itemView.findViewById(R.id.cardname);
            textView1.setText(card.get(position).cardname);
        }
    }
}
