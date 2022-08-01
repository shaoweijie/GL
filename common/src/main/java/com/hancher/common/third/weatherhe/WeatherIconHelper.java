package com.hancher.common.third.weatherhe;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Hancher
 * 时间：2020/9/27 0027 下午 1:47
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：用于获取assets中的天气图片
 */
public class WeatherIconHelper {
    private static WeatherIconHelper instance = new WeatherIconHelper();
    private static Context mContext;

    private WeatherIconHelper() {}
    public static WeatherIconHelper getInstance(Context context){
        mContext = context;
        return instance;
    }

    public static final String ROOT_PATH = "weather-icon-S1/color-64/";
    private List<String> pathList;
    private String defaultPath;

    /**
     * 根据图片名字获取Bitmap图片
     * @param imageName
     * @return
     */
    public Bitmap getAssetPic(String imageName){
        if (pathList==null || pathList.size() < 1){
            pathList = getAssetPicPath();
            for (String item : pathList) {
                if (item.contains("999")){
                    defaultPath = item;
                    break;
                }
            }
        }
        String path = null;
        for (String item : pathList) {
            if (item.contains(imageName)){
                path = ROOT_PATH + item;
                break;
            }
        }
        if (path == null){
            path = ROOT_PATH + defaultPath;
        }
        Bitmap bitmap = getAssetsBitmap(path);
//        HancherLogUtil.v("assert中获取到图片：",bitmap);
        return bitmap;
    }
    private static List<String> getAssetPicPath(){
        AssetManager am = mContext.getAssets();
        String[] path = null;
        try {
            path = am.list(ROOT_PATH);  // ""获取所有,填入目录获取该目录下所有资源
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> pciPaths = new ArrayList<>();
        for(int i = 0; i < path.length; i++){
            if (path[i].endsWith(".png")){  // 根据图片特征找出图片
                pciPaths.add(path[i]);
            }
        }
        return pciPaths;
    }

    /** 根据路径获取Bitmap图片
     * @param path
     * @return
     */
    private static Bitmap getAssetsBitmap(String path){
        AssetManager am = mContext.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = am.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

}
