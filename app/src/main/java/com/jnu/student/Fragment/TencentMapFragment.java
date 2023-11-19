package com.jnu.student.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.student.R;
import com.jnu.student.data.DataDownload;
import com.jnu.student.data.ShopLocation;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;


public class TencentMapFragment extends Fragment {
    private com.tencent.tencentmap.mapsdk.maps.MapView mapView = null;

    public TencentMapFragment() {
        // Required empty public constructor
    }

    public static TencentMapFragment newInstance(String param1, String param2) {
        TencentMapFragment fragment = new TencentMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tencent_map, container, false);
        mapView = rootView.findViewById(R.id.mapView);

        TencentMap tencentMap = mapView.getMap();

        LatLng point1 = new LatLng(22.255453, 113.54145);
        tencentMap.moveCamera(CameraUpdateFactory.newLatLng(point1));

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 在新线程中执行以下代码
                // 使用DataDownload类的实例下载JSON数据
                String responseData = new DataDownload().download("http://file.nidama.net/class/mobile_develop/data/bookstore.json");
                // 使用DataDownload类的方法解析下载的JSON数据并转换为ShopLocation对象的ArrayList
                ArrayList<ShopLocation> shopLocations = new DataDownload().parseJsonObjects(responseData);
                // 切换回主线程进行UI操作
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 获取Tencent地图实例
                        TencentMap tencentMap = mapView.getMap();
                        // 遍历商店位置的ArrayList，为每个位置创建标记并添加到地图上
                        for (ShopLocation shopLocation : shopLocations) {
                            // 获取商店的经纬度信息
                            LatLng point1 = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
                            // 创建标记选项，设置标记的位置和标题
                            MarkerOptions markerOptions = new MarkerOptions(point1)
                                    .title(shopLocation.getName());
                            // 在地图上添加标记
                            Marker marker = tencentMap.addMarker(markerOptions);
                        }
                    }
                });
            }
        }).start();


        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}