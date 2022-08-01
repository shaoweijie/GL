package com.hancher.common.utils.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.hancher.common.base.mvvm02.BaseApplication;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetConnectUtil {
    static class NetworkMessage{
        private boolean isNetworkConnected;
        private boolean isWifiConnected;
        private boolean isMobileConnected;
        private boolean isEthernetConnected;//以太网
        private boolean isVpnConnected;
        private boolean isBluetoothConnected;

        public NetworkMessage(boolean isNetworkConnected, boolean isWifiConnected, boolean isMobileConnected, boolean isEthernetConnected, boolean isWinmaxConnected, boolean isBluetoothConnected) {
            this.isNetworkConnected = isNetworkConnected;
            this.isWifiConnected = isWifiConnected;
            this.isMobileConnected = isMobileConnected;
            this.isEthernetConnected = isEthernetConnected;
            this.isVpnConnected = isWinmaxConnected;
            this.isBluetoothConnected = isBluetoothConnected;
        }

        public boolean isNetworkConnected() {
            return isNetworkConnected;
        }

        public boolean isWifiConnected() {
            return isWifiConnected;
        }

        public boolean isMobileConnected() {
            return isMobileConnected;
        }

        public boolean isEthernetConnected() {
            return isEthernetConnected;
        }

        public boolean isVpnConnected() {
            return isVpnConnected;
        }

        public boolean isBluetoothConnected() {
            return isBluetoothConnected;
        }
    }

    public static void startListener(){
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo mEthernetNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        NetworkInfo mVpnNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN);
        NetworkInfo mBluetoothNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
        NetworkMessage networkMessage = new NetworkMessage(mNetworkInfo != null && mNetworkInfo.isAvailable(),
                mWiFiNetworkInfo != null && mWiFiNetworkInfo.isConnected(),
                mMobileNetworkInfo != null && mMobileNetworkInfo.isConnected(),
                mEthernetNetworkInfo != null && mEthernetNetworkInfo.isConnected(),
                mVpnNetworkInfo != null && mVpnNetworkInfo.isConnected(),
                mBluetoothNetworkInfo != null && mBluetoothNetworkInfo.isConnected());
        try {
            EventBus.getDefault().post(mNetworkInfo);
            EventBus.getDefault().post(networkMessage);
        }catch (Exception e){
            LogUtil.d("EventBus异常");
        }
    }

    /**
     * 判断是否有网络连接 <br/>
     * android.permission.ACCESS_NETWORK_STATE
     *
     * @return
     * @Deprecated 'ConnectivityManager.getActiveNetworkInfo()' is deprecated
     */
    public boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断wifi是否可用
     * @return
     */
    public static boolean isWifiConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断移动网络是否可用
     * @return
     */
    public static boolean isMobileConnected() {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        return false;
    }

    /**
     * 获取网络连接信息
     * @return  ConnectivityManager 中的常量 <br/>
     * TYPE_MOBILE,  <br/>TYPE_WIFI,  <br/>TYPE_WIMAX,  <br/>TYPE_ETHERNET,  <br/>TYPE_BLUETOOTH
     */
    public static int getConnectedType() {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
            return mNetworkInfo.getType();
        }
        return -1;
    }

    /**
     * 通过ping 百度，判断是否能够连接
     * @author suncat
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     * @return
     */
    public static final boolean ping() {
        String result = null;
        String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
        boolean isReadResult = false; // 读取ping的内容，可以不加
        try {
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            if (isReadResult) {
                InputStream input = p.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(input));
                StringBuffer stringBuffer = new StringBuffer();
                String content = "";
                while ((content = in.readLine()) != null) {
                    stringBuffer.append(content);
                }
                LogUtil.v("------ping-----", "result content : " + stringBuffer.toString());
            }
            // ping的状态 //wifi不可用或未连接，返回2；WiFi需要认证，返回1；WiFi可用，返回0；
            return p.waitFor() == 0;
        } catch (Exception e) {
            LogUtil.e("----result---", "result = " + result);
        }
        return false;
    }
    /**
     * 废弃了？
     * apps should use the more versatile requestNetwork,
     * registerNetworkCallback or registerDefaultNetworkCallback functions instead for faster
     * and more detailed updates about the network changes they care about.
     * 既然不好使暂时不用了吧
     */
    public static class NetChangeReceiver extends BroadcastReceiver {

        public static boolean isWifiSwitchOpen = false;
        public static boolean isWifiConnected = false;
        public static boolean isMobileSwitchOpen = false;
        public static boolean isMobileConnected = false;
        public static int mobileConnectRang = 0; // 2G,3G,4G,5G

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiManager.RSSI_CHANGED_ACTION.equals(action)) { // WiFi信号质量
                // 没啥用
            } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) { // wifi网络连接
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                isMobileSwitchOpen = info.getState() == NetworkInfo.State.CONNECTED;
                LogUtil.i("网络开关状态发生变化："+info.getState());
            } else if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) { //WiFi开关
                int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_DISABLED);
                isWifiSwitchOpen = wifistate==WifiManager.WIFI_STATE_ENABLED;
                LogUtil.i("wifi开关状态发生变化(3:WIFI_STATE_ENABLED):"+wifistate);
            } else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) { // 网络连接状态
                isWifiConnected = NetConnectUtil.isWifiConnected();
                isMobileConnected = NetConnectUtil.isMobileConnected();
                LogUtil.i("网络连接发生变化, wifi：", isWifiConnected, ", 流量：", isMobileConnected);
            } else {
                LogUtil.d("action:" + action);
            }
        }

        public static NetChangeReceiver register(Context context){
            NetChangeReceiver netBroadcastReceiver = new NetChangeReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(netBroadcastReceiver, filter);
            return netBroadcastReceiver;
        }
    }
}
