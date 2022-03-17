package com.example.scu_mp.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.scu_mp.MessageChatActivity;
import com.example.scu_mp.R;
import com.example.scu_mp.SendMessageActivity;
import com.example.scu_mp.databinding.FragmentMessageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment containing the message list
 */
public class MessageFragment extends Fragment {
    //TODO: implement message list search functionality if you have time
    Context context;
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentMessageBinding binding;
    static int selected_tab;

    public static MessageFragment newInstance(int index) {
        MessageFragment fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.i("MessageListFragment", "onCreate");
        super.onCreate(savedInstanceState);

        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        //Log.i("MessageListFragment", "Tab Index: " + index);
        selected_tab = index;
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMessageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = view.findViewById(R.id.msg_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu(view);
                context = getActivity();
                Intent intent = new Intent(context, SendMessageActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }

    //Add fragments to tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new MessageFragmentBuyerList(), "Buyers");
        adapter.addFragment(new MessageFragmentSellerList(), "Sellers");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /* TODO: finish implementing if you have more time
    public void showPopupMenu(View view)
    {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        popup.getMenuInflater().inflate(R.menu.message_menu, popup.getMenu());
        context = getActivity();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.send_message:
                        Intent intent = new Intent(context, SendMessageActivity.class);
                        context.startActivity(intent);
                        return true;
                    case R.id.notifications:
                        //TODO: Do something -- call function
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }
     */

    @Override
    public void onDestroyView() {
        //Log.i("MessageListFragment", "onDestroyView");
        super.onDestroyView();
        binding = null;
    }
}