package com.hancher.common.utils.endurance;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

/**
 * 作者：Hancher
 * 时间：2020/9/29 0029 上午 9:59
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：Parcel序列化反序列化工具
 * String str = object2String(new Student("haha", families));
 * Student stu = unmarshall(str, Student.CREATOR);
 */
public class ParcelableUtil {
    public static String object2String(Parcelable stu) {
        // 1.序列化
        Parcel p = Parcel.obtain();
        stu.writeToParcel(p, 0);
        byte[] bytes = p.marshall();
        p.recycle();

        // 2.编码
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        return str;
    }

    public static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // this is extremely important!
        return parcel;
    }

    public static <T> T unmarshall(String str, Parcelable.Creator<T> creator) {
        // 1.解码
        byte[] bytes = Base64.decode(str, Base64.DEFAULT);
        // 2.反序列化
        Parcel parcel = unmarshall(bytes);
        return creator.createFromParcel(parcel);
    }
}
