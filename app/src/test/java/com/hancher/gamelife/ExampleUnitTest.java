package com.hancher.gamelife;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
public class ExampleUnitTest {
//    public static void main(String[] args) throws IOException {
//        String pp = "C:\\Users\\Administrator\\Desktop\\0921\\DIARY_excel.xls";
//        String pp2 = "C:\\Users\\Administrator\\Desktop\\0921\\DIARY_excel2.xls";
//        File finalXlsxFile = new File(pp);
//        Workbook workBook = new HSSFWorkbook(new FileInputStream(finalXlsxFile));
//        Sheet sheet = workBook.getSheetAt(0);
//        int rowNumber = sheet.getLastRowNum();
//        for (int j = 0; j < rowNumber; j++) {
//            String cellinfo = sheet.getRow(j).getCell(9).getStringCellValue();
//            String content = StringEscapeUtils.unescapeHtml(cellinfo);
//            String contentNoHtmlTag = HtmlUtil.delHTMLTag(content);
////            System.out.println(contentNoHtmlTag);
//            sheet.getRow(j).createCell(10).setCellValue(contentNoHtmlTag);
//        }
//        FileOutputStream out = new FileOutputStream(pp2);
//        workBook.write(out);
//    }

//    @Test
//    public void climp() throws IOException {
//        Document document = Jsoup.connect("https://tv.cctv.com/live/cctv1").get();
//        String data = document.html();
//        System.out.println(data);
//    }
//    @Test
//    public void printTT() {
//        try {
//            throw new RuntimeException("test run exception");
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//            System.out.println("-------------");
//            System.out.println(e.getMessage());
//            System.out.println("-------------");
//            System.out.println(e.toString());
//        }
//    }
//
//    @Test
//    public void printT() {
////        System.out.println(String.valueOf(System.currentTimeMillis()));
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2021, 3 - 1, 28);
//        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//        if (week < 1) {
//            week += 7;
//        }
//        int startEmpty = week - 1;
//    }
//
//    @Test
//    public void printAllRank() {
//        for (int i = 0; i < 390; i++) {
//            System.out.println(i + ":" + getMaxExperienceByRank(i) + ":" + getUnitExperienceByRank(i));
//            ;
//        }
//    }
//
//    public static String getUnitExperienceByRank(int rank) {
//        if (rank < 140) {
//            return new BigDecimal(getMaxExperienceByRank(rank + 1))
//                    .subtract(new BigDecimal(getMaxExperienceByRank(rank)))
//                    .divide(BigDecimal.valueOf(24), MathContext.ROUND_CEILING).toString();
//        } else {
//            return new BigDecimal(getMaxExperienceByRank(rank + 1))
//                    .subtract(new BigDecimal(getMaxExperienceByRank(rank)))
//                    .divide(BigDecimal.valueOf(7 * 24), MathContext.ROUND_CEILING)
//                    .add(BigDecimal.valueOf(40)).toString();
//        }
//    }
//
//    public static String getMaxExperienceByRank(int rank) {
//        int ext = rank % 10;
//        if (ext == 0) {
//            return getMaxExperienceByRank2(rank / 10);
//        }
//        String start = getMaxExperienceByRank2(rank / 10);
//        String end = getMaxExperienceByRank2(rank / 10 + 1);
//        BigDecimal startBig = new BigDecimal(start);
//        BigDecimal detla = new BigDecimal(end).subtract(startBig);
//        return detla.divide(BigDecimal.valueOf(10), MathContext.ROUND_CEILING)
//                .multiply(BigDecimal.valueOf(ext))
//                .add(startBig).toString();
//    }
//
//    public static String getMaxExperienceByRank2(int rank10) {
//        BigDecimal d = BigDecimal.valueOf(2).pow(rank10);
//        d = d.add(BigDecimal.valueOf(100 * rank10));
//        return d.toEngineeringString();
//    }

    @Test
    public void addition_isCorrect() {
//        List<File> fileList = new ArrayList<>();
//        getAllFile("F:\\我的坚果云\\4-学习笔记\\个人资料\\日记",fileList);
//        try {
//            openExcel("F:\\日记.xls", fileList);
//        } catch (IOException | WriteException e) {
//            e.printStackTrace();
//        }
//        for (File item : fileList) {
//            System.out.println("文件："+item.getAbsolutePath());
//        }
        assertEquals(4, 2 + 2);
    }


//    private void openExcel(String excelPath, List<File> fileList) throws IOException, WriteException {
//        File file = new File(excelPath);
//        WritableWorkbook workbook = Workbook.createWorkbook(file);
//        WritableSheet sheet = workbook.createSheet("测试", 0);
//        String[] titles = { "内容","标题", "设备型号","创建时间","天气",
//                "地点","图片","标签","uuid","天气",
//                "温度","风向","风级别","创建时间","经度",
//                "纬度"};
//        for (int i = 0; i < titles.length; i++) {
//            sheet.addCell(new Label(i, 0, titles[i]));
//        }
//        FileReader reader;
//        BufferedReader bufferedReader;
//        for (int i = 1; i < fileList.size(); i++) {
//            reader = new FileReader(fileList.get(i));
//            bufferedReader = new BufferedReader(reader);
//
//            String line="";
//            String[] arrs=null;
//            ArrayList<StringBuffer> stringBuffers = new ArrayList<>();
//            for (int j = 0; j < titles.length; j++) {
//                stringBuffers.add(new StringBuffer());
//            }
//            int current = 0;
//            //遍历出md内容
//            while ((line=bufferedReader.readLine())!=null) {
//                for (int j = 0; j < titles.length; j++) {
//                    if (line.startsWith(titles[j])){
//                        current = j;
//                        break;
//                    }
//                }
//
//
//                if (line.trim().length()==0){
//
//                } else if (line.startsWith(titles[current])){
//                    stringBuffers.get(current).append(line.substring(titles[current].length()+1)).append("\n");
//                } else {
//                    stringBuffers.get(current).append(line).append("\n");
//                }
//            }
//            bufferedReader.close();
//            reader.close();
//            //内容图片是否混合多个
//            if (stringBuffers.get(0).toString().contains("![")){
//                String isOne = String.valueOf(
//                        stringBuffers.get(0).toString().indexOf("![") == stringBuffers.get(0).toString().lastIndexOf("![")
//                );
//                sheet.addCell(new Label(titles.length, i, isOne));
//            }
//            //内容加工分离
//            for (int j = 0; j < titles.length; j++) {
//                if (j==8){
//                    sheet.addCell(new Label(j, i, UuidUtil.getUuidNoLine()));
//                } else if (stringBuffers.get(j).toString().trim().length()==0){
//                    continue;
//                } else if (j==3){
//                    String time = stringBuffers.get(j).toString().trim();
//                    sheet.addCell(new Label(j, i, time));
//                    time = DateUtil.changeDate(time, DateUtil.Type.STRING_YMD_HMS_2, DateUtil.Type.STRING_STAMP);
//                    sheet.addCell(new Label(13, i, time));
//                } else if (j==5){
//                    String position = stringBuffers.get(j).toString().trim();
//                    sheet.addCell(new Label(j, i, position));
//                    if(position.contains("0.0")){
//                        continue;
//                    } else if (position.contains("4.9E-324")){
//                        continue;
//                    }
//                    sheet.addCell(new Label(14, i, position.substring(0,position.indexOf(","))));
//                    sheet.addCell(new Label(15, i, position.substring(position.indexOf(",")+1)));
//                } else if (j==4 && stringBuffers.get(j).toString().trim().length()!=0){
//                    String weather = stringBuffers.get(j).toString().trim();
//                    sheet.addCell(new Label(9, i, weather.substring(0,weather.indexOf("/"))));
//                    sheet.addCell(new Label(10, i, weather.substring(weather.indexOf("/")+1,weather.indexOf("摄氏度"))));
//                    if (weather.contains("风")) {
//                        sheet.addCell(new Label(11, i, weather.substring(weather.lastIndexOf("/")+1,weather.indexOf("风")+1)));
//                        sheet.addCell(new Label(12, i, weather.substring(weather.lastIndexOf("风")+1,weather.lastIndexOf("级"))));
//                    }
//                    sheet.addCell(new Label(j, i, weather));
//                } else {
//                    sheet.addCell(new Label(j, i, stringBuffers.get(j).toString().trim()));
//                }
//            }
//        }
//
//        workbook.write();
//        workbook.close();
//    }
//
//    private void getAllFile(String rootPath,List<File> list) {
//        File rootDir = new File(rootPath);
//        File[] files = rootDir.listFiles();
//        assert files != null;
//        for (File item : files) {
//            if (item.isDirectory()){
//                getAllFile(item.getAbsolutePath(),list);
//            } else {
//                if(item.getAbsolutePath().endsWith(".md") && item.getParentFile().getAbsolutePath().contains("20")){
//                    list.add(item);
//                }
//            }
//        }
//    }

}