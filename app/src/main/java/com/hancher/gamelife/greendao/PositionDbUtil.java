package com.hancher.gamelife.greendao;

import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.hancher.common.third.mapbaidu.BdMapUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.common.utils.java.UuidUtil;
import com.hancher.gamelife.MainApplication;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/20 0020 15:25 <br/>
 * 描述 : 位置快速存入数据库
 */
public class PositionDbUtil {

    private static Position getPositionByBdLocation(BDLocation location) {
        Position position = new Position();
        position.setCoorType(location.getCoorType());
        position.setLatitude(location.getLatitude());
        position.setLongitude(location.getLongitude());
        position.setRadius(location.getRadius());
        position.setAddress(location.getAddrStr());
        position.setAddressDescribe(location.getLocationDescribe());
        position.setUuid(UuidUtil.getUuidNoLine());
        if (location.getPoiList() != null && location.getPoiList().size() > 0) {
            position.setPoiName(location.getPoiList().get(0).getName());
            position.setPoiAddr(location.getPoiList().get(0).getAddr());
            position.setPoiTag(location.getPoiList().get(0).getTags());
        }
        try {
            Long time = DateUtil.changeDate(location.getTime(), DateUtil.Type.STRING_YMD_HMS, DateUtil.Type.LONG_STAMP);
            position.setTimeStamp(time);
        } catch (Exception exception) {
            LogUtil.w("change date err:", exception);
        }
        position.setLocateType(BdMapUtil.getLocationTypeStr(location));
        position.setTime(location.getTime());
        return position;
    }

    public static Position save2Db(BDLocation location) {
        Position position = getPositionByBdLocation(location);
        MainApplication.getInstance().getDaoSession().getPositionDao().insert(position);
        return position;
    }

    public static String getFormatAddress(Position position) {
        if (!TextUtil.isEmail(position.getPoiName())) {
            return position.getPoiAddr() + " " + position.getPoiName();
        } else if (!TextUtils.isEmpty(position.getAddress())) {
            return position.getAddress();
        }
        return null;
    }

    public static String getLongiLat(Position position) {
        return position.getLongitude() + "," + position.getLatitude();
    }

    public static String getLongiLat2(Position position) {
        return TextUtil.double2String(position.getLongitude(),2) + ","
                + TextUtil.double2String(position.getLatitude(),2);
    }
}
