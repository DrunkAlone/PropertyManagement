package com.pro.propertymanagepro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pro.propertymanagepro.basic.InfoActivity;
import com.pro.propertymanagepro.basic.MyRepairsActivity;
import com.pro.propertymanagepro.basic.RepairsActivity;
import com.pro.propertymanagepro.service.ChatActivity;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.finishAllActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_chat;
    private Button bt_info;
    private Button bt_repairs;
    private Button bt_myRepairs;
    private Button bt_logout;
    private Button bt_forum;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addActivity(this);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        initView();
    }

    public void initView(){
        bt_chat = findViewById(R.id.main_chat);
        bt_info = findViewById(R.id.main_info);
        bt_repairs = findViewById(R.id.main_repairs);
        bt_myRepairs = findViewById(R.id.main_myrepairs);
        bt_logout = findViewById(R.id.main_logout);
        bt_forum = findViewById(R.id.main_forum);
        bt_chat.setOnClickListener(this);
        bt_info.setOnClickListener(this);
        bt_repairs.setOnClickListener(this);
        bt_myRepairs.setOnClickListener(this);
        bt_logout.setOnClickListener(this);
        bt_forum.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.main_chat){
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
            finish();
        }else if(view.getId() == R.id.main_info){
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }else if(view.getId() == R.id.main_repairs){
            Intent intent = new Intent(MainActivity.this, RepairsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }else if(view.getId() == R.id.main_myrepairs){
            Intent intent = new Intent(MainActivity.this, MyRepairsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }else if(view.getId() == R.id.main_logout){
            finishAllActivity();
        }else if(view.getId() == R.id.main_forum){
            Intent intent = new Intent(MainActivity.this, CentralActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
    }
}
