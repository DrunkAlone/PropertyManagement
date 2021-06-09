package com.pro.propertymanagepro.admin;

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
import com.pro.propertymanagepro.dao.RepairsService;
import com.pro.propertymanagepro.entity.Repairs;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;

public class AdminRepairsDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView et_id;
    private TextView et_username;
    private TextView et_projectName;
    private TextView et_date;
    private TextView et_roomNo;
    private TextView et_name;
    private TextView et_phone;
    private TextView et_distributer;
    private TextView et_handler;

    private Button bt_distribute;
    private Button bt_back;

    private int id;
    private String username;
    private String projectName;
    private String date;
    private String roomNo;
    private String name;
    private String phone;
    private String distributer;
    private String handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_admin_detail);
        addActivity(this);

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));

        initView();
    }

    public void initView(){
        et_id = findViewById(R.id.task_admin_id);
        et_username = findViewById(R.id.task_admin_username);
        et_projectName = findViewById(R.id.task_admin_projectName);
        et_date = findViewById(R.id.task_admin_date);
        et_roomNo = findViewById(R.id.task_admin_roomNo);
        et_name = findViewById(R.id.task_admin_name);
        et_phone = findViewById(R.id.task_admin_phone);
        et_distributer = findViewById(R.id.task_admin_distributer);
        et_handler = findViewById(R.id.task_admin_handler);

        bt_distribute = findViewById(R.id.task_admin_distribute);
        bt_back = findViewById(R.id.task_admin_back);

        bt_distribute.setOnClickListener(this);
        bt_back.setOnClickListener(this);

        RepairsService repairsService = new RepairsService(this);
        Repairs repairs = repairsService.getRepairbyID(id);

        username = "发起用户：" + repairs.getUsername();
        projectName = "任务名称：" + repairs.getProject_name();
        date = "发起时间：" + repairs.getDate();
        roomNo = "房间号码：" + repairs.getRoom();
        name = "联系人姓名：" + repairs.getName();
        phone = "联系人电话：" + repairs.getPhone();
        distributer = "发配者：" + repairs.getDistributer();
        handler = "接单人：" + repairs.getHandler();

        et_id.setText("任务编号：" + id);
        et_username.setText(username);
        et_projectName.setText(projectName);
        et_date.setText(date);
        et_roomNo.setText(roomNo);
        et_name.setText(name);
        et_phone.setText(phone);
        et_distributer.setText(distributer);
        et_handler.setText(handler);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.task_admin_back){
            finish();
        }else if(v.getId() == R.id.task_admin_distribute){

        }
    }
}
