package com.hancher.gamelife.main.tv.app;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/25 9:42 <br/>
 * 描述 :
 */
public class TvListAdapter extends BaseMultiItemQuickAdapter<TvListAdapter.TvListItem, BaseViewHolder> {
    public final static int TYPE_TITLE = 1;
    public final static int TYPE_TITLE_EMPTY = 2;
    public final static int TYPE_ITEM = 3;
    public final static int TYPE_ITEM_EMPTY = 4;

    public TvListAdapter(@Nullable List<TvListAdapter.TvListItem> data) {
        super(data);
        addItemType(TYPE_TITLE, R.layout.item_text_line);
        addItemType(TYPE_TITLE_EMPTY, R.layout.item_text_line);
        addItemType(TYPE_ITEM, R.layout.item_button);
        addItemType(TYPE_ITEM_EMPTY, R.layout.item_text);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TvListAdapter.TvListItem item) {
        switch (baseViewHolder.getItemViewType()) {
            case TYPE_TITLE:
                baseViewHolder.setText(R.id.item_text_title, item.getTitle());
                break;
            case TYPE_ITEM:
                baseViewHolder.setText(R.id.item_btn_title, item.getTitle());
                break;
        }
    }
    public static class TvListItem implements MultiItemEntity {
        private String title;
        private String url;
        public int type = 0;

        @Override
        public int getItemType() {
            return type;
        }

        public String getTitle() {
            return title;
        }

        public TvListAdapter.TvListItem setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public TvListAdapter.TvListItem setUrl(String url) {
            this.url = url;
            return this;
        }

        public int getType() {
            return type;
        }

        public TvListAdapter.TvListItem setType(int type) {
            this.type = type;
            return this;
        }

        @Override
        public String toString() {
            return "TvListAdapter.TvListItem{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
}
