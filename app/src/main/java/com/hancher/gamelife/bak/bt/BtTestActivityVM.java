package com.hancher.gamelife.bak.bt;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.gamelife.databinding.BtTestActivityBinding;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.reactivex.ObservableOnSubscribe;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/23 10:04 <br/>
 * 描述 : 登录界面
 */
public class BtTestActivityVM extends VMBaseActivity<BtTestActivityBinding, BtTestViewModel> {
    private static final int REQUEST_ENABLE_BT = 123;
    private String hancherBtMac = "A8:9C:ED:B1:68:F7";
    //这条是蓝牙串口通用的UUID，不要更改
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothSocket btSocket = null;
    BluetoothDevice btDevice = null;
    private BluetoothAdapter mBluetoothAdapter;
    //定义广播接收
    private BroadcastReceiver mReceiver=new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND))
            {
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getBondState()==BluetoothDevice.BOND_BONDED)
                {    //显示已配对设备
                    binding.txtInfo.append(device.getName()+"==>"+device.getAddress()+"\n");
                }else if(device.getBondState()!=BluetoothDevice.BOND_BONDED)
                {
                    binding.txtInfo.append(device.getName()+"==>"+device.getAddress()+"\n");
                }
            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                binding.txtInfo.append("搜索完成\n");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBt();
        initClick();
    }

    private void initClick() {
        binding.btnSearch.setOnClickListener(v -> {
            binding.txtInfo.setText("开始收集蓝牙广播信息\n");
            mBluetoothAdapter.startDiscovery();
        });
        binding.btnClear.setOnClickListener(v -> {
            binding.txtInfo.setText("清空信息\n");
        });
        binding.btnOld.setOnClickListener(v -> {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                binding.txtInfo.setText("发现旧链接\n");
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    binding.txtInfo.append(device.getName()+"==>"+device.getAddress()+"\n");
                }
            }
        });
        binding.btnStop.setOnClickListener(v -> {
            try {
                btSocket.close();
            } catch (IOException e) {
                binding.txtInfo.append("无法关闭客户端:"+e+"\n");
            }
        });
        binding.btnConnect.setOnClickListener(v -> {
            binding.txtInfo.setText("开始连接\n");
            AsyncUtils.run((ObservableOnSubscribe<String>) emitter -> {

                try {
                    btDevice = mBluetoothAdapter.getRemoteDevice(hancherBtMac);
                } catch (Exception e) {
                    emitter.onNext("获取远程设备失败:"+e+"\n");
                }
//                try {
//                    btSocket = btDevice.createRfcommSocketToServiceRecord(MY_UUID);
//                } catch (IOException e) {
//                    emitter.onNext("创建客户端socket失败:"+e+"\n");
//                }

                try {
                    Method m = btDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
                    btSocket = (BluetoothSocket) m.invoke(btDevice, 1);
                } catch (Exception e) {
                    emitter.onNext("创建客户端socket失败:"+e+"\n");
                }

                mBluetoothAdapter.cancelDiscovery();
                try {
                    btSocket.connect();
                    emitter.onNext("蓝牙通道已建立\n");
                } catch (Exception e){
                    emitter.onNext("连接失败:"+e+"\n");
                    try {
                        btSocket.close();
                    } catch (IOException closeException) {
                        emitter.onNext("无法关闭客户端:"+closeException+"\n");
                    }
                }
                emitter.onComplete();
            }, s -> binding.txtInfo.append(s));

        });

    }

    private void initBt() {
        mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter==null){
            binding.txtInfo.append("不支持蓝牙\n");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            //若没打开则打开蓝牙
            binding.txtInfo.append("尝试打开蓝牙\n");
            boolean result = mBluetoothAdapter.enable();
            binding.txtInfo.append("打开蓝牙"+(result?"成功":"失败")+"\n");
            if (!result){
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        //注册设备被发现时的广播
        IntentFilter filter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver,filter);
        //注册一个搜索结束时的广播
        IntentFilter filter2=new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver,filter2);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public String[] getPermission() {
        List<String> permissionList = new ArrayList<>();
//        位置权限
        if (Build.VERSION.SDK_INT<=Build.VERSION_CODES.P){
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
//      位置后台权限
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            permissionList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }
//        蓝牙权限
        permissionList.add(Manifest.permission.BLUETOOTH);
        permissionList.add(Manifest.permission.BLUETOOTH_ADMIN);
        return permissionList.toArray(new String[permissionList.size()]);
    }
}