package com.jnu.student;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.student.data.Book;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ShopItemAdapter shopItemAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这是用布局实现
        setContentView(R.layout.activity_main);

        RecyclerView mainRecyclerView = findViewById(R.id.recyclerview_main);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器

        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book("信息安全数学基础（第2版）",R.drawable.book_1));
        books.add(new Book("软件项目管理案例教程（第4版）",R.drawable.book_2));
        books.add(new Book("创新工程实践",R.drawable.book_no_name));
        //ShopItemAdapter shopItemAdapter = new ShopItemAdapter(books);
        shopItemAdapter = new ShopItemAdapter(books);
        mainRecyclerView.setAdapter(shopItemAdapter);

        registerForContextMenu(mainRecyclerView);//注册菜单


        addItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        String title = data.getStringExtra("title");
                        int randomDrawable = getRandomDrawableResource();
                        books.add(new Book(title,randomDrawable));
                        shopItemAdapter.notifyItemInserted(books.size());
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
    }
    //随机选择三张图片的某一张
    private int getRandomDrawableResource() {
        // Define an array of drawable resources
        int[] drawableResources = {R.drawable.book_no_name, R.drawable.book_1, R.drawable.book_2};

        // Generate a random index
        int randomIndex = new Random().nextInt(drawableResources.length);

        // Return the selected drawable resource
        return drawableResources[randomIndex];
    }
    ActivityResultLauncher<Intent> addItemLauncher;
    //点击响应
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this,"添加"+item.getOrder(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
                addItemLauncher.launch(intent);
                // Do something for item 1
                break;
            case 1:
                shopItemAdapter.removeItem();
                // Do something for item 2
                break;
            case 2:
                // Do something for item 3
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ViewHolder> {
        private ArrayList<Book> bookArrayList;

        //定义删除的变量：
        int position;
        public ShopItemAdapter(ArrayList<Book> books) {
            bookArrayList = books;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_item_row, viewGroup, false);
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
        //实现删除功能
        public void removeItem() {
            bookArrayList.remove(position);
            notifyItemRemoved(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            private final TextView textViewBookTitle;
            private final ImageView iamgeViewItem;

            //显示菜单
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo){
                menu.setHeaderTitle("具体操作");
                position = this.getAdapterPosition();
                menu.add(0,0, Menu.NONE,"添加");
                menu.add(0,1, Menu.NONE,"删除");
                menu.add(0,2, Menu.NONE,"修改");
            }
            public ViewHolder(View shopItemView) {
                super(shopItemView);

                textViewBookTitle = shopItemView.findViewById(R.id.text_view_book_title);
                iamgeViewItem = shopItemView.findViewById(R.id.image_view_book_cover);
                shopItemView.setOnCreateContextMenuListener(this);
            }
            public ImageView getIamgeViewItem() {
                return iamgeViewItem;
            }
            public TextView getTextViewBookTitle() {
                return textViewBookTitle;
            }
        }
    }
}