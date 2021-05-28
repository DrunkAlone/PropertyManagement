package com.pro.propertymanagepro.basic;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.adapter.VoteView;
import com.pro.propertymanagepro.dao.UserService;
import com.pro.propertymanagepro.dao.VoteService;
import com.pro.propertymanagepro.entity.User;
import com.pro.propertymanagepro.entity.Vote;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;

public class VoteDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private VoteView voteView;

    private TextView tv_title;
    private TextView tv_status;
    private TextView tv_content;
    private Button bt_approve;
    private Button bt_disapprove;
    private TextView tv_finish;
    private TextView tv_pubTime;

    private int id;
    private int userID;
    private String username;
    private String title;
    private String content;
    private String status;
    private String pubTime;
    private String nameList;
    private int approveNum;
    private int disapproveNum;
    private int totalNum;

    VoteService voteService;
    Vote vote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_detail);
        addActivity(this);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        username = intent.getStringExtra("username");
        UserService userService = new UserService(this);
//        userService.addUser(new User(1,"aa", "666", 30, 1, 302));
        userID = userService.getUser(username).getId();
        voteService = new VoteService(this);
        vote = voteService.getVote(id);
        title = vote.getTitle();
        content = vote.getContent();
        status = vote.getStatus();
        pubTime = vote.getPubTime();
        approveNum = vote.getApproveNum();
        totalNum = vote.getTotalNum();
        nameList = vote.getNameList();
        disapproveNum = totalNum - approveNum;
        initView();
    }

    public void initView(){
        tv_title = findViewById(R.id.vote_title);
        tv_status = findViewById(R.id.vote_status);
        tv_content = findViewById(R.id.vote_content);
        voteView = findViewById(R.id.vote_layout);
        bt_approve = findViewById(R.id.vote_approve);
        bt_disapprove = findViewById(R.id.vote_disapprove);
        tv_finish = findViewById(R.id.vote_finish);
        tv_pubTime = findViewById(R.id.vote_pubTime);

        //判断该user是否已投过票
        if(checkUser(userID, nameList)){
            bt_approve.setVisibility(View.GONE);
            bt_disapprove.setVisibility(View.GONE);
            tv_finish.setVisibility(View.VISIBLE);
        }else if(status.equals("已结束")){
            bt_approve.setVisibility(View.GONE);
            bt_disapprove.setVisibility(View.GONE);
            tv_finish.setText("该投票已结束！");
            tv_finish.setVisibility(View.VISIBLE);
        }else{
            tv_finish.setVisibility(View.GONE);
        }

        //设置字体
        Typeface typeface = ResourcesCompat.getFont(this, R.font.siyuansongti);
        tv_title.setTypeface(typeface);
        tv_content.setTypeface(typeface);

        initData();

        bt_approve.setOnClickListener(this);
        bt_disapprove.setOnClickListener(this);
    }

    public void initData(){

        tv_title.setText(title);
        tv_content.setText(content);
        tv_status.setText(status);
        tv_pubTime.setText("发布时间：" + pubTime);

        voteView.setApproveOf(approveNum + "");
        voteView.setOppose(disapproveNum + "");
        float f = (float)approveNum / (float)totalNum;
        voteView.setWeightForView(f);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.vote_approve){
            approveNum++;
            totalNum++;
            voteView.setApproveOf(approveNum + "");
            float rate = (float)approveNum / (float)totalNum;
            voteView.setWeightForView(rate);
            bt_approve.setVisibility(View.GONE);
            bt_disapprove.setVisibility(View.GONE);
            tv_finish.setVisibility(View.VISIBLE);

            //修改数据库
            vote.setApproveNum(approveNum);
            vote.setTotalNum(totalNum);
            if(totalNum == 1){
                vote.setNameList(String.valueOf(userID));
            }else{
                vote.setNameList(nameList + "," + userID);
            }
            voteService.updateVote(vote);
        }else if(v.getId() == R.id.vote_disapprove){
            disapproveNum++;
            totalNum++;
            voteView.setOppose(disapproveNum + "");
            float rate = (float)approveNum / (float)totalNum;
            voteView.setWeightForView(rate);
            bt_approve.setVisibility(View.GONE);
            bt_disapprove.setVisibility(View.GONE);
            tv_finish.setVisibility(View.VISIBLE);

            //修改数据库
            vote.setApproveNum(approveNum);
            vote.setTotalNum(totalNum);
            if(totalNum == 1){
                vote.setNameList(String.valueOf(userID));
            }else{
                vote.setNameList(nameList + "," + userID);
            }
            voteService.updateVote(vote);
        }
    }

    public boolean checkUser(int userID, String nameList){
        String []idList = nameList.split(",");
        for(String id : idList){
            if(id.equals(String.valueOf(userID))){
                return true;
            }
        }
        return false;
    }
}
