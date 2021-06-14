package com.pro.propertymanagepro.basic;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CustomMapStyleCallBack;
import com.baidu.mapapi.map.MapCustomStyleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.pro.propertymanagepro.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.addActivity;
import static com.pro.propertymanagepro.util.ActivityCollectorUtil.removeActivity;

public class SurroundingsActivity extends AppCompatActivity implements View.OnClickListener{

    private MapView mMapView = null;

    private BaiduMap mBaiduMap;

    public LocationClient mLocationClient = null;

    public PoiSearch poiSearch = null;

    private MyLocationListener myListener = new MyLocationListener();

    public OnGetPoiSearchResultListener listener;

    private Button bt_search;
    private TextView tv_city;
    private TextView tv_key;

    // 用于设置个性化地图的样式文件
    private static final String CUSTOM_FILE_NAME_CX = "custom_map_config_CX.sty";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surroundings);
        addActivity(this);
        //申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        initView();

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        // 获取.sty文件路径
        String customStyleFilePath = "F:\\desktop\\PropertyManagePro\\app\\src\\main\\res\\sty\\081e6d594545b73aa0733c09a4509c45.sty";

        MapCustomStyleOptions mapCustomStyleOptions = new MapCustomStyleOptions();
        mapCustomStyleOptions.localCustomStylePath(customStyleFilePath); //本地离线样式文件路径，如果在线方式加载失败，会默认加载本地样式文件。
        mapCustomStyleOptions.customStyleId("0ae6ef62468fbe119d2b1b15778a15d8"); //在线样式文件对应的id。

        mMapView.setMapCustomStyle(mapCustomStyleOptions, new CustomMapStyleCallBack() {
            @Override
            public boolean onPreLoadLastCustomMapStyle(String customStylePath) {
                return false; //默认返回false，由SDK内部处理加载逻辑；返回true则SDK内部不会做任何处理，由开发者自行完成样式加载。
            }

            @Override
            public boolean onCustomMapStyleLoadSuccess(boolean hasUpdate, String customStylePath) {
                return false; //默认返回false，由SDK内部处理加载逻辑；返回true则SDK内部不会做任何处理，由开发者自行完成样式加载。
            }

            @Override
            public boolean onCustomMapStyleLoadFailed(int status, String Message, String customStylePath) {
                return false; //默认返回false，由SDK内部处理加载逻辑；返回true则SDK内部不会做任何处理，由开发者自行完成样式加载。
            }
        });

        //创建POI检索实例
        poiSearch = PoiSearch.newInstance();
        //创建POI检索监听器
        listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    return;
                }

                List<PoiInfo> poiInfos = poiResult.getAllPoi();
                if (null == poiInfos) {
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(SurroundingsActivity.this, SurroundingsSearchActivity.class);
                intent.putExtra("info", (Serializable)poiInfos);
                startActivity(intent);
            }
            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
            //废弃
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        };

        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定位初始化
        mLocationClient = new LocationClient(this);
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        //设置定位模式
        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
                true,
                BitmapDescriptorFactory.fromResource(R.drawable.locate3),
                0xAAFFFF88,
                0xAA00FF00);
        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
    }

    public void initView(){
        bt_search = findViewById(R.id.btn_search);
        tv_city = findViewById(R.id.city);
        tv_key = findViewById(R.id.poi);

        bt_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_search){
            //设置检索监听器
            poiSearch.setOnGetPoiSearchResultListener(listener);
            //设置PoiCitySearchOption，发起检索请求
            poiSearch.searchInCity(new PoiCitySearchOption()
                    .city(tv_city.getText().toString().trim()) //必填
                    .keyword(tv_key.getText().toString().trim()) //必填
                    .pageNum(0));

        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //释放检索实例
        poiSearch.destroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
        removeActivity(this);
    }
}
