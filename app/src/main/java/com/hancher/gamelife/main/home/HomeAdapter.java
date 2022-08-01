package com.hancher.gamelife.main.home;

import android.content.Intent;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeAdapter.HomeItem, BaseViewHolder> {

    public static final int ACTIVITY = 0;

    public HomeAdapter(@Nullable List<HomeItem> data) {
        super(data);
        addItemType(ACTIVITY, R.layout.recycleritem_home);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeItem item) {
        switch (baseViewHolder.getItemViewType()){
            case ACTIVITY:
                baseViewHolder.setText(R.id.item_name, item.getName());
                baseViewHolder.setImageResource(R.id.image, item.getDrawable());
                break;
        }
    }

    public static class HomeItem implements MultiItemEntity {
        private int itemType;
        private String name;
        private Intent intent;
        private int drawable;

        public HomeItem(int itemType, String name, Intent intent, int drawable) {
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
