package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.pro.propertymanagepro.CentralActivity;
import com.pro.propertymanagepro.CentralAdminActivity;
import com.pro.propertymanagepro.CentralStaffActivity;
import com.pro.propertymanagepro.MainActivity;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AdministratorService;
import com.pro.propertymanagepro.dao.UserService;
import com.pro.propertymanagepro.entity.Administrator;
import com.pro.propertymanagepro.entity.User;

import org.w3c.dom.Text;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    private EditText et_username;
    private EditText et_password;
    private Button bt_login;
    private TextView tv_register;

    private String username;
    private String password;
    private int auth;

    private UserService userService;
    private User user;
    private AdministratorService administratorService;
    private Administrator administrator;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addActivity(this);

        userService = new UserService(this);
        administratorService = new AdministratorService(this);

        initView();
    }

    public void initView(){
        et_username = findViewById(R.id.login_username);
        et_password = findViewById(R.id.login_password);
        tv_register = findViewById(R.id.login_register);
        bt_login = findViewById(R.id.login_login);
        bt_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    public void onClick(View view){
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        if(view.getId() == R.id.login_login){
            if(username.equals("") && password.equals("")){
                Toast.makeText(LoginActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
            }else{
                EMClient.getInstance().login(username, password, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        show(200);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e("tag", "登录失败 " + i + " : " + s);
                        show(i);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        }
        else if(view.getId() == R.id.login_register){
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    private void show(final int code){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (code){
                    case 200:
                        //跳转至主界面
                        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, CentralActivity.class);
//                        Intent intent2 = new Intent(LoginActivity.this, CentralAdminActivity.class);
//                        Intent intent3 = new Intent(LoginActivity.this, CentralStaffActivity.class);
//                        intent.putExtra("username", username);
//                        intent2.putExtra("username", username);
//                        intent3.putExtra("username", username);
//                        if(username.equals("aa")){
//                            startActivity(intent2);
//                        }else if(username.equals("cd")){
//                            startActivity(intent3);
//                        }else{
//                            startActivity(intent);
//                        }

                        user = userService.getUser(username);

//                        //新增一个管理员
//                        administrator = new Administrator(1, username, "回忆", "山东省青岛市黄岛区和平小区3-4栋的房屋的维护与修缮管理以及绿化即车辆治安的管理。");
//                        administratorService.addAdministrator(administrator);
//                        user.setAuth(2);
//                        userService.updateUser(user);

                        auth = user.getAuth();
                        switch (auth){
                            case 0:
                                Intent intent = new Intent(LoginActivity.this, CentralActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent3 = new Intent(LoginActivity.this, CentralStaffActivity.class);
                                intent3.putExtra("username", username);
                                startActivity(intent3);
                                break;
                            case 2:
                                Intent intent2 = new Intent(LoginActivity.this, CentralAdminActivity.class);
                                intent2.putExtra("username", username);
                                startActivity(intent2);
                                break;
                        }
                        finish();
                        break;
                    case 204:
                        Toast.makeText(LoginActivity.this, "用户不存在，请先注册！", Toast.LENGTH_SHORT).show();
                        break;
                    case 202:
                        Toast.makeText(LoginActivity.this, "用户名与密码不匹配！", Toast.LENGTH_SHORT).show();
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
