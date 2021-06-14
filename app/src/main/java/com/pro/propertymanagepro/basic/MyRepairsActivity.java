package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView et_handled_date;
    private TextView et_finished_date;
    private TextView et_room;
    private TextView et_name;
    private TextView et_phone;
    private Button bt_back;
    private TextView et_description;
    private TextView et_distributer;
    private TextView et_handler;

    private LinearLayout l1;
    private LinearLayout l2;

    private int id;
    private int handled_status;
    private int finished_status;
    private String username;
    private String project_name;
    private String date;
    private String handled_date;
    private String finished_date;
    private String room;
    private String name;
    private String phone;
    private String description;
    private String distributer;
    private String handler;

    private RepairsService repairsService;
    private Repairs repairs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_repairs);
        addActivity(this);
        Intent intent = this.getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        username = intent.getStringExtra("username");
        initView();
    }

    public void initView(){
//        tv_title = findViewById(R.id.myrepairs_title);
        tv_id = findViewById(R.id.myrepairs_tv_id);
        et_project_name = findViewById(R.id.myrepairs_et_project_name);
        et_date = findViewById(R.id.myrepairs_et_date);
        et_handled_date = findViewById(R.id.myrepairs_et_handled_date);
        et_finished_date = findViewById(R.id.myrepairs_et_finished_date);
        et_room = findViewById(R.id.myrepairs_et_room);
        et_name = findViewById(R.id.myrepairs_et_name);
        et_phone = findViewById(R.id.myrepairs_et_phone);
        et_description = findViewById(R.id.myrepairs_description);
        et_distributer = findViewById(R.id.myrepairs_et_distributer);
        et_handler = findViewById(R.id.myrepairs_et_handler);
        bt_back = findViewById(R.id.myrepairs_back);

        l1 = findViewById(R.id.my_repairs_l1);
        l2 = findViewById(R.id.my_repairs_l2);

        bt_back.setOnClickListener(this);

        //设置字体
//        Typeface typeface = ResourcesCompat.getFont(this, R.font.siyuansongti);
//        tv_title.setTypeface(typeface);

        //为表单赋值
        repairsService = new RepairsService(MyRepairsActivity.this);
        repairs = repairsService.getRepairbyID(id);
        if(repairs == null){
            Toast.makeText(this, "您并没有提交过报修信息！", Toast.LENGTH_SHORT).show();
            finish();
        }
        handled_status = repairs.getHandle_status();
        finished_status = repairs.getFinished_status();

        id = repairs.getId();
        project_name = repairs.getProject_name();
        date = repairs.getDate();
        handled_date = repairs.getHandled_date();
        finished_date = repairs.getFinished_date();
        room = repairs.getRoom();
        name = repairs.getName();
        phone = repairs.getPhone();
        description = repairs.getDescription();
        distributer = repairs.getDistributer();
        handler = repairs.getHandler();

        tv_id.setText(String.valueOf(id));
        et_project_name.setText(project_name);
        et_date.setText(date);
        if(handled_status == 1)et_handled_date.setText(handled_date);
        if(finished_status == 1)et_finished_date.setText(finished_date);
        et_room.setText(room);
        et_name.setText(name);
        et_phone.setText(phone);
        et_description.setText("报修描述： " + description);
        et_distributer.setText(distributer);
        et_handler.setText(handler);

        if(handled_status == 0)l1.setVisibility(View.GONE);
        if(finished_status == 0)l2.setVisibility(View.GONE);
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
