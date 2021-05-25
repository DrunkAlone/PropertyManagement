package com.pro.propertymanagepro.basic;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.pro.propertymanagepro.R;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {

    private int pre = 0;

    private ViewPager viewPager;

    private LinearLayout pointGroup;

    private TextView iamgeDesc;

    // 图片资源ID
    private final int[] imageIds = { R.drawable.background, R.drawable.sample1, R.drawable.sample2};

    // 图片标题集合
    private final String[] imageDescriptions = { "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱", "揭秘北京电影如何升级"};

    private ArrayList<ImageView> imageList;

    /**
     * 上一个页面的位置
     */
    protected int lastPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pointGroup = (LinearLayout) findViewById(R.id.point_group);
        iamgeDesc = (TextView) findViewById(R.id.image_desc);
        iamgeDesc.setText(imageDescriptions[0]);

        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {

            // 初始化图片资源
            ImageView image = new ImageView(this);
            image.setBackgroundResource(imageIds[i]);
            imageList.add(image);
            // 添加指示点
            ImageView point = new ImageView(this);
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

        viewPager.setAdapter(new MyPagerAdapter());
        pointGroup.getChildAt(0).setBackgroundResource(R.drawable.point_focused);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            /**
             * 页面切换后调用
             * position 新的页面位置
             */
            public void onPageSelected(int position) {

                position = position % imageList.size();

                // 设置文字描述内容
                iamgeDesc.setText(imageDescriptions[position]);
                // 改变指示点的状态
                // 把当前点enbale 为true
                pointGroup.getChildAt(position).setBackgroundResource(R.drawable.point_focused);
                // 把上一个点设为false
                pointGroup.getChildAt(lastPosition).setBackgroundResource(R.drawable.point_bg);
                lastPosition = position;

            }

            @Override
            /**
             * 页面正在滑动的时候，回调
             */
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            /**
             * 当页面状态发生变化的时候，回调
             */
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*
         * 自动循环： 1、定时器：Timer 2、开子线程 while true 循环 3、ColckManager 4、 用handler
         * 发送延时信息，实现循环
         */
        isRunning = true;
        // 设置图片的自动滑动
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    /**
     * 判断是否自动滚动
     */
    private boolean isRunning = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            // 让viewPager 滑动到下一页
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (isRunning) {
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        };
    };

    protected void onDestroy() {

        super.onDestroy();
        isRunning = false;
    };

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        /**
         * 获得页面的总数
         */
        public int getCount() {
            return Integer.MAX_VALUE; // 使得图片可以循环
        }
        /**
         * 获得相应位置上的view
         * container view的容器，其实就是viewpager自身
         * position 相应的位置
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 给 container 添加一个view
            try{
                container.addView(imageList.get(position % imageList.size()));
            }catch (Exception ex){
                ex.printStackTrace();
            }
            // 返回一个和该view相对的object
            return imageList.get(position % imageList.size());
        }

        @Override
        /**
         * 判断 view和object的对应关系
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
         * 销毁对应位置上的object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            // System.out.println("destroyItem ::" + position);

            container.removeView((View) object);
            object = null;
        }
    }
}
