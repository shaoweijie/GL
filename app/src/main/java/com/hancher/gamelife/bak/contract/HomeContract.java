package com.hancher.gamelife.bak.contract;

import com.hancher.gamelife.main.home.HomeAdapter;

import java.util.ArrayList;

/**
 * 作者：Hancher
 * 时间：2020/9/19 0019 下午 5:37
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：
 */
public interface HomeContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        ArrayList<HomeAdapter.HomeItem> getListData();
    }
}
