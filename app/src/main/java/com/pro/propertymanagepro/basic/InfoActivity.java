package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.UserService;
import com.pro.propertymanagepro.entity.User;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_title;
    private TextView tv_username;
    private TextView tv_pwd;
    private TextView tv_age;
    private EditText et_pwd;
    private EditText et_repwd;
    private EditText et_age;
    private EditText et_roomNo;

    private RadioButton rb_female;
    private RadioButton rb_male;

    private Button bt_update;

    private User user;
    private String username;
    private String pwd;
    private String repwd;
    private int age;
    private int gender;
    private int roomNo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        addActivity(this);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        initView();
    }

    public void initView(){
        tv_title = findViewById(R.id.info_title);
        tv_username = findViewById(R.id.info_tv_username);
        tv_pwd = findViewById(R.id.info_tv_pwd);
        tv_age = findViewById(R.id.info_tv_age);
        et_pwd = findViewById(R.id.info_et_pwd);
        et_repwd = findViewById(R.id.info_et_repwd);
        et_roomNo = findViewById(R.id.info_et_roomNo);
        et_age = findViewById(R.id.info_et_age);
        bt_update = findViewById(R.id.info_update);

        //设置复选框
        rb_female = findViewById(R.id.info_rb_female);
        rb_male = findViewById(R.id.info_rb_male);

        //设置按钮监听事件
        bt_update.setOnClickListener(this);

        //查询数据库，填充默认值
        tv_username.setText(username);
        UserService userService = new UserService(InfoActivity.this);
        user = userService.getUser(username);
        et_age.setText(String.valueOf(user.getAge()));
        et_roomNo.setText(String.valueOf(user.getRoomNo()));

        //设置字体
        Typeface typeface = ResourcesCompat.getFont(this, R.font.siyuansongti);
        tv_title.setTypeface(typeface);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.info_update){
            pwd = et_pwd.getText().toString().trim();
            repwd = et_repwd.getText().toString().trim();
            age = Integer.parseInt(et_age.getText().toString());
            gender = rb_male.isChecked() ? 1 : 0;
            roomNo = Integer.parseInt(et_roomNo.getText().toString());
            boolean isEmpty = pwd.equals("") || repwd.equals("") ||
                    age == 0 || roomNo == 0;
            if(pwd.equals(repwd) && !isEmpty){
                user.setPwd(pwd);
                user.setAge(age);
                user.setGender(gender);
                user.setRoomNo(roomNo);
                UserService userService = new UserService(InfoActivity.this);
                if(userService.updateUser(user)){
                    Toast.makeText(InfoActivity.this, "成功修改！", Toast.LENGTH_SHORT).show();
                    System.out.println(userService.getUsers());
                    finish();
                }else{
                    Log.e("tag", "更新信息失败！");
                    Toast.makeText(InfoActivity.this, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(InfoActivity.this, "请确认完善所有信息！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
