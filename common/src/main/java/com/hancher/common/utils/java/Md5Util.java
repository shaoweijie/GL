package com.hancher.common.utils.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

//    /**
//     * MD5加密工具类
//     * @author pibigstar
//     *
//     */
//    public static String getStrMd5(String dataStr) {
//        //盐，用于混交md5
//        //String slat = "&%5123***&&%%$$#@";
//        try {
//            //dataStr = dataStr + slat;
//            MessageDigest m = MessageDigest.getInstance("MD5");
//            m.update(dataStr.getBytes("UTF8"));
//            byte s[] = m.digest();
//            String result = "";
//            for (int i = 0; i < s.length; i++) {
//                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
//            }
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "";
//    }

    /**
     * 计算字符串md5
     *
     * @param string
     * @return
     */
    public static String getStrMd5(String string) {
        if (TextUtil.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 加盐Md5
     *
     * @param string
     * @param slat
     * @return
     */
    public static String getStrMd5OnSlat(String string, String slat) {
        if (TextUtil.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest((string + slat).getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 字符串多次加密
     *
     * @param string 字符串
     * @param times  总次数
     * @return 加密的字符串
     */
    public static String getStrMd5ByRepeated(String string, int times) {
        if (TextUtil.isEmpty(string)) {
            return "";
        }
        String md5 = getStrMd5(string);
        for (int i = 0; i < times - 1; i++) {
            md5 = getStrMd5(md5);
        }
        return md5;
    }


    /**
     * 计算文件md5
     *
     * @param file
     * @return
     */
    public static String getFileMd5(File file) {
        if (file == null || !file.isFile() || !file.exists()) {
            return "";
        }
        FileInputStream in = null;
        String result = "";
        byte buffer[] = new byte[8192];
        int len;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            byte[] bytes = md5.digest();

            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
