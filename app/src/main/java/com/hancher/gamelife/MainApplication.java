package com.hancher.gamelife;

import com.hancher.common.CommonConfig;
import com.hancher.common.base.mvvm02.BaseApplication;
import com.hancher.common.third.BuglyManager;
import com.hancher.common.third.weatherhe.HeWeatherUtil;
import com.hancher.common.utils.android.ApkInfoUtil;
import com.hancher.gamelife.bak.contract.AppConfig;
import com.hancher.gamelife.greendao.DaoMaster;
import com.hancher.gamelife.greendao.DaoSession;
//import com.hancher.common.third.LeakCanaryManager;// 发布

/**
 * 主进程
 */
public class MainApplication extends BaseApplication {

    public static MainApplication instance;
    public static MainApplication getInstance(){
        return instance;
    }

    @Override
    protected void init() {
        instance = this;
        //bugly 170ms
        BuglyManager.init(this, AppConfig.BUGLY_ID);
        if (!ApkInfoUtil.isApkInDebug(this)) {
            // LeakCanaryManager.init(this);// 发布
            HeWeatherUtil.init();
        }
        initGreenDao();
    }

//    @Override
//    public Class getLoginActivityClass() {
//        return LoginActivity.class;
//    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, CommonConfig.DB_NAME);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }
    private DaoSession daoSession;
    public DaoSession getDaoSession() {
        return daoSession;
    }
}
