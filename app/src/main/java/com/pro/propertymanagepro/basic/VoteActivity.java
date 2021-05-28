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
import com.pro.propertymanagepro.dao.VoteService;
import com.pro.propertymanagepro.entity.Vote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;

public class VoteActivity extends AppCompatActivity {

    String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        addActivity(this);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");

        initView();
    }

    public void initView(){
//        Vote vote_sample = new Vote(1,
//                "社区问卷调查满意度",
//                "近日在小区内多次开展社区问卷调查，并对居民生活标准进行监控改良，本次投票统计居民对问卷调查的满意度。",
//                "2021年4月30日",
//                "2021年6月10日",
//                "",
//                "已结束",
//                80,
//                300);
//        Vote vote_sample1 = new Vote(1,
//                "健身器材居民意愿调查",
//                "为了响应“健康小区”行动号召，小区内或将引进一批全新健身器材，在此向各位居民调查意向。",
//                "2021年5月3日",
//                "2021年5月8日",
//                "",
//                "进行中",
//                45,
//                60);
        VoteService voteService = new VoteService(this);
//        voteService.addVote(vote_sample);
//        voteService.addVote(vote_sample1);
        List<Vote> votes = voteService.getVotes();
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < votes.size(); i++){
            Map<String, Object> map = new HashMap<>();
            Vote vote = votes.get(i);
            map.put("id", vote.getId());
            map.put("title", vote.getTitle());
            map.put("status", vote.getStatus());
            map.put("totalNum", "参与人数：" + vote.getTotalNum());
            map.put("endTime", "截止日期：" + vote.getEndTime());
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.item_vote_item,
                new String[]{"title", "status", "totalNum", "endTime"},
                new int[]{R.id.vote_item_title, R.id.vote_item_status, R.id.vote_item_totalNum, R.id.vote_item_endTime});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> map=(Map<String,Object>)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(VoteActivity.this, VoteDetailActivity.class);
                intent.putExtra("id", map.get("id").toString());
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}
