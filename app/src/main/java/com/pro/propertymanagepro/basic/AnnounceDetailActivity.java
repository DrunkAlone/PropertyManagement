package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AnnounceService;
import com.pro.propertymanagepro.entity.Announce;

import java.util.List;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class AnnounceDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_time;
    private Button bt_back;

    private int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce_detail);
        addActivity(this);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        initView();
    }

    public void initView(){
        tv_title = findViewById(R.id.announce_detail_title);
        tv_content = findViewById(R.id.announce_detail_content);
        tv_time = findViewById(R.id.announce_detail_time);
        bt_back = findViewById(R.id.announce_detail_back);

        bt_back.setOnClickListener(this);

        AnnounceService announceService = new AnnounceService(this);
        Announce announce = announceService.getAnnounceByID(id);
        tv_title.setText(announce.getTitle());
        tv_content.setText(announce.getContent());
        tv_time.setText(announce.getPubTime());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.announce_detail_back){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
