package com.jnu.student.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.jnu.student.Fragment.ButtonTasksFragment;
import com.jnu.student.Fragment.DailyTasksFragment;
import com.jnu.student.Fragment.DungeonTasksFragment;
import com.jnu.student.Fragment.HomeFragment;
import com.jnu.student.Fragment.RewardFragment;
import com.jnu.student.R;
import com.jnu.student.data.DataDailyTasks;
import com.jnu.student.data.DataDungeon;
import com.jnu.student.data.DataFinishTasks;
import com.jnu.student.data.DataGeneralTasks;
import com.jnu.student.data.DataRewardTasks;
import com.jnu.student.data.DataScore;
import com.jnu.student.data.DataWeeklyTasks;
import com.jnu.student.data.Dungeon;
import com.jnu.student.data.Tasks;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private int tabposition;
    private int mymenu;
    private Fragment statisticsFragment = new DailyTasksFragment();//统计
    private BottomNavigationView btmNavView;
    // 声明一个用于启动带有返回结果的活动的管理器(添加任务)
    private ActivityResultLauncher<Intent> addTasksLauncher;
    private ActivityResultLauncher<Intent> addRewardLauncher;
    // 声明一个用于启动带有返回结果的活动的管理器(加入副本)
    private ActivityResultLauncher<Intent> addDungeonLauncher;
    // 声明一个用于启动带有返回结果的活动的管理器(排序)
    private ActivityResultLauncher<Intent> SortLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navegation);
        // 第一次加载，显示任务页面
        if (savedInstanceState == null) {
            Fragment tasksFragment = new ButtonTasksFragment();
            loadTasksFragment(tasksFragment);
        }
        // 为启动带有返回结果的活动(Activity)注册一个处理程序(添加任务)
        addTasksLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        String tasks_type = data.getStringExtra("task_type");
                        if("daily".equals(tasks_type)) //每日任务
                        {
                            ArrayList<Tasks> daily_tasks = new DataDailyTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            String tags = data.getStringExtra("tags");
                            daily_tasks.add(new Tasks(title,score));
                            new DataDailyTasks().SaveTasks(this,daily_tasks);
                            loadTasksFragment(new ButtonTasksFragment());
                        } else if ("weekly".equals(tasks_type)) { //每周任务
                            ArrayList<Tasks> weekly_tasks = new DataWeeklyTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            String tags = data.getStringExtra("tags");
                            weekly_tasks.add(new Tasks(title,score));
                            new DataWeeklyTasks().SaveTasks(this,weekly_tasks);
                            loadTasksFragment(new ButtonTasksFragment(1));
                        } else if ("regular".equals(tasks_type)) { //普通任务
                            ArrayList<Tasks> general_tasks = new DataGeneralTasks().LoadTasks(this);
                            int score = Integer.parseInt(data.getStringExtra("score"));
                            String title = data.getStringExtra("title");
                            String tags = data.getStringExtra("tags");
                            general_tasks.add(new Tasks(title,score));
                            new DataGeneralTasks().SaveTasks(this,general_tasks);
                            loadTasksFragment(new ButtonTasksFragment(2));
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        //处理新建奖励
        addRewardLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        ArrayList<Tasks> reward_tasks = new DataRewardTasks().LoadTasks(this);
                        int score = Integer.parseInt(data.getStringExtra("reward_score"));
                        String title = data.getStringExtra("reward_title");
                        String tags = data.getStringExtra("reward_tags");
                        reward_tasks.add(new Tasks(title,-score));
                        new DataRewardTasks().SaveTasks(this,reward_tasks);
                        loadTasksFragment(new RewardFragment());
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );

        // 为启动带有返回结果的活动(Activity)注册一个处理程序(添加副本)
        addDungeonLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        String selected = data.getStringExtra("selectedText");
                        if("初级".equals(selected)) //初级
                        {
                            ArrayList<Dungeon> dungeon_tasks = new DataDungeon().LoadTasks(this);
                            dungeon_tasks.add(new Dungeon("初级",R.drawable.run));
                            new DataDungeon().SaveTasks(this,dungeon_tasks);
                            loadTasksFragment(new DungeonTasksFragment());
                        }
                        else if("高级".equals(selected))
                        {
                            ArrayList<Dungeon> dungeon_tasks = new DataDungeon().LoadTasks(this);
                            dungeon_tasks.add(new Dungeon("高级",R.drawable.jida));
                            new DataDungeon().SaveTasks(this,dungeon_tasks);
                            loadTasksFragment(new DungeonTasksFragment());
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        // 为启动带有返回结果的活动(Activity)注册一个处理程序(排序)
        SortLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        // 获取底部导航栏
        btmNavView = findViewById(R.id.bottom_navigation_menu);
        btmNavView.setOnItemSelectedListener(item -> {
            // 处理底部导航栏项的选择
            if (item.getItemId() == R.id.navigation_tasks) {
                // 用户点击了“任务”，加载ButtonTasksFragment
                Fragment tasksFragment = new ButtonTasksFragment();//任务
                loadTasksFragment(tasksFragment);
                mymenu = 0;
                return true;
            }
            if (item.getItemId() == R.id.navigation_home) {
                // 用户点击了“首页”
                Fragment homeFragment = new HomeFragment();
                loadTasksFragment(homeFragment);
                return true;
            }
            if (item.getItemId() == R.id.navigation_statistics) {
                // 用户点击了“统计”
                loadTasksFragment(statisticsFragment);
                return true;
            }
            if (item.getItemId() == R.id.navigation_reward) {
                Fragment rewardFragment = new RewardFragment();
                loadTasksFragment(rewardFragment);
                mymenu = 1;
                return true;
            }
            return false;
        });
        // 获取 FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 注册 FragmentResultListener
        fragmentManager.setFragmentResultListener("tabposition", this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        // 获取从 Fragment 传来的数据
                        int position = result.getInt("position", tabposition);
                        tabposition = position;
                        // 在这里处理获取到的标签页位置信息
                        // 可以根据需要做一些操作
                        //Toast.makeText(MainActivity.this, "获取到的标签页位置：" + position, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // 加载Fragment的方法
    private void loadTasksFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
    @Override
    // 创建选项菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        // 通过 getMenuInflater() 方法获取一个 MenuInflater 对象，
        // 用于将 XML 文件解析并填充到 Menu 对象中
        getMenuInflater().inflate(R.menu.add_button, menu);
        // 返回父类的 onCreateOptionsMenu 方法的结果，
        return super.onCreateOptionsMenu(menu);
    }

    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        // 这里可以接收来自 Fragment 的结果，但你已经在 onCreate 中注册了 FragmentResultListener，所以这个方法不需要重写
    }
    @Override
    //点击添加按钮
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.tasks_msg && mymenu == 0)
        {
            // 创建一个 PopupMenu
            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.tasks_msg));
            // 在菜单中添加选项
            popupMenu.getMenu().add("新建任务");
            popupMenu.getMenu().add("加入副本");
            popupMenu.getMenu().add("排序");
            popupMenu.getMenu().add("全部删除");
            // 设置菜单项点击事件
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                // 处理菜单项点击事件
                switch (menuItem.getTitle().toString()) {
                    case "新建任务":
                        // 处理选项一点击事件
                        // 创建一个新的意图（Intent）以启动 AddTasksActivity
                        Intent intent1 = new Intent(this, AddTasksActivity.class);
                        // 使用 addTasksLauncher 启动指定的 Intent
                        addTasksLauncher.launch(intent1);
                        return true;
                    case "加入副本":
                        // 处理选项二点击事件
                        // 创建一个新的意图（Intent）以启动 AddTasksActivity
                        Intent intent2 = new Intent(this, addDungeonActivity.class);
                        // 使用 addTasksLauncher 启动指定的 Intent
                        addDungeonLauncher.launch(intent2);
                        return true;
                    case "排序":
                        // 处理选项三点击事件
                        //排序每周任务
                        if(tabposition == 1)
                        {
                            //排序每周任务
                            ArrayList<Tasks> weeklytask = new DataWeeklyTasks().LoadTasks(this);
                            Collections.sort(weeklytask);
                            new DataWeeklyTasks().SaveTasks(this,weeklytask);
                            loadTasksFragment(new ButtonTasksFragment(1));
                        }
                        //排序每日任务
                        if(tabposition == 0)
                        {
                            ArrayList<Tasks> dailytask = new DataDailyTasks().LoadTasks(this);
                            Collections.sort(dailytask);
                            new DataDailyTasks().SaveTasks(this,dailytask);
                            loadTasksFragment(new ButtonTasksFragment(0));
                        }
                        //排序普通任务
                        if(tabposition == 2)
                        {
                            ArrayList<Tasks> generaltask = new DataGeneralTasks().LoadTasks(this);
                            Collections.sort(generaltask);
                            new DataGeneralTasks().SaveTasks(this,generaltask);
                            loadTasksFragment(new ButtonTasksFragment(2));
                        }
                        break;
                    case "全部删除":
                        // 处理选项三点击事件
                        //排序每周任务
                        if(tabposition == 1)
                        {
                            //排序每周任务
                            ArrayList<Tasks> weekly_tasks = new ArrayList<>();
                            new DataWeeklyTasks().SaveTasks(this, weekly_tasks);
                            loadTasksFragment(new ButtonTasksFragment(1));
                        }
                        //排序每日任务
                        if(tabposition == 0)
                        {
                            ArrayList<Tasks> daily_tasks = new ArrayList<>();
                            new DataDailyTasks().SaveTasks(this, daily_tasks);
                            loadTasksFragment(new ButtonTasksFragment(0));
                        }
                        //排序普通任务
                        if(tabposition == 2)
                        {
                            ArrayList<Tasks> general_tasks = new ArrayList<>();
                            new DataGeneralTasks().SaveTasks(this, general_tasks);
                            loadTasksFragment(new ButtonTasksFragment(2));
                        }
                        if(tabposition == 3)
                        {
                            ArrayList<Dungeon> dungeon_tasks = new ArrayList<>();
                            new DataDungeon().SaveTasks(this, dungeon_tasks);
                            loadTasksFragment(new ButtonTasksFragment(3));
                        }
                        if(tabposition == 4)
                        {
                            ArrayList<Tasks> finish_tasks = new ArrayList<>();
                            new DataFinishTasks().SaveTasks(this, finish_tasks);
                            loadTasksFragment(new ButtonTasksFragment(4));
                        }

                        //Toast.makeText(this, "排序成功"+new ButtonTasksFragment().defaultTab, Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            });
            // 显示 PopupMenu
            popupMenu.show();
            return true;
        }
        if(item.getItemId() == R.id.tasks_msg && mymenu == 1)
        {
            // 创建一个 PopupMenu
            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.tasks_msg));
            // 在菜单中添加选项
            popupMenu.getMenu().add("新建奖励");
            popupMenu.getMenu().add("排序");
            popupMenu.getMenu().add("全部删除");
            // 设置菜单项点击事件
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                // 处理菜单项点击事件
                switch (menuItem.getTitle().toString()) {
                    case "新建奖励":
                        // 处理选项一点击事件
                        Intent reward_intent = new Intent(this, addRewardActivity.class);
                        // 使用 addTasksLauncher 启动指定的 Intent
                        addRewardLauncher.launch(reward_intent);
                        return true;
                    case "排序":
                        ArrayList<Tasks> reward_tasks_sort= new DataRewardTasks().LoadTasks(this);
                        Collections.sort(reward_tasks_sort);
                        new DataRewardTasks().SaveTasks(this,reward_tasks_sort);
                        break;
                    case "全部删除":
                        ArrayList<Tasks> reward_tasks = new ArrayList<>();
                        new DataRewardTasks().SaveTasks(this,reward_tasks);
                        loadTasksFragment(new RewardFragment());
                }
                return false;
            });
            // 显示 PopupMenu
            popupMenu.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
