package com.hancher.gamelife;

import com.csvreader.CsvReader;
import com.google.gson.Gson;
import com.hancher.gamelife.spiders.SpidersTestEntry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SpidersTest {

    private File rootfile;
    private File currentfile;
    private File configJsonFile;
    private SpidersTestEntry entry;
    private final static boolean DEBUG_HTML = true;
    private File htmlDebugFile;
    private File cityList;
    private File locationIdDebugFile;

    @Test
    public void start() throws IOException {
        System.out.println("=================start=================");
        initPath();
        initJsonConfig();
        spidersWeather();
//        openWeb();
        System.out.println("================= end =================");
    }

    private void spidersWeather() throws IOException {
        initCity();
    }

    private void initCity() throws IOException {
//        https://github.com/qwd/LocationList.git
//        https://gitee.com/Hancher/LocationList

        // 创建CSV读对象
        CsvReader csvReader = new CsvReader(cityList.getAbsolutePath());
        // 读表头
        csvReader.readHeaders();
        StringBuffer stringBuffer = new StringBuffer();
        while (csvReader.readRecord()){
//            // 读一整行
//            System.out.println(csvReader.getRawRecord());
//            // 读这行的某一列
//            System.out.println(csvReader.get("Link"));
            stringBuffer.append(String.format(
                    "http://www.weather.com.cn/weather1d/%s.shtml\n", csvReader.get("Location_ID")));
        }
        if (DEBUG_HTML){
            FileWriter writer = new FileWriter(locationIdDebugFile);
            writer.write(stringBuffer.toString());
            writer.close();
        }
    }

    private void openWeb() throws IOException {
        if (entry.getIndex() == -1){
            for (int i = 0; i < entry.getData().size(); i++) {
                openWebItem(i);
            }
        } else {
            openWebItem(entry.getIndex());
        }
    }

    private void openWebItem(int i) throws IOException {
        SpidersTestEntry.Data item = entry.getData().get(i);
        Document document = Jsoup.connect(item.getUrl()).get();
        String data = document.html();
        System.out.println("返回长度："+data.length());
        if (DEBUG_HTML){
            FileWriter writer = new FileWriter(htmlDebugFile);
            writer.write(data);
            writer.close();
        }
    }

    private void initJsonConfig() throws FileNotFoundException {
        FileReader fileReader = new FileReader(configJsonFile.getAbsoluteFile()+"");
        Gson gson = new Gson();
//        Type type = new TypeToken<SpidersTestEntry>(){}.getType();
        entry = gson.fromJson(fileReader, SpidersTestEntry.class);
        System.out.println(entry.toString());
    }

    private void initPath() {
        rootfile = new File("");
        System.out.println("编译根目录"+rootfile.getAbsolutePath());
        currentfile = new File("src/test/java/com/hancher/gamelife");
        System.out.println("当前文件目录"+currentfile.getAbsolutePath());
        configJsonFile = new File(currentfile.getAbsolutePath(),"spiders/spiders_test_entry.json");
        System.out.println("配置文件目录"+configJsonFile.getAbsolutePath());
        htmlDebugFile = new File(currentfile.getAbsolutePath(),"spiders/debug.html");
        System.out.println("debug文件目录"+htmlDebugFile.getAbsolutePath());
        cityList = new File(currentfile.getAbsolutePath(),"city/China-City-List-latest-no-header.csv");
        System.out.println("cityList文件目录"+cityList.getAbsolutePath());
        locationIdDebugFile = new File(currentfile.getAbsolutePath(),"city/China-City-List-latest-url.txt");
        System.out.println("cityList文件目录"+locationIdDebugFile.getAbsolutePath());
    }


    public void readXml(String xmlFile) {
        assertEquals(4, 2 + 2);
    }

}