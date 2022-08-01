package com.hancher.gamelife.bak.contract;

import com.hancher.gamelife.bak.SettingsAdapter;

import java.util.ArrayList;

public interface SettingsContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        ArrayList<SettingsAdapter.SettingItem> updateSettingList();
    }
}
