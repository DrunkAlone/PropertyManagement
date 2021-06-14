package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.StaffService;
import com.pro.propertymanagepro.dao.UserService;
import com.pro.propertymanagepro.entity.Staff;
import com.pro.propertymanagepro.entity.User;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_title;
    private EditText et_username;
    private EditText et_password;
    private EditText et_repassword;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_age;
    private EditText et_roomNo;
    private RadioGroup rg_gender;
    private RadioButton rb_female;
    private RadioButton rb_male;
    private RadioButton rb_user;
    private RadioButton rb_staff;

    private Button bt_signup;

    private String username;
    private String password;
    private String repassword;
    private String name;
    private String phone;
    private int age;
    private int gender;
    private int roomNo;
    private int auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_register);
        addActivity(this);
        initView();
    }

    public void initView(){
        tv_title = findViewById(R.id.register_title);
        et_username = findViewById(R.id.register_username);
        et_password = findViewById(R.id.register_password);
        et_repassword = findViewById(R.id.register_repassword);
        et_name = findViewById(R.id.register_name);
        et_phone = findViewById(R.id.register_phone);
        et_age = findViewById(R.id.register_age);
        et_roomNo = findViewById(R.id.register_roomNo);

        bt_signup = findViewById(R.id.bt_register);

        rg_gender = findViewById(R.id.register_sexRadio);
        rb_female = findViewById(R.id.register_rb_female);
        rb_male = findViewById(R.id.register_rb_male);

        rb_staff = findViewById(R.id.register_rb_staff);
        rb_user = findViewById(R.id.register_rb_user);

        //设置按钮响应事件
        bt_signup.setOnClickListener(this);

        //设置字体
        Typeface typeface = ResourcesCompat.getFont(this, R.font.siyuansongti);
        tv_title.setTypeface(typeface);
    }

    @Override
    public void onClick(View view) {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        repassword = et_repassword.getText().toString().trim();
        name = et_name.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        age = Integer.parseInt(et_age.getText().toString().equals("") ? "0" : et_age.getText().toString());
        roomNo = Integer.parseInt(et_roomNo.getText().toString().equals("") ? "0" : et_roomNo.getText().toString());
        gender = rb_male.isChecked() ? 1 : 0;
        auth = rb_user.isChecked() ? 0 : 1;
        boolean isEmpty = username.equals("") || password.equals("") || repassword.equals("") ||
            name.equals("") || phone.equals("") ||
            age == 0 || roomNo == 0;
        if(password.equals(repassword)){
            if (!isEmpty) {
                if (password.length() >= 9 && password.length() <= 20) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().createAccount(username, password);
                                //添加用户信息至数据库
                                UserService userService = new UserService(RegisterActivity.this);
                                User user = new User(1, auth, username, password, name, age, gender, roomNo, phone, "");
                                //判断用户权限
                                if(auth == 1){
                                    StaffService staffService = new StaffService(RegisterActivity.this);
                                    Staff staff = new Staff(1, username, name, "", phone);
                                    staffService.addStaff(staff);
                                }
                                if(userService.addUser(user)){
                                    System.out.println(userService.getUsers());
                                    show(200);
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    show(400);
                                }
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                Log.e("tag", "注册失败 " + e.getErrorCode() + ", " + e.getMessage());
                                show(400);
                            }
                        }
                    }).start();
                }else{
                    show(411);
                }
            }else{
                show(410);
            }
        }else{
            show(409);
        }
    }

    private void show(final int code){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch(code){
                    case 200:
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        break;
                    case 400:
                        Toast.makeText(RegisterActivity.this, "注册失败！请重试！", Toast.LENGTH_SHORT).show();
                        break;
                    case 409:
                        Toast.makeText(RegisterActivity.this, "请确认两次输入的密码相同！", Toast.LENGTH_SHORT).show();
                        break;
                    case 410:
                        Toast.makeText(RegisterActivity.this, "请完善所有信息！", Toast.LENGTH_SHORT).show();
                        break;
                    case 411:
                        Toast.makeText(RegisterActivity.this, "密码长度不得小于九位或大于二十位！", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
