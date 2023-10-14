package com.jnu.student;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.view.Gravity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hellop000
        super.onCreate(savedInstanceState);
        //这是用布局实现
        setContentView(R.layout.activity_main);
        //通过TextView的id获取TextView对象：
        TextView textView = findViewById(R.id.text_vciew_hellow_world);
        String stringName = "hello_android";
        int stringId = getResources().getIdentifier(stringName, "string", getPackageName());
        if (stringId != 0) {
            String string = getString(stringId);
            textView.setText(string);
        }
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