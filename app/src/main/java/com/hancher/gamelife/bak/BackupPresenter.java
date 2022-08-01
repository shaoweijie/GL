//package com.hancher.gamelife.main.me.backup;
//
//import com.hancher.common.CommonConfig;
//import com.hancher.common.base.v01.BasePresenter;
//import com.hancher.common.utils.android.AsyncUtils;
//import com.hancher.common.utils.android.PathUtil;
//import com.hancher.common.utils.android.ToastUtil;
//import com.hancher.common.utils.endurance.DBUtil;
//import com.hancher.common.utils.endurance.IOUtil;
//import com.hancher.gamelife.MainApplication;
//import com.hancher.gamelife.bak.contract.BackupContract;
//import com.hancher.common.third.ZipUtil;
//
//import net.lingala.zip4j.exception.ZipException;
//
//import org.greenrobot.greendao.AbstractDao;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import io.reactivex.ObservableOnSubscribe;
//
//public class BackupPresenter extends BasePresenter<BackupActivity> implements BackupContract.Presenter {
//    public HashMap<String, String> copyMap = new HashMap<>();
//
//
//    @Override
//    public void onCreated(BackupActivity currentView) {
//        super.onCreated(currentView);
//        // /data/data/com.hancher.gamelife/databases/Hancher.db ->
//        // /storage/emulated/0/Android/data/com.hancher.gamelife/files/backup/db/Hancher.db
//        copyMap.put(PathUtil.innerDbDir, PathUtil.externalAppBackupDbDir);
//        // /storage/emulated/0/Android/data/com.hancher.gamelife/files/image
//        // /storage/emulated/0/Android/data/com.hancher.gamelife/files/backup
//        copyMap.put(PathUtil.externalAppFileImageDir, PathUtil.externalAppBackupDir);
//    }
//
//    @Override
//    public void startBackup() {
//        AsyncUtils.run((ObservableOnSubscribe<String>) emitter -> {
//            boolean result;
//
//            //删除压缩文件
//            File zipfile = new File(PathUtil.externalAppFileDir, "backup.zip");
//            if (zipfile.exists()){
//                result = IOUtil.deleteFile(zipfile);
//                emitter.onNext("删除已存在备份文件夹"+(result?"成功":"失败"));
//            }
//
//            //删除backup文件夹
//            result = true;
//            File backupFile = new File(PathUtil.externalAppBackupDir);
//            if (backupFile.exists()){
//                result = IOUtil.deleteFloder(backupFile);
//                emitter.onNext("删除已存在备份文件夹"+(result?"成功":"失败"));
//            }
//            if (!backupFile.exists()){
//                result = backupFile.mkdirs();
//                emitter.onNext("创建备份文件夹"+(result?"成功":"失败"));
//            }
//            if (!result) return;
//
//            //复制
//            emitter.onNext("开始复制...");
//            result = false;
//            for (Map.Entry<String, String> stringStringEntry : copyMap.entrySet()) {
//                String key = stringStringEntry.getKey();
//                String val = stringStringEntry.getValue();
//                result |= IOUtil.copyFolder(key,val);
//                emitter.onNext("复制"+new File(key).getName()+(result?"成功":"失败")+"！\n");
//            }
//            if (!result) return;
//
//            //压缩成zip
//            emitter.onNext("开始压缩...");
//            // /storage/emulated/0/Android/data/com.hancher.gamelife/files/backup ->
//            // /storage/emulated/0/Android/data/com.hancher.gamelife/files/backup.zip
//            try {
//                ZipUtil.zip(PathUtil.externalAppBackupDir + File.separator,
//                        PathUtil.externalAppFileDir + File.separator,
//                        CommonConfig.BACKUP_ZIP_PSD);
//                emitter.onNext("压缩成功\n");
//            } catch (ZipException zipException){
//                emitter.onNext("压缩失败\n");
//                return;
//            }
//
//            //分享到QQ
//            emitter.onNext("开始分享...");
//        }, s -> {
//            mView.appendLog(s);
//            if ("开始分享...".equals(s)){
//                File file = new File(PathUtil.externalAppFileDir, "backup.zip");
//                if (!file.exists()){
//                    ToastUtil.show("文件"+file.getAbsolutePath()+"不存在");
//                    return;
//                }
////                ShareUtil.shareFile(mView,file);
//                mView.shareFile(file);
//            }
//        });
//    }
//
//    @Override
//    public void startRecover(String selectFilePath) {
//        AsyncUtils.run((ObservableOnSubscribe<String>) emitter -> {
//            boolean result;
//            //递归删除已存在的恢复目录
//            emitter.onNext("开始删除缓存目录...");
//            result = IOUtil.deleteFloder(new File(PathUtil.externalAppBackupDir));
//            emitter.onNext("删除缓存目录"+(result?"成功":"失败")+"！\n");
//            if (!result) return;
//
//            //解压压缩文件
//            emitter.onNext("开始解压缩文件：" + selectFilePath + "...");
//            try {
//                ZipUtil.unZip(selectFilePath, PathUtil.externalAppFileDir, CommonConfig.BACKUP_ZIP_PSD);
//                emitter.onNext("解压缩成功\n");
//            } catch (ZipException zipException){
//                emitter.onNext("解压缩失败\n");
//                return;
//            }
//
//            emitter.onNext("开始恢复数据库："+PathUtil.innerDB+"...");
//            result = DBUtil.restoreDb(PathUtil.innerDB,PathUtil.externalAppBackupDb);
//            emitter.onNext("恢复数据库"+(result?"成功":"失败")+"！\n");
////            if (!result) return;
//
//            MainApplication.getInstance().getDaoSession().clear();
//            for (AbstractDao<?, ?> item : MainApplication.getInstance().getDaoSession().getAllDaos()) {
//                item.detachAll();
//            }
//        }, s -> {
//            mView.appendLog(s);
//        });
//    }
//}
