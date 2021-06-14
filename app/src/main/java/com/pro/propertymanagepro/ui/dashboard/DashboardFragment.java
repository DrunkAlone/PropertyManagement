package com.pro.propertymanagepro.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.basic.MomentAddActivity;
import com.pro.propertymanagepro.basic.MomentCategoryActivity;
import com.pro.propertymanagepro.basic.MomentSearchActivity;
import com.pro.propertymanagepro.dao.MomentService;
import com.pro.propertymanagepro.entity.Moment;
import com.pro.propertymanagepro.service.ChatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private ImageView iv_add;
    private ImageView iv_search;
    private ImageView iv_category;

    private String username;

    private AlertDialog alertDialog;
    private AlertDialog alertDialog1;

    final String[] items = {"社区杂谈", "社区新闻", "疑难解答"};
    final String[] options = {"聊天", "举报"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Bundle bundle1 = getActivity().getIntent().getExtras();
        username = bundle1.getString("username");

        initView(root);

        MomentService momentService = new MomentService(this.getActivity());
//        Moment moment = new Moment(1, "aa", 1, "我家房子漏水了，有没有人来看看？",
//                "picture", 2, "2021 年 4月23日 8 时 20 分");
//        for(int i = 0; i < 5; i++){
//            momentService.addMoment(moment);
//        }
        List<Moment> moments = momentService.getAllMoments();
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < moments.size(); i++){
            Map<String, Object> map = new HashMap<>();
            Moment m = moments.get(i);
            map.put("username", m.getUsername());
            map.put("content", m.getContent());
            map.put("pubTime", m.getPubTime());
            map.put("category", "分区：" + items[m.getCategory()]);
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(DashboardFragment.this.getActivity(),
                list,
                R.layout.item_moment,
                new String[]{"username", "content", "pubTime", "category"},
                new int[]{R.id.moment_name, R.id.moment_content, R.id.moment_time, R.id.moment_category});
        ListView listView = (ListView)root.findViewById(R.id.listview);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Map<String,Object> map=(Map<String,Object>)adapterView.getItemAtPosition(i);
//                Toast.makeText(DashboardFragment.this.getActivity(), map.get("username").toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map =(Map<String,Object>)parent.getItemAtPosition(position);
                String receiver = map.get("username").toString();
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setTitle("请选择操作");
                alertBuilder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                if(!username.equals(receiver)){
                                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                                    intent.putExtra("receiver", receiver);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getContext(), "您不能和自己发起聊天！", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 1:
                                Toast.makeText(getContext(), "举报成功！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                alertDialog1 = alertBuilder.create();
                alertDialog1.show();
                return true;
            }
        });

        iv_add.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MomentAddActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        iv_search.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), MomentSearchActivity.class);
            startActivity(intent);
        });

        iv_category.setOnClickListener(v ->{
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
            alertBuilder.setTitle("请选择动态分类");
            alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), MomentCategoryActivity.class);
                    intent.putExtra("category", which);
                    startActivity(intent);
                }
            });
            alertDialog = alertBuilder.create();
            alertDialog.show();
        });

        return root;
    }

    public void initView(View root){
        iv_add = root.findViewById(R.id.dashboard_moment_add);
        iv_search = root.findViewById(R.id.dashboard_moment_search);
        iv_category = root.findViewById(R.id.dashboard_moment_category);
    }

}
