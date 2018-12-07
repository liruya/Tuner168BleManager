package com.liruya.tuner168blemanager;

public interface BleScanListener {
    void onScanTimeout();

    void onDeviceScanned ( String mac, String name, int rssi, byte[] bytes );
}
