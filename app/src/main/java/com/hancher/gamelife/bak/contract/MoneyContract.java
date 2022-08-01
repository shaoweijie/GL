package com.hancher.gamelife.bak.contract;

import com.hancher.gamelife.greendao.Diary;

import org.xclcharts.chart.PieData;

import java.util.ArrayList;
import java.util.List;

public interface MoneyContract {
    interface Model {
    }

    interface View {
        void updateData(List<Diary> dataIn, List<Diary> dataOut);
    }

    interface Presenter {
        void queryAllMoneyRecords(long startTime, long endTime);

        ArrayList<PieData> changeNoteEntity2PieData(List<Diary> dataIn);

        Float getTotalMoney(List<Diary> dataIn);
    }
}
