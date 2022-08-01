package com.hancher.gamelife.main.human;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CharactersAdapter extends BaseMultiItemQuickAdapter<CharactersAdapter.CharacterItem, BaseViewHolder> {

    public static final int HUMAN = 0;
    public static final int FOOTER = 1;
    private final Activity activity;


    public CharactersAdapter(@Nullable List<CharacterItem> data, Activity activity) {
        super(data);
        LogUtil.d("");
        addItemType(HUMAN, R.layout.recycleritem_human);
        addItemType(FOOTER, R.layout.recyclerview_footer);
        this.activity = activity;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CharacterItem item) {
//        LogUtil.d("item:"+item.getName());
        switch (baseViewHolder.getItemViewType()) {
            case HUMAN:
                baseViewHolder.setText(R.id.item_name, item.getName());
                Glide.with(activity)
                        .load(item.getPicture())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.item_person)
                        .into((ImageView) baseViewHolder.getView(R.id.image));
                break;
            case FOOTER:
//                baseViewHolder.setText(R.id.item_name, "");
//                baseViewHolder.setImageBitmap(R.id.image,null);
                break;
        }
    }

    public static class CharacterItem implements MultiItemEntity {
        private int itemType = CharactersAdapter.HUMAN;
        private String name;
        private String uuid;
        private Bitmap picture;

        public CharacterItem() {
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public CharacterItem setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public String getName() {
            return name;
        }

        public CharacterItem setName(String name) {
            this.name = name;
            return this;
        }

        public String getUuid() {
            return uuid;
        }

        public CharacterItem setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Bitmap getPicture() {
            return picture;
        }

        public CharacterItem setPicture(Bitmap picture) {
            this.picture = picture;
            return this;
        }

        @Override
        public String toString() {
            return "HumansItem{" +
                    "itemType=" + itemType +
                    ", name='" + name + '\'' +
                    ", uuid='" + uuid + '\'' +
                    ", picture='" + picture + '\'' +
                    '}';
        }
    }
}
