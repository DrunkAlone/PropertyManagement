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
import com.pro.propertymanagepro.dao.AdviceService;
import com.pro.propertymanagepro.entity.Advice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class MyAdviceBasicActivity extends AppCompatActivity {

    private String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advice_basic);
        addActivity(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        initView();
    }

    public void initView(){
        AdviceService adviceService = new AdviceService(this);
        List<Advice> advices = adviceService.getAdviceByUser(username);
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < advices.size(); i++){
            Map<String, Object> map = new HashMap<>();
            Advice advice = advices.get(i);
            map.put("id", advice.getId());
            map.put("type", advice.getType() == 0 ? "建议" : "投诉");
            map.put("username", "用户名：" + advice.getUsername());
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.item_my_advice,
                new String[]{"type", "username"},
                new int[]{R.id.item_my_advice_type, R.id.item_my_advice_username});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map=(Map<String,Object>)parent.getItemAtPosition(position);
                Intent intent = new Intent(MyAdviceBasicActivity.this, MyAdviceActivity.class);
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
