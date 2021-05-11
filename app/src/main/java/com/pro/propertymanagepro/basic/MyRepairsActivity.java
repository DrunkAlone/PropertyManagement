package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.RepairsService;
import com.pro.propertymanagepro.entity.Repairs;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class MyRepairsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_title;
    private TextView tv_id;
    private TextView et_project_name;
    private TextView et_date;
    private TextView et_room;
    private TextView et_name;
    private TextView et_phone;
    private Button bt_back;
    private TextView et_description;

    private int id;
    private String username;
    private String project_name;
    private String date;
    private String room;
    private String name;
    private String phone;
    private String description;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_repairs);
        addActivity(this);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        initView();
    }

    public void initView(){
//        tv_title = findViewById(R.id.myrepairs_title);
        tv_id = findViewById(R.id.myrepairs_tv_id);
        et_project_name = findViewById(R.id.myrepairs_et_project_name);
        et_date = findViewById(R.id.myrepairs_et_date);
        et_room = findViewById(R.id.myrepairs_et_room);
        et_name = findViewById(R.id.myrepairs_et_name);
        et_phone = findViewById(R.id.myrepairs_et_phone);
        et_description = findViewById(R.id.myrepairs_description);
        bt_back = findViewById(R.id.myrepairs_back);

        bt_back.setOnClickListener(this);

        //设置字体
//        Typeface typeface = ResourcesCompat.getFont(this, R.font.siyuansongti);
//        tv_title.setTypeface(typeface);

        //为表单赋值
        RepairsService repairsService = new RepairsService(MyRepairsActivity.this);
        Repairs repairs = repairsService.getRepairbyUsername(username);
        id = repairs.getId();
        project_name = repairs.getProject_name();
        date = repairs.getDate();
        room = repairs.getRoom();
        name = repairs.getName();
        phone = repairs.getPhone();
        description = repairs.getDescription();
        tv_id.setText(String.valueOf(id));
        et_project_name.setText(project_name);
        et_date.setText(date);
        et_room.setText(room);
        et_name.setText(name);
        et_phone.setText(phone);
        et_description.setText("报修描述： " + description);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.myrepairs_back){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
