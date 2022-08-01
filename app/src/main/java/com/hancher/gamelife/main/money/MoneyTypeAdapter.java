package com.hancher.gamelife.main.money;

import android.graphics.Bitmap;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.gamelife.R;

import org.jetbrains.annotations.NotNull;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/17 0017 21:26 <br/>
 * 描述 : 点击打卡界面 列表adapter
 */
public class MoneyTypeAdapter extends BaseQuickAdapter<Bitmap, BaseViewHolder> {

    public MoneyTypeAdapter() {
        super(R.layout.colock_in_type_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Bitmap bitmap) {
        baseViewHolder.setImageBitmap(R.id.item_image, bitmap);
    }

}
