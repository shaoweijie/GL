package com.hancher.common.utils.endurance;

import com.hancher.common.utils.android.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/29 0029 12:09 <br/>
 * 描述 : 复制文件
 */
public class IOUtil {
    /**
     * 复制单个文件
     *
     * @param oldPathName File 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
     * @param newPathName File 复制后路径+文件名 如：data/user/0/com.test/cache/abc.txt
     * @return <code>true</code> if and only if the file was copied;
     * <code>false</code> otherwise
     */
    public static boolean copyFile(File oldPathName, File newPathName) {
        try {
            if (!oldPathName.exists()) {
                LogUtil.e("oldFile不存在");
                return false;
            } else if (!oldPathName.isFile()) {
                LogUtil.e("oldPathName非文件");
                return false;
            } else if (!oldPathName.canRead()) {
                LogUtil.e("oldPathName无法读取");
                return false;
            }

            if (!newPathName.getParentFile().exists()) {
                if (!newPathName.getParentFile().mkdirs()) {
                    LogUtil.w("目录" + newPathName.getParentFile().getAbsolutePath() + "不存在，并且创建失败");
                    return false;
                }
            }
            FileInputStream fileInputStream = new FileInputStream(oldPathName);
            FileOutputStream fileOutputStream = new FileOutputStream(newPathName);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            LogUtil.e("单个文件复制错误：" + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制文件夹及其中的文件，未测试
     *
     * @param oldPath String 原文件夹路径 如：data/user/0/com.test/files
     * @param newPath String 复制后的路径 如：data/user/0/com.test/cache
     * @return <code>true</code> if and only if the directory and files were copied;
     * <code>false</code> otherwise
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        try {
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    LogUtil.e("创建文件夹失败");
                    return false;
                }
            }
            File oldFile = new File(oldPath);
            String[] files = oldFile.list();
            File temp;
            for (String file : files) {
                temp = new File(oldPath + File.separator + file);
                if (temp.isDirectory()) {
                    copyFolder(oldPath + File.separator + file, newPath + File.separator + file);
                } else {
                    copyFile(temp, new File(newPath + File.separator + temp.getName()));
                }
            }
        } catch (Exception e) {
            LogUtil.e("复制文件夹失败：" + e.toString());
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除单个文件
     *
     * @param file 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(File file) {
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                LogUtil.v("删除文件：" + file.getPath());
                return true;
            }
        }
        return false;
    }

    /**
     * 递归删除文件夹
     *
     * @param path
     * @return
     */
    public static boolean deleteFloder(File path) {
        try {
            if (path.isFile()) {
                deleteFile(path);
            } else if (path.isDirectory()) {
                String[] list = path.list();
                for (int i = 0; i < list.length; i++) {
                    deleteFloder(new File(path.getPath() + File.separator + list[i]));
                }
                path.delete();
                LogUtil.v("删除文件夹：" + path.getPath());
            }
        } catch (Exception e) {
            LogUtil.e("删除文件夹失败：" + e.toString());
            return false;
        }
        return true;
    }

    /**
     * 获取当前File类信息
     *
     * @param listDir
     */
    public static String getFileInfo(File listDir) {
        String[] files = listDir.list();
        StringBuffer log = new StringBuffer();
        log.append("path=").append(listDir.getAbsolutePath());
        log.append(", F/D=").append(listDir.isFile() ? "F" : listDir.isDirectory() ? "D" : "no");
        log.append(", files=").append(Arrays.toString(files));
        return log.toString();
    }

    public static void writeStringToFile(String s, String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(s.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readStringFromFile(String filePath) {
        StringBuffer str = new StringBuffer();
        File file = new File(filePath);
        try {
            FileReader fr = new FileReader(file);
            char[] buf = new char[6];
            int len;
            while ((len = fr.read(buf)) != -1) {
                str.append(new String(buf, 0, len));
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    /**
     * 根据文件夹或者文件父目录创建文件夹
     * @param folder 文件或者文件夹
     * @return 是否创建文件夹成功
     */
    public static boolean initFolder(String folder){
        File file = new File(folder);
        if (file.isDirectory()){
            if (!file.exists()){
                return file.mkdirs();
            }
        } else {
            file = file.getParentFile();
            if (!file.exists()){
                return file.mkdirs();
            }
        }
        return true;
    }
}
