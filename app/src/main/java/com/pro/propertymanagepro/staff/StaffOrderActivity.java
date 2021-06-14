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

public class StaffOrderActivity extends AppCompatActivity {

    private String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_staff);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        addActivity(this);
        initView();
    }

    public void initView(){
        RepairsService repairsService = new RepairsService(this);
        List<Repairs> repairs = repairsService.getUnhandledTasks();
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < repairs.size(); i++){
            Map<String, Object> map = new HashMap<>();
            Repairs repair = repairs.get(i);
            map.put("id", repair.getId());
            map.put("projectName", "项目名称：" + repair.getProject_name());
            map.put("username", "申请用户：" + repair.getUsername());
            map.put("date", "发起日期：" + repair.getDate());
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.item_order_staff,
                new String[]{"projectName", "username", "date"},
                new int[]{R.id.order_item_staff_projectName, R.id.order_item_staff_username, R.id.order_item_staff_date,});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map=(Map<String,Object>)parent.getItemAtPosition(position);
                Intent intent = new Intent(StaffOrderActivity.this, StaffOrderDetailActivity.class);
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
