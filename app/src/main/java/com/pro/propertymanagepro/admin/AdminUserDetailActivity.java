package com.pro.propertymanagepro.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.UserService;
import com.pro.propertymanagepro.entity.User;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class AdminUserDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_username;
    private EditText et_age;
    private EditText et_roomNo;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_license;

    private RadioButton rb_female;
    private RadioButton rb_male;

    private Button bt_update;
    private Button bt_back;

    private User user;
    private String username;
    private String pwd;
    private String repwd;
    private String name;
    private String phone;
    private String license;
    private int id;
    private int age;
    private int gender;
    private int roomNo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin_detail);
        addActivity(this);
        Intent intent = this.getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        initView();
    }

    public void initView(){
        tv_username = findViewById(R.id.user_admin_username);
        et_roomNo = findViewById(R.id.user_admin_roomNo);
        et_age = findViewById(R.id.user_admin_age);
        et_name = findViewById(R.id.user_admin_name);
        et_phone = findViewById(R.id.user_admin_phone);
        et_license = findViewById(R.id.user_admin_license);
        bt_update = findViewById(R.id.user_admin_update);
        bt_back = findViewById(R.id.user_admin_back);

        //设置复选框
        rb_female = findViewById(R.id.user_admin_rb_female);
        rb_male = findViewById(R.id.user_admin_rb_male);

        //设置按钮监听事件
        bt_update.setOnClickListener(this);
        bt_back.setOnClickListener(this);

        //查询数据库，填充默认值
        UserService userService = new UserService(this);
        user = userService.getUserByID(id);
        tv_username.setText(String.valueOf(user.getUsername()));
        et_age.setText(String.valueOf(user.getAge()));
        et_roomNo.setText(String.valueOf(user.getRoomNo()));
        et_name.setText(String.valueOf(user.getName()));
        et_phone.setText(String.valueOf(user.getPhone()));
        et_license.setText(user.getLicense());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.user_admin_update){
            age = Integer.parseInt(et_age.getText().toString());
            gender = rb_male.isChecked() ? 1 : 0;
            roomNo = Integer.parseInt(et_roomNo.getText().toString());
            name = et_name.getText().toString().trim();
            phone = et_phone.getText().toString().trim();
            license = et_license.getText().toString().trim();
            boolean isEmpty = name.equals("") || phone.equals("") ||
                    age == 0 || roomNo == 0;
            if(!isEmpty){
                user.setPwd(pwd);
                user.setAge(age);
                user.setGender(gender);
                user.setRoomNo(roomNo);
                user.setName(name);
                user.setPhone(phone);
                if(!license.equals(""))user.setLicense(license);
                UserService userService = new UserService(this);
                if(userService.updateUser(user)){
                    Toast.makeText(this, "成功修改！", Toast.LENGTH_SHORT).show();
                    System.out.println(userService.getUsers());
                    finish();
                }else{
                    Log.e("tag", "更新信息失败！");
                    Toast.makeText(this, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "请确认完善所有信息！", Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId() == R.id.user_admin_back){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
