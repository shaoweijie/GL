package com.hancher.gamelife.main.money;

import android.graphics.Bitmap;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.common.utils.android.ImageUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.greendao.ColockInType;

import org.jetbrains.annotations.NotNull;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/17 0017 21:26 <br/>
 * 描述 : 点击打卡界面 列表adapter
 */
public class MoneyAdapter extends BaseQuickAdapter<ColockInType, BaseViewHolder> {

    public MoneyAdapter() {
        super(R.layout.colock_in_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ColockInType colockInItem) {
        baseViewHolder.setText(R.id.item_title, colockInItem.getTitle());
        baseViewHolder.setText(R.id.item_count, String.format("共打卡 %s 次", colockInItem.getCount()));
        try {
            Bitmap bitmap = ImageUtil.string2Bitmap(colockInItem.getImage());
            baseViewHolder.setImageBitmap(R.id.item_image, bitmap);
        } catch (Exception e){
            LogUtil.w("字符串转图片失败，无法为列表中添加图片, title="+colockInItem.getTitle());
            baseViewHolder.setImageResource(R.id.item_image, R.drawable.ic_action_action_search);
        }

    }

}
