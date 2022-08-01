package com.hancher.gamelife;

import com.hancher.common.third.ZipUtil;
import com.hancher.common.utils.android.PictureCombineUtil;
import com.hancher.common.utils.java.DecryptUtil;

import net.lingala.zip4j.exception.ZipException;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
/*
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
*/

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ZipTest {
    private final static long ZIP_SPLIT_SIZE = 103809024;
    private String rootPath = "D:\\360极速浏览器下载\\qqmv";
    private String mp4 = "HAC764.mp4";
    private String code = "YSBC-C-20220522-007";
    private String unZip = "QQ视频_ad8a49487ef67dedace519561edcede91651971305.zip";
    private final static int PIC_COUNT = 200;
    @Test
    public void combinePic() throws FileNotFoundException {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            files.add(new File("D:\\360极速浏览器下载\\mm\\HAC764_"+i*1595/16+".jpg"));
        }
        PictureCombineUtil.combinePicture(files,4,new File("D:\\360极速浏览器下载\\mm\\HAC764.jpg"));
    }

    /**
     * 从视频中截取图片压缩视频
     * @throws IOException
     * @throws EncoderException
     */
    @Test
    public void capturePicAndZipTest() throws IOException, EncoderException, InterruptedException {
        //根据时长、个数 进行截图
        Encoder encoder = new Encoder();
        MultimediaInfo m = encoder.getInfo(new File(rootPath,mp4));
        long ls = m.getDuration()/1000;
        System.out.println("视频时长："+ls);
        for (int i = 0; i < PIC_COUNT; i++) {
            capturePic(i * ls / PIC_COUNT);
        }
        //加密压缩
        zipTest();
    }

    public void capturePic(long time) throws IOException, InterruptedException {
        String veido_path = new File(rootPath, mp4).getAbsolutePath();
        String ffmpeg_path = new File("src/test/java/com/hancher/gamelife/ffmpeg/ffmpeg.exe").getAbsolutePath();
        String pic_patch = new File(rootPath,code+"_"+time + ".jpg").getAbsolutePath();

        List<String> commands = new java.util.ArrayList<>();
        commands.add(ffmpeg_path);
        commands.add("-ss");//起始时间 必须放最前面
        commands.add(time+"");
        commands.add("-i"); // 视频位置
        commands.add(veido_path);
        commands.add("-y"); //覆盖
        commands.add("-f");//图片格式
        commands.add("image2");
        commands.add(pic_patch);
        commands.add("-t");//持续时间
        commands.add("0.001");//
//        commands.add("-s");
//        commands.add("700x525");

        //执行命令 ffmpeg -i ./xxx.mp4 -y -f image2 -ss time ./xxx_time.jpg
        System.out.println("开始截图："+commands+", 保存路径"+pic_patch);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commands);
        Process process = builder.start();
        InputStream ins = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String line;
        while ((line = reader.readLine()) != null) {
            String msg = new String(line.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(msg); // 输出
        }
        System.out.println("开始等待");
        int exitValue = process.waitFor();
        if (exitValue == 0) {
            System.out.println("返回值 I ：" + exitValue );
        } else {
            System.out.println("返回值 E ：" + exitValue );
        }

        process.getOutputStream().close(); // 关闭

        System.out.println("截取结束");
    }

    private void zipTest() {
        long start = System.currentTimeMillis();
        //源文件、压缩文件位置
        File srcFile = new File(rootPath,mp4);
        File desFile = new File(rootPath,code+".zip");
        System.out.println("目标压缩位置："+desFile.getAbsolutePath());
        //压缩密码为名字的二版加密
        String pass = DecryptUtil.encrypt2(code);
        System.out.println("加密结果："+pass);
        //压缩
        ZipUtil.zip(srcFile.getAbsolutePath(),desFile.getAbsolutePath(),pass);
        System.out.println("压缩时间："+(System.currentTimeMillis()-start)/1000 +"秒");
    }

    @Test
    public void zipSplitTest() {
        long start = System.currentTimeMillis();
        File srcFile = new File(mp4);
        String name = srcFile.getName().substring(0,srcFile.getName().indexOf("."));
        File desFile = new File(srcFile.getParent(),name+"-e2-.zip");
        System.out.println(desFile.getAbsolutePath());
        //压缩密码为名字的二版加密
        String pass = DecryptUtil.encrypt2(name);
        System.out.println(pass);
        ZipUtil.zipSplit(srcFile.getAbsolutePath(),desFile.getAbsolutePath(),pass,ZIP_SPLIT_SIZE);
        String perfix = desFile.getAbsolutePath().substring(0,desFile.getAbsolutePath().indexOf("."));
        System.out.println(perfix);
        File perfixFile;
        File perfixNewFile;
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            if (i<10) {
                perfixFile = new File(perfix +".z0"+i);
                perfixNewFile = new File(perfix +"z0"+i+".zip");
            } else {
                perfixFile = new File(perfix +".z"+i);;
                perfixNewFile = new File(perfix +"z"+i+".zip");
            }
            if (perfixFile.exists()){
                perfixFile.renameTo(perfixNewFile);
            } else {
                break;
            }
        }
        System.out.println((System.currentTimeMillis()-start)/1000 +"秒");
    }

    @Test
    public void unzipTest() throws ZipException {
        long start = System.currentTimeMillis();
        File srcFile = new File(unZip);
        String perfix = srcFile.getAbsolutePath().substring(0,srcFile.getAbsolutePath().indexOf("."));
        System.out.println(perfix);
        File perfixFile;
        File perfixNewFile;
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            if (i<10) {
                perfixFile = new File(perfix +".z0"+i);
                perfixNewFile = new File(perfix +"z0"+i+".zip");
            } else {
                perfixFile = new File(perfix +".z"+i);;
                perfixNewFile = new File(perfix +"z"+i+".zip");
            }
            if (perfixNewFile.exists()){
                perfixNewFile.renameTo(perfixFile);
            } else {
                break;
            }
        }

        String name = srcFile.getName().substring(0,srcFile.getName().indexOf("-e2-."));
        System.out.println(name);
        File desFile = new File(srcFile.getParent());
        System.out.println(desFile.getAbsolutePath());
        String pass = DecryptUtil.encrypt2(name);
        System.out.println(pass);
        ZipUtil.unZip(srcFile.getAbsolutePath(),desFile.getAbsolutePath(),pass);
        System.out.println((System.currentTimeMillis()-start)/1000 +"秒");
    }
}