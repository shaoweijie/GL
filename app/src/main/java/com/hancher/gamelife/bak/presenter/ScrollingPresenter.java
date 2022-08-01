package com.hancher.gamelife.bak.presenter;

import com.hancher.common.base.mvp01.BasePresenter;
import com.hancher.gamelife.bak.contract.ScrollingContract;
import com.hancher.gamelife.bak.fragment.ScrollingFragment;
import com.hancher.gamelife.bak.list.ScrollingAdapter;

import java.util.ArrayList;

public class ScrollingPresenter extends BasePresenter<ScrollingFragment> implements ScrollingContract.Presenter {
    @Override
    public ArrayList<ScrollingAdapter.ScrollingItem> getListData() {
        ArrayList<ScrollingAdapter.ScrollingItem> datas = new ArrayList<>();
        for (int i = 0; i <50; i++) {
            datas.add(new ScrollingAdapter.ScrollingItem(ScrollingAdapter.ACCOUNT, "账户"));
        }
        return datas;
    }
}
