package com.hancher.gamelife.bak.list;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScrollingAdapter extends BaseMultiItemQuickAdapter<ScrollingAdapter.ScrollingItem, BaseViewHolder> {

    public static final int ACCOUNT = 0;
    public static final int ADVERTISING = 1;

    public ScrollingAdapter(@Nullable List<ScrollingItem> data) {
        super(data);
        addItemType(ACCOUNT, R.layout.recycleritem_scrolling);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ScrollingItem item) {
        switch (baseViewHolder.getItemViewType()){
            case ACCOUNT:
                baseViewHolder.setText(R.id.item_account, item.getName());
                break;
        }
    }

    public static class ScrollingItem implements MultiItemEntity {
        private int itemType;
        private String name;

        public ScrollingItem(int itemType, String name) {
            this.itemType = itemType;
            this.name = name;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
