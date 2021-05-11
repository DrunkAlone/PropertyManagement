package com.pro.propertymanagepro.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.adapter.MessageAdapter;
import com.pro.propertymanagepro.entity.Message;

import java.util.ArrayList;
import java.util.List;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    //对话内容列表
    private TextView tv_chatContent;
    //会话输入框
    private EditText et_input;
    //发送按钮
    private Button bt_send;
    //聊天标题
    private TextView tv_title;
    //回退图标
    private ImageView iv_back;

    //消息接收人
    private String receiver;

    private List<Message> messageList = new ArrayList<>();
    private RecyclerView msgRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;

    public EMMessageListener msgListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);
        addActivity(this);

        Intent intent = this.getIntent();
        receiver = intent.getStringExtra("receiver");

        initView();
    }

    public void initView(){
        tv_chatContent = findViewById(R.id.chat_next);
        et_input = findViewById(R.id.chat_messageInput);
        bt_send = findViewById(R.id.chat_send);
        tv_title = findViewById(R.id.chat_title);
        iv_back = findViewById(R.id.chat_back);
        bt_send.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        tv_title.setText(receiver);

        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        messageAdapter = new MessageAdapter(messageList = getData());
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        msgRecyclerView.setAdapter(messageAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        msgListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                Log.i("tag", "信息接收回调！");
                for(final EMMessage message : list){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String msg = message.getBody().toString();
                            int length = msg.length();
                            msg = msg.substring(msg.indexOf(":") + 2, length - 1);
//                            String mm = tv_chatContent.getText() + "\n"
//                                    + msg;
//                            tv_chatContent.setText(mm);
                            messageList.add(new Message(msg, Message.TYPE_RECEIVED));
                            messageAdapter.notifyItemInserted(messageList.size() - 1);
                            msgRecyclerView.scrollToPosition(messageList.size() - 1);
                        }
                    });
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.chat_send){
            String input = et_input.getText().toString();
            if(input.equals("")){
                Toast.makeText(ChatActivity.this, "发送信息不能为空！", Toast.LENGTH_SHORT).show();
            }else{
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(input, receiver);
                //如果是群聊，设置chattype，默认是单聊
                //if (chatType == CHATTYPE_GROUP)
                //    message.setChatType(EMMessage.ChatType.GroupChat);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);
                messageList.add(new Message(input, Message.TYPE_SEND));
                messageAdapter.notifyItemInserted(messageList.size() - 1);
                msgRecyclerView.scrollToPosition(messageList.size() - 1);
                et_input.setText("");
                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.i("tag", "消息发送成功！");
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e("tag", "消息发送失败： "+ i + " : "+ s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        }else if(view.getId() == R.id.chat_back){
            finish();
        }
    }

    private List<Message> getData(){
        List<Message> list = new ArrayList<>();
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(receiver);
        //获取此会话的所有消息
        if(conversation != null){
            List<EMMessage> messages = conversation.getAllMessages();
            System.out.println("***********");
            System.out.println(messages.size());
            if(messages.size() == 0){
                list.add(new Message("hello", Message.TYPE_RECEIVED));
            }else{
                for(EMMessage message : messages){
                    runOnUiThread(() -> {
                        String msg = message.getBody().toString();
                        int length = msg.length();
                        msg = msg.substring(msg.indexOf(":") + 2, length - 1);
                        if(message.direct().toString().equals("SEND")){
                            list.add(new Message(msg, Message.TYPE_SEND));
                        }else {
                            list.add(new Message(msg, Message.TYPE_RECEIVED));
                        }
                    });
                }
            }
        }else{
            list.add(new Message("hello", Message.TYPE_RECEIVED));
        }
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        removeActivity(this);
    }
}
