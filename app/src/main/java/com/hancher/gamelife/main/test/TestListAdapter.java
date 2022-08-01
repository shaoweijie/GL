package com.hancher.gamelife.main.test;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TestListAdapter extends BaseMultiItemQuickAdapter<TestListAdapter.TestListItem, BaseViewHolder>
        implements LoadMoreModule, UpFetchModule {

    public static final int DIARY = 0;

    public TestListAdapter(@Nullable List<TestListItem> data) {
        super(data);
        addItemType(DIARY, R.layout.colock_in_list_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TestListItem item) {
        switch (item.getItemType()) {
            case DIARY:
                baseViewHolder.setText(R.id.item_title, item.getTitle());
                baseViewHolder.setText(R.id.item_time, item.getDescription());
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
        private String className;
        private String description;

        public String getDescription() {
            return description;
        }

        public TestListItem setDescription(String description) {
            this.description = description;
            return this;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }
        public String getPackageName() {
            return packageName;
        }

        public void setClassName(String className) {
            this.className = className;
        }
        public String getClassName() {
            return className;
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
                    ", className='" + className + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
}
