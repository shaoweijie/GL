package com.hancher.gamelife.bak.contract;

import com.hancher.gamelife.bak.list.FieldFilterAdapter;
import com.hancher.gamelife.main.human.BirthdayAdapter;

import java.util.List;

public interface BirthdayContract {
    interface Model {
    }

    interface View {
        void updateData(List<BirthdayAdapter.BirthdayItem> datas);
    }

    interface Presenter {
        List<FieldFilterAdapter.FieldFilterItem> getFilterData();
        void queryBirthdayData(List<FieldFilterAdapter.FieldFilterItem> fieldDatas);
    }
}
