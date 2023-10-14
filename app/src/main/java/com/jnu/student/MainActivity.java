package com.jnu.student;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.view.Gravity;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hellop0000
        super.onCreate(savedInstanceState);
        //这是用布局实现
        setContentView(R.layout.activity_main);
        //通过TextView的id获取TextView对象：
        /*TextView textView = findViewById(R.id.text_vciew_hello);
        int stringId = getResources().getIdentifier("hello_android", "string", getPackageName());
        if (stringId != 0) {
            String string = getString(stringId);
            textView.setText(string);
        }*/
        TextView textView_hello = findViewById(R.id.text_vciew_hello);
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
}