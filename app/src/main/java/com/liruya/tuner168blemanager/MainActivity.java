package com.liruya.tuner168blemanager;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.liruya.common.BaseActivity;
import com.liruya.tuner168blemanager.ui.main.MainFragment;
import com.liruya.tuner168blemanager.ui.main.MainViewModel;

public class MainActivity extends BaseActivity {
    private final int REQUEST_BLUETOOTH_CODE = 1;
    private final int REQUEST_LOCATION_CODE = 2;

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.container, MainFragment.newInstance())
                                       .commitNow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().unbindService(MainActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BLUETOOTH_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "打开蓝牙失败,请在设置中手动打开蓝牙", Toast.LENGTH_SHORT)
                     .show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_CODE) {
            Log.e(TAG, "onRequestPermissionsResult: " + permissions[0] + "  " + grantResults[0] );
            if (Manifest.permission.ACCESS_COARSE_LOCATION.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                BleManager.getInstance().startScan();
            } else {
                Toast.makeText(MainActivity.this, "拒绝定位权限无法扫描蓝牙设备.", Toast.LENGTH_SHORT)
                     .show();
            }
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.main_activity;
    }

    @Override
    protected void getIntentData(@NonNull Intent intent) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        if (BleManager.getInstance().checkBleSupported(MainActivity.this)) {
            BleManager.getInstance().bindService(MainActivity.this);
            if (!BleManager.getInstance().isBluetoothEnabled()) {
                BleManager.getInstance().requestBluetoothEnable(MainActivity.this, REQUEST_BLUETOOTH_CODE);
            }
        }
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void initEvent() {

    }
}
