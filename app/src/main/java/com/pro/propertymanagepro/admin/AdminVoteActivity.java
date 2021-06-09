package com.pro.propertymanagepro.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.dao.VoteService;
import com.pro.propertymanagepro.entity.Vote;

import java.util.Calendar;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;

public class AdminVoteActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText title;
    private EditText content;
    private EditText year;
    private EditText month;
    private EditText day;
    private Button submit;

    private AlertDialog alertDialog;
    private AlertDialog alertDialog1;
    private AlertDialog alertDialog2;

    final String[] years = new String[5];
    final String[] months = new String[12];
    final String[] days = new String[30];

    private String vote_title;
    private String vote_content;
    private String vote_pubTime;
    private String vote_endTime;
    private String vote_status;
    private String vote_year;
    private String vote_month;
    private String vote_day;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_admin);
        addActivity(this);
        initArrays();
        initView();
    }

    public void initView(){
        title = findViewById(R.id.vote_admin_title);
        content = findViewById(R.id.vote_admin_content);
        year = findViewById(R.id.vote_admin_year);
        month = findViewById(R.id.vote_admin_month);
        day = findViewById(R.id.vote_admin_day);

        submit = findViewById(R.id.vote_admin_submit);

        year.setOnClickListener(this);
        month.setOnClickListener(this);
        day.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vote_admin_year:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle("请选择年份");
                alertBuilder.setItems(years, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        year.setText(years[which]);
                    }
                });
                alertDialog = alertBuilder.create();
                alertDialog.show();
                break;
            case R.id.vote_admin_month:
                AlertDialog.Builder alertBuilder1 = new AlertDialog.Builder(this);
                alertBuilder1.setTitle("请选择月份");
                alertBuilder1.setItems(months, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        month.setText(months[which]);
                    }
                });
                alertDialog1 = alertBuilder1.create();
                alertDialog1.show();
                break;
            case R.id.vote_admin_day:
                AlertDialog.Builder alertBuilder2 = new AlertDialog.Builder(this);
                alertBuilder2.setTitle("请选择日期");
                alertBuilder2.setItems(days, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        day.setText(days[which]);
                    }
                });
                alertDialog2 = alertBuilder2.create();
                alertDialog2.show();
                break;
            case R.id.vote_admin_submit:
                vote_year = year.getText().toString().trim();
                vote_month = month.getText().toString().trim();
                vote_day = day.getText().toString().trim();
                boolean isDateEmpty = vote_year.equals("") || vote_month.equals("") || vote_day.equals("");
                if(!isDateEmpty){
                    vote_endTime = vote_year + "年" + vote_month + "月" + vote_day + "日";
                    vote_title = title.getText().toString();
                    vote_content = content.getText().toString();
                    vote_pubTime = getTime();
                    vote_status = "进行中";
                    boolean isEmpty = vote_title.equals("") || vote_content.equals("");
                    if(!isEmpty){
                        Vote vote = new Vote(1, vote_title, vote_content, vote_pubTime, vote_endTime, "", vote_status, 1, 2);
                        VoteService voteService = new VoteService(this);
                        if(voteService.addVote(vote)){
                            Toast.makeText(this, "发布投票成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(this, "发布投票失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "请填写正确的投票信息！", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "请选择正确的日期！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void initArrays(){
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        for(int i = 0; i < 5; i++){
            years[i] = y + "";
            y++;
        }
        for(int i = 1; i <= 12; i++){
            months[i - 1] = i + "";
        }
        for(int i = 1; i <= 30; i++){
            days[i - 1] = i + "";
        }
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        String now = calendar.get(Calendar.YEAR)+ " 年 " + (calendar.get(Calendar.MONTH)+1) + " 月 " + calendar.get(Calendar.DAY_OF_MONTH)+ " 日 ";
        return now;
    }
}
