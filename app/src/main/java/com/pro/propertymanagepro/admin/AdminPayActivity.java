package com.pro.propertymanagepro.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.OrderService;
import com.pro.propertymanagepro.entity.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;

public class AdminPayActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_admin);
        addActivity(this);
        initView();
    }

    public void initView(){
        OrderService orderService = new OrderService(this);
        List<Order> orders = orderService.getAllOrders();
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < orders.size(); i++){
            Map<String, Object> map = new HashMap<>();
            Order order = orders.get(i);
            map.put("id", order.getId());
            map.put("type", order.getType());
            map.put("orderID", "订单编号：" + order.getOrderID());
            map.put("username", "操作用户：" + order.getUsername());
            map.put("amount", "交易金额：" + order.getAmount() + "元");
            map.put("date", "交易日期：" + order.getPayDate());
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.item_pay_admin,
                new String[]{"type", "orderID", "username", "amount", "date"},
                new int[]{R.id.pay_item_type, R.id.pay_item_orderID, R.id.pay_item_username, R.id.pay_item_amount, R.id.pay_item_time});
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map=(Map<String,Object>)parent.getItemAtPosition(position);
                Intent intent = new Intent(AdminPayActivity.this, AdminPayDetailActivity.class);
                intent.putExtra("id", map.get("id").toString());
                startActivity(intent);
            }
        });
    }
}
