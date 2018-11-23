package com.liruya.tuner168blemanager.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ble.api.DataUtil;
import com.liruya.tuner168blemanager.R;

import java.util.List;


public class BleDeviceAdapter extends RecyclerView.Adapter<BleDeviceAdapter.BleDeviceViewHolder> {

    private Context mContext;
    private List<BleDevice> mBleDevices;

    public BleDeviceAdapter(Context context, List<BleDevice> bleDevices) {
        mContext = context;
        mBleDevices = bleDevices;
    }

    @NonNull
    @Override
    public BleDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BleDeviceViewHolder holder = new BleDeviceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ble_device, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BleDeviceViewHolder holder, int position) {
        BleDevice device = mBleDevices.get(position);
        holder.tv_name.setText(device.getName());
        holder.tv_mac.setText(device.getAddress());
        holder.tv_data.setText(DataUtil.byteArrayToHex(device.getData()));
        holder.tv_rssi.setText("" + device.getRssi());
    }

    @Override
    public int getItemCount() {
        return mBleDevices == null ? 0 : mBleDevices.size();
    }

    class BleDeviceViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tv_name;
        private AppCompatTextView tv_mac;
        private AppCompatTextView tv_data;
        private AppCompatTextView tv_rssi;

        public BleDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.item_device_tv_name);
            tv_mac = itemView.findViewById(R.id.item_device_tv_mac);
            tv_data = itemView.findViewById(R.id.item_device_tv_data);
            tv_rssi = itemView.findViewById(R.id.item_device_tv_rssi);
        }
    }
}
