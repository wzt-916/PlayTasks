package com.jnu.student.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.student.R;
import com.jnu.student.data.DataScore;

public class ScoreFragment extends Fragment {


    public ScoreFragment() {
        // Required empty public constructor
    }

    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        TextView textView = view.findViewById(R.id.score_text);
        textView.setText(String.valueOf(new DataScore().loadScore(this.getContext())));
        getParentFragmentManager().setFragmentResultListener("AllScore", this, (requestKey, result) -> {
            int score = 0;
            if (result.containsKey("allScore")) {
                score = result.getInt("allScore");
            }
            // 更新 TextView 的显示
            textView.setText(String.valueOf(score));
        });
        Button scoreToZeroButton =view.findViewById(R.id.score_to_zero);

        // 设置按钮点击事件监听器
        scoreToZeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在按钮点击时执行的操作
                textView.setText(String.valueOf(0));
                new DataScore().saveScore(getContext(),0);
            }
        });
        return view;
    }
}