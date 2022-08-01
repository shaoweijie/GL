package com.hancher.common.third.mapbaidu;

import com.baidu.location.BDLocation;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/19 0019 17:05 <br/>
 * 描述 : 判断当前应用是否拥有数据库，从而将位置保存进数据库
 */
public interface LocationChangeListener {
    void onLocationChange(BDLocation location);
}
