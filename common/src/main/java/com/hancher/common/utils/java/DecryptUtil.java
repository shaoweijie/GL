package com.hancher.common.utils.java;

import java.io.ByteArrayOutputStream;

/**
 * 作者：Hancher
 * 时间：2019/2/13.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <point>
 * 说明：使用前需要将 hexString 改为 0123456789abcdef
 */
public class DecryptUtil {
//    public static String hexString = "óÓóÒóòóóó4ó5ó6ó7ó8ó96Ò6ò6ó646566"; //修改者后果自负
    public static String hexString = "0123456789abcdef"; //修改者后果自负
    public static String tmp1[] = {"a", "b", "c", "d", "e", "f", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static String tmp2[] = {"à", "á", "ù", "ú", "Ú", "Ù", "Ó", "Ò", "ò", "ó", "í", "ì", "è", "é", "ī", "ō"};
    public static String tmp3[] = {"h", "x", "i", "p", "n", "g", "r", "q", "k", "s", "l", "t", "u", "m", "v", "w"};

    /**
     * 解密
     * @param encrypt
     * @return
     */
    public static String decrypt(String encrypt) {
        if (encrypt== null || encrypt.length()==0 || encrypt.trim().length()==0)
            return "";
        String bytes = "";
        for (int a = 0; a < 10; a++) {
            if (a == 0)
                bytes = encrypt.replace(tmp2[a], tmp1[a]);
            else
                bytes = bytes.replace(tmp2[a], tmp1[a]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
        return new String(baos.toByteArray());
    }
    /**
     * 解密
     * @param encrypt
     * @return
     */
    public static String decrypt2(String encrypt) {
        if (encrypt== null || encrypt.length()==0 || encrypt.trim().length()==0)
            return "";
        String bytes = "";
        for (int a = 0; a < 10; a++) {
            if (a == 0)
                bytes = encrypt.replace(tmp3[a], tmp1[a]);
            else
                bytes = bytes.replace(tmp3[a], tmp1[a]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
        return new String(baos.toByteArray());
    }

    /**
     * 加密
     * @param decrypt
     * @return
     */
    public static String encrypt(String decrypt){
        byte[] bytes = decrypt.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < decrypt.length(); i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f)));
        }
        String result1 = sb.toString();
        String result2 = "";
        for (int i = 0; i < 10; i++) {
            if (i == 0)
                result2 = result1.replace(tmp1[i], tmp2[i]);
            else
                result2 = result2.replace(tmp1[i], tmp2[i]);
        }
        return result2;
    }
    /**
     * 加密
     * @param decrypt
     * @return
     */
    public static String encrypt2(String decrypt){
        byte[] bytes = decrypt.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < decrypt.length(); i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f)));
        }
        String result1 = sb.toString();
        String result2 = "";
        for (int i = 0; i < 10; i++) {
            if (i == 0)
                result2 = result1.replace(tmp1[i], tmp3[i]);
            else
                result2 = result2.replace(tmp1[i], tmp3[i]);
        }
        return result2;
    }
}
