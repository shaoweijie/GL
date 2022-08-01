package com.hancher.gamelife.bak.contract;

import com.hancher.gamelife.main.me.MeAdapter;

import java.util.ArrayList;

public interface MeContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        ArrayList<MeAdapter.MeItem> updateSettingList();
    }
}
