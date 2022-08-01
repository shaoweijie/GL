package com.hancher.common.utils.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.media.FaceDetector;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Base64;

import com.hancher.common.utils.java.UuidUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class ImageUtil {
    public static Bitmap file2Bitmap(String selectFilePath){
        return BitmapFactory.decodeFile(selectFilePath);
    }
    public static Bitmap getFaceBitmapByClip(Bitmap bitmap) {

        // 识别人脸数组定义
        FaceDetector.Face[] faces = new FaceDetector.Face[2];
        FaceDetector faceDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 2);

        // 人脸识别器开始识别，速度还是比较快的，放在主线程也没什么问题
        Bitmap bitmap3 = bitmap.copy(Bitmap.Config.RGB_565, true);
        int realFaces = faceDetector.findFaces(bitmap3, faces);
        if (realFaces == 0) {
            LogUtil.w("没有找到脸");
            return null;
        }
        LogUtil.d("找到", realFaces, "张脸");

        //画布
        FaceDetector.Face face = faces[0];
        PointF myMidPoint = new PointF();
        face.getMidPoint(myMidPoint);
        float distance = face.eyesDistance();   //得到人脸中心点和眼间距离参数，并对每个人脸进行画框

        int[] ltrb = new int[]{
                (int) (myMidPoint.x - 2 * distance),
                (int) (myMidPoint.y - 1.5 * distance),
                (int) (myMidPoint.x + 2 * distance),
                (int) (myMidPoint.y + 2.5 * distance),
        };

        int[] newLtrb = changeSize(bitmap3.getWidth(), bitmap3.getHeight(), ltrb, (int) (4 * distance));

        LogUtil.d("剪裁前:", bitmap3.getWidth(), "*", bitmap3.getHeight(), ",剪裁后:", Arrays.toString(newLtrb));
        try {
            Bitmap bitmap2 = Bitmap.createBitmap(bitmap3, newLtrb[0], newLtrb[1], newLtrb[2] - newLtrb[0], newLtrb[3] - newLtrb[1], null, false);
            return bitmap2;
        } catch (Exception e){
            LogUtil.e(e);
            return bitmap3;
        }

    }
    public static Bitmap[] getAllFaceBitmapByClip(Bitmap bitmap) {
        int maxFaceCount = 20;
        // 识别人脸数组定义
        FaceDetector.Face[] faces = new FaceDetector.Face[maxFaceCount];
        FaceDetector faceDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), maxFaceCount);

        // 人脸识别器开始识别，速度还是比较快的，放在主线程也没什么问题
        Bitmap bitmap3 = bitmap.copy(Bitmap.Config.RGB_565, true);
        int realFaces = faceDetector.findFaces(bitmap3, faces);
        if (realFaces == 0) {
            LogUtil.w("没有找到脸");
            return null;
        }
        LogUtil.d("找到", realFaces, "张脸");

        Bitmap[] faceBitmaps = new Bitmap[realFaces];
        for (int i = 0; i < realFaces; i++) {
            //画布
            FaceDetector.Face face = faces[i];
            PointF myMidPoint = new PointF();
            face.getMidPoint(myMidPoint);
            float distance = face.eyesDistance();   //得到人脸中心点和眼间距离参数，并对每个人脸进行画框

            int[] ltrb = new int[]{
                    (int) (myMidPoint.x - 2 * distance),
                    (int) (myMidPoint.y - 1.5 * distance),
                    (int) (myMidPoint.x + 2 * distance),
                    (int) (myMidPoint.y + 2.5 * distance),
            };

            int[] newLtrb = changeSize(bitmap3.getWidth(), bitmap3.getHeight(), ltrb, (int) (4 * distance));

            LogUtil.d("剪裁前:", bitmap3.getWidth(), "*", bitmap3.getHeight(), ",剪裁后:", Arrays.toString(newLtrb));
            try {
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap3, newLtrb[0], newLtrb[1], newLtrb[2] - newLtrb[0], newLtrb[3] - newLtrb[1], null, false);
                faceBitmaps[i] = bitmap2;
            } catch (Exception e){
                LogUtil.e(e);
            }
        }
        return faceBitmaps;
    }
    /**
     * 先移动脸部框，如果脸部框大于图像，则缩小
     * @param width
     * @param height
     * @param ltrb
     * @param sequence
     * @return
     */
    private static int[] changeSize(int width, int height, int[] ltrb, int sequence) {
        if (ltrb[0]>=0 && ltrb[1]>=0 && ltrb[2]<=width && ltrb[3]<=height){
            return ltrb;
        }
        // 可平移的，移动
        if (ltrb[0]<0 && sequence <= width){
            ltrb[2] = sequence;
            ltrb[0] = 0;
        }
        if (ltrb[1]<0 && sequence <= height){
            ltrb[3] = sequence;
            ltrb[0] = 0;
        }
        if (ltrb[2]>width && sequence <= width){
            ltrb[0] = width - sequence;
            ltrb[2] = width;
        }
        if (ltrb[3]>height && sequence <= height){
            ltrb[1] = height - sequence;
            ltrb[3] = height;
        }

        // 不可平移的，缩小
        if (ltrb[0] < 0){
            ltrb[0] = 0;
        }
        if (ltrb[1] < 0){
            ltrb[1] = 0;
        }
        if (ltrb[2] > width){
            ltrb[2] = width;
        }
        if (ltrb[3] > height){
            ltrb[3] = height;
        }

        return ltrb;
    }

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 保存bitmap到本地
     *
     * @param mBitmap
     * @return
     */
    public static String saveBitmapPng(Bitmap mBitmap, String saveDir) {
        File filePic;
        try {
            filePic = new File(saveDir, UuidUtil.getUuid() + ".png");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }
    /**
     * 保存bitmap到本地
     *
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Bitmap mBitmap, String saveDir) {
        File filePic;
        try {
            filePic = new File(saveDir, UuidUtil.getUuid() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }


    /**
     * 字符串转图片
     *
     * @param imgStr   --->图片字符串
     * @param filename --->图片文件名
     * @return
     */
    public static boolean generateImage(String imgStr, String filename) {

        if (imgStr == null) {
            return false;
        }
//        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
//            byte[] b = decoder.decodeBuffer(imgStr);
            byte[] b = Base64.decode(imgStr, Base64.DEFAULT);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream("D:/Systems/" + filename);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 图片转字符串
     *
     * @param filePath --->文件路径
     * @return
     */
    public static String getImageStr(String filePath) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(filePath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
//        BASE64Encoder encoder = new BASE64Encoder();
//        return encoder.encode(data);
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    /**
     * 　　* 将bitmap转换成base64字符串
     * 　　*
     * 　　* @param bitmap
     * 　　* @return base64 字符串
     */
    public static String bitmap2String(Bitmap bitmap) {
        try {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byte[] bytes = bStream.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            LogUtil.w("change err:", e);
            return null;
        }
    }

    /**
     * 　　* 将base64转换成bitmap图片
     * 　　*
     * 　　* @param string base64字符串
     * 　　* @return bitmap
     */
    public static Bitmap string2Bitmap(String string) {
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            LogUtil.w("change err:", e);
            return null;
        }
    }

    public static Bitmap changeBitmapSize(Bitmap bm, int width2){

// 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) width2) / width;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public static void notifyImage(Context context,String imagePath) {
        MediaScannerConnection.scanFile(context,
                new String[] { imagePath }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        //....
                    }
                });

    }
}
