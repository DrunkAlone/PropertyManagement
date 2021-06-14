package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AnnounceService;
import com.pro.propertymanagepro.entity.Announce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class AnnounceActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);
        addActivity(this);
//        Announce announce = new Announce(1,
//                "关于改善社区卫生环境的通知",
//                "卫工作开展前，社区居民对创卫工作认识不足，环境卫生意识不强，公共文明责任缺乏。为此，社区需要积极开展创卫宣传工作，在社区内，要营造出不时可见的“创建国家卫生城市，美化我们共同的家园”“禁止乱倒垃圾”等宣传告示的氛围。",
//                "2021年3月21日");
//        Announce announce2 = new Announce(1,
//                "促进居民社区参与的方式方法",
//                "建立健全居民参与社区民主议事的各项制度。为进一步增强居民自治意识，保障广大居民对社区事务的知情权、参与权和建议权等基本政治权利，提高社区民主自治水平，应该积极推进社区居委会的直接选举，完善居务公开、民主评议、事务听证和社区居民代表会议等各项制度。",
//                "2021年2月8日");
//        AnnounceService announceService = new AnnounceService(this);
//        announceService.addAnnounce(announce);
//        announceService.addAnnounce(announce2);
        initView();
    }

    public void initView(){
        AnnounceService announceService = new AnnounceService(this);
        List<Announce> announces = announceService.getAllAnnoucements();
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < announces.size(); i++){
            Map<String, Object> map = new HashMap<>();
            Announce announce = announces.get(i);
            map.put("id", announce.getId());
            map.put("title", announce.getTitle());
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.item_announce,
                new String[]{"title"},
                new int[]{R.id.announce_title});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> map=(Map<String,Object>)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(AnnounceActivity.this, AnnounceDetailActivity.class);
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
