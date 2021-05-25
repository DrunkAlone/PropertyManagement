package com.pro.propertymanagepro.basic;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.adapter.MessageAdapter;
import com.pro.propertymanagepro.entity.Message;
import com.pro.propertymanagepro.entity.Response;
import com.pro.propertymanagepro.service.RobotService;
import com.pro.propertymanagepro.service.RobotServiceImpl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.finishAllActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class ConsultActivity extends AppCompatActivity implements View.OnClickListener{

    //对话内容列表
    private TextView tv_chatContent;
    //会话输入框
    private EditText et_input;
    //发送按钮
    private Button bt_send;
    //回退图标
    private ImageView iv_back;

    private RobotServiceImpl robotService;

    private String input;

    private List<Message> messageList = new ArrayList<>();
    private RecyclerView msgRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        addActivity(this);

        robotService = new RobotServiceImpl();

        initView();
    }

    public void initView(){
        tv_chatContent = findViewById(R.id.chat_next);
        et_input = findViewById(R.id.consult_input);
        bt_send = findViewById(R.id.consult_send);
        iv_back = findViewById(R.id.chat_back);

        bt_send.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        messageAdapter = new MessageAdapter(messageList = getData());
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        msgRecyclerView.setAdapter(messageAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.consult_send){
            input = et_input.getText().toString().trim();
            messageList.add(new Message(input, Message.TYPE_SEND));
            messageAdapter.notifyItemInserted(messageList.size() - 1);
            msgRecyclerView.scrollToPosition(messageList.size() - 1);
            et_input.setText("");
            new Thread(() -> {
                Response response = robotService.answer(input);
                try {
                    String respond = getQuickResponse(input).equals(input) ? new String(response.getContent().getBytes(), "UTF-8").replace("{br}", "\n") : getQuickResponse(input);
                    runOnUiThread(() -> {
                        messageList.add(new Message(respond, Message.TYPE_RECEIVED));
                        messageAdapter.notifyItemInserted(messageList.size() - 1);
                        msgRecyclerView.scrollToPosition(messageList.size() - 1);
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }).start();
        }else if(view.getId() == R.id.chat_back){
            finish();
        }
    }

    //初始化消息列表
    private List<Message> getData(){
        List<Message> list = new ArrayList<>();
        list.add(new Message("您好，我是物业管理小助手，请问有什么可以帮助您~", Message.TYPE_RECEIVED));
        return list;
    }

    //自定义快捷回复
    private String getQuickResponse(String word){
        if(word.contains("你好")){
            return "你好你好~";
        }else if(word.contains("再见")){
            return "再见，祝您生活愉快！";
        }else if(word.contains("功能")){
            return "主要功能有：\n在线报修、\n生活缴费、\n智慧停车、\n社区公告、\n投诉建议、\n联系我们、\n发表动态、\n查看好友、\n社区论坛、\n智能客服、\n社区周边、\n区民投票。";
        }else return word;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
