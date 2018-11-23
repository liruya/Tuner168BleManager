package com.liruya.tuner168blemanager.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liruya.common.BaseFragment;
import com.liruya.tuner168blemanager.BleManager;
import com.liruya.tuner168blemanager.BleScanListener;
import com.liruya.tuner168blemanager.R;

import java.util.List;

public class MainFragment extends BaseFragment {

    private SwipeRefreshLayout main_swipe_refresh;
    private RecyclerView main_rv_show;

    private MainViewModel mViewModel;
    private BleDeviceAdapter mAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
        initData();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.main_fragment;
    }

    @Override
    protected void initView(View view) {
        main_swipe_refresh = view.findViewById(R.id.main_swipe_refresh);
        main_rv_show = view.findViewById(R.id.main_rv_show);

        main_rv_show.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        main_rv_show.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
    }

    @Override
    protected void initData() {
        final Observer<List<BleDevice>> observer = new Observer<List<BleDevice>>() {
            @Override
            public void onChanged(List<BleDevice> bleDevices) {
                mAdapter.notifyDataSetChanged();
            }
        };
        mViewModel = ViewModelProviders.of(this)
                                       .get(MainViewModel.class);
        mViewModel.observe(this, observer);

        BleManager.getInstance().addBleScanListener(new BleScanListener() {
            @Override
            public void onStartScan() {
                if (main_swipe_refresh.isRefreshing() == false) {
                    main_swipe_refresh.setRefreshing(true);
                }
            }

            @Override
            public void onStopScan() {
                main_swipe_refresh.setRefreshing(false);
            }

            @Override
            public void onDeviceScanned(String mac, String name, int rssi, byte[] bytes) {
                BleDevice device = mViewModel.getBleDevice(mac);
                if (device == null) {
                    mViewModel.addDevice(new BleDevice(mac, name, rssi, bytes));
                } else {
                    device.setName(name);
                    device.setData(bytes);
                    device.setRssi(rssi);
                }
                mViewModel.setValue(mViewModel.getModel());
            }
        });

        mAdapter = new BleDeviceAdapter(getContext(), mViewModel.getModel());
        main_rv_show.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        main_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getModel().clear();
                mViewModel.setValue(mViewModel.getModel());
                if (BleManager.getInstance().checkLocationPermission(getContext())) {
                    BleManager.getInstance().startScan();
                } else {
                    main_swipe_refresh.setRefreshing(false);
                    BleManager.getInstance().requestLocationPermission(getActivity(), 2);
                }
            }
        });
    }
}
