package com.hancher.gamelife.bak.contract;

import com.hancher.gamelife.bak.list.ScrollingAdapter;

import java.util.ArrayList;

public interface ScrollingContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        ArrayList<ScrollingAdapter.ScrollingItem> getListData();
    }
}
