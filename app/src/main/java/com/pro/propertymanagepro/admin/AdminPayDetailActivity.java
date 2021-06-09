package com.pro.propertymanagepro.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.OrderService;
import com.pro.propertymanagepro.entity.Order;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;

public class AdminPayDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_id;
    private TextView tv_username;
    private TextView tv_amount;
    private TextView tv_type;
    private TextView tv_date;
    private Button bt_back;

    private int id;
    private String orderID;
    private String username;
    private int amount;
    private String type;
    private String date;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_admin_detail);
        addActivity(this);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        initView();
    }

    public void initView(){
        tv_id = findViewById(R.id.pay_admin_id);
        tv_username = findViewById(R.id.pay_admin_username);
        tv_amount = findViewById(R.id.pay_admin_amount);
        tv_type = findViewById(R.id.pay_admin_type);
        tv_date = findViewById(R.id.pay_admin_time);
        bt_back = findViewById(R.id.pay_admin_back);

        OrderService orderService = new OrderService(this);
        Order order = orderService.getOrderByID(id);
        if(order == null){
            Toast.makeText(this, "没有符合条件的结果，请重试！", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            orderID = order.getOrderID();
            username = order.getUsername();
            amount = order.getAmount();
            type = order.getType();
            date = order.getPayDate();

            tv_id.setText("订单编号：" + orderID);
            tv_username.setText("操作用户：" + username);
            tv_amount.setText("支付金额：" + amount + "元");
            tv_type.setText("缴费类型：" + type);
            tv_date.setText("缴费时间：" + date);
        }

        bt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.pay_admin_back){
            finish();
        }
    }
}
