package com.hancher.common.utils.android;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;

import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/21 9:46 <br/>
 * 描述 : 数据刷新工具
 */
public class BaseAdapterUtil {
    public static void update(BaseQuickAdapter adapter, List datas) {
        int oldSize = 0;
        try {
            oldSize = adapter.getData().size();
        } catch (Exception ignored) {
        }
        int newSize = datas.size();
        LogUtil.d("列表数据个数:", oldSize, "->", newSize);

        adapter.getData().clear();
        adapter.addData(datas);

        if (newSize == 0) {
            adapter.notifyItemRangeRemoved(0, oldSize);
        } else if (oldSize > newSize) {
            adapter.notifyItemRangeRemoved(newSize, oldSize - newSize);
            adapter.notifyItemRangeChanged(0, newSize);
        } else if (newSize > oldSize) {
            adapter.notifyItemRangeInserted(oldSize, newSize - oldSize);
            adapter.notifyItemRangeChanged(0, oldSize);
        } else {
            adapter.notifyItemRangeChanged(0, oldSize);
        }

        if (adapter instanceof LoadMoreModule && adapter.getLoadMoreModule().isLoading()) {
            adapter.getLoadMoreModule().loadMoreComplete();
        }
    }
}
