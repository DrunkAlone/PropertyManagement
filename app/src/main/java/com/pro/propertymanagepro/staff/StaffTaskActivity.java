package com.pro.propertymanagepro.staff;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.RepairsService;
import com.pro.propertymanagepro.entity.Repairs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class StaffTaskActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_staff);
        addActivity(this);
        initView();
    }

    public void initView(){
        RepairsService repairsService = new RepairsService(this);
        List<Repairs> repairs = repairsService.getAllRepairs();
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < repairs.size(); i++){
            Map<String, Object> map = new HashMap<>();
            Repairs repair = repairs.get(i);
            map.put("id", repair.getId());
            map.put("projectName", "项目名称：" + repair.getProject_name());
            map.put("username", "申请用户：" + repair.getUsername());
            map.put("date", "发起日期：" + repair.getDate());
            map.put("distributeStatus", repair.getDistribute_status() == 0 ? "任务未派发" : "任务已派发");
            map.put("handleStatus", repair.getHandle_status() == 0 ? "任务未接单" : "任务已接单");
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.item_task_staff,
                new String[]{"projectName", "username", "date", "distributeStatus", "handleStatus"},
                new int[]{R.id.task_item_staff_projectName, R.id.task_item_staff_username, R.id.task_item_staff_date,
                        R.id.task_item_staff_distributeStatus, R.id.task_item_staff_handleStatus});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map=(Map<String,Object>)parent.getItemAtPosition(position);
                Intent intent = new Intent(StaffTaskActivity.this, StaffTaskDetailActivity.class);
                intent.putExtra("id", map.get("id").toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
