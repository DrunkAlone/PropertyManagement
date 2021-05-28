package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.search.core.PoiInfo;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AnnounceService;
import com.pro.propertymanagepro.entity.Announce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;

public class SurroundingsSearchActivity extends AppCompatActivity {

    private TextView result;

    ArrayList<PoiInfo> poiInfos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surroundings_search);
        addActivity(this);
        poiInfos = (ArrayList<PoiInfo>)getIntent().getSerializableExtra("info");
        initView();
    }

    public void initView(){
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < poiInfos.size(); i++){
            Map<String, Object> map = new HashMap<>();
            PoiInfo poiInfo = poiInfos.get(i);
            map.put("name", poiInfo.name);
            map.put("address", poiInfo.address);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.item_surroundings_search,
                new String[]{"name", "address"},
                new int[]{R.id.surroundings_name, R.id.surroundings_address});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }
}
