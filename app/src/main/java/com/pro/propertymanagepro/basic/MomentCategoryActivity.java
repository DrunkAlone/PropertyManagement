package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.MomentService;
import com.pro.propertymanagepro.entity.Moment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class MomentCategoryActivity extends AppCompatActivity {

    private int category;

    private TextView tv_title;

    final String[] items = {"社区杂谈", "社区新闻", "疑难解答"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_category_find);
        addActivity(this);
        Intent intent = getIntent();
        category = intent.getIntExtra("category", 0);
        initView();
    }

    public void initView(){
        tv_title = findViewById(R.id.moment_category_title);
        tv_title.setText(items[category]);

        MomentService momentService = new MomentService(this);
        List<Moment> moments = momentService.getMomentByCategory(category);
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < moments.size(); i++){
            Map<String, Object> map = new HashMap<>();
            Moment m = moments.get(i);
            map.put("username", m.getUsername());
            map.put("content", m.getContent());
            map.put("pubTime", m.getPubTime());
            map.put("category", "分区：" + items[m.getCategory()]);
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.item_moment,
                new String[]{"username", "content", "pubTime", "category"},
                new int[]{R.id.moment_name, R.id.moment_content, R.id.moment_time, R.id.moment_category});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> map=(Map<String,Object>)adapterView.getItemAtPosition(i);
                Toast.makeText(MomentCategoryActivity.this, map.get("username").toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
