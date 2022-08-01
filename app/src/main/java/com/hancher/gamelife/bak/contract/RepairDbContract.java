package com.hancher.gamelife.bak.contract;

import java.util.HashMap;

public interface RepairDbContract {
    interface Model {
    }

    interface View {
        void updateNoteResult(Integer createtime, Integer updatetime, Integer deleteflag);

        void updateAccountResult(HashMap<String, Integer> o);

        void updateLogView(String o);
    }

    interface Presenter {
        void repairDb();

        void repairAccountDb();

        void importData();

        void repairNoteLocation();

        void testNet();
    }
}
