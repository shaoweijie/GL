package com.hancher.gamelife.bak;

import android.content.Intent;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SettingsAdapter extends BaseMultiItemQuickAdapter<SettingsAdapter.SettingItem, BaseViewHolder> {

    public static final int SETTING = 0;
    public static final int ADVERTISING = 5;
    public static final int SETTING_TOP = 1;
    public static final int SETTING_BOTTOM = 2;
    public static final int SETTING_FOOT = 3;
    public static final int SETTING_HEAD = 4;

    public SettingsAdapter(@Nullable List<SettingsAdapter.SettingItem> data) {
        super(data);
        addItemType(SETTING, R.layout.recycleritem_settingt);
        addItemType(SETTING_TOP, R.layout.recycleritem_settingt);
        addItemType(SETTING_BOTTOM, R.layout.recycleritem_settingt);
        addItemType(SETTING_FOOT, R.layout.recycleritem_settingt);
        addItemType(SETTING_HEAD, R.layout.recycleritem_settingt);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SettingsAdapter.SettingItem item) {
        switch (baseViewHolder.getItemViewType()){
            case SETTING_TOP:
                baseViewHolder.setVisible(R.id.top_line,true);
                baseViewHolder.setGone(R.id.head_line,true);
                baseViewHolder.setGone(R.id.foot_line,true);
                baseViewHolder.setGone(R.id.bottom_line,true);
                break;
            case SETTING_HEAD:
                baseViewHolder.setGone(R.id.top_line,true);
                baseViewHolder.setVisible(R.id.head_line,true);
                baseViewHolder.setGone(R.id.foot_line,true);
                baseViewHolder.setGone(R.id.bottom_line,true);
                break;
            case SETTING_FOOT:
                baseViewHolder.setGone(R.id.top_line,true);
                baseViewHolder.setGone(R.id.head_line,true);
                baseViewHolder.setVisible(R.id.foot_line,true);
                baseViewHolder.setGone(R.id.bottom_line,true);
                break;
            case SETTING_BOTTOM:
                baseViewHolder.setGone(R.id.top_line,true);
                baseViewHolder.setGone(R.id.head_line,true);
                baseViewHolder.setGone(R.id.foot_line,true);
                baseViewHolder.setVisible(R.id.bottom_line,true);
                break;
        }

        baseViewHolder.setText(R.id.txt_title,item.getName());
        baseViewHolder.setImageResource(R.id.item_icon,item.getDrawable());
    }


    public static class SettingItem implements MultiItemEntity {
        private int itemType;
        private String name;
        private Intent intent;
        private int drawable;

        public static final int SETTING = 0;
        public static final int ADVERTISING = 5;
        public static final int SETTING_TOP = 1;
        public static final int SETTING_BOTTOM = 2;
        public static final int SETTING_FOOT = 3;
        public static final int SETTING_HEAD = 4;

        public SettingItem(int itemType, String name, Intent intent, int drawable) {
            this.itemType = itemType;
            this.name = name;
            this.intent = intent;
            this.drawable = drawable;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public String getName() {
            return name;
        }

        public Intent getIntent() {
            return intent;
        }

        public int getDrawable() {
            return drawable;
        }
    }
}
