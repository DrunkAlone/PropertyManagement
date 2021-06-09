package com.pro.propertymanagepro.admin;

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

public class AdminRepairsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_admin);
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
                R.layout.item_task_admin,
                new String[]{"projectName", "username", "date", "distributeStatus", "handleStatus"},
                new int[]{R.id.task_item_admin_projectName, R.id.task_item_admin_username, R.id.task_item_admin_date,
                    R.id.task_item_admin_distributeStatus, R.id.task_item_admin_handleStatus});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map=(Map<String,Object>)parent.getItemAtPosition(position);
                Intent intent = new Intent(AdminRepairsActivity.this, AdminRepairsDetailActivity.class);
                intent.putExtra("id", map.get("id").toString());
                startActivity(intent);
            }
        });
    }
}
