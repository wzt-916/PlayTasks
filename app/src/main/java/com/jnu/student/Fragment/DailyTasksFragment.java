package com.jnu.student.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.student.Activity.AddTasksActivity;
import com.jnu.student.R;
import com.jnu.student.data.DataDailyTasks;
import com.jnu.student.data.DataFinishTasks;
import com.jnu.student.data.Tasks;

import java.util.ArrayList;

public class DailyTasksFragment extends Fragment {
    public static int daily_score = 0;
    private RecyclerView tasksRecyclerView;
    private DailyTasksFragment.TasksAdapter tasksAdapter;

    private ArrayList<Tasks> daily_tasks;
    public DailyTasksFragment() {
        // Required empty public constructor
    }
    public static DailyTasksFragment newInstance(String param1, String param2) {
        DailyTasksFragment fragment = new DailyTasksFragment();
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
        // 通过提供的 inflater 将 fragment_book_list 布局实例化为视图
        // rootView 将包含 fragment_book_list.xml 中定义的视图
        View rootView = inflater.inflate(R.layout.fragment_tasks_list, container, false);
        // 从实例化的布局中查找具有特定 ID（R.id.recyclerview_main）的 RecyclerView
        tasksRecyclerView = rootView.findViewById(R.id.recyclerview_main);
        // 创建一个 LinearLayoutManager 来管理 RecyclerView 中的项目
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        // 将 LinearLayoutManager 的方向设置为垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tasksRecyclerView.setLayoutManager(linearLayoutManager);
        daily_tasks = new DataDailyTasks().LoadTasks(this.getContext());
        if(daily_tasks.size() == 0)
        {
            View root= inflater.inflate(R.layout.empty_tasks, container, false);
            TextView textView1 = root.findViewById(R.id.empty_textView1);
            TextView textView2 = root.findViewById(R.id.empty_textView2);
            textView1.setText("无每日任务");
            textView2.setText("    五星上将麦克阿涛曾经说过,开始就是成功了一半");
            return root;
        }
        tasksAdapter = new TasksAdapter(daily_tasks);
        tasksRecyclerView.setAdapter(tasksAdapter);
        registerForContextMenu(tasksRecyclerView);
        return rootView;
    }
   public boolean onContextItemSelected(MenuItem item) {
        if(item.getGroupId() != 0)
        {
            return false;
        }
        switch (item.getItemId()) {
            case 0:
                // Do something for item 1
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getContext());
                builder1.setTitle("添加提醒");
                builder1.setMessage("记得添加呀");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 处理确定按钮点击事件的逻辑
                    }
                });
                builder1.create().show();
                break;
            case 1:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this.getContext());
                builder2.setTitle("删除");
                builder2.setMessage("你要删除吗?");
                builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "确定按钮被点击", Toast.LENGTH_SHORT).show();
                        daily_tasks.remove(item.getOrder());
                        tasksAdapter.notifyItemRemoved(item.getOrder());
                        new DataDailyTasks().SaveTasks(DailyTasksFragment.this.getContext(),daily_tasks);
                    }
                });
                builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder2.create().show();
                // Do something for item 2
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    public class TasksAdapter extends RecyclerView.Adapter<DailyTasksFragment.TasksAdapter.ViewHolder> {

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
                            TextView scoreTextView = getTextViewScore();
                            daily_score = Integer.parseInt(scoreTextView.getText().toString());
                            //添加到已完成任务
                            ArrayList<Tasks> finish_task = new DataFinishTasks().LoadTasks(getContext());
                            finish_task.add(new Tasks(textViewTitle.getText().toString(),daily_score));
                            new DataFinishTasks().SaveTasks(getContext(),finish_task);
                            // CheckBox 被选中时的逻辑
                            Toast.makeText(getContext(), daily_score+"", Toast.LENGTH_SHORT).show();
                            buttonView.setChecked(false);
                            if (getActivity() != null) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("dailyScore", daily_score);
                                getParentFragmentManager().setFragmentResult("updateScore", bundle);
                            }
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
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.tasks_row, viewGroup, false);

            return new DailyTasksFragment.TasksAdapter.ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
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