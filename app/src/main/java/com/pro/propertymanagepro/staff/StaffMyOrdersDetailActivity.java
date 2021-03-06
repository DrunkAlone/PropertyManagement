package com.pro.propertymanagepro.staff;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.CentralStaffActivity;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.RepairsService;
import com.pro.propertymanagepro.entity.Repairs;

import java.util.Calendar;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class StaffMyOrdersDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView et_id;
    private TextView et_username;
    private TextView et_projectName;
    private TextView et_date;
    private TextView et_roomNo;
    private TextView et_name;
    private TextView et_phone;
    private TextView et_distributer;
    private TextView et_description;
    private TextView et_handled_date;
    private TextView et_finished_date;

    private ImageView iv_back;

    private Button bt_submit;

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
    private String handled_date;
    private String finished_date;

    private RepairsService repairsService;
    private Repairs repairs;

    private LinearLayout l1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders_staff_detail);
        addActivity(this);

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        staffName = intent.getStringExtra("username");

        initView();
    }

    public void initView(){
        et_id = findViewById(R.id.order_my_staff_id);
        et_username = findViewById(R.id.order_my_staff_username);
        et_projectName = findViewById(R.id.order_my_staff_projectName);
        et_date = findViewById(R.id.order_my_staff_date);
        et_handled_date = findViewById(R.id.order_my_staff_handled_date);
        et_finished_date = findViewById(R.id.order_my_staff_finished_date);
        et_roomNo = findViewById(R.id.order_my_staff_roomNo);
        et_name = findViewById(R.id.order_my_staff_name);
        et_phone = findViewById(R.id.order_my_staff_phone);
        et_distributer = findViewById(R.id.order_my_staff_distributer);
        et_description = findViewById(R.id.order_my_staff_description);

        iv_back = findViewById(R.id.order_my_staff_back);

        bt_submit = findViewById(R.id.order_my_staff_submit);

        l1 = findViewById(R.id.order_l1);

        iv_back.setOnClickListener(this);
        bt_submit.setOnClickListener(this);

        repairsService = new RepairsService(this);
        repairs = repairsService.getRepairbyID(id);

        if(repairs.getFinished_status() == 0){
            l1.setVisibility(View.GONE);
        }else{
            bt_submit.setVisibility(View.INVISIBLE);
        }

        username = "???????????????" + repairs.getUsername();
        projectName = "???????????????" + repairs.getProject_name();
        date = "???????????????" + repairs.getDate();
        handled_date = "???????????????" + repairs.getHandled_date();
        finished_date = "???????????????" + repairs.getFinished_date();
        roomNo = "???????????????" + repairs.getRoom();
        name = "??????????????????" + repairs.getName();
        phone = "??????????????????" + repairs.getPhone();
        distributer = "????????????" + repairs.getDistributer();
        description = "???????????????" + repairs.getDescription();

        et_id.setText("???????????????" + id);
        et_username.setText(username);
        et_projectName.setText(projectName);
        et_date.setText(date);
        et_handled_date.setText(handled_date);
        et_finished_date.setText(finished_date);
        et_roomNo.setText(roomNo);
        et_name.setText(name);
        et_phone.setText(phone);
        et_distributer.setText(distributer);
        et_description.setText(description);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.order_my_staff_back){
            finish();
        }else if(v.getId() == R.id.order_my_staff_submit){
            repairs.setFinished_status(1);
            repairs.setFinished_date(getTime());
            if(repairsService.updateRepairs(repairs)){
                Toast.makeText(this, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CentralStaffActivity.class);
                intent.putExtra("username", staffName);
                startActivity(intent);
            }else{
                Toast.makeText(this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        String now = calendar.get(Calendar.YEAR)+ "???" + (calendar.get(Calendar.MONTH)+1) + "???" + calendar.get(Calendar.DAY_OF_MONTH)+ "???";
        return now;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
