package com.jnu.student.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jnu.student.R;

public class addDungeonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dungeon);
        Button buttonOk = findViewById(R.id.dungeon_button_OK);
        // 为Button添加点击事件监听器
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建一个新的Intent对象
                Intent intent = new Intent();

                // 获取选中的 RadioButton 的 ID
                RadioGroup radioGroup = findViewById(R.id.radio_Group);
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // 根据选中的 RadioButton 获取相应的数据
                String selectedText = "";
                if (selectedId != -1) {
                    RadioButton radioButton = findViewById(selectedId);
                    selectedText = radioButton.getText().toString();
                }
                intent.putExtra("selectedText", selectedText);
                // 设置返回结果为RESULT_OK，并将带有数据的Intent传递回前一个Activity
                setResult(Activity.RESULT_OK, intent);
                // 结束当前的BookDetailsActivity，并返回到前一个Activity
                addDungeonActivity.this.finish();
            }
        });
        // 取消按钮实现
        Button buttonCancel = findViewById(R.id.dungeon_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 结束当前的BookDetailsActivity，并返回到前一个Activity
                addDungeonActivity.this.finish();
            }
        });
    }
}