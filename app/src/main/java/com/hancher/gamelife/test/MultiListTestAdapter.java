package com.hancher.gamelife.test;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2022/6/27 11:43 <br/>
 * 描述 : 多布局列表测试，当RecyclerView布局不止一个类型时，用此adapter
 */
public class MultiListTestAdapter extends BaseMultiItemQuickAdapter<MultiListTestAdapter.TestListItem, BaseViewHolder>{

    public static final int LIGHT = 0;
    public static final int DARK = 1;

    public MultiListTestAdapter(@Nullable List<TestListItem> data) {
        super(data);
        addItemType(LIGHT, R.layout.colock_in_list_item);
        addItemType(DARK, R.layout.colock_in_list_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TestListItem item) {
        baseViewHolder.setText(R.id.item_title, item.getTitle());
        baseViewHolder.setText(R.id.item_time, item.getPackageName());
        baseViewHolder.setImageDrawable(R.id.item_image, item.getIcon());
        switch (item.getItemType()) {
            case LIGHT:
                baseViewHolder.setBackgroundColor(R.id.card_container, Color.parseColor("#2196F3"));
                break;
            case DARK:
                baseViewHolder.setBackgroundColor(R.id.card_container, Color.parseColor("#4CAF50"));
                break;
            default:
                break;
        }
    }

    /**
     * 作者 : Hancher ytu_shaoweijie@163.com <br/>
     * 时间 : 2021/6/20 0020 15:58 <br/>
     * 描述 : 混合条目
     */
    public static class TestListItem implements MultiItemEntity{

        private String title;
        private String packageName;
        private Drawable icon;

        public Drawable getIcon() {
            return icon;
        }

        public TestListItem setIcon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public TestListItem setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getPackageName() {
            return packageName;
        }

        public TestListItem setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public int getType() {
            return type;
        }

        public TestListItem setType(int type) {
            this.type = type;
            return this;
        }

        int type;

        @Override
        public int getItemType() {
            return type;
        }

        @Override
        public String toString() {
            return "TestListItem{" +
                    "title='" + title + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", icon=" + icon +
                    ", type=" + type +
                    '}';
        }
    }
}
