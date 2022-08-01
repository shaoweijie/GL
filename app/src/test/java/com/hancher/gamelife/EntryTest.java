package com.hancher.gamelife;

import com.hancher.common.utils.java.DecryptUtil;

import org.junit.Test;

import java.util.Arrays;

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
public class EntryTest {

    @Test
    public void addition_isCorrect() {
        System.out.println(Arrays.toString("你好".getBytes()));
        System.out.println(DecryptUtil.encrypt2("HAC764"));
        System.out.println(DecryptUtil.decrypt2("n4xp"));
    }
}