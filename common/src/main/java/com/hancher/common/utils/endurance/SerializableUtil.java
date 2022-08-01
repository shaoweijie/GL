package com.hancher.common.utils.endurance;

import com.hancher.common.utils.android.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * 作者：Hancher
 * 时间：2020/9/29 0029 上午 10:04
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：Serializable序列化反序列化工具
 */
public class SerializableUtil {

    /**
     * 序列化对象
     *
     * @param person
     * @return
     * @throws IOException
     */
    public static String serialize(Serializable person) {
        try {
//        startTime = System.currentTimeMillis();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(person);
            String serStr = byteArrayOutputStream.toString("ISO-8859-1");
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
            objectOutputStream.close();
            byteArrayOutputStream.close();
//        Log.d("serial", "serialize str =" + serStr);
//        endTime = System.currentTimeMillis();
//        Log.d("serial", "序列化耗时为:" + (endTime - startTime));
            return serStr;
        } catch (Exception e){
            LogUtil.e(e);
        }
        return "";
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     */
    public static Object deSerialization(String str) {
        try {
//        startTime = System.currentTimeMillis();
            String redStr = java.net.URLDecoder.decode(str, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes(StandardCharsets.ISO_8859_1));
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
//        endTime = System.currentTimeMillis();
//        Log.d("serial", "反序列化耗时为:" + (endTime - startTime));
        return object;
        } catch (Exception e){
            LogUtil.e(e);
        }
        return "";
    }
}
