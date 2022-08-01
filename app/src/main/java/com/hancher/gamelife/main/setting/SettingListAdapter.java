package com.hancher.gamelife.main.setting;

import android.content.Intent;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SettingListAdapter extends BaseMultiItemQuickAdapter<SettingListAdapter.SettingListItem, BaseViewHolder> {

    public SettingListAdapter(@Nullable List<SettingListItem> data) {
        super(data);
        addItemType(SettingListItem.DEFAULT, R.layout.recycleritem_setting_list);
        addItemType(SettingListItem.ARROW, R.layout.recycleritem_setting_list);
        addItemType(SettingListItem.CHECKBOX, R.layout.recycleritem_setting_list);
        addItemType(SettingListItem.TITLE, R.layout.recycleritem_setting_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SettingListItem item) {
        if (item.isTopLine()) {
            baseViewHolder.setVisible(R.id.top_line, true);
        } else {
            baseViewHolder.setGone(R.id.top_line, true);
        }
        if (item.isHeadLine()) {
            baseViewHolder.setVisible(R.id.head_line, true);
        } else {
            baseViewHolder.setGone(R.id.head_line, true);
        }
        if (item.isFootLine()) {
            baseViewHolder.setVisible(R.id.foot_line, true);
        } else {
            baseViewHolder.setGone(R.id.foot_line, true);
        }
        if (item.isBottomLine()) {
            baseViewHolder.setVisible(R.id.bottom_line, true);
        } else {
            baseViewHolder.setGone(R.id.bottom_line, true);
        }

        baseViewHolder.setText(R.id.txt_title, item.getName());
        if (TextUtil.isEmpty(item.getDescrition())) {
            baseViewHolder.setGone(R.id.txt_description, true);
        } else {
            baseViewHolder.setVisible(R.id.txt_description, true);
            baseViewHolder.setText(R.id.txt_description, item.getDescrition());
        }

        if (item.getItemType() == SettingListItem.ARROW) {
            baseViewHolder.setVisible(R.id.right_icon, true);
        } else {
            baseViewHolder.setGone(R.id.right_icon, true);
        }

        if (item.getItemType() == SettingListItem.CHECKBOX) {
            baseViewHolder.setVisible(R.id.right_checkBox, true);
            ((CheckBox) baseViewHolder.getView(R.id.right_checkBox)).setChecked(item.isCheckBoxCheck());
        } else {
            baseViewHolder.setGone(R.id.right_checkBox, true);
        }
    }

    public static class SettingListItem implements MultiItemEntity {
        private int itemType;
        private String name;
        private String descrition;
        private Intent intent;
        private boolean topLine = false;
        private boolean bottomLine = false;
        private boolean headLine = false;
        private boolean footLine = false;
        private boolean checkBoxCheck = false;

        public static final int DEFAULT = 0;
        public static final int ARROW = 1;
        public static final int CHECKBOX = 2;
        public static final int TITLE = 3;

        public SettingListItem(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public SettingListItem setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public String getDescrition() {
            return descrition;
        }

        public SettingListItem setDescrition(String descrition) {
            this.descrition = descrition;
            return this;
        }

        public boolean isCheckBoxCheck() {
            return checkBoxCheck;
        }

        public SettingListItem setCheckBoxCheck(boolean checkBoxCheck) {
            this.checkBoxCheck = checkBoxCheck;
            return this;
        }

        public boolean isTopLine() {
            return topLine;
        }

        public SettingListItem setTopLine(boolean topLine) {
            this.topLine = topLine;
            return this;
        }

        public boolean isBottomLine() {
            return bottomLine;
        }

        public SettingListItem setBottomLine(boolean bottomLine) {
            this.bottomLine = bottomLine;
            return this;
        }

        public boolean isHeadLine() {
            return headLine;
        }

        public SettingListItem setHeadLine(boolean headLine) {
            this.headLine = headLine;
            return this;
        }

        public boolean isFootLine() {
            return footLine;
        }

        public SettingListItem setFootLine(boolean footLine) {
            this.footLine = footLine;
            return this;
        }

        public String getName() {
            return name;
        }

        public SettingListItem setName(String name) {
            this.name = name;
            return this;
        }

        public Intent getIntent() {
            return intent;
        }

        public SettingListItem setIntent(Intent intent) {
            this.intent = intent;
            return this;
        }
    }
}
