package com.example.scu_mp.ui.sell;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.scu_mp.R;
import com.example.scu_mp.SellActivity1;
import com.example.scu_mp.databinding.FragmentSellBinding;
import com.example.scu_mp.listing;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SellFragment extends Fragment {

    private FragmentSellBinding binding;

    private Button furniture, textbooks, computers, schoolsupplies, transportation, miscellaneous;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sell,container, false);
        Button furniture = view.findViewById(R.id.btnfurniture);
        Button textbooks = view.findViewById(R.id.btntextbooks);
        Button computers = view.findViewById(R.id.btncomputers);
        Button schoolsupplies = view.findViewById(R.id.btnschoolsupplies);
        Button transportation = view.findViewById(R.id.btntransportation);
        Button miscellaneous = view.findViewById(R.id.btnmiscellaneous);


        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "Furniture");
                startActivity(intent);
            }
        });

        textbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "Textbooks");
                startActivity(intent);
            }
        });

        transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "Transportation");
                startActivity(intent);
            }
        });

        computers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "Computers");
                startActivity(intent);
            }
        });

        schoolsupplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "School Supplies");
                startActivity(intent);
            }
        });

        miscellaneous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), listing.class);
                intent.putExtra("Category", "Miscellaneous");
                startActivity(intent);
            }
        });





        Button btnOpen1 = view.findViewById(R.id.btnStartYourListing);
        btnOpen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SellActivity1.class);
                startActivity(intent);
            }
        });
        Button btnOpen2 = view.findViewById(R.id.btnListAnItem);
        btnOpen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SellActivity1.class);
                startActivity(intent);
            }
        });
        return view;


    }
    public SellFragment(){
        //Required empty public constructor
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}