package com.hancher.gamelife.bak.list;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.gamelife.R;

import org.greenrobot.greendao.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FieldFilterAdapter extends BaseMultiItemQuickAdapter<FieldFilterAdapter.FieldFilterItem, BaseViewHolder> {

    public FieldFilterAdapter(@Nullable List<FieldFilterItem> data) {
        super(data);
        addItemType(FieldFilterItem.TYPE_TEXT, R.layout.filter_text_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FieldFilterItem item) {
        switch (baseViewHolder.getItemViewType()){
            case FieldFilterItem.TYPE_TEXT:
                baseViewHolder.setText(R.id.field_name,item.getField());
                baseViewHolder.setText(R.id.field_value,item.getValue());
                ((EditText)baseViewHolder.findView(R.id.field_value)).addTextChangedListener(new TextWatcher() {
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

    public static class FieldFilterItem implements MultiItemEntity {
        public String field;
        public Property fieldProperty;
        public String value;
        public int type = TYPE_TEXT;
        public static final int TYPE_TEXT = 0;

        public FieldFilterItem(String field) {
            this.field = field;
        }

        public FieldFilterItem(String field, Property fieldProperty) {
            this.field = field;
            this.fieldProperty = fieldProperty;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Property getFieldProperty() {
            return fieldProperty;
        }

        public void setFieldProperty(Property fieldProperty) {
            this.fieldProperty = fieldProperty;
        }

        @Override
        public int getItemType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

    }
}
