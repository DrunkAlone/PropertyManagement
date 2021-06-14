package com.pro.propertymanagepro.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.pro.propertymanagepro.dao.UserService;
import com.pro.propertymanagepro.entity.User;
import com.pro.propertymanagepro.service.ChatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private EditText et_word;
    private ImageView iv_search;
    private String word;
    private String username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        /*
        //存图片的id
        int[] imageid=new int[]{
                R.drawable.avatar1, R.drawable.avatar, R.drawable.avatar2,
                R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5,
        };
        //存名字
        String[] title=new String[]{
                "aa","cd","ddd","小李","张发财","王富贵"
        };
        //存时间
        String[] time = new String[]{
                " 8:21"," 9:32", "3小时前","21小时前","3天前","一周前"
        };
        //数据源为List，List是顺序容器，里面的元素类型为Map（散列表），Map的关键字为String，值为Object
        List<Map<String,Object>> listitem=new ArrayList<Map<String,Object>>();
        //将数据放入List
        for(int i=0;i<imageid.length;i++)
        {
            //创建一个map对象，即一个List的元素。
            Map<String,Object> map= new HashMap<String,Object>();
            map.put("image",imageid[i]);
            map.put("name",title[i]);
            map.put("time",time[i]);
            listitem.add(map);
        }
        //设置并将数据放入适配器，第一个参数为上下文指针，第二个参数为数据来源，第三个参数为XML实现文件
        //第四个参数为List中的两个子项，第五个参数为listitem中的两个TextView ID
        SimpleAdapter adapter=new SimpleAdapter(NotificationsFragment.this.getActivity(),listitem,R.layout.item_friends,new String[]{"name","image","time"}
                ,new int[]{R.id.title,R.id.image,R.id.time});
        //获取xml中的listview
        ListView listview= (ListView) root.findViewById(R.id.listview);
        //将适配器放入listview中
        listview.setAdapter(adapter);
        //设置事件监听器
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //获得List中的一个元素，此时获取适配器的配置并强制转化为Map
                Map<String,Object> map=(Map<String,Object>)adapterView.getItemAtPosition(i);
                String receiver = map.get("name").toString();
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("receiver", receiver);
                startActivity(intent);
            }
        });
         */

        Bundle bundle1 = getActivity().getIntent().getExtras();
        username = bundle1.getString("username");

        et_word = root.findViewById(R.id.user_search_word);
        iv_search = root.findViewById(R.id.user_search);
        iv_search.setOnClickListener(v -> {

            word = et_word.getText().toString().trim();

            UserService userService = new UserService(getActivity());
            List<User> users = userService.getUsersByUsername(word);
            List<Map<String, Object>> list = new ArrayList<>();
            for(int i = 0; i < users.size(); i++){
                Map<String, Object> map = new HashMap<>();
                User user = users.get(i);
                map.put("id", user.getId());
                map.put("name", user.getName());
                map.put("username", user.getUsername());
                map.put("gender", user.getGender() == 1 ? R.drawable.male : R.drawable.female);
                list.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                    list,
                    R.layout.item_user_search,
                    new String[]{"name", "gender"},
                    new int[]{R.id.item_user_search_name, R.id.item_user_search_gender});
            ListView listView = root.findViewById(R.id.listview);
            listView.setAdapter(adapter);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Map<String,Object> map=(Map<String,Object>)parent.getItemAtPosition(position);
                    String receiver = map.get("username").toString();
                    if(receiver.equals(username)){
                        Toast.makeText(getContext(), "您不能和自己发起聊天！", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("receiver", receiver);
                        startActivity(intent);
                    }
                }
            });
        });
        return root;
    }
}
