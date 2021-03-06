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
import com.pro.propertymanagepro.admin.AdminAdviceActivity;
import com.pro.propertymanagepro.admin.AdminAnnounceActivity;
import com.pro.propertymanagepro.admin.AdminLocationActivity;
import com.pro.propertymanagepro.admin.AdminPayActivity;
import com.pro.propertymanagepro.admin.AdminRepairsActivity;
import com.pro.propertymanagepro.admin.AdminRepairsDetailActivity;
import com.pro.propertymanagepro.admin.AdminUserActivity;
import com.pro.propertymanagepro.admin.AdminVoteActivity;
import com.pro.propertymanagepro.basic.AdviceActivity;
import com.pro.propertymanagepro.basic.AnnounceActivity;
import com.pro.propertymanagepro.basic.ConsultActivity;
import com.pro.propertymanagepro.basic.MomentAddActivity;
import com.pro.propertymanagepro.basic.PayActivity;
import com.pro.propertymanagepro.basic.RepairsActivity;
import com.pro.propertymanagepro.basic.SurroundingsActivity;
import com.pro.propertymanagepro.basic.TestActivity;
import com.pro.propertymanagepro.basic.VoteActivity;
import com.pro.propertymanagepro.dao.AdministratorService;
import com.pro.propertymanagepro.entity.Administrator;
import com.pro.propertymanagepro.ui.dashboard.DashboardFragment;
import com.pro.propertymanagepro.ui.notifications.NotificationsFragment;
import com.pro.propertymanagepro.util.PermissionActivity;

import java.util.ArrayList;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class HomeAdminFragment extends Fragment implements View.OnClickListener{
    private HomeViewModel homeViewModel;

    private TextView tv_repairs;
    private TextView tv_pay;
    private TextView tv_vehicle;
    private TextView tv_annouce;
    private TextView tv_advice;
    private TextView tv_contact;
    private TextView tv_push;
    private TextView tv_friend;
    private TextView tv_forum;
    private TextView tv_consult;
    private TextView tv_surroundings;
    private TextView tv_vote;

    private ImageView repairs;
    private ImageView pay;
    private ImageView vehicle;
    private ImageView announce;
    private ImageView advice;
    private ImageView user;
    private ImageView push;
    private ImageView friend;
    private ImageView forum;
    private ImageView consult;
    private ImageView surroundings;
    private ImageView vote;

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

    final String[] items = {"????????????:18170265886", "????????????:property@gmail.com"};
    //???????????????
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_admin, container, false);

        repairs = root.findViewById(R.id.home_admin_repairs);
        pay = root.findViewById(R.id.home_admin_pay);
        announce = root.findViewById(R.id.home_admin_announce);
        advice = root.findViewById(R.id.home_admin_advice);
        user = root.findViewById(R.id.home_admin_user);
        push = root.findViewById(R.id.home_admin_moment);
        surroundings = root.findViewById(R.id.home_admin_surroundings);
        vote = root.findViewById(R.id.home_admin_vote);

        repairs.setOnClickListener(this);
        pay.setOnClickListener(this);
        announce.setOnClickListener(this);
        advice.setOnClickListener(this);
        user.setOnClickListener(this);
        push.setOnClickListener(this);
        surroundings.setOnClickListener(this);
        vote.setOnClickListener(this);

        Bundle bundle1 = getActivity().getIntent().getExtras();
        username = bundle1.getString("username");

        //?????????????????????
//        AdministratorService administratorService = new AdministratorService(this.getContext());
//        Administrator administrator = new Administrator(1, username, "??????", "???????????????????????????????????????3-4???????????????????????????????????????????????????????????????????????????");
//        administratorService.addAdministrator(administrator);

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

        viewPager.setAdapter(new HomeAdminFragment.MyPagerAdapter());
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
            case R.id.home_admin_repairs:
                Intent intent = new Intent(HomeAdminFragment.this.getActivity(), AdminRepairsActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                break;
            case R.id.home_admin_pay:
                Intent intent5 = new Intent(HomeAdminFragment.this.getActivity(), AdminPayActivity.class);
                intent5.putExtra("username", username);
                startActivity(intent5);
                break;
            case R.id.home_admin_announce:
                Intent intent4 = new Intent(HomeAdminFragment.this.getActivity(), AdminAnnounceActivity.class);
                intent4.putExtra("username", username);
                startActivity(intent4);
                break;
            case R.id.home_admin_advice:
                Intent intent2 = new Intent(HomeAdminFragment.this.getActivity(), AdminAdviceActivity.class);
                intent2.putExtra("username", username);
                startActivity(intent2);
                break;
            case R.id.home_admin_user:
                Intent intent9 = new Intent(HomeAdminFragment.this.getActivity(), AdminUserActivity.class);
                intent9.putExtra("username", username);
                startActivity(intent9);
                break;
            case R.id.home_admin_moment:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(new HomeFragment()).replace(R.id.nav_host_fragment_admin, new DashboardFragment()).commit();
                break;
            case R.id.home_admin_surroundings:
                Intent intent7 = new Intent(HomeAdminFragment.this.getActivity(), SurroundingsActivity.class);
                intent7.putExtra("username", username);
                startActivity(intent7);
                break;
            case R.id.home_admin_vote:
                Intent intent8 = new Intent(HomeAdminFragment.this.getActivity(), AdminVoteActivity.class);
                intent8.putExtra("username", username);
                startActivity(intent8);
                break;
        }
    }
}
