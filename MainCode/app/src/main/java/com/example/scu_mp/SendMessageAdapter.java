package com.example.scu_mp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SendMessageAdapter extends RecyclerView.Adapter {
    Context context;
    String selected_product = "";
    List<ProductHelperClass> products;

    public SendMessageAdapter(Context context, List<ProductHelperClass> products)
    {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getItemCount()
    {
        return products.size();
    }

    //Inflates the appropriate layout according to ViewType
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.sendmsg_productlist_row, parent, false);
        return new ProductListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String item_name, item_price, item_category;
        item_name = products.get(position).getItemName();
        item_price = products.get(position).getPrice();
        item_category = products.get(position).getCategory();
        ((ProductListHolder) holder).linlayout.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v)
            {
                if(selected_product.equals(""))
                {
                    selected_product = products.get(position).getItemName();
                    ((ProductListHolder) holder).linlayout.setBackgroundColor(R.color.light_gray);
                }
                else if(selected_product.equals(products.get(position).getItemName())) //same selection clicked again
                {
                    selected_product = ""; //no selected product
                    //set background color to null
                    ((ProductListHolder) holder).linlayout.setBackgroundColor(R.drawable.list_border);
                }
            }
        });
        ((ProductListHolder) holder).bind(item_name, item_price, item_category);
    }

    private class ProductListHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, itemCategory;
        LinearLayout linlayout;

        ProductListHolder(View itemView)
        {
            super(itemView);

            linlayout = (LinearLayout) itemView;

            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemCategory = (TextView) itemView.findViewById(R.id.item_category);
        }

        void bind(String item_name, String item_price, String item_category)
        {
            itemName.setText(item_name);
            String formatted_itemPrice = "$" + item_price;
            itemPrice.setText(formatted_itemPrice);
            itemCategory.setText(item_category);
        }
    }

    //Accessor method
    public String getSelected_product()
    {
        return selected_product;
    }
}
