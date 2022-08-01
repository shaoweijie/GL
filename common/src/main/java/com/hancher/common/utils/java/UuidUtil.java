package com.hancher.common.utils.java;

import java.util.UUID;

/**
 * 作者：Hancher
 * 时间：2020/9/23 0023 下午 1:17
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：创建并获取uuid
 */
public class UuidUtil {
    public static String getUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
    public static String getUuidNoLine() {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return uuid;
    }
}
