package com.liruya.tuner168blemanager;

public interface BleScanListener {
    void onStartScan ();

    void onStopScan ();

    void onDeviceScanned ( String mac, String name, int rssi, byte[] bytes );
}
