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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.student.Fragment.ButtonTasksFragment;
import com.jnu.student.Fragment.DailyTasksFragment;
import com.jnu.student.R;
import com.jnu.student.data.DataDailyTasks;
import com.jnu.student.data.DataGeneralTasks;
import com.jnu.student.data.DataWeeklyTasks;
import com.jnu.student.data.Tasks;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Fragment tasksFragment = new ButtonTasksFragment();//任务
    private Fragment statisticsFragment = new DailyTasksFragment();//统计
    private BottomNavigationView btmNavView;
    // 声明一个用于启动带有返回结果的活动的管理器(添加任务)
    private ActivityResultLauncher<Intent> addTasksLauncher;
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
        // 为启动带有返回结果的活动(Activity)注册一个处理程序(添加副本)
        addDungeonLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){

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
                loadTasksFragment(tasksFragment);
                return true;
            }
            if (item.getItemId() == R.id.navigation_home) {
                // 用户点击了“首页”
                return true;
            }
            if (item.getItemId() == R.id.navigation_statistics) {
                // 用户点击了“统计”
                loadTasksFragment(statisticsFragment);
                return true;
            }
            if (item.getItemId() == R.id.navigation_reward) {
                // 用户点击了“奖励”，加载DailyTasksFragment
                return true;
            }
            return false;
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

    @Override
    //点击添加按钮
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.btn_msg)
        {
            // 创建一个 PopupMenu
            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.btn_msg));
            // 在菜单中添加选项
            popupMenu.getMenu().add("新建任务");
            popupMenu.getMenu().add("加入副本");
            popupMenu.getMenu().add("排序");
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
                        addTasksLauncher.launch(intent2);
                        return true;
                    case "排序":
                        // 处理选项三点击事件
                        Toast.makeText(this, "排序成功", Toast.LENGTH_SHORT).show();
                        return true;
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
