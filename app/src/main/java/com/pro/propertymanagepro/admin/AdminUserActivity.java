package com.pro.propertymanagepro.admin;

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
import com.pro.propertymanagepro.dao.UserService;
import com.pro.propertymanagepro.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class AdminUserActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);
        addActivity(this);
        initView();
    }

    public void initView(){
        UserService userService = new UserService(this);
        List<User> users = userService.getUsers();
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            Map<String, Object> map = new HashMap<>();
            User user = users.get(i);
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            map.put("gender", user.getGender() == 1 ? R.drawable.male : R.drawable.female);
            map.put("phone", "手机号：" + user.getPhone());
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.item_user_admin,
                new String[]{"username", "gender", "phone"},
                new int[]{R.id.user_item_admin_name, R.id.user_item_admin_gender, R.id.user_item_admin_phone});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map=(Map<String,Object>)parent.getItemAtPosition(position);
                Intent intent = new Intent(AdminUserActivity.this, AdminUserDetailActivity.class);
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
