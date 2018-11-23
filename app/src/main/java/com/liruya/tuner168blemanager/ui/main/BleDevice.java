package com.liruya.tuner168blemanager.ui.main;

public class BleDevice {
    private String mAddress;
    private String mName;
    private int mRssi;
    private byte[] mData;

    public BleDevice(String address, String name, int rssi, byte[] data) {
        mAddress = address;
        mName = name;
        mRssi = rssi;
        mData = data;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getRssi() {
        return mRssi;
    }

    public void setRssi(int rssi) {
        mRssi = rssi;
    }

    public byte[] getData() {
        return mData;
    }

    public void setData(byte[] data) {
        mData = data;
    }
}
