package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.graphics.Typeface;
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
import androidx.core.content.res.ResourcesCompat;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.AdviceService;
import com.pro.propertymanagepro.entity.Advice;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class AdviceActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_title;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_content;
    private Button bt_commit;
    private RadioButton rb_category;

    private int type;
    private String username;
    private String name;
    private String phone;
    private String content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        addActivity(this);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        initView();
    }

    public void initView(){
        tv_title = findViewById(R.id.advice_title);
        et_name = findViewById(R.id.advice_et_name);
        et_phone = findViewById(R.id.advice_et_phone);
        et_content = findViewById(R.id.advice_content);
        bt_commit = findViewById(R.id.advice_commit);
        rb_category = findViewById(R.id.advice_category_jy);

        bt_commit.setOnClickListener(this);
        //设置字体
        Typeface typeface = ResourcesCompat.getFont(this, R.font.siyuansongti);
        tv_title.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.advice_commit){
            //0是建议，1是投诉
            type = rb_category.isChecked() ? 0 : 1;
            name = et_name.getText().toString().trim();
            phone = et_phone.getText().toString().trim();
            content = et_content.getText().toString();
            boolean isEmpty = name.equals("") || phone.equals("") || content.equals("");
            if(!isEmpty){
                Advice advice = new Advice(1, type, username, name, phone, content);
                AdviceService adviceService = new AdviceService(AdviceActivity.this);
                if(adviceService.addAdvice(advice)){
                    Toast.makeText(AdviceActivity.this, "成功提交！", Toast.LENGTH_SHORT).show();
                    System.out.println(adviceService.getAllAdvices());
                    finish();
                }else{
                    Log.e("tag", "提交信息失败！");
                    Toast.makeText(AdviceActivity.this, "提交失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(AdviceActivity.this, "请完善报修信息！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
