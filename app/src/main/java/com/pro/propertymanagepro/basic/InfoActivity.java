package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AdministratorService;
import com.pro.propertymanagepro.dao.StaffService;
import com.pro.propertymanagepro.dao.UserService;
import com.pro.propertymanagepro.entity.Administrator;
import com.pro.propertymanagepro.entity.Staff;
import com.pro.propertymanagepro.entity.User;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_title;
    private TextView tv_username;
    private TextView tv_pwd;
    private TextView tv_repwd;
    private TextView tv_age;
    private TextView tv_roomNo;
    private TextView tv_gender;
    private TextView tv_name;
    private TextView tv_phone;
    private EditText et_pwd;
    private EditText et_repwd;
    private EditText et_age;
    private EditText et_roomNo;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_license;

    private RadioButton rb_female;
    private RadioButton rb_male;

    private Button bt_update;
    private Button bt_back;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;

    private User user;
    private String username;
    private String pwd;
    private String repwd;
    private String name;
    private String phone;
    private String license;
    private int age;
    private int gender;
    private int roomNo;
    private int auth;

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
        tv_repwd = findViewById(R.id.info_tv_repassword);
        tv_roomNo = findViewById(R.id.info_tv_roomNo);
        tv_gender = findViewById(R.id.info_tv_gender);
        tv_name = findViewById(R.id.info_tv_name);
        tv_phone = findViewById(R.id.info_tv_phone);
        et_pwd = findViewById(R.id.info_et_pwd);
        et_repwd = findViewById(R.id.info_et_repwd);
        et_roomNo = findViewById(R.id.info_et_roomNo);
        et_age = findViewById(R.id.info_et_age);
        et_name = findViewById(R.id.info_et_name);
        et_phone = findViewById(R.id.info_et_phone);
        et_license = findViewById(R.id.info_et_license);
        bt_update = findViewById(R.id.info_update);
        bt_back = findViewById(R.id.info_back);

        //设置复选框
        rb_female = findViewById(R.id.info_rb_female);
        rb_male = findViewById(R.id.info_rb_male);

        //设置linearLayout
        linearLayout1 = findViewById(R.id.info_l1);
        linearLayout2 = findViewById(R.id.info_l2);

        //设置按钮监听事件
        bt_update.setOnClickListener(this);
        bt_back.setOnClickListener(this);

        //查询数据库，填充默认值
        tv_username.setText(username);
        UserService userService = new UserService(InfoActivity.this);
        user = userService.getUser(username);
        auth = user.getAuth();
        et_age.setText(String.valueOf(user.getAge()));
        et_roomNo.setText(String.valueOf(user.getRoomNo()));
        et_name.setText(String.valueOf(user.getName()));
        et_phone.setText(String.valueOf(user.getPhone()));
        et_license.setText(String.valueOf(user.getLicense()));

        //若非业主用户，则不予以显示房间号及车牌号信息
        if(auth != 0){
            linearLayout1.setVisibility(View.INVISIBLE);
            linearLayout2.setVisibility(View.INVISIBLE);
        }

        //设置字体
//        Typeface typeface = ResourcesCompat.getFont(this, R.font.siyuansongti);
//        tv_title.setTypeface(typeface);
//        tv_username.setTypeface(typeface);
//        tv_pwd.setTypeface(typeface);
//        tv_age.setTypeface(typeface);
//        tv_repwd.setTypeface(typeface);
//        tv_roomNo.setTypeface(typeface);
//        tv_gender.setTypeface(typeface);
//        tv_name.setTypeface(typeface);
//        tv_phone.setTypeface(typeface);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.info_update){
            pwd = et_pwd.getText().toString().trim();
            repwd = et_repwd.getText().toString().trim();
            age = Integer.parseInt(et_age.getText().toString());
            gender = rb_male.isChecked() ? 1 : 0;
            roomNo = Integer.parseInt(et_roomNo.getText().toString());
            name = et_name.getText().toString().trim();
            phone = et_phone.getText().toString().trim();
            license = et_license.getText().toString().trim();
            boolean isEmpty = pwd.equals("") || repwd.equals("") || name.equals("") || phone.equals("") ||
                    age == 0 || roomNo == 0;
            if(pwd.equals(repwd) && !isEmpty){
                user.setPwd(pwd);
                user.setAge(age);
                user.setGender(gender);
                user.setRoomNo(roomNo);
                user.setName(name);
                user.setPhone(phone);
                if(!license.equals(""))user.setLicense(license);
                UserService userService = new UserService(InfoActivity.this);
                if(userService.updateUser(user)){
                    if(auth == 1){
                        StaffService staffService = new StaffService(this);
                        Staff staff = staffService.getStaff(username);
                        staff.setName(name);
                        staff.setPhone(phone);
                        staffService.updateStaff(staff);
                    }
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
        }else if(view.getId() == R.id.info_back){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
