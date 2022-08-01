package com.hancher.gamelife.main.note;

import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.greendao.Diary;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/20 0020 16:03 <br/>
 * 描述 : 笔记实体类转换
 */
public class NoteUtil {
    public static List<NoteAdapter.NoteItem> db2list(List<Diary> entities) {
        List<NoteAdapter.NoteItem> list = new ArrayList<>();
        String monthTmp = "";
        for(Diary entity: entities){
            NoteAdapter.NoteItem item = new NoteAdapter.NoteItem();
            item.setTitle(entity.getTitle());
            item.setTag(entity.getTag());
            item.setUuid(entity.getUuid());
            try {
                item.setTime(entity.getCreatetime());
            } catch (Exception exception){
                LogUtil.w("date change err", exception);
            }
            if (!monthTmp.equals(item.getYearMonth())){
                item.setItemType(NoteAdapter.NOTE_LITTLE_HEADER);
            } else {
                item.setItemType(NoteAdapter.NOTE_LITTLE);
            }
            monthTmp = item.getYearMonth();
            list.add(item);
        }
        return list;
    }


}
