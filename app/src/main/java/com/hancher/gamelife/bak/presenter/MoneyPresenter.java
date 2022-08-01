package com.hancher.gamelife.bak.presenter;

import com.hancher.common.base.mvp01.BasePresenter;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.java.ColorUtil;
import com.hancher.gamelife.bak.activity.MoneyMvpActivity;
import com.hancher.gamelife.bak.contract.MoneyContract;
import com.hancher.gamelife.greendao.Diary;

import org.xclcharts.chart.PieData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneyPresenter extends BasePresenter<MoneyMvpActivity> implements MoneyContract.Presenter {

    @Override
    public void queryAllMoneyRecords(long startTime, long endTime) {
//        AsyncUtils.run((ObservableOnSubscribe<ArrayList<List<Diary>>>) emitter -> {
//            QueryBuilder<Diary> qb = MainApplication.getInstance().getDaoSession().getDiaryDao().queryBuilder();
//            qb.where(DiaryDao.Properties.Tag.eq("收入"),
//                    qb.or(DiaryDao.Properties.Deleteflag.isNull(), DiaryDao.Properties.Deleteflag.notEq("1")),
//                    DiaryDao.Properties.Createtime.ge(startTime),DiaryDao.Properties.Createtime.lt(endTime));
//            List<Diary> data1 = qb.list();
//            QueryBuilder<Diary> qb2 = MainApplication.getInstance().getDaoSession().getDiaryDao().queryBuilder();
//            qb2.where(DiaryDao.Properties.Tag.eq("支出"),
//                    qb2.or(DiaryDao.Properties.Deleteflag.isNull(), DiaryDao.Properties.Deleteflag.notEq("1")),
//                    DiaryDao.Properties.Createtime.ge(startTime),DiaryDao.Properties.Createtime.lt(endTime));
//            List<Diary> data2 = qb2.list();
//
//            ArrayList<List<Diary>> data = new ArrayList<>();
//            data.add(data1);
//            data.add(data2);
//            emitter.onNext(data);
//            emitter.onComplete();
//        }, noteEntities -> {
//            mView.updateData(noteEntities.get(0),noteEntities.get(1));
//        });
    }

    @Override
    public ArrayList<PieData> changeNoteEntity2PieData(List<Diary> data){
        ArrayList<PieData> pieDataIn = new ArrayList<>();
        HashMap<String, Float> inPresent = new HashMap<>();
        Float total = 0f;
        for (Diary item : data) {
            try {
                String title = item.getTitle();
                int index = title.indexOf(" ");
                String name = title.substring(0, index);
                String money = title.substring(index).trim();
                Float moneyFl = Float.valueOf(money);
                total += moneyFl;
                try {
                    Float oldMoneyFl = inPresent.get(title.substring(0, index));
                    if (oldMoneyFl!=null){
                        moneyFl += oldMoneyFl;
                    }
                } catch (Exception ignored){ }
                inPresent.put(name,moneyFl);
            } catch (Exception e){
                LogUtil.e(e);
            }
        }
        for (Map.Entry<String, Float> entry : inPresent.entrySet()){
            LogUtil.v("详情:"+entry.getKey()+", "+entry.getValue() +", "+(entry.getValue()*100/total));
            pieDataIn.add( new PieData(entry.getKey(),entry.getKey()+":"+entry.getValue(),entry.getValue()*100/total, ColorUtil.getRandomColor()) );
        }
        return pieDataIn;
    }

    @Override
    public Float getTotalMoney(List<Diary> data) {
        Float total = 0f;
        for (Diary item : data) {
            try {
                String title = item.getTitle();
                int index = title.indexOf(" ");
                String money = title.substring(index).trim();
                Float moneyFl = Float.valueOf(money);
                total += moneyFl;
            } catch (Exception e){
                LogUtil.e(e);
            }
        }
        return total;
    }

}
