package com.pro.propertymanagepro.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AdviceService;
import com.pro.propertymanagepro.entity.Advice;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;

public class AdminAdviceDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_id;
    private TextView tv_type;
    private TextView tv_username;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_description;
    private Button bt_back;

    private int id;
    private int type;
    private String username;
    private String name;
    private String phone;
    private String description;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_admin_detail);
        addActivity(this);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        initView();
    }

    public void initView(){
        tv_id = findViewById(R.id.advice_admin_id);
        tv_type = findViewById(R.id.advice_admin_type);
        tv_username = findViewById(R.id.advice_admin_username);
        tv_name = findViewById(R.id.advice_admin_name);
        tv_phone = findViewById(R.id.advice_admin_phone);
        tv_description = findViewById(R.id.advice_admin_content);
        bt_back = findViewById(R.id.advice_admin_back);

        AdviceService adviceService = new AdviceService(this);
        Advice advice = adviceService.getAdviceByID(id);
        if(advice == null){
            Toast.makeText(this, "没有符合条件的结果，请重试！", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            type = advice.getType();
            username = advice.getUsername();
            name = advice.getName();
            phone = advice.getPhone();
            description = advice.getContent();

            tv_id.setText(String.valueOf(id));
            tv_type.setText(type == 0 ? "建议" : "投诉");
            tv_username.setText(username);
            tv_name.setText(name);
            tv_phone.setText(phone);
            tv_description.setText(description);
        }

        bt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.advice_admin_back){
            finish();
        }
    }
}
