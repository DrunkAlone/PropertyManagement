package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.RepairsService;
import com.pro.propertymanagepro.entity.Repairs;

import java.util.Calendar;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class RepairsActivity extends AppCompatActivity implements View.OnClickListener{

//    private TextView tv_id;
    private TextView tv_title;
    private EditText et_project_name;
    private EditText et_date;
    private EditText et_room;
    private EditText et_name;
    private EditText et_phone;
    private Button bt_commit;
    private EditText et_description;

    private String username;
    private String project_name;
    private String date;
    private String room;
    private String name;
    private String phone;
    private String description;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairs);
        addActivity(this);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        initView();
    }

    public void initView(){
//        tv_id = findViewById(R.id.repairs_id);
        tv_title = findViewById(R.id.repairs_title);
        et_project_name = findViewById(R.id.repairs_et_project_name);
        et_date = findViewById(R.id.repairs_et_date);
        et_room = findViewById(R.id.repairs_et_room);
        et_name = findViewById(R.id.repairs_et_name);
        et_phone = findViewById(R.id.repairs_et_phone);
        et_description = findViewById(R.id.repairs_description);
        bt_commit = findViewById(R.id.repairs_commit);

        et_date.setText(getTime());

        bt_commit.setOnClickListener(this);

        //设置字体
        Typeface typeface = ResourcesCompat.getFont(this, R.font.siyuansongti);
        tv_title.setTypeface(typeface);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.repairs_commit){
            project_name = et_project_name.getText().toString().trim();
            date = et_date.getText().toString().trim();
            room = et_room.getText().toString().trim();
            name = et_name.getText().toString().trim();
            phone = et_phone.getText().toString().trim();
            description = et_description.getText().toString().trim();
            boolean isEmpty = project_name.equals("") || date.equals("") || room.equals("") || name.equals("") ||
                phone.equals("") || description.equals("");
            if(!isEmpty){
                Repairs repairs = new Repairs(1, username, project_name, date, "", "", room, name, phone, description, 0, 0, 0, 5, "无", "无");
                RepairsService repairsService = new RepairsService(RepairsActivity.this);
                if(repairsService.addRepairs(repairs)){
                    Toast.makeText(RepairsActivity.this, "成功提交！", Toast.LENGTH_SHORT).show();
                    System.out.println(repairsService.getAllRepairs());
                    finish();
                }else{
                    Log.e("tag", "提交信息失败！");
                    Toast.makeText(RepairsActivity.this, "提交失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RepairsActivity.this, "请完善报修信息！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        String now = calendar.get(Calendar.YEAR)+ "年" + (calendar.get(Calendar.MONTH)+1) + "月" + calendar.get(Calendar.DAY_OF_MONTH)+ "日";
        return now;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
