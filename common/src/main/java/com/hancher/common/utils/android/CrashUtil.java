package com.hancher.common.utils.android;

import android.os.Handler;
import android.os.Looper;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/10/9 0009 23:30 <br/>
 * 描述 : 异常拦截工具
 */
public class CrashUtil {
    private CrashHandler mCrashHandler;

    private static CrashUtil mInstance;

    private CrashUtil(){

    }

    private static CrashUtil getInstance(){
        if(mInstance == null){
            synchronized (CrashUtil.class){
                if(mInstance == null){
                    mInstance = new CrashUtil();
                }
            }
        }

        return mInstance;
    }

    public static void init(CrashHandler crashHandler){
        getInstance().setCrashHandler(crashHandler);
    }

    private void setCrashHandler(CrashHandler crashHandler){

        mCrashHandler = crashHandler;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        if (mCrashHandler != null) {//捕获异常处理
                            mCrashHandler.uncaughtException(Looper.getMainLooper().getThread(), e);
                        }
                    }
                }
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if(mCrashHandler!=null){//捕获异常处理
                    mCrashHandler.uncaughtException(t,e);
                }
            }
        });

    }

    public interface CrashHandler{
        void uncaughtException(Thread t,Throwable e);
    }
}
