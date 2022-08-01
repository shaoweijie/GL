package com.hancher.gamelife.bak.list;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.java.UuidUtil;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CharacterExtAdapter extends BaseMultiItemQuickAdapter<CharacterExtAdapter.CharacterExtItem, BaseViewHolder> {

    public static final int EXT = 0;
    private final Activity activity;

    public CharacterExtAdapter(@Nullable List<CharacterExtItem> data, Activity activity) {
        super(data);
        addItemType(EXT, R.layout.key_value_item);
        this.activity = activity;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CharacterExtItem item) {
        LogUtil.d("item="+item);
        switch (baseViewHolder.getItemViewType()) {
            case EXT:
                baseViewHolder.setText(R.id.ext_key, item.getKey());
                baseViewHolder.setText(R.id.ext_value, item.getValue());
                EditText extKey = ((EditText) baseViewHolder.findView(R.id.ext_key));
                extKey.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        item.setKey(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                EditText extValue = ((EditText) baseViewHolder.findView(R.id.ext_value));
                extValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        item.setValue(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
        }
    }

    public static class CharacterExtItem implements MultiItemEntity{
        String uuid = UuidUtil.getUuidNoLine();
        String key = "";
        String value = "";

        public String getUuid() {
            return uuid;
        }

        public CharacterExtItem setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public String getKey() {
            return key;
        }

        public CharacterExtItem setKey(String key) {
            this.key = key;
            return this;
        }

        public String getValue() {
            return value;
        }

        public CharacterExtItem setValue(String value) {
            this.value = value;
            return this;
        }

        @Override
        public int getItemType() {
            return EXT;
        }

        @Override
        public String toString() {
            return "CharacterExtItem{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
