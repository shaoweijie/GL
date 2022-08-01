package com.hancher.gamelife.bak.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvp01.BaseMvpActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.gamelife.bak.contract.MoneyContract;
import com.hancher.gamelife.bak.presenter.MoneyPresenter;
import com.hancher.gamelife.databinding.MoneyActivityBinding;
import com.hancher.gamelife.greendao.Diary;

import java.util.List;

public class MoneyMvpActivity extends BaseMvpActivity<MoneyActivityBinding, MoneyPresenter>
        implements MoneyContract.View {
    long time = System.currentTimeMillis();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.btnLeft.setOnClickListener(v -> {
            time = (Long) DateUtil.changeDate(time, DateUtil.Type.LONG_STAMP, DateUtil.Type.LONG_FIRST_OF_MONTH) - 100L;
            changeMonth();
        });
        binding.btnRight.setOnClickListener(v -> {
            time = (Long) DateUtil.changeDate(time, DateUtil.Type.LONG_STAMP, DateUtil.Type.LONG_FIRST_OF_NEXT_MONTH);
            changeMonth();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeMonth();
    }

    public void changeMonth(){
        long startTime = DateUtil.changeDate(time,DateUtil.Type.LONG_STAMP,DateUtil.Type.LONG_FIRST_OF_MONTH);
        long endTime = DateUtil.changeDate(time,DateUtil.Type.LONG_STAMP,DateUtil.Type.LONG_FIRST_OF_NEXT_MONTH);
        String timeStr = DateUtil.changeDate(time,DateUtil.Type.LONG_STAMP,DateUtil.Type.STRING_YM);
        binding.textView8.setText(timeStr);
        presenter.queryAllMoneyRecords(startTime,endTime);
    }

    @Override
    public void updateData(List<Diary> dataIn, List<Diary> dataOut) {
        LogUtil.i("收入:"+dataIn.size()+"条，支出:"+dataOut.size()+"条");
        binding.chart1.initView("支出饼图:"+presenter.getTotalMoney(dataOut), presenter.changeNoteEntity2PieData(dataOut));
        binding.chart2.initView("收入饼图:"+presenter.getTotalMoney(dataIn), presenter.changeNoteEntity2PieData(dataIn));
        binding.chart3.initView(new double[]{presenter.getTotalMoney(dataIn),presenter.getTotalMoney(dataOut)});
    }
}