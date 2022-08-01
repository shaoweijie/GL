package com.hancher.common.utils.java;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 作者：Hancher
 * 时间：2020/10/20 0020 下午 4:43
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：GB2312与UTF8字符串互转工具类
 */
public class Gb2312ToUtf8Util {

    public static String utf8ToGb2312(String str){
        StringBuffer sb = new StringBuffer();
        for ( int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '+' :
                    sb.append( ' ' );
                    break ;
                case '%' :
                    try {
                        sb.append(( char )Integer.parseInt (
                                str.substring(i+1,i+3),16));
                    }
                    catch (NumberFormatException e) {
                        throw new IllegalArgumentException();
                    }
                    i += 2;
                    break ;
                default :
                    sb.append(c);
                    break ;
            }
        }
        String result = sb.toString();
        String res= null ;
        try {
            byte [] inputBytes = result.getBytes( "8859_1" );
            res= new String(inputBytes, "UTF-8" );
        }
        catch (Exception e){}
        return res;
    }
    // 将 GB2312 编码格式的字符串转换为 UTF-8 格式的字符串：
    public static String gb2312ToUtf8(String str) {
        String urlEncode = "" ;
        try {
            urlEncode = URLEncoder.encode (str, "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlEncode;
    }
}
