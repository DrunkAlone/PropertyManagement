package com.pro.propertymanagepro.basic;

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
import com.pro.propertymanagepro.admin.AdminRepairsActivity;
import com.pro.propertymanagepro.admin.AdminRepairsDetailActivity;
import com.pro.propertymanagepro.dao.RepairsService;
import com.pro.propertymanagepro.entity.Repairs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class MyRepairsBasicActivity extends AppCompatActivity {

    private String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_repairs_basic);
        addActivity(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        initView();
    }

    public void initView(){
        RepairsService repairsService = new RepairsService(this);
        List<Repairs> repairs = repairsService.getRepairsbyUsername(username);
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < repairs.size(); i++){
            Map<String, Object> map = new HashMap<>();
            Repairs repair = repairs.get(i);
            map.put("id", repair.getId());
            map.put("projectName", "项目名称：" + repair.getProject_name());
            map.put("date", "发起日期：" + repair.getDate());
            map.put("distributeStatus", repair.getDistribute_status() == 0 ? "任务未派发" : "任务已派发");
            map.put("handleStatus", repair.getHandle_status() == 0 ? "任务未接单" : "任务已接单");
            map.put("finishedStatus", repair.getFinished_status() == 0 ? "任务未完成" : "任务已完成");
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.item_my_repairs,
                new String[]{"projectName", "date", "distributeStatus", "handleStatus", "finishedStatus"},
                new int[]{R.id.item_my_repairs_projectName, R.id.item_my_repairs_date,
                        R.id.item_my_repairs_distributeStatus, R.id.item_my_repairs_handleStatus, R.id.item_my_repairs_finishedStatus});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map=(Map<String,Object>)parent.getItemAtPosition(position);
                Intent intent = new Intent(MyRepairsBasicActivity.this, MyRepairsActivity.class);
                intent.putExtra("id", map.get("id").toString());
                intent.putExtra("username", username);
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
