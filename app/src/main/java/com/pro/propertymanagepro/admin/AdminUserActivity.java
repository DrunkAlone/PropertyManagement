package com.pro.propertymanagepro.admin;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;

public class AdminUserActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);
        addActivity(this);
    }
}
