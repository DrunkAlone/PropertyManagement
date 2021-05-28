package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.CentralActivity;
import com.pro.propertymanagepro.R;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class PaySuccessActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_id;
    private TextView tv_amount;
    private TextView tv_type;
    private TextView tv_date;
    private Button bt_next;
    private Button bt_back;

    private String username;
    private String orderID;
    private int amount;
    private String type;
    private String date;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        addActivity(this);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        orderID = intent.getStringExtra("orderID");
        amount = intent.getIntExtra("amount", 1);
        type = intent.getStringExtra("type");
        date = intent.getStringExtra("date");
        initView();
    }

    public void initView(){
        tv_id = findViewById(R.id.pay_success_id);
        tv_amount = findViewById(R.id.pay_success_amount);
        tv_type = findViewById(R.id.pay_success_type);
        tv_date = findViewById(R.id.pay_success_date);
        bt_next = findViewById(R.id.pay_success_next);
        bt_back = findViewById(R.id.pay_success_back);

        bt_back.setOnClickListener(this);
        bt_next.setOnClickListener(this);

        tv_id.setText("订单编号：" + orderID);
        tv_amount.setText("支付金额：" + amount + ".00" + "元");
        tv_type.setText("缴费类型：" + type);
        tv_date.setText("缴费时间：" + date);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pay_success_next) {
            finish();
        }else if(v.getId() == R.id.pay_success_back){
            Intent intent = new Intent(this, CentralActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
