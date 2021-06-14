package com.pro.propertymanagepro.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.hyphenate.chat.EMClient;
import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.basic.InfoActivity;
import com.pro.propertymanagepro.basic.LoginActivity;
import com.pro.propertymanagepro.basic.MyAdviceBasicActivity;
import com.pro.propertymanagepro.basic.MyMomentActivity;
import com.pro.propertymanagepro.basic.MyRepairsBasicActivity;
import com.pro.propertymanagepro.basic.SettingsActivity;
import com.pro.propertymanagepro.dao.AdviceService;
import com.pro.propertymanagepro.dao.MomentService;
import com.pro.propertymanagepro.entity.Advice;
import com.pro.propertymanagepro.entity.Moment;
import com.pro.propertymanagepro.staff.StaffMyOrdersActivity;

import java.util.List;

import static com.pro.propertymanagepro.util.ActivityCollectorUtil.finishAllActivity;

public class InfoStaffFragment extends Fragment {

    private InfoViewModel infoViewModel;

    private String username;

    private TextView tv_username;

    private ItemView iv_update;
    private ItemView iv_repairs;
    private ItemView iv_moments;
    private ItemView iv_settings;
    private ItemView iv_logout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        infoViewModel =
                ViewModelProviders.of(this).get(InfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_info_staff, container, false);

        Bundle bundle1 = getActivity().getIntent().getExtras();
        username = bundle1.getString("username");

        tv_username = root.findViewById(R.id.info_username);
        tv_username.setText(username);

        iv_update = root.findViewById(R.id.info_update);
        iv_repairs = root.findViewById(R.id.info_myrepairs);
        iv_moments = root.findViewById(R.id.info_mymoment);
        iv_settings = root.findViewById(R.id.info_settings);
        iv_logout = root.findViewById(R.id.info_logout);

        iv_update.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                Intent intent = new Intent(InfoStaffFragment.this.getActivity(), InfoActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        iv_repairs.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                Intent intent = new Intent(InfoStaffFragment.this.getActivity(), StaffMyOrdersActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        iv_moments.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                MomentService momentService = new MomentService(getContext());
                List<Moment> list = momentService.getMomentByUser(username);
                if(list.size() == 0){
                    Toast.makeText(getContext(), "您并没有发布动态！", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(InfoStaffFragment.this.getActivity(), MyMomentActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            }
        });
        iv_settings.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                Intent intent = new Intent(InfoStaffFragment.this.getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        iv_logout.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                finishAllActivity();
                EMClient.getInstance().logout(true);
                Intent intent = new Intent(InfoStaffFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
