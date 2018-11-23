package com.liruya.tuner168blemanager.ui.main;

import com.liruya.tuner168blemanager.BaseViewModel;
import com.liruya.tuner168blemanager.BleManager;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends BaseViewModel<List<BleDevice>> {
    private List<BleDevice> mDevices;

    public MainViewModel() {
        mDevices = new ArrayList<>();
    }

    public List<BleDevice> getModel() {
        return mDevices;
    }

    public boolean contains(String mac) {
        for (BleDevice device : mDevices) {
            if (device.getAddress()
                      .equals(mac)) {
                return true;
            }
        }
        return false;
    }

    public void addDevice(BleDevice device) {
        if (device == null) {
            return;
        }
        mDevices.add(device);
    }

    public BleDevice getBleDevice(String mac) {
        for (BleDevice device : mDevices) {
            if (device.getAddress()
                      .equals(mac)) {
                return device;
            }
        }
        return null;
    }

    public void startScan() {
        BleManager.getInstance().startScan();
    }

    public void stopScan() {
        BleManager.getInstance().stopScan();
    }
}
