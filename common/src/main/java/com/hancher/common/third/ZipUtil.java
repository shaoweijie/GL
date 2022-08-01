package com.hancher.common.third;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/19 0019 9:58 <br/>
 * 描述 :
 */
public class ZipUtil {

    /**
     * 压缩
     *  @param srcFileStr 源目录
     * @param dest       要压缩的目录
     * @param passwd     密码 不是必填
     * @return
     */
    public static boolean zip(String srcFileStr, String dest, String passwd) {
        try {
            File srcfile = new File(srcFileStr);

            //创建目标文件
            String destname = buildDestFileName(srcfile, new File(dest));
            System.out.println(destname);

            ZipParameters par = new ZipParameters();
            /*
             * COMP_STORE = 0;（仅打包，不压缩）
             * COMP_DEFLATE = 8;（默认）
             * COMP_AES_ENC = 99; 加密压缩
             */
            par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            /*
             * DEFLATE_LEVEL_FASTEST = 1; (速度最快，压缩比最小)
             * DEFLATE_LEVEL_FAST = 3; (速度快，压缩比小)
             * DEFLATE_LEVEL_NORMAL = 5; (一般)
             * DEFLATE_LEVEL_MAXIMUM = 7;
             * DEFLATE_LEVEL_ULTRA = 9;
             */
            par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            if (passwd != null) {
                par.setEncryptFiles(true);
                /*
                 * ENC_METHOD_AES
                 *      parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                 * ENC_METHOD_STANDARD
                 */
                par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
                par.setPassword(passwd.toCharArray());
            }
            ZipFile zipfile = new ZipFile(destname);
            if (srcfile.isDirectory()) {
                zipfile.addFolder(srcfile, par);
            } else {
                zipfile.addFile(srcfile, par);
            }
        } catch (ZipException zipException){
            System.out.println("zip 压缩报错:"+zipException);
            return false;
        }
        return true;
    }

    /**
     * 压缩
     *  @param srcFileStr 源目录
     * @param dest       要压缩的目录
     * @param passwd     密码 不是必填
     * @return
     */
    public static boolean zipSplit(String srcFileStr, String dest, String passwd, long splitSize) {
        try {
            File srcfile = new File(srcFileStr);

            //创建目标文件
            String destname = buildDestFileName(srcfile, new File(dest));
            System.out.println(destname);

            ZipParameters par = new ZipParameters();
            /*
             * COMP_STORE = 0;（仅打包，不压缩）
             * COMP_DEFLATE = 8;（默认）
             * COMP_AES_ENC = 99; 加密压缩
             */
            par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            /*
             * DEFLATE_LEVEL_FASTEST = 1; (速度最快，压缩比最小)
             * DEFLATE_LEVEL_FAST = 3; (速度快，压缩比小)
             * DEFLATE_LEVEL_NORMAL = 5; (一般)
             * DEFLATE_LEVEL_MAXIMUM = 7;
             * DEFLATE_LEVEL_ULTRA = 9;
             */
            par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            if (passwd != null) {
                par.setEncryptFiles(true);
                /*
                 * ENC_METHOD_AES
                 *      parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                 * ENC_METHOD_STANDARD
                 */
                par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
                par.setPassword(passwd.toCharArray());
            }
            ZipFile zipfile = new ZipFile(destname);
            if (srcfile.isDirectory()) {
                zipfile.createZipFileFromFolder(srcfile, par, true, splitSize);
            } else {
                zipfile.createZipFile(srcfile, par, true, splitSize);
            }
        } catch (ZipException zipException){
            System.out.println("zip 压缩报错:"+zipException);
            return false;
        }
        return true;
    }
    /**
     * 解压
     *
     * @param zipfile 压缩包文件
     * @param dest    目标文件
     * @param passwd  密码
     */
    public static void unZip(String zipfile, String dest, String passwd) throws ZipException {
        ZipFile zfile = new ZipFile(zipfile);
//            zfile.setFileNameCharset("UTF-8");
//            if (zfile.isValidZipFile()) {
//                LogUtil.w("utf8失败");
//                zfile.setFileNameCharset("GBK");
//            }
//            if (zfile.isValidZipFile()) {
//                LogUtil.w("gbk失败");
//                throw new ZipException("压缩文件不合法，可能已经损坏！");
//            }
        zfile.setFileNameCharset("GBK");

        File file = new File(dest);
        if (file.isDirectory() && !file.exists()) {
            file.mkdirs();
        }

        if (passwd != null && zfile.isEncrypted()) {
            zfile.setPassword(passwd.toCharArray());
        }
        zfile.extractAll(dest);
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

    /**
     * 通过源文件和目标文件，确定目标文件的绝对位置
     * @param srcfile 源文件，必须存在
     * @param destFile 目标文件，非必须
     * @return 目标位置
     */
    private static String buildDestFileName(File srcfile, File destFile) {
        if (destFile == null) { // 目标位置没有设定时
            if (srcfile.isDirectory()) { // 源目录是个文件夹时，目标文件放到源目录上一层
                return srcfile.getParent() + File.separator + srcfile.getName() + ".zip";
            } else { // 源目录是个文件时，目标文件放到源目录旁边
                String filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                return srcfile.getParent() + File.separator + filename + ".zip";
            }
        } else { //目标文件给定时
            initFolder(destFile.getAbsolutePath());
            if (destFile.isDirectory()) { // 复制到目录
                return destFile.getAbsolutePath() + File.separator + srcfile.getName() + ".zip";
            } else { // 如果是文件直接返回目录
                return destFile.getAbsolutePath();
            }
        }
    }

    private static void createPath(String dest) {
        File destDir;
        if (dest.endsWith(File.separator)) {
            destDir = new File(dest);//给出的是路径时
        } else {
            destDir = new File(dest.substring(0, dest.lastIndexOf(File.separator)));
        }

        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }
}
