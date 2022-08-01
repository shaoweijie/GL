package com.hancher.gamelife.main.me;

import android.content.Intent;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MeAdapter extends BaseMultiItemQuickAdapter<MeAdapter.MeItem, BaseViewHolder> {

    public MeAdapter(@Nullable List<MeItem> data) {
        super(data);
        addItemType(MeItem.SETTING, R.layout.recycleritem_home);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MeItem item) {
        switch (baseViewHolder.getItemViewType()){
            case MeItem.SETTING:
                baseViewHolder.setText(R.id.item_name,item.getName());
                baseViewHolder.setImageResource(R.id.image,item.getDrawable());
                break;
        }

    }

    public static class MeItem implements MultiItemEntity {
        public static final int SETTING = 0;
        private int itemType = SETTING;
        private String name;
        private Intent intent;
        private int drawable;

        public MeItem(int itemType, String name, Intent intent, int drawable) {
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
