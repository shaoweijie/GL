package com.hancher.common.utils.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;

import com.hancher.common.base.mvvm02.BaseApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者：Hancher
 * 时间：2019/4/16.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <point>
 * 说明：
 */
public class PicUtil {

    /**
     * 剪裁srcImage中间正方形部分，并将边长缩小为lengthOfthumbSquare，并保存到thumbImage
     * @param srcImage 源文件
     * @param thumbImage 输出文件
     * @param lengthOfthumbSquare 输出文件边长
     */
    public static void makeThumbnail(String srcImage, String thumbImage, int lengthOfthumbSquare) {
        try {
            Bitmap thumb = ThumbnailUtils.extractThumbnail(
                    BitmapFactory.decodeFile(srcImage),lengthOfthumbSquare,lengthOfthumbSquare);
            if (thumb==null){
                LogUtil.e("thumb null");
                return;
            }
            File filePic = new File(thumbImage);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            thumb.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("生成缩略图失败："+e.toString());
            return;
        }
        LogUtil.i("生成缩略图成功："+thumbImage);
    }
    public static String saveBitmap2File(Bitmap bitmap) throws FileNotFoundException {
        return saveBitmap2File(bitmap,PathUtil.sdDownload);
    }
    public static String saveBitmap2File(Bitmap bitmap, String path) throws FileNotFoundException {
        File file = new File(path,System.currentTimeMillis()+".jpg");
        FileOutputStream outputStream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Uri uri = Uri.fromFile(file);
        BaseApplication.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        return file.getAbsolutePath();
    }
}
