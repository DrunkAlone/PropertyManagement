package com.pro.propertymanagepro.staff;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.CentralStaffActivity;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.RepairsService;
import com.pro.propertymanagepro.dao.StaffService;
import com.pro.propertymanagepro.entity.Repairs;
import com.pro.propertymanagepro.entity.Staff;

import java.util.Calendar;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class StaffOrderDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView et_id;
    private TextView et_username;
    private TextView et_projectName;
    private TextView et_date;
    private TextView et_roomNo;
    private TextView et_name;
    private TextView et_phone;
    private TextView et_distributer;
    private TextView et_description;

    private Button bt_accept;
    private Button bt_back;

    private int id;
    private String staffName;
    private String username;
    private String projectName;
    private String date;
    private String roomNo;
    private String name;
    private String phone;
    private String distributer;
    private String description;

    private RepairsService repairsService;
    private Repairs repairs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_staff_detail);

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        staffName = intent.getStringExtra("username");

        addActivity(this);
        initView();
    }

    public void initView(){
        et_id = findViewById(R.id.order_staff_id);
        et_username = findViewById(R.id.order_staff_username);
        et_projectName = findViewById(R.id.order_staff_projectName);
        et_date = findViewById(R.id.order_staff_date);
        et_roomNo = findViewById(R.id.order_staff_roomNo);
        et_name = findViewById(R.id.order_staff_name);
        et_phone = findViewById(R.id.order_staff_phone);
        et_distributer = findViewById(R.id.order_staff_distributer);
        et_description = findViewById(R.id.order_staff_description);

        bt_back = findViewById(R.id.order_staff_back);
        bt_accept = findViewById(R.id.order_staff_accept);

        bt_back.setOnClickListener(this);
        bt_accept.setOnClickListener(this);

        repairsService = new RepairsService(this);
        repairs = repairsService.getRepairbyID(id);

        username = "发起用户：" + repairs.getUsername();
        projectName = "任务名称：" + repairs.getProject_name();
        date = "发起时间：" + repairs.getDate();
        roomNo = "房间号码：" + repairs.getRoom();
        name = "联系人姓名：" + repairs.getName();
        phone = "联系人电话：" + repairs.getPhone();
        distributer = "发配者：" + repairs.getDistributer();
        description = "任务描述：" + repairs.getDescription();

        et_id.setText("任务编号：" + id);
        et_username.setText(username);
        et_projectName.setText(projectName);
        et_date.setText(date);
        et_roomNo.setText(roomNo);
        et_name.setText(name);
        et_phone.setText(phone);
        et_distributer.setText(distributer);
        et_description.setText(description);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.order_staff_back){
            finish();
        }else if(v.getId() == R.id.order_staff_accept){
            StaffService staffService = new StaffService(this);
            Staff staff = staffService.getStaff(staffName);
            repairs.setHandle_status(1);
            repairs.setHandler(staff.getName());
            repairs.setHandled_date(getTime());
            if(repairsService.updateRepairs(repairs)){
                Toast.makeText(StaffOrderDetailActivity.this, "您已成功接受任务！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CentralStaffActivity.class);
                intent.putExtra("username", staffName);
                startActivity(intent);
            }else{
                Toast.makeText(StaffOrderDetailActivity.this, "接受任务失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        String now = calendar.get(Calendar.YEAR)+ "年" + (calendar.get(Calendar.MONTH)+1) + "月" + calendar.get(Calendar.DAY_OF_MONTH)+ "日";
        return now;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
