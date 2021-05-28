package com.pro.propertymanagepro.basic;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.DepositService;
import com.pro.propertymanagepro.dao.OrderService;
import com.pro.propertymanagepro.entity.Deposit;
import com.pro.propertymanagepro.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;

public class PayActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_type;
    private EditText et_amount;
    private Button bt_type_choose;
    private Button bt_deposit_choose;
    private Button bt_submit;

    OrderService orderService;
    DepositService depositService;

    private AlertDialog alertDialog;
    private AlertDialog alertDialog1;

    private String username;
    private String orderID;
    private int amount;
    private String type;
    private String payDate;
    private String status;

    final String[] items = {"停车费", "水费", "电费", "天然气费", "供暖费"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        addActivity(this);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");

        orderService = new OrderService(this);
        depositService = new DepositService(this);

        initView();
    }

    public void initView(){
        et_type = findViewById(R.id.pay_type);
        et_amount = findViewById(R.id.pay_amount);
        bt_type_choose = findViewById(R.id.pay_type_choose);
        bt_deposit_choose = findViewById(R.id.pay_deposit_choose);
        bt_submit = findViewById(R.id.pay_submit);

        bt_type_choose.setOnClickListener(this);
        bt_deposit_choose.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    protected void onDestroy() {
        super.onDestroy();
    };

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.pay_type_choose){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("请选择缴费类型");
            alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_type.setText(items[which]);
                }
            });
            alertDialog = alertBuilder.create();
            alertDialog.show();
        }else if(v.getId() == R.id.pay_deposit_choose){
            if(et_type.getText().toString().equals("")){
                Toast.makeText(this, "请先选择缴费类型！", Toast.LENGTH_SHORT).show();
            }else{
                type = et_type.getText().toString();
                Deposit deposit = depositService.getDepositByUsernameAndType(username, type);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle("余额查询");
                alertBuilder.setMessage("您的" + type + "余额为：" + (deposit == null ? 0 : deposit.getAmount()) + "元");
                alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        }else if(v.getId() == R.id.pay_submit){
            orderID = getRandomID();
            amount = Integer.parseInt(et_amount.getText().toString().trim());
            type = et_type.getText().toString();
            payDate = getTime();
            status = "交易完成";
            boolean isEmpty = username.equals("") || orderID.equals("") || type.equals("") || amount == 0
                    || payDate.equals("");
            if(!isEmpty){
                Order order = new Order(1, orderID, username, amount, type, payDate, status);
                orderService.addOrder(order);
                //更改余额信息
                Deposit deposit = depositService.getDepositByUsernameAndType(username, type);
                if(deposit == null){
                    Deposit d = new Deposit(1, username, type, amount, payDate);
                    depositService.addDeposit(d);
                }else{
                    deposit.setAmount(deposit.getAmount() + amount);
                    deposit.setLastDate(payDate);
                    depositService.updateDeposit(deposit);
                }
                System.out.println(orderService.getAllOrders());
                System.out.println(depositService.getAllDeposits());
                Intent intent = new Intent(PayActivity.this, PaySuccessActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("orderID", orderID);
                intent.putExtra("amount", amount);
                intent.putExtra("type", type);
                intent.putExtra("date", payDate);
                startActivity(intent);
            }else{
                Toast.makeText(this, "请输入正确的缴费信息！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        String now = calendar.get(Calendar.YEAR)+ "年" + (calendar.get(Calendar.MONTH)+1) + "月" + calendar.get(Calendar.DAY_OF_MONTH)+ "日"
                + calendar.get(Calendar.HOUR) + "时"+ calendar.get(Calendar.MINUTE) + "分";
        return now;
    }

    public String getRandomID(){
        long date = new Date().getTime();
        int random = (int)((Math.random()*9+1)*100000);
        return date + "" + random;
    }
}
