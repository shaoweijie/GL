package com.hancher.gamelife.main.note;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.common.third.PinYinUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NoteAdapter extends BaseMultiItemQuickAdapter<NoteAdapter.NoteItem, BaseViewHolder>
        implements LoadMoreModule, UpFetchModule {

    public static final int NOTE = 0;
    public static final int ADVERTISING = 1;
    public static final int NOTE_LITTLE = 2;
    public static final int NOTE_LITTLE_HEADER = 3;
    public static final String[] color = new String[]{"#f44336", "#FF9800", "#FFEB3B", "#4CAF50", "#00BCD4", "#2196F3", "#673AB7"};

    public NoteAdapter(@Nullable List<NoteItem> data) {
        super(data);
        addItemType(NOTE_LITTLE, R.layout.recycleritem_note_little);
        addItemType(NOTE_LITTLE_HEADER, R.layout.recycleritem_note_little);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoteItem item) {
        //年月 header
        if (baseViewHolder.getItemViewType() == NOTE_LITTLE_HEADER) {
            baseViewHolder.setText(R.id.text_month, item.getYearMonth());
            baseViewHolder.setVisible(R.id.text_month, true);
        } else {
            baseViewHolder.setGone(R.id.text_month, true);
        }
        // 几号
        baseViewHolder.setText(R.id.text_day, item.getDay());
        // 第几周
        baseViewHolder.setText(R.id.text_weekth, item.getWeekOfMonth());
        baseViewHolder.setBackgroundColor(R.id.text_weekth, item.getWeekOfMonthColor());
        // 周几
        baseViewHolder.setText(R.id.text_week, item.getWeek());
        baseViewHolder.setBackgroundColor(R.id.text_week, item.getWeekColor());
        // 标题
        baseViewHolder.setText(R.id.text_title, item.getTitle());
        // 标签
        if (!TextUtil.isEmptyOrNullStr(item.getTag())) {
            baseViewHolder.setVisible(R.id.text_tag, true);
            baseViewHolder.setText(R.id.text_tag, item.getTag());
            baseViewHolder.setBackgroundColor(R.id.text_tag, item.getTagColor());
        } else {
            baseViewHolder.setGone(R.id.text_tag, true);
        }
    }

    /**
     * 作者 : Hancher ytu_shaoweijie@163.com <br/>
     * 时间 : 2021/6/20 0020 15:58 <br/>
     * 描述 : 一条笔记记录RecyclerView item 实体类：
     * 实现 MultiItemEntity，增加 getItemType
     */
    public static class NoteItem implements MultiItemEntity {

        public static final String[] color = new String[]{"#f44336", "#FF9800", "#FFEB3B", "#4CAF50", "#00BCD4", "#2196F3", "#673AB7"};
        int itemType = NOTE_LITTLE;
        long time = 1;
        String yearMonth = "1970-01";
        String day = "1";
        String week = "周一";
        String weekOfMonth = "1";
        int weekColor = 1;
        int weekOfMonthColor = 1;

        String uuid = "";
        String title = "标题", tag = "标签";
        int tagColor = 1;

        public long getTime() {
            return time;
        }

        public NoteItem setTime(long time) {
            this.time = time;
            setNoteItemTime();
            return this;
        }

        private void setNoteItemTime() {
            try {
                setYearMonth(DateUtil.changeDate(time, DateUtil.Type.LONG_STAMP, DateUtil.Type.STRING_YM));
                // 几号
                setDay(DateUtil.changeDate(time, DateUtil.Type.LONG_STAMP, DateUtil.Type.STRING_D));
                // 第几周
                Integer weekOfMonth = DateUtil.changeDate(time, DateUtil.Type.LONG_STAMP, DateUtil.Type.INT_WEEK_OF_MONTH);
                if (weekOfMonth != null) {
                    String weekTh;
                    switch (weekOfMonth) {
                        case 1:
                            weekTh = "①";
                            break;
                        case 2:
                            weekTh = "②";
                            break;
                        case 3:
                            weekTh = "③";
                            break;
                        case 4:
                            weekTh = "④";
                            break;
                        case 5:
                            weekTh = "⑤";
                            break;
                        case 6:
                            weekTh = "⑥";
                            break;
                        default:
                            weekTh = "第" + weekOfMonth + "周";
                            break;
                    }
                    setWeekOfMonth(weekTh);
                    setWeekOfMonthColor(Color.parseColor(color[weekOfMonth % 7]));
                }

                // 周几
                String dayOfWeek = DateUtil.changeDate(time, DateUtil.Type.LONG_STAMP, DateUtil.Type.STRING_WEEK);
                Integer dayOfWeekInt = DateUtil.changeDate(time, DateUtil.Type.LONG_STAMP, DateUtil.Type.INT_DAY_OF_WEEK);
                if (dayOfWeek != null && dayOfWeekInt != null) {
                    setWeek(dayOfWeek);
                    setWeekColor(Color.parseColor(color[dayOfWeekInt % 7]));
                }
            } catch (Exception exception) {
                LogUtil.w("date change err", exception);
            }
        }

        public int getWeekColor() {
            return weekColor;
        }

        public NoteItem setWeekColor(int weekColor) {
            this.weekColor = weekColor;
            return this;
        }

        public int getWeekOfMonthColor() {
            return weekOfMonthColor;
        }

        public NoteItem setWeekOfMonthColor(int weekOfMonthColor) {
            this.weekOfMonthColor = weekOfMonthColor;
            return this;
        }

        public int getTagColor() {
            return tagColor;
        }

        public NoteItem setTagColor(int tagColor) {
            this.tagColor = tagColor;
            return this;
        }

        public String getWeekOfMonth() {
            return weekOfMonth;
        }

        public NoteItem setWeekOfMonth(String weekOfMonth) {
            this.weekOfMonth = weekOfMonth;
            return this;
        }

        public String getUuid() {
            return uuid;
        }

        public NoteItem setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public String getYearMonth() {
            return yearMonth;
        }

        public NoteItem setYearMonth(String yearMonth) {
            this.yearMonth = yearMonth;
            return this;
        }

        public String getDay() {
            return day;
        }

        public NoteItem setDay(String day) {
            this.day = day;
            return this;
        }

        public String getWeek() {
            return week;
        }

        public NoteItem setWeek(String week) {
            this.week = week;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public NoteItem setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getTag() {
            return tag;
        }

        public NoteItem setTag(String tag) {
            this.tag = tag;
            if (!TextUtil.isEmpty(tag)) {
                int index = PinYinUtil.cn2Spell(tag).charAt(0);
                setTagColor(Color.parseColor(color[index % 7]));
            }
            return this;
        }

        public NoteItem setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
