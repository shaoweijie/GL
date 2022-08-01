package com.hancher.common.third.mapbaidu;

import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;

import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/19 0019 12:43 <br/>
 * 描述 : 打印、转换的工具类
 */
public class BdMapUtil {
    /**
     * 若周围地址查询到，则返回周围地址；
     * <br/>
     * 若行政地址查询到，则返回行政地址；
     * <br/>
     * 其他返回null
     * @param bdLocation
     * @return
     */
    public static String getFormatAddress(BDLocation bdLocation){
        if (bdLocation == null){
            return null;
        }
        if (bdLocation.getPoiList().size()>0){
            return bdLocation.getPoiList().get(0).getAddr() +" " + bdLocation.getPoiList().get(0).getName();
        } else if (!TextUtils.isEmpty(bdLocation.getAddrStr())){
            return bdLocation.getAddrStr();
        }
        return null;
    }

    /**
     * 用于参考的
     * @param bdLocation
     */
    public static StringBuffer getDetailInfo(BDLocation bdLocation){
        int locType = bdLocation.getLocType(); //获取定位类型、定位错误返回码
        String coorType = bdLocation.getCoorType(); //获取经纬度坐标类型
        double latitude = bdLocation.getLatitude();    //获取纬度信息
        double longitude = bdLocation.getLongitude();    //获取经度信息
        float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
        String address = bdLocation.getAddrStr();    //获取详细地址信息
        List<Poi> poiList = bdLocation.getPoiList(); //获取周边POI信息
        String time = bdLocation.getTime();    //获取定位时间
        String country = bdLocation.getCountry();    //获取国家信息
        String countryCode = bdLocation.getCountryCode();    //获取国家码
        String city = bdLocation.getCity();    //获取城市信息
        String cityCode = bdLocation.getCityCode();    //获取城市码
        String district = bdLocation.getDistrict();    //获取区县信息
        String street = bdLocation.getStreet();    //获取街道信息
        String streetNumber = bdLocation.getStreetNumber();    //获取街道码
        String locationDescribe = bdLocation.getLocationDescribe();    //获取当前位置描述信息
        String locTypeDescription = bdLocation.getLocTypeDescription();

        StringBuffer stringBuffer = new StringBuffer("location:\n");
        stringBuffer.append("locType=").append(locType).append("\n");
        stringBuffer.append("coorType=").append(coorType).append("\n");
        stringBuffer.append("latitude=").append(latitude).append("\n");
        stringBuffer.append("longitude=").append(longitude).append("\n");
        stringBuffer.append("radius=").append(radius).append("\n");
        stringBuffer.append("address=").append(address).append("\n");
        stringBuffer.append("time=").append(time).append("\n");
        stringBuffer.append("country=").append(country).append("\n");
        stringBuffer.append("countryCode=").append(countryCode).append("\n");
        stringBuffer.append("city=").append(city).append("\n");
        stringBuffer.append("cityCode=").append(cityCode).append("\n");
        stringBuffer.append("district=").append(district).append("\n");
        stringBuffer.append("street=").append(street).append("\n");
        stringBuffer.append("streetNumber=").append(streetNumber).append("\n");
        stringBuffer.append("locationDescribe=").append(locationDescribe).append("\n");
        stringBuffer.append("locTypeDescription=").append(locTypeDescription).append("\n");

        if (poiList != null && poiList.size()>0) {
        stringBuffer.append("Pois:{\n");
        for (Poi poi : poiList) {
            stringBuffer.append("    poi.id=").append(poi.getId()).append("\n");
            stringBuffer.append("    poi.tags=").append(poi.getTags()).append("\n");
            stringBuffer.append("    poi.name=").append(poi.getName()).append("\n");
            stringBuffer.append("    poi.rank=").append(poi.getRank()).append("\n");
            stringBuffer.append("    poi.address=").append(poi.getAddr()).append("\n\n");
        }
        stringBuffer.append("}");
        }
        return stringBuffer;
    }

    public static String getErrorInfo(int code){
        switch (code){
            case 61:return "GPS定位结果 GPS定位成功";
            case 62:return "定位失败 无法获取有效定位依据，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位";
            case 63:return "网络异常 没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位";
            case 66:return "离线定位结果 通过requestOfflineLocaiton调用时对应的返回结果";
            case 67:return "离线定位失败";
            case 161:return "网络定位结果 网络定位成功";
            case 162:return "请求串密文解析失败 一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件";
            case 505:return "AK不存在或者非法 请按照说明文档重新申请AK";
            default: return "";
        }
    }

    /**
     * 判断返回码是否成功，如有以下则为成功：
     * <br/>
     * TypeGpsLocation/ TypeNetWorkLocation/ TypeOffLineLocation/ TypeCacheLocation
     * @param bdLocation
     * @return
     */
    public static boolean isBDLocationSuccess(BDLocation bdLocation){
        int locType = bdLocation.getLocType(); //获取定位类型、定位错误返回码
        if (locType == BDLocation.TypeGpsLocation
                || locType == BDLocation.TypeNetWorkLocation
                || locType == BDLocation.TypeOffLineLocation
                || locType == BDLocation.TypeCacheLocation){
            return true;
        }
        return false;
    }

    public static String getLocationTypeStr(BDLocation bdLocation){
        switch (bdLocation.getLocType()){
            case BDLocation.TypeGpsLocation:return "gps";
            case BDLocation.TypeNetWorkLocation:return "net";
            case BDLocation.TypeOffLineLocation:return "offline";
            case BDLocation.TypeCacheLocation:return "cache";
            default:return bdLocation.getLocTypeDescription();
        }
    }

    public static String getLongiLat(BDLocation bdLocation) {
        return bdLocation.getLongitude()+","+bdLocation.getLatitude();
    }
}
