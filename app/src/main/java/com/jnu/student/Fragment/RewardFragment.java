package com.jnu.student.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.student.R;
import com.jnu.student.data.DataDailyTasks;
import com.jnu.student.data.DataRewardTasks;
import com.jnu.student.data.DataScore;
import com.jnu.student.data.Tasks;

import java.util.ArrayList;

public class RewardFragment extends Fragment {

    private static int score;
    public static int tasks_score = 0;
    private RecyclerView tasksRecyclerView;
    private RewardFragment.TasksAdapter tasksAdapter;

    private ArrayList<Tasks> reward_tasks;
    public RewardFragment() {
        // Required empty public constructor
    }

    public static RewardFragment newInstance(String param1, String param2) {
        RewardFragment fragment = new RewardFragment();
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
        // rootView 将包含 fragment_book_list.xml 中定义的视图
        View rootView = inflater.inflate(R.layout.fragment_reward_tasks, container, false);
        // 从实例化的布局中查找具有特定 ID（R.id.recyclerview_main）的 RecyclerView
        tasksRecyclerView = rootView.findViewById(R.id.recycle_reward);
        // 创建一个 LinearLayoutManager 来管理 RecyclerView 中的项目
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        // 将 LinearLayoutManager 的方向设置为垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tasksRecyclerView.setLayoutManager(linearLayoutManager);
        reward_tasks = new DataRewardTasks().LoadTasks(this.getContext());
        if(reward_tasks.size() == 0)
        {
            View root= inflater.inflate(R.layout.empty_tasks, container, false);
            TextView textView1 = root.findViewById(R.id.empty_textView1);
            TextView textView2 = root.findViewById(R.id.empty_textView2);
            textView1.setText("无奖励");
            textView2.setText("    五星上将麦克阿涛曾经说过,开始就是成功了一半");
            return root;
        }

        //显示分数
        TextView textView = rootView.findViewById(R.id.rewardtextView);
        score = new DataScore().loadScore(this.getContext());
        textView.setText(String.valueOf(score));

        getParentFragmentManager().setFragmentResultListener("updateScore", this, (requestKey, result) -> {
            score = new DataScore().loadScore(this.getContext());
            int updatedScore = score;
            if (result.containsKey("rewardScore")) {
                updatedScore += result.getInt("rewardScore");
            }
            // 更新 TextView 的显示
            textView.setText(String.valueOf(updatedScore));
            new DataScore().saveScore(this.getContext(),updatedScore);
            if (getActivity() != null) {
                int all_score = new DataScore().loadScore(this.getContext());
                Bundle bundle = new Bundle();
                bundle.putInt("allScore", all_score);
                getParentFragmentManager().setFragmentResult("AllScore", bundle);
            }
        });

        tasksAdapter = new RewardFragment.TasksAdapter(reward_tasks);
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
                        reward_tasks.remove(item.getOrder());
                        tasksAdapter.notifyItemRemoved(item.getOrder());
                        new DataDailyTasks().SaveTasks(RewardFragment.this.getContext(), reward_tasks);
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
    public class TasksAdapter extends RecyclerView.Adapter<RewardFragment.TasksAdapter.ViewHolder> {

        private ArrayList<Tasks> tasksArrayList;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final TextView textViewScore;
            private final CheckBox checkBox;

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");

                menu.add(5, 0, this.getAdapterPosition(), "添加提醒");
                menu.add(5, 1, this.getAdapterPosition(), "删除");
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
                            tasks_score = Integer.parseInt(scoreTextView.getText().toString());
                            // CheckBox 被选中时的逻辑
                            Toast.makeText(getContext(), tasks_score +"", Toast.LENGTH_SHORT).show();
                            buttonView.setChecked(false);
                            if (getActivity() != null) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("rewardScore", tasks_score);
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
        public RewardFragment.TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.tasks_row, viewGroup, false);

            return new RewardFragment.TasksAdapter.ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(RewardFragment.TasksAdapter.ViewHolder viewHolder, final int position) {
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