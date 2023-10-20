package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.jnu.student.data.ShopItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这是用布局实现
        setContentView(R.layout.activity_main);

        RecyclerView mainRecyclerView = findViewById(R.id.recyclerview_main);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器

        ArrayList<ShopItem> shopItems = new ArrayList<ShopItem>();
        for(int i = 0; i < 20; i++)
        {
            shopItems.add(new ShopItem("信息数学安全基础"+i,1.5,R.drawable.book_1));
            shopItems.add(new ShopItem("软件项目管理案例开发"+i,2.5,R.drawable.book_2));
            shopItems.add(new ShopItem("没有名字"+i,3.5,R.drawable.book_no_name));
        }
        ShopItemAdapter shopItemAdapter = new ShopItemAdapter(shopItems);
        mainRecyclerView.setAdapter(shopItemAdapter);

        registerForContextMenu(mainRecyclerView);//注册菜单

        //通过TextView的id获取TextView对象：
        /*TextView textView = findViewById(R.id.text_vciew_hello);
        int stringId = getResources().getIdentifier("hello_android", "string", getPackageName());
        if (stringId != 0) {
            String string = getString(stringId);
            textView.setText(string);
        }*/
        /*TextView textView_hello = findViewById(R.id.text_vciew_hello);
        int stringId_hello = getResources().getIdentifier("hello","string",getPackageName());
        String hello = getString(stringId_hello);
        textView_hello.setText(hello);

        TextView textView_JNU = findViewById(R.id.text_vciew_JNU);
        int stringId_JNU = getResources().getIdentifier("JNU","string",getPackageName());
        String string_JNU = getString(stringId_JNU);
        textView_JNU.setText(string_JNU);

        Button button_change_text = (Button) findViewById(R.id.button_change_text);
        int stringId_change_text = getResources().getIdentifier("change_text","string",getPackageName());
        String string_change_text = getString(stringId_change_text);
        button_change_text.setText(string_change_text);

        Button button = findViewById(R.id.button_change_text);
        button.setOnClickListener(v -> {
            // do something...
            String temp = textView_JNU.getText().toString();
            textView_JNU.setText(textView_hello.getText().toString());
            textView_hello.setText(temp);

            Toast.makeText(MainActivity.this,"交换成功1",Toast.LENGTH_SHORT).show();
        });

        //交换文本
        button_change_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = textView_JNU.getText().toString();
                textView_JNU.setText(textView_hello.getText().toString());
                textView_hello.setText(temp);

                //展示Toast
                Toast.makeText(MainActivity.this,"交换成功",Toast.LENGTH_SHORT).show();

                //展示AlertDialog
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("信息")
                        .setMessage("交换成功")
                        .setPositiveButton("确定",null)
                        .show();
            }
        });
        //String text = textView.getText().toString();
        //获取字符串资源
        /*String stringName = "hello_world";
        int stringId = getResources().getIdentifier(stringName, "string", getPackageName());
        if (stringId != 0) {
            String string = getString(stringId);
        }
        //获取图片资源
        String drawableName = "my_image";
        int drawableId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
        if (drawableId != 0) {
            Drawable drawable = getResources().getDrawable(drawableId, null);
        }

        //这是用Java代码实现在屏幕输出文字
        // 创建一个RelativeLayout对象
         RelativeLayout myLayout = new RelativeLayout(this);
        //myLayout.setBackgroundColor(Color.YELLOW);        //背景颜色
        // 创建一个TextView对象
        TextView myTextView = new TextView(this);
        //通过ID获取字符串资源
        myTextView.setText("text");
        myTextView.setTextSize(24);
        myTextView.setGravity(Gravity.CENTER);       //居中对其

        // 创建LayoutParams对象并设置宽度和高度
        RelativeLayout.LayoutParams textViewParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);

        // 将TextView对象添加到RelativeLayout对象中
        myLayout.addView(myTextView, textViewParams);
        // 设置布局
        setContentView(myLayout);*/
    }
    //点击响应
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case 0:
                // Do something for item 1
                break;
            case 1:
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
        private ArrayList<ShopItem> shopItemArrayList;

        public ShopItemAdapter(ArrayList<ShopItem> shopItems) {
            shopItemArrayList = shopItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.shop_item_row, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.getTextViewName().setText(shopItemArrayList.get(position).getName());
            viewHolder.getTextViewPrice().setText(shopItemArrayList.get(position).getPrice()+"");
            viewHolder.getIamgeViewItem().setImageResource(shopItemArrayList.get(position).getImageResourceId());
        }

        @Override
        public int getItemCount() {
            return shopItemArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            private final TextView textViewName;
            private final TextView textViewPrice;
            private final ImageView iamgeViewItem;

            //显示菜单
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo){
                menu.setHeaderTitle("具体操作");
                menu.add(0,0, Menu.NONE,"添加");
                menu.add(0,0, Menu.NONE,"删除");
                menu.add(0,0, Menu.NONE,"修改");
            }

            public ViewHolder(View shopItemView) {
                super(shopItemView);

                textViewName = shopItemView.findViewById(R.id.textview_item_name);
                textViewPrice = shopItemView.findViewById(R.id.textview_item_price);
                iamgeViewItem = shopItemView.findViewById(R.id.imageview_item);
                shopItemView.setOnCreateContextMenuListener(this);
            }
            public ImageView getIamgeViewItem() {
                return iamgeViewItem;
            }
            public TextView getTextViewName() {
                return textViewName;
            }
            public TextView getTextViewPrice() {
                return textViewPrice;
            }
        }
    }
}