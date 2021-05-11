package com.pro.propertymanagepro.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pro.propertymanagepro.MainActivity;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.basic.AdviceActivity;
import com.pro.propertymanagepro.basic.AnnounceActivity;
import com.pro.propertymanagepro.basic.MomentAddActivity;
import com.pro.propertymanagepro.basic.MyRepairsActivity;
import com.pro.propertymanagepro.basic.PayActivity;
import com.pro.propertymanagepro.basic.RepairsActivity;
import com.pro.propertymanagepro.ui.dashboard.DashboardFragment;
import com.pro.propertymanagepro.ui.info.InfoFragment;
import com.pro.propertymanagepro.ui.notifications.NotificationsFragment;
import com.pro.propertymanagepro.util.PermissionActivity;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private HomeViewModel homeViewModel;

    private TextView tv_repairs;
    private TextView tv_vehicle;
    private TextView tv_clean;
    private TextView tv_annouce;
    private TextView tv_advice;
    private TextView tv_contact;
    private TextView tv_push;
    private TextView tv_friend;
    private TextView tv_forum;

    private ImageView repairs;
    private ImageView vehicle;
    private ImageView clean;
    private ImageView announce;
    private ImageView advice;
    private ImageView contact;
    private ImageView push;
    private ImageView friend;
    private ImageView forum;

    private String username;

    private AlertDialog alertDialog;

    final String[] items = {"手机号码:18170265886", "电子邮箱:property@gmail.com"};
    //默认初始化
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "siyuansongti");
//        tv_repairs.setTypeface(typeface);
        repairs = root.findViewById(R.id.home_repairs);
        vehicle = root.findViewById(R.id.home_vehicle);
        clean = root.findViewById(R.id.home_clean);
        announce = root.findViewById(R.id.home_annouce);
        advice = root.findViewById(R.id.home_advice);
        contact = root.findViewById(R.id.home_contact);
        push = root.findViewById(R.id.home_push);
        friend = root.findViewById(R.id.home_friends);
        forum = root.findViewById(R.id.home_forum);

        repairs.setOnClickListener(this);
        vehicle.setOnClickListener(this);
        clean.setOnClickListener(this);
        announce.setOnClickListener(this);
        advice.setOnClickListener(this);
        contact.setOnClickListener(this);
        push.setOnClickListener(this);
        friend.setOnClickListener(this);
        forum.setOnClickListener(this);

        Bundle bundle1 = getActivity().getIntent().getExtras();
        username = bundle1.getString("username");
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_repairs:
                Intent intent = new Intent(HomeFragment.this.getActivity(), RepairsActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                break;
            case R.id.home_vehicle:
                Intent intent5 = new Intent(HomeFragment.this.getActivity(), PayActivity.class);
                intent5.putExtra("username", username);
                startActivity(intent5);
                break;
            case R.id.home_clean:
                Toast.makeText(getContext(), "该功能尚未开发，敬请期待！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.home_annouce:
                Intent intent4 = new Intent(HomeFragment.this.getActivity(), AnnounceActivity.class);
                intent4.putExtra("username", username);
                startActivity(intent4);
                break;
            case R.id.home_advice:
                Intent intent2 = new Intent(HomeFragment.this.getActivity(), AdviceActivity.class);
                intent2.putExtra("username", username);
                startActivity(intent2);
                break;
            case R.id.home_contact:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setTitle("联系方式");
                alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PERMISSION_GRANTED){
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    Uri data = Uri.parse("tel:" + "18170265886");
                                    intent.setData(data);
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(getActivity(), PermissionActivity.class);
                                    startActivity(intent);
                                }
                                break;
                            case 1:
                                break;
                        }
                    }
                });
                alertDialog = alertBuilder.create();
                alertDialog.show();
                break;
            case R.id.home_push:
                Intent intent3 = new Intent(HomeFragment.this.getActivity(), MomentAddActivity.class);
                intent3.putExtra("username", username);
                startActivity(intent3);
                break;
            case R.id.home_forum:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.hide(new HomeFragment()).show(new DashboardFragment()).commit();
                fragmentTransaction.hide(new HomeFragment()).replace(R.id.nav_host_fragment, new DashboardFragment()).commit();
                break;
            case R.id.home_friends:
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.hide(new HomeFragment()).replace(R.id.nav_host_fragment, new NotificationsFragment()).commit();
                break;
        }
    }
}
