package com.example.scu_mp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.scu_mp.AddressActivity;
import com.example.scu_mp.LoginActivity;
import com.example.scu_mp.MainActivity;
import com.example.scu_mp.ProfilePaymentmethodsActivity;
import com.example.scu_mp.R;
import com.example.scu_mp.databinding.FragmentProfileBinding;
import com.example.scu_mp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private FirebaseUser user;
    private DatabaseReference ref;

    private String userID;

    RelativeLayout paymentcell,addresscell;
    AppCompatButton bt_logout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container, false);

        paymentcell=(RelativeLayout) view.findViewById(R.id.paymentcell);
        paymentcell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ProfilePaymentmethodsActivity.class);
                startActivity(intent);
            }
        });

        addresscell=(RelativeLayout) view.findViewById(R.id.addresscell);
        addresscell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });

        bt_logout = view.findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(getActivity(), LoginActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                getActivity().finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullname = view.findViewById(R.id.tv_full_name);
        final TextView username = view.findViewById(R.id.tv_username);
        final TextView email_adr = view.findViewById(R.id.tv_email_profile);
        final TextView phone_num = view.findViewById(R.id.tv_phone_profile);
        // final TextView address = view.findViewById(R.id.tv_address_profile); --> not sure how to implement because user enters info later

        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User prof = snapshot.getValue(User.class);

                if (prof != null){
                    String full_name = prof.first_name + " " + prof.last_name;
                    String user_name = prof.user_name;
                    String email = prof.email;
                    String phone = prof.phone;

                    fullname.setText(full_name);
                    username.setText(user_name);
                    email_adr.setText(email);
                    phone_num.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong! Sorry!" , Toast.LENGTH_SHORT).show();
            }
        });
        //binding = FragmentProfileBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        //return root;
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}