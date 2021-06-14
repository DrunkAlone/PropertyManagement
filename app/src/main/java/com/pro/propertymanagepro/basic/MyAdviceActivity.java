package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AdviceService;
import com.pro.propertymanagepro.entity.Advice;

import java.util.List;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class MyAdviceActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_type;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_content;
    private Button bt_back;

    private int id;
    private String type;
    private String username;
    private String name;
    private String phone;
    private String content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advice);
        addActivity(this);
        Intent intent = this.getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        initView();
    }

    public void initView(){
        tv_type = findViewById(R.id.myadvice_tv_type);
        tv_name = findViewById(R.id.myadvice_tv_name);
        tv_phone = findViewById(R.id.myadvice_tv_phone);
        tv_content = findViewById(R.id.myadvice_tv_description);
        bt_back = findViewById(R.id.myadvice_back);

        bt_back.setOnClickListener(this);

        //为表单赋值
        AdviceService adviceService = new AdviceService(this);
        Advice advice = adviceService.getAdviceByID(id);
        type = advice.getType() == 0 ? "建议" : "投诉";
        name = advice.getName();
        phone = advice.getPhone();
        content = advice.getContent();

        tv_type.setText(type);
        tv_name.setText(name);
        tv_phone.setText(phone);
        tv_content.setText(content);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.myadvice_back){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
