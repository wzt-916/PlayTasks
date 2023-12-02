package com.jnu.student.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.student.Activity.addDungeonActivity;
import com.jnu.student.Activity.advancedActivity;
import com.jnu.student.Activity.primaryMainActivity;
import com.jnu.student.R;
import com.jnu.student.data.DataDailyTasks;
import com.jnu.student.data.DataDungeon;
import com.jnu.student.data.DataGeneralTasks;
import com.jnu.student.data.Dungeon;
import com.jnu.student.data.Tasks;

import java.util.ArrayList;

public class DungeonTasksFragment extends Fragment {

    private RecyclerView tasksRecyclerView;
    private DungeonTasksFragment.TasksAdapter tasksAdapter;

    private ArrayList<Dungeon> dungeon_tasks;
    public DungeonTasksFragment() {
        // Required empty public constructor
    }

    public static DungeonTasksFragment newInstance(String param1, String param2) {
        DungeonTasksFragment fragment = new DungeonTasksFragment();
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
        dungeon_tasks = new DataDungeon().LoadTasks(this.getContext());
        if(dungeon_tasks.size() == 0)
        {
            View root= inflater.inflate(R.layout.empty_tasks, container, false);
            TextView textView1 = root.findViewById(R.id.empty_textView1);
            TextView textView2 = root.findViewById(R.id.empty_textView2);
            textView1.setText("无副本任务");
            textView2.setText("    五星上将麦克阿涛曾经说过,开始就是成功了一半");
            return root;
        }
        tasksAdapter = new DungeonTasksFragment.TasksAdapter(dungeon_tasks);
        tasksRecyclerView.setAdapter(tasksAdapter);
        registerForContextMenu(tasksRecyclerView);
        return rootView;
    }
    public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
        private ArrayList<Dungeon> bookArrayList;

        //定义删除的变量：
        int position;

        public TasksAdapter(ArrayList<Dungeon> books) {
            bookArrayList = books;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.dungeon_detail, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.getTextViewBookTitle().setText(bookArrayList.get(position).getTitle());
            viewHolder.getIamgeViewItem().setImageResource(bookArrayList.get(position).getImageResourceId());
        }

        @Override
        public int getItemCount() {
            return bookArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewBookTitle;
            private final ImageView imageViewItem;

            //显示菜单
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");
                position = this.getAdapterPosition();
                menu.add(0, 0, this.getAdapterPosition(), "添加");
                menu.add(0, 1, this.getAdapterPosition(), "删除");
                menu.add(0, 2, this.getAdapterPosition(), "修改");
            }

            public ViewHolder(View shopItemView) {
                super(shopItemView);

                textViewBookTitle = shopItemView.findViewById(R.id.text_view_book_title);
                imageViewItem = shopItemView.findViewById(R.id.image_view_book_cover);
                shopItemView.setOnCreateContextMenuListener(this);
                // 为 ImageView 设置点击事件监听器
                imageViewItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Dungeon dungeon = bookArrayList.get(position);
                            if(dungeon.getTitle().equals("初级"))
                            {
                                // 在这里处理图片点击事件，例如显示一个 Toast
                                Toast.makeText(v.getContext(), "点击了图片：" + dungeon.getTitle(), Toast.LENGTH_SHORT).show();
                                // 创建一个新的 Intent 对象
                                Intent intent = new Intent(v.getContext(), primaryMainActivity.class);
                                // 启动新的 Activity
                                v.getContext().startActivity(intent);
                            }
                            else {
                                Toast.makeText(v.getContext(), "点击了图片：" + dungeon.getTitle(), Toast.LENGTH_SHORT).show();
                                // 创建一个新的 Intent 对象
                                Intent intent = new Intent(v.getContext(), advancedActivity.class);
                                // 启动新的 Activity
                                v.getContext().startActivity(intent);
                            }
                        }
                    }
                });
            }

            public ImageView getIamgeViewItem() {
                return imageViewItem;
            }

            public TextView getTextViewBookTitle() {
                return textViewBookTitle;
            }
        }
    }
}