package com.pro.propertymanagepro.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AdministratorService;
import com.pro.propertymanagepro.entity.Administrator;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class AdminLocationActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_location;
    private Button bt_back;

    private String username;
    private String location;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_admin);
        addActivity(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        initView();
    }

    public void initView(){
        tv_location = findViewById(R.id.location_admin_content);
        bt_back = findViewById(R.id.location_admin_back);

        AdministratorService administratorService = new AdministratorService(this);
        Administrator administrator = administratorService.getAdministrator(username);
        location = administrator.getLocation();
        tv_location.setText(location);

        bt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.location_admin_back){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
