package com.jnu.student.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jnu.student.R;

public class SetDungeonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_dungeon);
        // 在布局中找到ID为button_item_details_ok的Button控件
        Button buttonstart = findViewById(R.id.startDungeonButton);
        // 为Button添加点击事件监听器
        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建一个新的Intent对象
                Intent intent = new Intent();

                // 设置返回结果为RESULT_OK，并将带有数据的Intent传递回前一个Activity
                setResult(Activity.RESULT_OK, intent);
                // 结束当前的BookDetailsActivity，并返回到前一个Activity
                SetDungeonActivity.this.finish();
            }
        });
    }
}