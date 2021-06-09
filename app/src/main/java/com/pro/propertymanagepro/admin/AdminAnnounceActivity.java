package com.pro.propertymanagepro.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AnnounceService;
import com.pro.propertymanagepro.entity.Announce;

import java.util.Calendar;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class AdminAnnounceActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_title;
    private EditText et_content;
    private Button bt_push;

    private String title;
    private String content;
    private String username;
    private String pubTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce_admin);
        addActivity(this);
        //获取用户名
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        //初始化界面组件
        initView();
    }

    public void initView(){
        et_title = findViewById(R.id.announce_admin_title);
        et_content = findViewById(R.id.announce_admin_content);
        bt_push = findViewById(R.id.announce_admin_push);

        bt_push.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.announce_admin_push){
            title = et_title.getText().toString();
            content = et_content.getText().toString();
            pubTime = getTime();
            boolean isEmpty = title.equals("") || content.equals("");
            if(!isEmpty){
                Announce announce = new Announce(1, title, content, pubTime);
                AnnounceService announceService = new AnnounceService(this);
                if(announceService.addAnnounce(announce)){
                    Toast.makeText(this, "发布成功！", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "发布失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "请输入正确的公告信息！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        String now = calendar.get(Calendar.YEAR)+ " 年 " + (calendar.get(Calendar.MONTH)+1) + " 月 " + calendar.get(Calendar.DAY_OF_MONTH)+ " 日 ";
        return now;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
