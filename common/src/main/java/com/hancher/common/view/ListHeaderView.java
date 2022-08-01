package com.hancher.common.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hancher.common.R;
import com.hancher.common.utils.java.DateUtil;

import java.util.ArrayList;

/**
 * 作者：Hancher
 * 时间：2020/2/8.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：
 */
public class ListHeaderView extends RelativeLayout implements View.OnClickListener {
    private final OnHeaderChangeListener mListener;
    private final Context mContext;
    private View left;
    private View right;
    private TextView filiterMonth;
    private EditText search;
    private View btnSerarch;
    private Long startTime;
    private Long endTime;
    private TextView tag;

    public ListHeaderView(Context context, OnHeaderChangeListener listener) {
        super(context);
        mListener = listener;
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.recyclerview_header_filiter,this,true);
        left = findViewById(R.id.btn_left);
        right = findViewById(R.id.btn_right);
        filiterMonth = findViewById(R.id.filiter_month);
        search = findViewById(R.id.search);
        btnSerarch = findViewById(R.id.btn_search);
        tag = findViewById(R.id.tag);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        btnSerarch.setOnClickListener(this);
        tag.setOnClickListener(this);

        startTime = DateUtil.getNow(DateUtil.Type.LONG_FIRST_OF_MONTH);
        endTime = DateUtil.getNow(DateUtil.Type.LONG_FIRST_OF_NEXT_MONTH);
        changeMonthText(startTime);

        mListener.onHeaderMonthChange(startTime,endTime);
    }

    @Override
    public void onClick(View v) {
        if (v == left){
            endTime = startTime;
            Long time = startTime-1;
            startTime = DateUtil.changeDate(time, DateUtil.Type.LONG_STAMP,
                    DateUtil.Type.LONG_FIRST_OF_MONTH);
            changeMonthText(startTime);
            mListener.onHeaderMonthChange(startTime,endTime);
        } else if (v == right){
            startTime = endTime;
            endTime = DateUtil.changeDate(endTime, DateUtil.Type.LONG_STAMP,
                    DateUtil.Type.LONG_FIRST_OF_NEXT_MONTH);
            changeMonthText(startTime);
            mListener.onHeaderMonthChange(startTime,endTime);
        } else if (v == btnSerarch){
            tag.setText("过滤全部标签");
            if (!TextUtils.isEmpty(search.getText())) {
                mListener.onHeaderSearchChange(search.getText().toString());
            }
        } else if (v == tag){
            search.setText(null);
            ArrayList<String> tags = mListener.onHeaderGetTags();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setItems(tags.toArray(new String[tags.size()]),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mListener.onHeaderTagChange(tags.get(which));
                            tag.setText(tags.get(which));
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    }
    public void changeMonthText(Long month){
        filiterMonth.setText(DateUtil.changeDate(month, DateUtil.Type.LONG_STAMP,
                DateUtil.Type.STRING_YM));
    }

    public void clear() {
        tag.setText("过滤全部标签");
        startTime = DateUtil.getNow(DateUtil.Type.LONG_FIRST_OF_MONTH);
        endTime = DateUtil.getNow(DateUtil.Type.LONG_FIRST_OF_NEXT_MONTH);
        changeMonthText(startTime);
    }

    public interface OnHeaderChangeListener{
        void onHeaderMonthChange(Long start, Long end);
        void onHeaderTagChange(String tag);
        void onHeaderSearchChange(String search);
        ArrayList<String> onHeaderGetTags();
    }
}
