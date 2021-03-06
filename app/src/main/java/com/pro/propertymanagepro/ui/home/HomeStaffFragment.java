package com.pro.propertymanagepro.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.pro.propertymanagepro.R;
import com.pro.propertymanagepro.basic.AdviceActivity;
import com.pro.propertymanagepro.basic.AnnounceActivity;
import com.pro.propertymanagepro.basic.ConsultActivity;
import com.pro.propertymanagepro.basic.MomentAddActivity;
import com.pro.propertymanagepro.basic.PayActivity;
import com.pro.propertymanagepro.basic.RepairsActivity;
import com.pro.propertymanagepro.basic.SurroundingsActivity;
import com.pro.propertymanagepro.basic.TestActivity;
import com.pro.propertymanagepro.basic.VoteActivity;
import com.pro.propertymanagepro.dao.StaffService;
import com.pro.propertymanagepro.entity.Staff;
import com.pro.propertymanagepro.staff.StaffMyOrdersActivity;
import com.pro.propertymanagepro.staff.StaffOrderActivity;
import com.pro.propertymanagepro.staff.StaffTaskActivity;
import com.pro.propertymanagepro.ui.dashboard.DashboardFragment;
import com.pro.propertymanagepro.ui.notifications.NotificationsFragment;
import com.pro.propertymanagepro.util.PermissionActivity;

import java.util.ArrayList;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class HomeStaffFragment extends Fragment implements View.OnClickListener{

    private ImageView iv_task;
    private ImageView iv_order;
    private ImageView iv_myorders;
    private ImageView iv_announce;

    private String username;

    private AlertDialog alertDialog;

    private int pre = 0;

    private ViewPager viewPager;

    private LinearLayout pointGroup;

    private TextView iamgeDesc;

    // ????????????ID
    private final int[] imageIds = { R.drawable.background2, R.drawable.background3, R.drawable.background4, R.drawable.background5};

    // ??????????????????
    private final String[] imageDescriptions = { "??????????????????????????????????????????",
            "2021????????????????????????", "??????????????????????????????????????????", "?????????????????????"};

    private ArrayList<ImageView> imageList;

    /**
     * ????????????????????????
     */
    protected int lastPosition;

    //???????????????
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_staff, container, false);
        iv_task = root.findViewById(R.id.home_staff_task);
        iv_order = root.findViewById(R.id.home_staff_order);
        iv_myorders = root.findViewById(R.id.home_staff_myorders);
        iv_announce = root.findViewById(R.id.home_staff_announce);

        iv_task.setOnClickListener(this);
        iv_order.setOnClickListener(this);
        iv_myorders.setOnClickListener(this);
        iv_announce.setOnClickListener(this);

        Bundle bundle1 = getActivity().getIntent().getExtras();
        username = bundle1.getString("username");

//        Staff staff = new Staff(1, "cd", "??????", "", "14089765590");
//        StaffService staffService = new StaffService(getContext());
//        staffService.addStaff(staff);
//        System.out.println("#####################\n" + staffService.getStaffs());

        //???????????????
        viewPager = root.findViewById(R.id.viewpager);
        pointGroup = root.findViewById(R.id.point_group);
        iamgeDesc = root.findViewById(R.id.image_desc);
        iamgeDesc.setText(imageDescriptions[0]);

        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {

            // ?????????????????????
            ImageView image = new ImageView(getContext());
            image.setBackgroundResource(imageIds[i]);
            imageList.add(image);
            // ???????????????
            ImageView point = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.rightMargin = 20;
            point.setLayoutParams(params);

            point.setBackgroundResource(R.drawable.point_bg);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }

        viewPager.setAdapter(new HomeStaffFragment.MyPagerAdapter());
        pointGroup.getChildAt(0).setBackgroundResource(R.drawable.point_focused);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            /**
             * ?????????????????????
             * position ??????????????????
             */
            public void onPageSelected(int position) {

                position = position % imageList.size();

                // ????????????????????????
                iamgeDesc.setText(imageDescriptions[position]);
                // ????????????????????????
                // ????????????enbale ???true
                pointGroup.getChildAt(position).setBackgroundResource(R.drawable.point_focused);
                // ?????????????????????false
                pointGroup.getChildAt(lastPosition).setBackgroundResource(R.drawable.point_bg);
                lastPosition = position;

            }

            @Override
            /**
             * ????????????????????????????????????
             */
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            /**
             * ?????????????????????????????????????????????
             */
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*
         * ??????????????? 1???????????????Timer 2??????????????? while true ?????? 3???ColckManager 4??? ???handler
         * ?????????????????????????????????
         */
        isRunning = true;
        // ???????????????????????????
        handler.sendEmptyMessageDelayed(0, 3000);

        return root;
    }

    private boolean isRunning = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            // ???viewPager ??????????????????
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (isRunning) {
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        };
    };

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        /**
         * ?????????????????????
         */
        public int getCount() {
            return Integer.MAX_VALUE; // ????????????????????????
        }
        /**
         * ????????????????????????view
         * container view????????????????????????viewpager??????
         * position ???????????????
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // ??? container ????????????view
            try{
                container.addView(imageList.get(position % imageList.size()));
            }catch (Exception ex){
                ex.printStackTrace();
            }
            // ??????????????????view?????????object
            return imageList.get(position % imageList.size());
        }

        @Override
        /**
         * ?????? view???object???????????????
         */
        public boolean isViewFromObject(View view, Object object) {
            if (view == object) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        /**
         * ????????????????????????object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            // System.out.println("destroyItem ::" + position);

            container.removeView((View) object);
            object = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_staff_task:
                Intent intent = new Intent(HomeStaffFragment.this.getActivity(), StaffTaskActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                break;
            case R.id.home_staff_order:
                Intent intent5 = new Intent(HomeStaffFragment.this.getActivity(), StaffOrderActivity.class);
                intent5.putExtra("username", username);
                startActivity(intent5);
                break;
            case R.id.home_staff_announce:
                Intent intent4 = new Intent(HomeStaffFragment.this.getActivity(), AnnounceActivity.class);
                intent4.putExtra("username", username);
                startActivity(intent4);
                break;
            case R.id.home_staff_myorders:
                Intent intent2 = new Intent(HomeStaffFragment.this.getActivity(), StaffMyOrdersActivity.class);
                intent2.putExtra("username", username);
                startActivity(intent2);
                break;
        }
    }
}
