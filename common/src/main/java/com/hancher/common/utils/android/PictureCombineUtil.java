package com.hancher.common.utils.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2022/4/9 0009 19:04 <br/>
 * 描述 : 图片拼接工具
 */
public class PictureCombineUtil {

    /**
     * 拼接图片
     * @param pics 所有的图片
     * @param widthCount 宽上的个数
     * @param outFile 输出路径
     * @throws FileNotFoundException 异常
     */
    public static void combinePicture(List<File> pics, int widthCount, File outFile) throws FileNotFoundException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pics.get(0).getAbsolutePath(), options);
        int itemHeight = options.outHeight; //单个高度
        int itemWidth = options.outWidth; //单个宽度
        int heightCount = pics.size()/widthCount; // 高的个数
        LogUtil.d("总个数 %d, 个数 %d x %d, 单个 %d x %d", pics.size(), widthCount, heightCount, itemWidth, itemHeight);

        int[][] positions = new int[pics.size()][4];// 所有图片的位置，左上x,y,右下角x,y
        int heightCurrent = 0; // 当前item左上角x
        int widthCurrent = 0;// 当前item左上角y
        for (int i = 0; i < heightCount; i++) {// 第i行
            widthCurrent = 0;
            //计算位置
            for (int j = 0; j < widthCount; j++) { //第j列
                LogUtil.d("当前位置 %d x %d", i, j);
                BitmapFactory.decodeFile(pics.get(i * widthCount + j).getAbsolutePath(), options);
                positions[i * widthCount + j] = new int[]{
                        widthCurrent,
                        heightCurrent,
                        itemWidth + widthCurrent,
                        itemHeight + heightCurrent
                };
                widthCurrent += itemWidth;
            }
            heightCurrent += itemHeight;
        }
        //创建画布、画笔
        Bitmap backgroud = Bitmap.createBitmap(itemWidth*widthCount, itemHeight*heightCount, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(backgroud);
        Paint paint = new Paint();
        //绘制每一个图片
        for (int i = 0; i < pics.size(); i++) {
            Bitmap item = BitmapFactory.decodeFile(pics.get(i).getAbsolutePath());
            item = Bitmap.createBitmap(item, 0, 0, itemWidth, itemHeight);
            canvas.drawBitmap(item, positions[i][0], positions[i][1], paint);
        }
        //输出到文件
        FileOutputStream outputStream = new FileOutputStream(outFile);
        backgroud.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
    }

    public static void combinePicture2(List<File> pics, int widthCount, File outFile) throws FileNotFoundException {
        int[][] positions = new int[widthCount][4];
        int heightCurrent = 0;
        for (int i = 0; i <= pics.size() / widthCount; i++) {// 几行
            //计算单行高度
            int sum = 0; // 忘记干什么了？
            for (int j = 0; j < widthCount && i * widthCount + j < pics.size(); j++) {// 每行
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(pics.get(i * widthCount + j).getAbsolutePath(), options);
                sum += options.outWidth / options.outHeight;
            }
            int heightCurrentRow = widthCount / sum; // 当前行高度

            int widthCurrentItem = 0;
            //计算位置
            for (int j = 0; j < widthCount && i * widthCount + j < pics.size(); j++) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(pics.get(i * widthCount + j).getAbsolutePath(), options);
                positions[i * widthCount + j] = new int[]{
                        widthCurrentItem,
                        heightCurrent,
                        options.outWidth * heightCurrentRow / options.outHeight,
                        heightCurrentRow
                };
                widthCurrentItem += options.outWidth * heightCurrentRow / options.outHeight;
            }
            heightCurrent += heightCurrentRow;
        }
        Bitmap backgroud = Bitmap.createBitmap(widthCount, heightCurrent, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(backgroud);
        Paint paint = new Paint();
        for (int i = 0; i < pics.size(); i++) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap item = BitmapFactory.decodeFile(pics.get(i).getAbsolutePath(), options);
            Matrix matrix = new Matrix();
            int scale = positions[i][2] / options.outWidth;
            matrix.postScale(scale, scale);
//            matrix.setScale(scale, scale);
            item = Bitmap.createBitmap(item, 0, 0, options.outWidth, options.outHeight, matrix, false);
            canvas.drawBitmap(item, positions[i][0], positions[i][1], paint);
//            Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        }
        FileOutputStream outputStream = new FileOutputStream(outFile);
        backgroud.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
    }
}
