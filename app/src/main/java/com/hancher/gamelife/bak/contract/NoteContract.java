package com.hancher.gamelife.bak.contract;

import com.hancher.gamelife.bak.list.FieldFilterAdapter;
import com.hancher.gamelife.greendao.Diary;
import com.hancher.gamelife.main.note.NoteAdapter;

import java.util.List;

/**
 * 作者：Hancher
 * 时间：2020/10/11 0011 下午 3:40
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：
 */
public interface NoteContract {
    interface Model {
    }

    interface View {
        void onDataQueried(List<NoteAdapter.NoteItem> datas);

        void onDataDeleted(Diary s, int position);
    }

    interface Presenter {
        void startSyncNote();

        void queryData(List<FieldFilterAdapter.FieldFilterItem> fieldDatas, int page);

        void deleteItem(String uuid, int position);

        List<FieldFilterAdapter.FieldFilterItem> getFilterData();

    }
}
