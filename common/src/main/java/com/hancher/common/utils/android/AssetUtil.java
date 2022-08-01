package com.hancher.common.utils.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetUtil {

    /**
     * 获取某路径下所有图片
     * @param context
     * @param path
     * @return
     */
    public static Map<String,Bitmap> getAllImageMap(Context context, String path) {
        Map<String,Bitmap> data = new HashMap<>();
        AssetManager am = context.getAssets();
        try {
            String[] files = am.list(path);
            for(String file: files){
                InputStream is = am.open(path+File.separator+file);
                Bitmap image = BitmapFactory.decodeStream(is);
                data.put(file,image);
            }
        } catch (IOException e) {
            LogUtil.e("读取asset io 失败：", e);
        }
        LogUtil.d("assets中读取到图片数量："+data.size());
        return data;
    }
    /**
     * 获取某路径下所有图片
     * @param context
     * @param path
     * @return
     */
    public static List<Bitmap> getAllImageList(Context context, String path) {
        List<Bitmap> data = new ArrayList();
        AssetManager am = context.getAssets();
        try {
            String[] files = am.list(path);
            for(String file: files){
                InputStream is = am.open(path+File.separator+file);
                Bitmap image = BitmapFactory.decodeStream(is);
                data.add(image);
            }
        } catch (IOException e) {
            LogUtil.e("读取asset io 失败：", e);
        }
        LogUtil.d("assets中读取到图片数量："+data.size());
        return data;
    }
    /**
     * 获取Assets中图片
     * @param context
     * @param fileName
     * @return
     */
    public static Bitmap getImage(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 获取Assets中图片，并将其转换为rgb565
     * @param context
     * @param fileName
     * @return
     */
    public static Bitmap getImage565(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getAssets();
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is,null,options);
            is.close();
        } catch (IOException e) {
            LogUtil.d(e);
        }
        return image;
    }

    /**
     * 获取json文件
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context,String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 获取ttf文件
     * @param context
     * @param fileName
     */
    public static void getTtf(Context context,String fileName){
        AssetManager assetManager = context.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, fileName);
    }
}
