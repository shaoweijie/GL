package com.hancher.gamelife.bak.contract;

import java.io.File;

public interface BackupContract {
    interface Model {
    }

    interface View {
        void appendLog(String log);

        void shareFile(File file);
    }

    interface Presenter {
        void startBackup();

        void startRecover(String selectFilePath);
    }
}
