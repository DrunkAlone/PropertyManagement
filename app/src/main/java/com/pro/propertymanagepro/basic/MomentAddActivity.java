package com.pro.propertymanagepro.basic;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.pro.propertymanagepro.CentralActivity;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.MomentService;
import com.pro.propertymanagepro.entity.Moment;

import java.util.Calendar;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class MomentAddActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_title;
    private TextView tv_input;
    private TextView tv_count;
    private TextView tv_category;
    private Button bt_choose;
    private Button bt_push;

    private AlertDialog alertDialog;

    private int id = 1;
    private String username;
    private int category = 1;
    private String content;
    private String picture = "";
    private int picNum = 0;
    private String pubTime;

    final String[] items = {"社区杂谈", "社区新闻", "疑难解答"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_add);
        addActivity(this);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        initView();
        //字数统计监听
        tv_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.length() + "/140";
                tv_count.setText(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initView(){
        tv_title = findViewById(R.id.moment_add_title);
        tv_input = findViewById(R.id.moment_add_input);
        tv_count = findViewById(R.id.moment_add_count);
        tv_category = findViewById(R.id.moment_add_category);
        bt_push = findViewById(R.id.moment_add_push);
        bt_choose = findViewById(R.id.moment_add_category_choose);

        //按钮监听
        bt_push.setOnClickListener(this);
        bt_choose.setOnClickListener(this);

        //设置字体
        Typeface typeface = ResourcesCompat.getFont(this, R.font.siyuansongti);
        tv_title.setTypeface(typeface);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.moment_add_push){
            content = tv_input.getText().toString().trim();
            String type = tv_category.getText().toString().trim();
            if(content.equals("") || type.equals("")){
                Toast.makeText(this, "请勿发布空动态！", Toast.LENGTH_SHORT).show();
            }else{
                pubTime = getTime();
                for(int i = 0; i < 3; i++){
                    if(type.equals(items[i])){
                        category = i;
                        break;
                    }
                }
                Moment moment = new Moment(1, username, category, content, picture, picNum, pubTime);
                MomentService momentService = new MomentService(this);
                momentService.addMoment(moment);
                Toast.makeText(this, "成功发布！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else if(view.getId() == R.id.moment_add_category_choose){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("请选择动态分类");
            alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tv_category.setText(items[which]);
                }
            });
            alertDialog = alertBuilder.create();
            alertDialog.show();
        }
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        String now = calendar.get(Calendar.YEAR)+ " 年 " + (calendar.get(Calendar.MONTH)+1) + " 月 " + calendar.get(Calendar.DAY_OF_MONTH)+ " 日 "
                + calendar.get(Calendar.HOUR) + " 时 "+ calendar.get(Calendar.MINUTE) + " 分";
        return now;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
