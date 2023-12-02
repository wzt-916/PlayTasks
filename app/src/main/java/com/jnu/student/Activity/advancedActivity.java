package com.jnu.student.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jnu.student.R;
import com.jnu.student.data.Tasks;

import java.util.ArrayList;

public class advancedActivity extends AppCompatActivity {
    private RecyclerView tasksRecyclerView;
    private TasksAdapter tasksAdapter;
    private ArrayList<Tasks> daily_tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tasks_list);
        // 从实例化的布局中查找具有特定 ID（R.id.recyclerview_main）的 RecyclerView
        tasksRecyclerView =findViewById(R.id.recyclerview_main);
        // 创建一个 LinearLayoutManager 来管理 RecyclerView 中的项目

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        // 将 LinearLayoutManager 的方向设置为垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tasksRecyclerView.setLayoutManager(linearLayoutManager);
        daily_tasks = new ArrayList<>();

        daily_tasks.add(new Tasks("早睡",10));
        daily_tasks.add(new Tasks("早起",10));
        daily_tasks.add(new Tasks("吃早餐",10));
        daily_tasks.add(new Tasks("不逃课",10));
        daily_tasks.add(new Tasks("按时完成作业",10));
        daily_tasks.add(new Tasks("修课程",10));
        daily_tasks.add(new Tasks("保持锻炼",10));
        daily_tasks.add(new Tasks("每天读书",10));
        daily_tasks.add(new Tasks("学技能",10));

        tasksAdapter = new TasksAdapter(daily_tasks);
        tasksRecyclerView.setAdapter(tasksAdapter);
        registerForContextMenu(tasksRecyclerView);
    }
    public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

        private ArrayList<Tasks> tasksArrayList;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final TextView textViewScore;
            private final CheckBox checkBox;

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");

                menu.add(0, 0, this.getAdapterPosition(), "添加提醒");
                menu.add(0, 1, this.getAdapterPosition(), "删除");
            }

            public ViewHolder(View tasksView) {
                super(tasksView);
                // Define click listener for the ViewHolder's View

                textViewTitle = tasksView.findViewById(R.id.text_view_tasks_title);
                textViewScore = tasksView.findViewById(R.id.text_view_score);
                checkBox = tasksView.findViewById(R.id.checkBox); // 初始化 CheckBox
                tasksView.setOnCreateContextMenuListener(this);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // 在这里处理 CheckBox 被点击时的逻辑
                        if (isChecked) {
                            // 可以执行其他操作，例如修改数据等
                        } else {
                            // CheckBox 被取消选中时的逻辑
                        }
                    }
                });
            }

            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public TextView getTextViewScore() {
                return textViewScore;
            }

        }
        public TasksAdapter(ArrayList<Tasks> tasks) {
            tasksArrayList = tasks;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.tasks_row, viewGroup, false);

            return new TasksAdapter.ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(TasksAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewTitle().setText(tasksArrayList.get(position).getTitle());
            viewHolder.getTextViewScore().setText(tasksArrayList.get(position).getScore()+ "");
        }

        // Return the size of your dataset (invoked by the layout manager)
        public int getItemCount() {
            return tasksArrayList.size();
        }
        // 添加 CheckBox 的点击事件监听器
    }
}