package com.jnu.student.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jnu.student.Activity.HelpActivity;
import com.jnu.student.Activity.PhotoActivity;
import com.jnu.student.Activity.SetDungeonActivity;
import com.jnu.student.Activity.WalletActivity;
import com.jnu.student.Activity.addRewardActivity;
import com.jnu.student.R;

public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // 找到布局中的视图组件(钱包)
        TextView walletTextView = rootView.findViewById(R.id.walletTextView);
        // 设置钱包点击事件监听器
        walletTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
            }
        });

        // 找到布局中的视图组件(副本设置)
        TextView settingsTextView = rootView.findViewById(R.id.settingsTextView);
        // 设置钱包点击事件监听器
        settingsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetDungeonActivity.class);
                startActivity(intent);
            }
        });

        // 找到布局中的视图组件(帮助)
        TextView helpTextView = rootView.findViewById(R.id.helpTextView);
        // 设置钱包点击事件监听器
        helpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        // 找到布局中的视图组件(给啊涛喝奶茶)
        TextView photoTextView = rootView.findViewById(R.id.buyTeaTextView);
        // 设置钱包点击事件监听器
        photoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}