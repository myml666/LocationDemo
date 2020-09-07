package com.itfitness.locationdemo;


import android.Manifest;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.itfitness.locationdemo.utils.LocationUtils;

public class MainActivity extends AppCompatActivity {
    //申请的权限
    private static final String[] mPermissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            ,Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
    }
    /**
     * 请求权限
     */
    private void requestPermissions() {
        if (PermissionsUtil.hasPermission(this,mPermissions)) {
            //有访问权限
            initLocation();
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    //用户授予了访问权限
                    initLocation();
                }
                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    //用户拒绝了访问的申请
                }
            }, mPermissions);
        }
    }

    /**
     * 加载位置
     */
    private void initLocation() {
        LocationUtils.getInstance(this).setAddressCallback(new LocationUtils.AddressCallback() {
            @Override
            public void onGetAddress(Address address) {
                String countryName = address.getCountryName();//国家
                String adminArea = address.getAdminArea();//省
                String locality = address.getLocality();//市
                String subLocality = address.getSubLocality();//区
                String featureName = address.getFeatureName();//街道
                LogUtils.eTag("定位地址",countryName,adminArea,locality,subLocality,featureName);
            }

            @Override
            public void onGetLocation(double lat, double lng) {
                LogUtils.eTag("定位经纬度",lat,lng);
            }
        });
    }
}
