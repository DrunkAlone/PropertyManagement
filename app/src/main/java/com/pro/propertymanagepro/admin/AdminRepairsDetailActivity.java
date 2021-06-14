package com.pro.propertymanagepro.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.CentralAdminActivity;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AdministratorService;
import com.pro.propertymanagepro.dao.RepairsService;
import com.pro.propertymanagepro.dao.StaffService;
import com.pro.propertymanagepro.dao.UserService;
import com.pro.propertymanagepro.entity.Administrator;
import com.pro.propertymanagepro.entity.Repairs;
import com.pro.propertymanagepro.entity.Staff;
import com.pro.propertymanagepro.entity.User;

import java.util.Calendar;
import java.util.List;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

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
    private String adminName;
    private String username;
    private String projectName;
    private String date;
    private String roomNo;
    private String name;
    private String phone;
    private String distributer;
    private String handler;

    private RepairsService repairsService;
    private Repairs repairs;
    private StaffService staffService;
    private Staff staff;

    private AlertDialog alertDialog;
    private AlertDialog alertDialog1;

    final String[] items = {"随机派发", "指定派发"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_admin_detail);
        addActivity(this);

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        adminName = intent.getStringExtra("username");

        staffService = new StaffService(this);

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

        repairsService = new RepairsService(this);
        repairs = repairsService.getRepairbyID(id);

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

        if(!repairs.getDistributer().equals("无")){
            bt_distribute.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.task_admin_back){
            finish();
        }else if(v.getId() == R.id.task_admin_distribute){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("请选择派发方式");
            alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == 0){
                        repairs.setDistribute_status(1);
//                        UserService userService = new UserService(AdminRepairsDetailActivity.this);
//                        User user = userService.getUser(adminName);
//                        repairs.setDistributer(user.getName());
                        AdministratorService administratorService = new AdministratorService(getApplicationContext());
                        Administrator administrator = administratorService.getAdministrator(adminName);
                        repairs.setDistributer(administrator.getName());
                        if(repairsService.updateRepairs(repairs)){
                            Toast.makeText(AdminRepairsDetailActivity.this, "您已成功派发任务！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), CentralAdminActivity.class);
                            intent.putExtra("username", adminName);
                            startActivity(intent);
                        }else{
                            Toast.makeText(AdminRepairsDetailActivity.this, "派发任务失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
//                        System.out.println("#################" + repairsService.getAllRepairs());
                    }else{
                        List<Staff> staffs = staffService.getStaffs();
                        System.out.println(staffs);
                        String []staffList = new String[staffs.size()];
                        for(int i = 0; i < staffList.length; i++){
                            staffList[i] = staffs.get(i).getName();
                        }
                        alertBuilder.setTitle("请选择维修人员");
                        alertBuilder.setItems(staffList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(AdminRepairsDetailActivity.this, String.valueOf(items[which]), Toast.LENGTH_SHORT).show();
                                repairs.setHandle_status(1);
                                repairs.setHandler(staffList[which]);
                                repairs.setDistribute_status(1);
                                repairs.setHandled_date(getTime());
                                AdministratorService administratorService = new AdministratorService(getApplicationContext());
                                Administrator administrator = administratorService.getAdministrator(adminName);
                                repairs.setDistributer(administrator.getName());
                                if(repairsService.updateRepairs(repairs)){
                                    Toast.makeText(AdminRepairsDetailActivity.this, "您已成功派发任务！", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), CentralAdminActivity.class);
                                    intent.putExtra("username", adminName);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(AdminRepairsDetailActivity.this, "派发任务失败，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alertDialog1 = alertBuilder.create();
                        alertDialog1.show();
                    }
                }
            });
            alertDialog = alertBuilder.create();
            alertDialog.show();
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
