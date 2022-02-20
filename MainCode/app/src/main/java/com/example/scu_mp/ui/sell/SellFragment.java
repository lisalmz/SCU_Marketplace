package com.example.scu_mp.ui.sell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.scu_mp.databinding.FragmentSellBinding;

public class SellFragment extends Fragment {

    private FragmentSellBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SellViewModel sellViewModel =
                new ViewModelProvider(this).get(SellViewModel.class);

        binding = FragmentSellBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSell;
        sellViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}