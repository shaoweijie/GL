package com.hancher.gamelife.bak.activity;

import android.Manifest;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvp01.BaseMvpActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.bak.contract.FaceTestContract;
import com.hancher.gamelife.bak.presenter.FaceTestPresenter;
import com.hancher.gamelife.databinding.FaceTestActivityBinding;

import java.io.IOException;
import java.io.InputStream;

public class FaceTestMvpActivity extends BaseMvpActivity<FaceTestActivityBinding, FaceTestPresenter>
        implements FaceTestContract.View{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap = getImageFromAssetsFile("lyf.jpg");

        if (bitmap == null) {
            return;
        }
        binding.imageTop.setImageBitmap(bitmap);
        Bitmap bitmap2 = getImageAndFaceRect(bitmap);
        binding.imageBottom.setImageBitmap(bitmap2);
    }
    
    private Bitmap getImageAndFaceRect(Bitmap bitmap){

        // 识别人脸数组定义
        FaceDetector.Face[] faces = new FaceDetector.Face[2];
        FaceDetector faceDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 2);

        // 人脸识别器开始识别，速度还是比较快的，放在主线程也没什么问题
        int realFaces = faceDetector.findFaces(bitmap, faces);

        //画布
        Bitmap bitmap2 = bitmap.copy(Bitmap.Config.RGB_565, true);
        Canvas canvas = new Canvas(bitmap2);
        Paint myPaint = new Paint();
        myPaint.setColor(Color.GREEN);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(3);          //设置位图上paint操作的参数

        float distance;
        for (int i = 0; i < realFaces; i++) {
            FaceDetector.Face face = faces[i];
            PointF myMidPoint = new PointF();
            face.getMidPoint(myMidPoint);
            distance = face.eyesDistance();   //得到人脸中心点和眼间距离参数，并对每个人脸进行画框
            int[] ltrb = new int[]{
                    (int) (myMidPoint.x - 2*distance),
                    (int) (myMidPoint.y - 2*distance),
                    (int) (myMidPoint.x + 2*distance),
                    (int) (myMidPoint.y + 2*distance),
            };
            if (ltrb[0]<0){
                ltrb[0] = 0;
                ltrb[2] = (int)(4*distance);
            } else if (ltrb[1]<0){
                ltrb[1] = 0;
                ltrb[3] = (int)(4*distance);
            } else if (ltrb[2] > bitmap2.getHeight()){
                ltrb[0] = (int)(bitmap2.getHeight() - 4*distance);
                ltrb[2] = bitmap2.getHeight();
            } else if (ltrb[3] > bitmap2.getWidth()){
                ltrb[1] = (int)(bitmap2.getWidth() - 4*distance);
                ltrb[3] = bitmap2.getWidth();
            }
            canvas.drawRect(            //矩形框的位置参数
                    ltrb[0],
                    ltrb[1],
                    ltrb[2],
                    ltrb[3],
                    myPaint);
        }
        return bitmap2;
    }

    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
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

    //todo 添加权限检查
    public String[] getPermission() {

        return new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
    }
}