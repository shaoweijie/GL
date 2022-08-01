package com.hancher.common.third.mapbaidu;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.text.TextUtils;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.hancher.common.utils.android.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/15 15:09 <br/>
 * 描述 : 自动获取位置
 * 1. 注意以下属性需要在AndroidManifest中定义
 * <meta-data
 * android:name="com.baidu.lbsapi.API_KEY"
 * android:value="mCGEzxRuOxEVmW2PzHsfPjaUBK2AjKUB" /><br/>
 * 2. 注意 debug 和 user 的 value同<br/>
 * 3. 注意 权限
 * ACCESS_COARSE_LOCATION
 * ACCESS_FINE_LOCATION
 * 4. 注意 百度是绑定包名的
 */
public class BdMapManager {
    private static volatile BdMapManager instance;

    private BdMapManager() {
    }

    public static BdMapManager getInstance() {
        if (instance == null) {
            synchronized (BdMapManager.class) {
                if (instance == null) {
                    instance = new BdMapManager();
                }
            }
        }
        return instance;
    }

    private List<LocationChangeListener> listeners = new ArrayList<>();
    private LocationClient mLocationClient;
    private LocationClientOption.LocationMode locationMode;
    private Context mContext;
    private START_TYPE startType;
    private BDAbstractLocationListener locationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String address = bdLocation.getAddrStr();    //获取详细地址信息
            List<Poi> poiList = bdLocation.getPoiList(); //获取周边POI信息
            if (startType == BdMapManager.START_TYPE.ONCE) {
                onLocationChange(bdLocation);
                stop(null);
            } else if (startType == BdMapManager.START_TYPE.CYCLE) {
                onLocationChange(bdLocation);
            } else if (startType == BdMapManager.START_TYPE.ADDRESS) {
                if (!TextUtils.isEmpty(address) || poiList.size() > 0) {
                    onLocationChange(bdLocation);
                    stop(null);
                }
            }
        }
    };

    private void onLocationChange(BDLocation bdLocation) {
        for(LocationChangeListener listener: listeners){
            listener.onLocationChange(bdLocation);
        }
    }

    public enum START_TYPE {
        ONCE, //定位一次
        CYCLE, //循环定位
        ADDRESS, //直到定位到位置信息
    }


    public void stop(LocationListener rmListener) {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        if (rmListener != null && listeners != null && listeners.size() > 0 && listeners.contains(rmListener)) {
            listeners.remove(rmListener);
        }
    }

    public void start(Context context, BdMapManager.START_TYPE type, LocationChangeListener listener) {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(context);
            mLocationClient.registerLocationListener(locationListener);
        }
        listeners.add(listener);
        mContext = context;
        startType = type;
        mLocationClient.setLocOption(getLocationOption());
        if (mLocationClient.isStarted()) {
            mLocationClient.restart();
        } else {
            mLocationClient.start();
        }
    }

    private LocationClientOption getLocationOption() {
        LocationClientOption option = new LocationClientOption();
        LocationClientOption.LocationMode mode = getMode();

        /*
          可选，设置定位模式，
          默认高精度，Hight_Accuracy：高精度
          LocationMode. Battery_Saving：低功耗；
          LocationMode. Device_Sensors：仅使用设备；
         */
        option.setLocationMode(mode);
        /*
          可选，设置是否,当GPS有效时按照1S/1次频率输出GPS结果，默认false
         */
        option.setLocationNotify(mode == LocationClientOption.LocationMode.Hight_Accuracy);
        /*
          可选，设置是否,当GPS有效时按照1S/1次频率输出GPS结果，默认false
         */
        option.setOpenGps(mode == LocationClientOption.LocationMode.Hight_Accuracy);
        /*
          可选，设置返回经纬度坐标类型，
          默认GCJ02。GCJ02：国测局坐标；
          BD09ll：百度经纬度坐标；
          BD09：百度墨卡托坐标；
          海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
         */
        option.setCoorType("GCJ02");
        /*
          可选，定位SDK内部是一个service，并放到了独立进程。
          设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
         */
        option.setIgnoreKillProcess(false);
        /*
          可选，设置是否收集Crash信息，
          默认收集，即参数为false
         */
        option.SetIgnoreCacheException(false);
        /*
          可选，V7.2版本新增能力。
          如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi 是否超出有效期，
          若超出有效期，会先重新扫描Wi-Fi，然后定位
         */
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        /*
          可选，设置是否,需要过滤GPS仿真结果，
          默认需要，即参数为false
         */
        option.setEnableSimulateGps(false);
        /*
          可选，是否需要地址信息，
          默认为不需要，即参数为false，
          如果开发者需要获得当前点的地址信息，此处必须为true
         */
        option.setIsNeedAddress(true);
        /*
          可选，是否需要周边POI信息，
          默认为不需要，即参数为false，
          如果开发者需要获得周边POI信息，此处必须为true
         */
        option.setIsNeedLocationPoiList(true);
        if (startType == BdMapManager.START_TYPE.ONCE) {
            /*
              可选，设置发起定位请求的间隔，
              int类型，单位ms。
              如果设置为0，则代表单次定位，即仅定位一次，默认为0。
              如果设置非0，需设置1000ms以上才有效
             */
            option.setScanSpan(0);
        } else if (startType == BdMapManager.START_TYPE.CYCLE || startType == BdMapManager.START_TYPE.ADDRESS) {
            option.setScanSpan(1000);
        }
        return option;
    }

    private LocationClientOption.LocationMode getMode() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        List<String> provider = locationManager.getAllProviders();
        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            && provider.contains(LocationManager.GPS_PROVIDER);
        boolean isNetEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                && provider.contains(LocationManager.NETWORK_PROVIDER);
        if (isGPSEnable) {
            locationMode = LocationClientOption.LocationMode.Hight_Accuracy;
        } else if (isNetEnable) {
            locationMode = LocationClientOption.LocationMode.Battery_Saving;
        } else {
            locationMode = LocationClientOption.LocationMode.Device_Sensors;
        }
        LogUtil.d("locationMode"+locationMode);
        return locationMode;
    }
}
