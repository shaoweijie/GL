package com.hancher.gamelife.bak.contract;

import java.io.File;

public interface TcpTestContract {
    interface Model {
    }

    interface View {
        void changeLogText(String log);
    }

    interface Presenter {
        void tcpConnect(String serverIp, File file);
    }
}
