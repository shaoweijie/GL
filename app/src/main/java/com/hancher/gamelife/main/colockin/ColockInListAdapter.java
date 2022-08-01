package com.hancher.gamelife.main.colockin;

import android.graphics.Bitmap;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;
import com.hancher.common.utils.android.ImageUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.greendao.ColockIn;
import com.hancher.gamelife.greendao.ColockInType;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/17 0017 21:26 <br/>
 * 描述 : 点击打卡界面 列表adapter
 */
public class ColockInListAdapter extends BaseQuickAdapter<ColockIn, BaseViewHolder> implements LoadMoreModule {

    private Map<String, ColockInType> typeMap = new HashMap<>();

    public ColockInListAdapter() {
        super(R.layout.colock_in_list_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ColockIn colockIn) {
        try {
            String title = typeMap.get(colockIn.getTypeUuid()).getTitle();
            if (!TextUtil.isEmpty(colockIn.getDescription())) {
                title = title + " : " + colockIn.getDescription();
            }
            baseViewHolder.setText(R.id.item_title, title);
        } catch (Exception e) {

        }
        try {
            String time = DateUtil.changeDate(colockIn.getCreateTime(), DateUtil.Type.LONG_STAMP,
                    DateUtil.Type.STRING_YMD_HMS);
            time += " " + DateUtil.changeDate(colockIn.getCreateTime(), DateUtil.Type.LONG_STAMP,
                    DateUtil.Type.STRING_WEEK);
            baseViewHolder.setText(R.id.item_time, time);
        } catch (Exception exception) {
            LogUtil.w("显示列表时间失败");
        }
        try {
            String image = typeMap.get(colockIn.getTypeUuid()).getImage();
            Bitmap bitmap = ImageUtil.string2Bitmap(image);
            baseViewHolder.setImageBitmap(R.id.item_image, bitmap);
        } catch (Exception exception) {
            LogUtil.w("显示列表图片失败:", exception.getMessage());
        }
        SwipeLayout swipeLayout = baseViewHolder.getView(R.id.swipe_layout);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
    }


    public void setType(List<ColockInType> colockInItems) {
        for (ColockInType item : colockInItems) {
            typeMap.put(item.getUuid(), item);
        }
    }
}
