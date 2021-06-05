package com.pro.propertymanagepro.basic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyphenate.util.Utils;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.database.UserURL;
import com.pro.propertymanagepro.entity.RestResponse;
import com.pro.propertymanagepro.entity.Test;
import com.pro.propertymanagepro.entity.User;
import com.pro.propertymanagepro.util.ResponseCode;
import com.pro.propertymanagepro.util.ResponseMessage;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_add;
    private Button bt_search;
    private Button bt_update;
    private Button bt_delete;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private static final MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
    private final Message message = new Message();

    private int signInId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    public void initView(){
        bt_add = findViewById(R.id.test_add);
        bt_search = findViewById(R.id.test_search);
        bt_update = findViewById(R.id.test_update);
        bt_delete = findViewById(R.id.test_delete);

        bt_add.setOnClickListener(this);
        bt_search.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_add:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        OkHttpClient okHttpClient = new OkHttpClient();
//                        String name = "aa";
//                        FormBody formBody = new FormBody.Builder().add("name", name).build();
//                        Request request = new Request.Builder()
//                                .url(UserURL.ADD)
//                                .post(formBody)
//                                .build();
//                        try (Response response = okHttpClient.newCall(request).execute()) {
//                            Looper.prepare();
//                            if (Boolean.parseBoolean(response.body().string()))
//                            {
//                                Toast.makeText(TestActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                            }
//                            else
//                            {
//                                Toast.makeText(TestActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
//                            }
//                            Looper.loop();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
                try {
                    String name = "dda";
                    String password = "memory";
                    Request request = new Request.Builder().url(UserURL.SIGN_IN_UP).post(
                            RequestBody.create(mapper.writeValueAsString(new Test(200, name, password)), mediaType)
                    ).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            message.what = ResponseCode.REQUEST_FAILED;
                            handler.post(()-> ResponseMessage.showMessage(getApplicationContext(),message));
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                ResponseBody body = response.body();
                                if (body != null) {
                                    RestResponse restResponse = mapper.readValue(body.string(), RestResponse.class);
                                    message.what = restResponse.getCode();
                                    if(message.what == ResponseCode.SIGN_IN_SUCCESS){
                                        handler.post(()->{
                                            signInId = (int)restResponse.getData();
                                        });
                                    }
                                } else {
                                    message.what = ResponseCode.EMPTY_RESPONSE;
                                    Log.e("RESPONSE_BODY_EMPTY", response.message());
                                }
                            } else {
                                message.what = ResponseCode.SERVER_ERROR;
                                Log.e("SERVER_ERROR", response.message());
                            }
                            handler.post(()->ResponseMessage.showMessage(getApplicationContext(),message));
                        }
                    });
                } catch (JsonProcessingException e) {
                    message.what = ResponseCode.JSON_SERIALIZATION;
                    ResponseMessage.showMessage(getApplicationContext(),message);
                    e.printStackTrace();
                }
                break;
            case R.id.test_search:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient1 = new OkHttpClient();
                        String name1 = "aa";
                        FormBody formBody1 = new FormBody.Builder().add("name", name1).build();
                        Request request1 = new Request.Builder()
                                .url(UserURL.GET)
                                .post(formBody1)
                                .build();
                        try (Response response = okHttpClient1.newCall(request1).execute()) {
                            List<Test> users = JSONArray.parseArray(response.body().string(),Test.class);
                            Looper.prepare();
                            if(users.size() == 0)
                            {
                                Toast.makeText(TestActivity.this,"查询失败", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(TestActivity.this,"查询成功" + users, Toast.LENGTH_SHORT).show();
                                System.out.println(users);
                            }
                            Looper.loop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.test_update:
                try {
                    Test test1 = new Test("ddd", "666");
                    Test test2 = new Test(1, "name", "password");
                    System.out.println(mapper.writeValueAsString(test1));
                    System.out.println(mapper.writeValueAsString(test2));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
