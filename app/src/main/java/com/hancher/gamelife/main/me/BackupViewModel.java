package com.hancher.gamelife.main.me;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hancher.common.third.ZipUtil;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.PathUtil;
import com.hancher.common.utils.endurance.DBUtil;
import com.hancher.common.utils.endurance.IOUtil;
import com.hancher.gamelife.MainApplication;

import net.lingala.zip4j.exception.ZipException;

import org.greenrobot.greendao.AbstractDao;

import java.io.File;

import io.reactivex.ObservableOnSubscribe;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/28 0028 23:46 <br/>
 * 描述 :
 */
public class BackupViewModel extends ViewModel {

    String BACKUP_ZIP_PSD = "Hancher";

    MutableLiveData<String> backupInfo = new MutableLiveData<>();
    MutableLiveData<String> backupZipPath = new MutableLiveData<>();

    public MutableLiveData<String> getBackupZipPath() {
        return backupZipPath;
    }

    public MutableLiveData<String> getBackupInfo() {
        return backupInfo;
    }

    public void startBackup() {

        AsyncUtils.run((ObservableOnSubscribe<String>) emitter -> {

            //删除压缩文件
            File zipfile = new File(PathUtil.sdHancherBackupZip);
            if (zipfile.exists()) {
                emitter.onNext(String.format("删除备份文件夹:%b", IOUtil.deleteFile(zipfile)));
            }

            //删除backup文件夹
            File backupFile = new File(PathUtil.externalAppBackupDir);
            if (backupFile.exists()) {
                emitter.onNext(String.format("删除备份文件夹:%b", IOUtil.deleteFloder(backupFile)));
            }
            if (!backupFile.exists()) {
                emitter.onNext(String.format("创建备份文件夹:%b", backupFile.mkdirs()));
            }

            //复制
            emitter.onNext("\n开始复制...");
            String src = PathUtil.innerDbDir;
            String des = PathUtil.externalAppBackupDbDir;
            emitter.onNext(String.format("{\n%s\n-->\n%s\n}   复制结果:%b\n", src, des, IOUtil.copyFolder(src, des)));

            //压缩成zip
            emitter.onNext("开始压缩...");
            src = PathUtil.externalAppBackupDir + File.separator;
            des = PathUtil.sdDownloadHancherZip;
            boolean result = ZipUtil.zip(src, des, BACKUP_ZIP_PSD);
            emitter.onNext(String.format("{\n%s\n-->\n%s\n}   压缩结果:%b\n", src, des, result));
            if (!result) { emitter.onComplete(); return;}

            result = new File(PathUtil.sdDownloadHancherZip).exists();
            emitter.onNext(String.format("压缩文件存在:%b", result));
            if (!result) { emitter.onComplete(); return;}

            if (backupFile.exists()) {
                emitter.onNext(String.format("删除备份文件夹:%b", IOUtil.deleteFloder(backupFile)));
            }

            //分享到QQ
            emitter.onNext("开始分享...");
            emitter.onComplete();
        }, s -> {
            backupInfo.setValue(s);
            if ("开始分享...".equals(s)) {
                backupZipPath.setValue(PathUtil.sdDownloadHancherZip);
            }
        });
    }

    public void startRecover(String selectFilePath) {
        AsyncUtils.run((ObservableOnSubscribe<String>) emitter -> {
            boolean result;
            //递归删除已存在的恢复目录
            emitter.onNext("开始删除缓存目录...");
            result = IOUtil.deleteFloder(new File(PathUtil.externalAppBackupDir));
            emitter.onNext("删除缓存目录" + (result ? "成功" : "失败") + "！\n");
            if (!result) return;

            //解压压缩文件
            emitter.onNext("开始解压缩文件：" + selectFilePath + "...");
            try {
                ZipUtil.unZip(selectFilePath, PathUtil.externalAppFileDir, BACKUP_ZIP_PSD);
                emitter.onNext("解压缩成功\n");
            } catch (ZipException zipException) {
                emitter.onNext("解压缩失败\n");
                return;
            }

            emitter.onNext("开始恢复数据库：" + PathUtil.innerDB + "...");
            result = DBUtil.restoreDb(PathUtil.innerDB, PathUtil.externalAppBackupDb);
            emitter.onNext("恢复数据库" + (result ? "成功" : "失败") + "！\n");
//            if (!result) return;

            MainApplication.getInstance().getDaoSession().clear();
            for (AbstractDao<?, ?> item : MainApplication.getInstance().getDaoSession().getAllDaos()) {
                item.detachAll();
            }
        }, s -> backupInfo.setValue(s));
    }
}
