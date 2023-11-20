package com.jnu.student.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jnu.student.Fragment.DailyTasksFragment;
import com.jnu.student.Fragment.DungeonTasksFragment;
import com.jnu.student.Fragment.GeneralTasksFragment;
import com.jnu.student.Fragment.WeeklyTasksFragment;
import com.jnu.student.R;

public class MainActivity extends AppCompatActivity {
    private String []tabHeaderStrings = {"每日任务","每周任务","普通任务","副本任务"};
    private BottomNavigationView btmNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_bottom_navegation);
        // 获取ViewPager2和TabLayout的实例
        /*
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // 创建适配器
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);

        // 将TabLayout和ViewPager2进行关联
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabHeaderStrings[position])
        ).attach();
        */
        btmNavView = findViewById(R.id.bottom_navigation_menu);
        btmNavView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_tasks) {
                return true;
            }
            if (item.getItemId() == R.id.navigation_home) {
                return true;
            }
            if (item.getItemId() == R.id.navigation_reward) {
                return true;
            }
            return false;
        });

    }

    public class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 4;
        public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // 根据位置返回对应的Fragment实例
            switch (position) {
                case 0:
                    return new DailyTasksFragment();
                case 1:
                    return new WeeklyTasksFragment();
                case 2:
                    return new GeneralTasksFragment();
                case 3:
                    return new DungeonTasksFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_TABS;
        }
    }
}